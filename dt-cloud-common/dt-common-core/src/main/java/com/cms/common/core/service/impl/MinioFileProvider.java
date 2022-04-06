package com.cms.common.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cms.common.core.service.FileProvider;
import com.cms.common.tool.constant.ConstantCode;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.messages.Bucket;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            String fileName = file.getOriginalFilename();
            if (null != fileName) {
                String objectName = IdUtil.simpleUUID()+fileName.substring(fileName.lastIndexOf("."));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                // 文件存储的目录结构
                objectName = sdf.format(new Date()) + "/" + objectName;
                bucketName = bucketName != null ? bucketName : BUCKET;
                PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
                minioClient.putObject(putObjectArgs);
                // 生成HTTP地址
                return presignedGetObject(bucketName, objectName);
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
    public String presignedGetObject(String bucketName, String objectName) {
        String presignedObjectUrl = null;
        try {
            GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(EXPIRES_TIME_SEC).build();
            presignedObjectUrl = minioClient.getPresignedObjectUrl(objectUrlArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取HTTP文件预览地址异常:"+e.fillInStackTrace());
        }
        return presignedObjectUrl;
    }

}
