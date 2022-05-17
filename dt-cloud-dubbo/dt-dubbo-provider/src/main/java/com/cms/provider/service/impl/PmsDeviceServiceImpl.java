package com.cms.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.provider.entity.DeviceEntity;
import com.cms.provider.mapper.PmsDeviceMapper;
import com.cms.provider.service.PmsDeviceService;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2022/5/17 17:13
 */
@Service
public class PmsDeviceServiceImpl extends ServiceImpl<PmsDeviceMapper, DeviceEntity> implements PmsDeviceService {
}
