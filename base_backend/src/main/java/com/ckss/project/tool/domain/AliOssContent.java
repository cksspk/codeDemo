package com.ckss.project.tool.domain;

import com.ckss.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className: AliOssContent
 * @description: 对应文件上传到阿里oss之后保存在数据库中
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliOssContent extends BaseEntity implements Serializable {

    private Long id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 空间名
     */
    private String bucket;
    /**
     * 大小,eg:5kb
     */
    private String size;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 空间类型
     */
    private String type = "公开";
}
