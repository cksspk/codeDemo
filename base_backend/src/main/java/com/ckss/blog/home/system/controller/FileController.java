package com.ckss.blog.home.system.controller;

import com.ckss.blog.common.web.vo.Result;
import com.ckss.blog.home.system.service.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @className: FileController
 * @description: 文件上传
 * @author: cksspk
 * @date: 2020/4/2
 **/

@RestController
@RequestMapping()
public class FileController {


    @Autowired
    private FileService fileService;


    /**
     * 文件上传     -上传图片到aliyunOSS
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload/alioss")
    public Result uploadAliOss(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        String uploadUrl = fileService.uploadAliOss(file);
        //返回r对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);

    }


    /**
     * 文件上传     -上传图片到fdfs
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload/fdfs")
    public Result uploadFDfs(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {

        String uploadUrl = fileService.uploadFDfs(file);
        //返回r对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
