package com.ckss.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: AliOssCallBackDemo
 * @description: AliOss回调设置
 * @author: cksspk
 * @date: 2020/4/19
 **/

@RestController
@RequestMapping("api/upload")
public class AliOssCallBackDemoController {

    @RequestMapping
    public String test(HttpServletRequest request){

        System.out.println(request.getPathInfo());
        return "hi ";

    }



    @RequestMapping("/aliyun/oss/callback")
    public void callDemo(HttpServletRequest request, String ossCallbackBody, HttpServletResponse response) throws IOException {

        String results = "{\"Status\":\"OK\"}";
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(200);
        response.flushBuffer();

        System.out.println("ossCallbackBody"+ossCallbackBody);
        System.out.println("oss callback");

    }

}
