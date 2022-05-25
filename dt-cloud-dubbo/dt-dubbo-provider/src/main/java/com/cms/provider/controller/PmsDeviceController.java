package com.cms.provider.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.provider.entity.DeviceEntity;
import com.cms.provider.service.PmsDeviceService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DT辰白 Created by 2022/5/17 17:12
 */
@Api(tags = "设备管理API")
@RestController
@RequestMapping("/pms")
public class PmsDeviceController {

    private final PmsDeviceService pmsDeviceService;

    public PmsDeviceController(PmsDeviceService pmsDeviceService) {
        this.pmsDeviceService = pmsDeviceService;
    }

    @GetMapping(value = "/list")
    public ResultUtil<?> list() {
        List<DeviceEntity> entities = pmsDeviceService.list();
        return ResultUtil.success(entities);
    }
}
