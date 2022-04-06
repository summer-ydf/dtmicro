package com.cms.common.core.service;

import com.cms.common.core.service.impl.MinioFileProvider;
import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author ydf Created by 2022/4/6 15:45
 */
public interface FileProvider {

    static FileProvider create(String url,String accessKey,String secretKey) {
        return new MinioFileProvider(url,accessKey,secretKey);
    }

    void makeBucket(String bucketName);

    List<Bucket> listBuckets();

    void removeBucket(String bucketName);

    String putObject(MultipartFile file, String bucketName);

    void putObject(String bucketName, String objectName, String fileName);

    void removeObject(String bucketName, String objectName);

    String presignedGetObject(String bucketName, String objectName);
}
