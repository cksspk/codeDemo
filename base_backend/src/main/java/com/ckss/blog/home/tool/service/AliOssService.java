package com.ckss.blog.home.tool.service;

import com.ckss.blog.home.tool.domain.AliOssContent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @className: AliOssService
 * @description: AliOss服务接口
 * @author: cksspk
 * @date: 2020/4/15
 **/

public interface AliOssService {

    /**
     * 分页查询aliOss图片集合
     * @param aliOssContent 查询条件
     * @return  分页集合
     */
    List<AliOssContent> selectContentList(AliOssContent aliOssContent);


    /**
     * 上传图片到AliOss
     * @param file 图片
     * @param host 类型host
     * @return 图片信息
     */
    AliOssContent upload(MultipartFile file, String host);
}
