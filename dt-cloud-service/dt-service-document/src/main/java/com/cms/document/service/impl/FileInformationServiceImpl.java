package com.cms.document.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.document.entity.FileInformationEntity;
import com.cms.document.mapper.FileInformationMapper;
import com.cms.document.service.FileInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Objects;

/**
 * @author DT辰白 Created by 2022/4/7 10:28
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

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<IPage<FileInformationEntity>> pageSearch(SysSearchPage request) {
        Page<FileInformationEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<FileInformationEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    public void removeObject(String bucketName, String objectName) {
        this.baseMapper.removeObject(bucketName,objectName);
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
