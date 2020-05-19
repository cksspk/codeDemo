package com.ckss.blog.home.tool.service;

import com.ckss.blog.home.tool.domain.FastDfsContent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @className: FastDfsService
 * @description: FastDfs服务
 * @author: cksspk
 * @date: 2020/4/15
 **/

public interface FastDfsService {
    /**
     * 上传文件到fastdfs
     * @param file 文件
     */
    FastDfsContent upload(MultipartFile file);

    /**
     * 删除fastdfs上的文件
     * @param url
     */
    void delFile(String url);

    /**
     * 分页查询fastdfs图片地址
     * @param fastDfsContent
     * @return
     */
    List<FastDfsContent> selectContentList(FastDfsContent fastDfsContent);

    /**
     * 删除文件
     * @param ids
     * @return
     */
    int delFileByIds(String ids);
}
