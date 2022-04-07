package com.cms.document.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.document.entity.FileInformationEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ydf Created by 2022/4/7 10:28
 */
public interface FileInformationService extends IService<FileInformationEntity> {

    void saveFile(MultipartFile file,String bucket,String url);
}
