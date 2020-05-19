package com.ckss.blog.home.tool.domain;

import com.ckss.blog.common.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: FastDfsContent
 * @description: 存放在FastDfs实体
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Data
public class FastDfsContent extends BaseEntity implements Serializable {

    private Long id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 分组名
     */
    private String group;
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


}
