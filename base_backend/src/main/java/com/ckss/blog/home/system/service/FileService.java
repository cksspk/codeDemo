package com.ckss.blog.home.system.service;


import org.springframework.web.multipart.MultipartFile;


/**
 * @className: FileService
 * @description: 文件上传服务
 * @author: cksspk
 * @date: 2020/4/2
 **/
public interface FileService {
    /**
     * 上传文件  -AliyunOss
     * @param file
     * @return
     */
    String uploadAliOss(MultipartFile file);

    /**
     * 上传文件     -fDfs
     * @param file
     * @return
     */
    String uploadFDfs(MultipartFile file);
}
