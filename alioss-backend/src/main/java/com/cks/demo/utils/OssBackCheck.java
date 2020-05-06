package com.cks.demo.utils;

import com.aliyun.oss.common.utils.BinaryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @className: OssBackCheck
 * @description: oss回调验证
 * @author: cksspk
 * @date: 2020/4/22
 **/

@Slf4j
@Component
public class OssBackCheck {

    /**
     * @Description OSS回调请求验证
     * @Param [authorizationInput, pubKeyInput, ossCallbackBody, queryString, uri]
     * @Return boolean
     */
    public boolean verifyOSSCallbackRequest(String authorizationInput, String pubKeyInput, String ossCallbackBody, String queryString, String uri){
        boolean ret = false;
        try {
            //将base64编码的数据进行还原
            byte[] authorization = BinaryUtil.fromBase64String(authorizationInput);
            byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
            String pubKeyAddr = new String(pubKey);
            if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/") && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
                log.error("pub key addr must be oss address");
                return false;
            }
            //获取请求中的公钥信息
            String retString = executeGet(pubKeyAddr);
            retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
            retString = retString.replace("-----END PUBLIC KEY-----", "");
            String decodeUri = java.net.URLDecoder.decode(uri, "utf-8");
            if (queryString != null && !"".equals(queryString)) {
                decodeUri += "?" + queryString;
            }
            decodeUri += "\n" + ossCallbackBody;
            ret = doCheck(decodeUri, authorization, retString);
        } catch (Exception e) {
            ret = false;
            log.error("验证OSS请求出现异常：" + e);
        }
        return ret;
    }

    /**
     * @Description 获取请求中的参数
     * @Param [pubKeyUrl]
     * @Return java.lang.String
     */
    @SuppressWarnings({"finally"})
    private String executeGet(String pubKeyUrl) throws Exception {
        BufferedReader in = null;
        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(pubKeyUrl));
            HttpResponse response = defaultHttpClient.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
            return content;
        } catch (Exception e) {
            log.error("解析公钥参数失败：" + e);
            throw new Exception("解析公钥参数失败!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭BufferedReader出现异常：" + e);
                }
            }
        }
    }

    /**
     * @Description 对请求参数进行规则校验
     * @Author hzl
     * @Date 2018/11/27
     * @Param [content, sign, publicKey]
     * @Return boolean
     */
    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(sign);
            return bverify;
        } catch (Exception e) {
            log.error("校验出现异常：" + e);
        }
        return false;
    }
}
