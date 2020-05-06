package com.cks.demo.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.cks.demo.common.OssExpireTimeConstant;
import com.cks.demo.config.AliOssConfig;
import com.cks.demo.service.AliOssService;
import com.cks.demo.utils.OssBackCheck;
import com.cks.demo.vo.AliOssPolicy;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * @className: AliOssServiceImpl
 * @description: AliOssService服务实现.
 * @author: cksspk
 * @date: 2020/4/22
 **/

@Service
public class AliOssServiceImpl implements AliOssService {


    @Autowired
    private OSSClient client;

    @Autowired
    private OssBackCheck ossBackCheck;

    @Override
    public AliOssPolicy policy() {

        // 直传有效截止时间
        long expireEndTime = System.currentTimeMillis() + (AliOssConfig.EXPIRE_TIME * 1000);
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConditions = new PolicyConditions();
        // 设置可上传文件的大小
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, AliOssConfig.MIN, AliOssConfig.MAX);
        // 设置上传文件的前缀、可忽略
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, AliOssConfig.DIR);
        // 生成policy
        String postPolicy = client.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);

        // 封装policy等信息
        AliOssPolicy aliOssPolicy = new AliOssPolicy();
        aliOssPolicy.setOssAccessKeyId(AliOssConfig.ACCESS_ID);
        aliOssPolicy.setPolicy(encodedPolicy);
        aliOssPolicy.setSignature(postSignature);
        aliOssPolicy.setDir(AliOssConfig.DIR);
        aliOssPolicy.setHost(AliOssConfig.HOST);
        aliOssPolicy.setExpire(String.valueOf(expireEndTime / 1000));

        return aliOssPolicy;
    }

    @Override
    public AliOssPolicy policyCallback(String fileType) {

        //1. 生成签名
        //  1.1 根据业务场景设置过期时间
//        long expireEndTime = System.currentTimeMillis() + (AliOssConfig.EXPIRE_TIME * 1000);
        long expireEndTime = getExpireTime(fileType);

        System.out.println("过期时间："+ expireEndTime);


        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConditions = new PolicyConditions();
        //  1.2设置可上传文件的大小
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, AliOssConfig.MIN, AliOssConfig.MAX);
        //  1.3设置上传文件的前缀，AliOssConfig.DIR 可针对不同业务设置比不同前缀，根据日期不同设置文件夹
        // 构建日期路径：AliOssConfig.DIR/2019/02/26/文件名
        //方式1.3.1后台生成文件路径
        String filePath = AliOssConfig.DIR + new DateTime().toString("yyyy/MM/dd") +"/";

        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, filePath);

        // 生成policy
        String postPolicy = client.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);


        //2. callBack 信息 通过JSONObject生成
        JSONObject jasonCallback = new JSONObject();
        jasonCallback.put("callbackUrl", "http://"+AliOssConfig.CALLBACK_HOST+AliOssConfig.CALLBACK_INTERFACE);
        jasonCallback.put("callbackHost", AliOssConfig.CALLBACK_HOST);
        //返回数据中应该包含某些数据 TODO
        jasonCallback.put("callbackBody",
                "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
//        jasonCallback.put("callbackBody",
//                "filename=123");

        jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
        String callBack = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());


        //3. 封装policy等信息
        AliOssPolicy aliOssPolicy = new AliOssPolicy();
        aliOssPolicy.setOssAccessKeyId(AliOssConfig.ACCESS_ID);
        aliOssPolicy.setPolicy(encodedPolicy);
        aliOssPolicy.setSignature(postSignature);
        aliOssPolicy.setDir(filePath);
        aliOssPolicy.setHost(AliOssConfig.HOST);
        aliOssPolicy.setExpire(String.valueOf(expireEndTime / 1000));
//        aliOssPolicy.setExpire(String.valueOf(expireEndTime));
        aliOssPolicy.setCallback(callBack);

        return aliOssPolicy;
    }

    /**
     * 签名认证：阿里云内网地址是否需要进行验证操作？
     */
    @Override
    public void callback(String ossCallbackBody, String authorization, String publicKeyUrlBase64, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("回调函数"+ossCallbackBody);
        //1. 回调函数设置对签名行验证
        boolean isCallBack = ossBackCheck.verifyOSSCallbackRequest(authorization, publicKeyUrlBase64, ossCallbackBody, request.getQueryString(), request.getRequestURI());
        if (isCallBack) {
            response.setStatus(HttpServletResponse.SC_OK);
            //2. 解析ossCallbackBody参数
             String filename = request.getParameter("filename");
            //        Object parse = JSON.parse(ossCallbackBody);
            System.out.println(filename);

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    /**
     * 根据参数设置过期时间
     * @return
     */
    private Long getExpireTime(String fileType){
        if(OssExpireTimeConstant.EXPIRE_TYPE_SORT.equals(fileType)){
            //设置过期时间为3s
            return System.currentTimeMillis() + (3L * 1000);
        }else if (OssExpireTimeConstant.EXPIRE_TYPE_LONG.equals(fileType)){
            //设置过期时间为30s
//            System.out.println("过期时间30s");
            return System.currentTimeMillis() + (30L * 1000);
        }
        //默认3s
        return System.currentTimeMillis() + (3L * 1000);

    }
}
