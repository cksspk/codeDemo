package com.cks.demo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: AliOssPolicy
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/20
 **/

@Data
public class AliOssPolicy implements Serializable {
    /**
     * 上传认证id
     */
    private String ossAccessKeyId;
    /**
     * policy
     */
    private String policy;
    /**
     * 签名
     */
    private String signature;
    /**
     * 直传文件的开头（路径）
     */
    private String dir;
    /**
     * 直传地址
     */
    private String host;
    /**
     * 上传截止时间
     */
    private String expire;

    /**
     * 上传回调
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String callback;

}

