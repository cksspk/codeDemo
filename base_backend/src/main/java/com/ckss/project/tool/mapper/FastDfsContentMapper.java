package com.ckss.project.tool.mapper;

import com.ckss.project.tool.domain.FastDfsContent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: FastDfsMapper
 * @description: FastDfs数据接口
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Component
public interface FastDfsContentMapper {

    /**
     * 存入数据
     * @param fastDfsContent fast参数
     * @return 是否成功
     */
    int insertContent(FastDfsContent fastDfsContent);

    /**
     * 分页查询fastdfs
     * @param fastDfsContent 参数
     * @return  list-FastDfsContent
     */
    List<FastDfsContent> selectContentList(FastDfsContent fastDfsContent);
}
