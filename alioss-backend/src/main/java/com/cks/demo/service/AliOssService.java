package com.cks.demo.service;

import com.cks.demo.vo.AliOssPolicy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: AliOssService
 * @description: AliOssService接口.
 * @author: cksspk
 * @date: 2020/4/22
 **/

public interface AliOssService {
    /**
     * 获取签名 -不包含回调
     * @return
     */
    AliOssPolicy policy();

    /**
     * 获取签名 -包含回调地址
     * @return
     */
    AliOssPolicy policyCallback(String fileType);


    /**
     * oss上传回调
     * @param ossCallbackBody
     * @param authorization
     * @param publicKeyUrlBase64
     * @param request
     * @param response
     */
    void callback(String ossCallbackBody, String authorization, String publicKeyUrlBase64, HttpServletRequest request, HttpServletResponse response);

}
