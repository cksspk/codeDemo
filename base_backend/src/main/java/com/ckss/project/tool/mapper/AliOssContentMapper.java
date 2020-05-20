package com.ckss.project.tool.mapper;

import com.ckss.project.tool.domain.AliOssContent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: AliOssMapper
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Component
public interface AliOssContentMapper {

    /**
     * 分页查询aliOss图片list
     * @param aliOssContent 查询条件
     * @return list-AliOssContent
     */
    List<AliOssContent> selectContentList(AliOssContent aliOssContent);


    /**
     * 插入数据
     *
     * @param qiNiuContent 数据实体
     * @return 受影响的行数
     */
    int insertContent(AliOssContent qiNiuContent);
}
