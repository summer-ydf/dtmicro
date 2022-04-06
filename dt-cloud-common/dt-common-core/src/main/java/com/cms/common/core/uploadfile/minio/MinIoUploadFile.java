package com.cms.common.core.uploadfile.minio;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.cms.common.tool.constant.ConstantCode;
import io.minio.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Minio文件服务
 * @author ydf Created by 2021/6/24 10:14
 */
@CommonsLog
@PropertySource(value = {"classpath:application.yml"})
public class MinIoUploadFile {

    private final Environment environment;

    private static MinioClient minioClient;

    private static final int EXPIRES_TIME_SEC = 7200;

    public MinIoUploadFile(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(Objects.requireNonNull(environment.getProperty("minio.client.url")))
                    .credentials(Objects.requireNonNull(environment.getProperty("minio.accessKey")), Objects.requireNonNull(environment.getProperty("minio.secretKey")))
                    .build();
            Boolean isExist = bucketIsExist(ConstantCode.MINIO_COMMON_BUCKET_NAME);
            if(!isExist) {
                createBucket(ConstantCode.MINIO_COMMON_BUCKET_NAME);
            }
            log.info("初始化Minio文件服务器============================");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化Minio配置异常: 【{}】", e.fillInStackTrace());
        }
    }

    /**
     * 查看存储bucket是否存在
     * @param bucketName 存储bucket名称
     * @return true/false
     */
    public Boolean bucketIsExist(String bucketName) {
        boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     * @param bucketName 存储bucket名称
     * @return true/false
     */
    public Boolean createBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     * @param bucketName 存储bucket名称
     * @return true/false
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 文件上传
     * @param file 文件
     * @param bucketName 存储bucket名称
     * @return true/false
     */
    public String uploadFile(MultipartFile file, String bucketName) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String objectName = IdUtil.simpleUUID()+fileName.substring(fileName.lastIndexOf("."));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 文件存储的目录结构
            objectName = sdf.format(new Date()) + "/" + objectName;
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName != null ? bucketName : ConstantCode.MINIO_COMMON_BUCKET_NAME).object(objectName)
                    .stream(file.getInputStream(),file.getSize(),-1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
            return objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件下载
     * @param bucketName 存储bucket名称
     * @param fileName 文件名称
     * @param res response
     */
    public void downloadFile(String bucketName, String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName).object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf)) != -1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                //设置强制下载不打开
                res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param fileName 文件名称
     * @param bucketName 存储bucket名称
     * @return  true/false
     */
    public Boolean delFile(String bucketName, String fileName){
        try {
            RemoveObjectArgs objectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build();
            minioClient.removeObject(objectArgs);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String presignedGetHttpObject(String bucketName, String objectName){
        String presignedObjectUrl = null;
        try {
            GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(EXPIRES_TIME_SEC).build();
            minioClient.getPresignedObjectUrl(objectUrlArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("presignedGetObject error:"+e.getMessage());
        }
        return presignedObjectUrl;
    }
}
