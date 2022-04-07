package com.cms.common.core.service;

import com.cms.common.core.service.impl.MinioFileProvider;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
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

    List<String> listBucketNames();

    void removeBucket(String bucketName);

    String putObject(MultipartFile file, String bucketName);

    void putObject(String bucketName, String objectName, String fileName);

    void removeObject(String bucketName, String objectName);

    Iterable<Result<Item>> listObjects(String bucketName);

    String presignedGetHttpObject(String bucketName, String objectName);

    String presignedGetChainObject(String fileId);
}
