package com.cms.common.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cms.common.core.service.FileProvider;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ydf Created by 2022/4/6 15:44
 */
@CommonsLog
public class MinioFileProvider implements FileProvider {

    private MinioClient minioClient;
    private static final String BUCKET = "default";
    private static final int EXPIRES_TIME_SEC = 60;

    public MinioFileProvider(String url, String accessKey, String secretKey) {
        try {
            this.minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build());
            if(!isExist){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET).build());
            }
            log.info("初始化Minio文件服务器============================");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化Minio配置异常: 【{}】", e.fillInStackTrace());
        }
    }

    @Override
    public void makeBucket(String bucketName) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("创建桶异常：{}",e.fillInStackTrace());
        }
    }

    @Override
    public List<String> listBucketNames() {
        // 列出所有存储桶
        List<Bucket> bucketList = getBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    @Override
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("删除桶异常：{}",e.fillInStackTrace());
        }
    }

    @Override
    public String putObject(MultipartFile file, String bucketName) {
        try {
            String fileName = file.getOriginalFilename();
            if (null != fileName) {
                // 文件存储的目录结构
                String objectName = IdUtil.simpleUUID()+fileName.substring(fileName.lastIndexOf("."));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                objectName = sdf.format(new Date()) + "/" + objectName;
                bucketName = bucketName != null ? bucketName : BUCKET;
                // 保存文件
                minioClient.putObject(PutObjectArgs.builder()
                        .stream(file.getInputStream(), file.getSize(), PutObjectArgs.MIN_MULTIPART_SIZE)
                        .object(objectName)
                        .contentType(file.getContentType())
                        .bucket(bucketName)
                        .build());
                // 生成HTTP地址
                return presignedGetHttpObject(bucketName, objectName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传文件异常：{}",e.fillInStackTrace());
        }
        return null;
    }

    @Override
    public void putObject(String bucketName, String objectName, String fileName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket(bucketName).object(objectName).filename(fileName).build();
            minioClient.uploadObject(uploadObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传本地文件异常：{}",e.fillInStackTrace());
        }
    }

    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("删除文件异常：{}",e.fillInStackTrace());
        }
    }

    @Override
    public Iterable<Result<Item>> listObjects(String bucketName) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .recursive(true)
                .build());
    }

    @Override
    public String presignedGetHttpObject(String bucketName, String objectName) {
        String presignedObjectUrl = null;
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .method(Method.GET)
                    .expiry(EXPIRES_TIME_SEC).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取HTTP文件预览地址异常:"+e.fillInStackTrace());
        }
        return presignedObjectUrl;
    }

    @Override
    public String presignedGetChainObject(String fileId) {
        //校验文件是否为空
        if (StringUtils.isEmpty(fileId) && !fileId.startsWith("http://") && !fileId.startsWith("https://")){
            return fileId;
        }
        URL url = null;
        try{
            url = new URL(fileId);
        }catch (Exception e){
            log.error("不合法的URL："+e.getMessage());
            return fileId;
        }
        String path = url.getPath();
        String[] split = path.split("/");
        String bucketName = split[1];
        Map<String, String> params = parseURLParam(fileId);
        if(!expire(params)){
            return fileId;
        }
        String bucketNamePre = "/" + bucketName;
        String objectName = path.substring(bucketNamePre.length() + 1);
        return presignedGetHttpObject(bucketName,objectName);
    }

    @Override
    public String shareGetHttpObject(String bucketName, String objectName, int exp) {
        String presignedObjectUrl = null;
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .method(Method.GET)
                    .expiry(exp).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取HTTP文件预览地址异常:"+e.fillInStackTrace());
        }
        return presignedObjectUrl;
    }

    @Override
    public void downloadFile(String bucketName, String objectName, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int totalSize = 0;
        try {
            // 查询对象信息
            StatObjectResponse statObject = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            if (statObject != null && statObject.size() > 0) {
                // 获取文件流
                inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
                // 获取文件大小
                // 此输入流在不受阻塞情况下能读取的字节数,总是0，不能用次函数：inputStream.available()
                totalSize = Math.toIntExact(statObject.size());
                log.info("Minio当前下载文件大小:"+ totalSize / 1024 + "KB");
                // 输出文件到浏览器
                byte[] bf = new byte[1024];
                int length;
                response.reset();
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Length", String.valueOf(totalSize));
                response.setCharacterEncoding("UTF-8");
                outputStream = response.getOutputStream();
                // 输出文件
                while ((length = inputStream.read(bf)) > 0) {
                    outputStream.write(bf, 0, length);
                }
            }else {
                log.info("文件信息不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("下载文件异常：{}",e.fillInStackTrace());
        } finally {
            // 关闭输入流
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 关闭输出流
            assert outputStream != null;
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Bucket> getBuckets() {
        List<Bucket> bucketList = new ArrayList<>();
        try {
            bucketList = minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("列出所有存储桶异常：{}",e.fillInStackTrace());
        }
        return bucketList;
    }

    private boolean expire(Map<String, String> params){
        //获取链接有效时长 默认两小时
        String date = params.get("X-Amz-Date");
        if(StringUtils.isNotBlank(date)){
            Date exp = null;
            try {
                exp = DateUtils.parseDate(date.replaceAll("T", "").replaceAll("Z", ""), new String[]{"yyyyMMddHHmmss"});
            } catch (ParseException e) {
                e.printStackTrace();
                log.info("DateUtils parseDate is error:"+e.getMessage());
            }
            if(exp != null){
                //exp解析时间差8个小时
                exp = DateUtils.addSeconds(exp, 8 * 60 * 60 + EXPIRES_TIME_SEC);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(exp);
                log.info("过期时间："+format);
                // 当exp < date时 返回true
                return exp.before(new Date());
            }
        }
        return true;
    }

    private Map<String, String> parseURLParam(String url) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (StringUtils.isNotBlank(arrSplitEqual[0])) {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    private String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }
}
