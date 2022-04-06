package com.cms.common.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cms.common.core.service.FileProvider;
import com.cms.common.tool.constant.ConstantCode;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ydf Created by 2022/4/6 15:44
 */
@CommonsLog
public class MinioFileProvider implements FileProvider {

    private MinioClient minioClient;
    private static final String BUCKET = ConstantCode.MINIO_COMMON_BUCKET_NAME;
    private static final int EXPIRES_TIME_SEC = 7200;
    private static final String BUCKET_NAME_PRE = "/" + BUCKET;
    private static final int BUCKET_NAME_PRE_LEN = BUCKET_NAME_PRE.length();

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
    public List<Bucket> listBuckets() {
        // 列出所有存储桶
        List<Bucket> bucketList = new ArrayList<>();
        try {
            bucketList = minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("列出所有存储桶异常：{}",e.fillInStackTrace());
        }
        return bucketList;
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
//            String fileName = file.getOriginalFilename();
//            if (null != fileName) {
//                String objectName = IdUtil.simpleUUID()+fileName.substring(fileName.lastIndexOf("."));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                // 文件存储的目录结构
//                objectName = sdf.format(new Date()) + "/" + objectName;
//                bucketName = bucketName != null ? bucketName : BUCKET;
//                PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
//                        .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
//                minioClient.putObject(putObjectArgs);
//                System.out.println("上传======");
//                System.out.println(bucketName);
//                System.out.println(objectName);
//                // 生成HTTP地址
//                return presignedGetHttpObject(bucketName, objectName);
//            }
            int idx = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
            String suffix = file.getOriginalFilename().substring(idx + 1);
            String fileName = UUID.randomUUID() + "." + suffix;

            // 保存文件
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(file.getInputStream(), file.getSize(), PutObjectArgs.MIN_MULTIPART_SIZE)
                    .object(fileName)
                    .contentType(file.getContentType())
                    .bucket(BUCKET)
                    .build());
            return fileName;
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
    public String presignedGetHttpObject(String bucketName, String objectName) {
        String presignedObjectUrl = null;
        try {
//            GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder()
//                    .bucket(bucketName != null ? bucketName : BUCKET).object(objectName).expiry(EXPIRES_TIME_SEC).method(Method.POST).build();
//            presignedObjectUrl = minioClient.getPresignedObjectUrl(objectUrlArgs);
            presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(BUCKET)
                    .object(objectName).
                            method(Method.GET)
                    .expiry(7, TimeUnit.DAYS).build());
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
        Map<String, String> params = parseURLParam(fileId);
        if(!expire(params)){
            return fileId;
        }
        String objectName = path.substring(BUCKET_NAME_PRE_LEN + 1);
        return presignedGetHttpObject(null,objectName);
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
                System.out.println("过期时间："+format);
                // 当exp < date时 返回true
                return exp.before(new Date());
            }
        }
        return true;
    }

    public static Map<String, String> parseURLParam(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (StringUtils.isNotBlank(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    private static String TruncateUrlPage(String strURL) {
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
