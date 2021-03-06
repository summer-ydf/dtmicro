package com.cms.common.core.service;

import com.cms.common.core.service.impl.MinioFileProvider;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author DT辰白 Created by 2022/4/6 15:45
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

    String shareGetHttpObject(String bucketName, String objectName, int exp);

    void downloadFile(String bucketName, String objectName, HttpServletResponse response);
}
