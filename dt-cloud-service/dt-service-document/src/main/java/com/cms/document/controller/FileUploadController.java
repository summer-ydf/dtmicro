package com.cms.document.controller;

import com.cms.common.core.service.FileProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ydf Created by 2022/4/7 9:30
 */
@Api(tags = "文件系统API")
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final FileProvider fileProvider;

    public FileUploadController(FileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件",name = "file",required = true,allowMultiple = true,dataType = "MultipartFile")
    })
    @PostMapping(value = "/uploadFile",headers = "content-type=multipart/form-data")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return fileProvider.putObject(file, null);
    }

    @ApiOperation(value = "获取文件HTTP地址")
    @GetMapping(value = "/getUrl")
    public String getUrl(@RequestParam String objectName) {
        return fileProvider.presignedGetHttpObject(null,objectName);
    }

    @ApiOperation(value = "生成新的HTTP地址")
    @GetMapping(value = "/getHttpUrl")
    public String getHttpUrl(@RequestParam String fileId) {
        return fileProvider.presignedGetChainObject(fileId);
    }

}
