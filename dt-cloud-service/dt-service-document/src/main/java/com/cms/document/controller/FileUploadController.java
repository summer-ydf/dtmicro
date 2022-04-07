package com.cms.document.controller;

import com.cms.common.core.service.FileProvider;
import com.cms.common.tool.result.ResultUtil;
import com.cms.document.service.FileInformationService;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Iterator;
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
    private Result<Item> result;

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

    @ApiOperation(value = "获取所有存储桶")
    @GetMapping(value = "/listBucketNames")
    public ResultUtil<List<String>> listBucketNames() {
        return ResultUtil.success(fileProvider.listBucketNames());
    }

    @ApiOperation(value = "获取所有存储桶")
    @GetMapping(value = "/listObjects/{bucket}")
    public ResultUtil<?> listObjects(@PathVariable String bucket) {
        Iterable<Result<Item>> results = fileProvider.listObjects(bucket);
        Iterator<Result<Item>> iter = results.iterator();
        int i = 0;
        while(iter.hasNext()) {
            i = i + 1;
            Result<Item> result = iter.next();
            try {
                Item item = result.get();
                System.out.println("存储文件详情=================文件详情："+i);
                System.out.println("etag:" + item.etag());
                System.out.println("objectName:" + item.objectName());
                System.out.println("isDir:" + item.isDir());
                System.out.println("lastModified:" + item.lastModified());
                System.out.println("owner:" + item.owner().displayName());
                System.out.println("size:" + item.size());
                System.out.println("userMetadata:" + item.userMetadata());
                System.out.println("storageClass:" + item.storageClass());
                System.out.println("item to String:" + item.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.success();
    }

}
