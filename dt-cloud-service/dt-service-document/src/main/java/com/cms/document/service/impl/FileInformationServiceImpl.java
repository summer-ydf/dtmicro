package com.cms.document.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.document.entity.FileInformationEntity;
import com.cms.document.mapper.FileInformationMapper;
import com.cms.document.service.FileInformationService;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2022/4/7 10:28
 */
@Service
public class FileInformationServiceImpl extends ServiceImpl<FileInformationMapper, FileInformationEntity> implements FileInformationService {
}
