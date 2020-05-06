package com.cks.demo.controller;


import com.cks.demo.service.AliOssService;
import com.cks.demo.vo.AliOssPolicy;
import com.cks.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

/**
 * @className: AliOssController
 * @description: 接口controller.
 * @author: cksspk
 * @date: 2020/4/22
 **/

@RequestMapping("api/aliOss")
@RestController
public class AliOssController {

    @Autowired
    private AliOssService aliOssService;

    /**
     * 生成签名 -不包含回调
     *
     * @return
     */
    @GetMapping("/policy")
    public Result policy(){
        AliOssPolicy aliOssPolicy = aliOssService.policy();
        return Result.ok().data("data",aliOssPolicy);
    }


    /**
     * 生成签名 -包含回调
     *
     * @return
     */
    @GetMapping("/policyCallback")
    public Result policyCallback(@RequestParam(value = "fileType", defaultValue = "1") String fileType){
//        System.out.println("FileType: "+fileType);
        AliOssPolicy aliOssPolicy = aliOssService.policyCallback(fileType);
        return Result.ok().data("data",aliOssPolicy);
    }


    /**
     *  OSS上传回调上, 超过5秒视为超时
     *
     * @Param [ossCallbackBody, authorization, publicKeyUrlBase64, request, response]
     */
    @RequestMapping("/callback")
    public Result callBack(@RequestBody String ossCallbackBody,
                           @RequestHeader("Authorization") String authorization,
                           @RequestHeader("x-oss-pub-key-url") String publicKeyUrlBase64,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        //返回文件信息，
        aliOssService.callback(ossCallbackBody, authorization, publicKeyUrlBase64, request, response);
        return Result.ok();
    }

}
