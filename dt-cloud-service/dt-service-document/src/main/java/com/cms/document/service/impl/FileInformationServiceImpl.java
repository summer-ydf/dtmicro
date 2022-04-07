package com.cms.document.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.document.entity.FileInformationEntity;
import com.cms.document.mapper.FileInformationMapper;
import com.cms.document.service.FileInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Objects;

/**
 * @author ydf Created by 2022/4/7 10:28
 */
@Service
public class FileInformationServiceImpl extends ServiceImpl<FileInformationMapper, FileInformationEntity> implements FileInformationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(MultipartFile file,String bucket,String url) {
        FileInformationEntity fileInformation = new FileInformationEntity();
        fileInformation.setFileUrl(url);
        fileInformation.setFilename(file.getOriginalFilename());
        fileInformation.setSize(file.getSize());
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        fileInformation.setSuffix(suffix);
        fileInformation.setBucket(bucket);
        fileInformation.setObjectName(getObjectName(url));
        this.baseMapper.insert(fileInformation);
    }

    private String getObjectName(String fileId) {
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
        String bucketNamePre = "/" + bucketName;
        return path.substring(bucketNamePre.length() + 1);
    }
}
