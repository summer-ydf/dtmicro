package com.cms.document.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.core.service.FileProvider;
import com.cms.common.tool.result.ResultUtil;
import com.cms.document.entity.FileInformationEntity;
import com.cms.document.service.FileInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ydf Created by 2022/4/7 9:30
 */
@Api(tags = "文件系统API")
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final FileInformationService fileInformationService;
    private final FileProvider fileProvider;

    public FileUploadController(FileProvider fileProvider, FileInformationService fileInformationService) {
        this.fileProvider = fileProvider;
        this.fileInformationService = fileInformationService;
    }

    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "bucket名称",name = "bucketName",required = false),
            @ApiImplicitParam(value = "文件",name = "file",required = true,allowMultiple = true,dataType = "MultipartFile")
    })
    @PostMapping(value = "/uploadFile",headers = "content-type=multipart/form-data")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam String bucketName) {
        String url = fileProvider.putObject(file, bucketName);
        // 保存上传文件信息
        fileInformationService.saveFile(file,bucketName,url);
        return url;
    }

    @ApiOperation(value = "获取所有存储桶")
    @GetMapping(value = "/listBucketNames")
    public ResultUtil<List<String>> listBucketNames() {
        return ResultUtil.success(fileProvider.listBucketNames());
    }

    @ApiOperation(value = "分页查询文件列表")
    @GetMapping("/page")
    public ResultUtil<IPage<FileInformationEntity>> page(SysSearchPage request) {
        return fileInformationService.pageSearch(request);
    }

    @ApiOperation(value = "获取文件HTTP地址")
    @GetMapping(value = "/getUrl")
    public ResultUtil<String> getUrl(@RequestParam String bucketName, @RequestParam String objectName) {
        return ResultUtil.success(fileProvider.presignedGetHttpObject(bucketName,objectName));
    }

    @ApiOperation(value = "查看文件")
    @GetMapping(value = "/getHttpUrl")
    public ResultUtil<String> getHttpUrl(@RequestParam String fileId) {
        return ResultUtil.success(fileProvider.presignedGetChainObject(fileId));
    }

    @ApiOperation(value = "分享文件")
    @GetMapping(value = "/share")
    public ResultUtil<String> share(@RequestParam String bucketName, @RequestParam String objectName, @RequestParam String type,@RequestParam int exp) {
        return ResultUtil.success(fileProvider.shareGetHttpObject(bucketName,objectName,type,exp));
    }

    @ApiOperation(value = "下载文件")
    @PostMapping("/downloadFile")
    public void downloadFile(@RequestBody FileInformationEntity information, HttpServletResponse response) {
        fileProvider.downloadFile(information.getBucket(), information.getObjectName(),response);
    }

    @ApiOperation(value = "删除文件")
    @PostMapping("/delFile")
    public ResultUtil<?> delFile(@RequestParam String bucketName, @RequestParam String objectName) {
        fileProvider.removeObject(bucketName, objectName);
        fileInformationService.removeObject(bucketName,objectName);
        return ResultUtil.success();
    }
}
