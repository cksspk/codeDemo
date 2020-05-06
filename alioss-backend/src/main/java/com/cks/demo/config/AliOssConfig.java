package com.cks.demo.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: AliOssConfig
 * @description: aliOss初始化配置.
 * @author: cksspk
 * @date: 2020/4/20
 **/

@Component
public class AliOssConfig implements InitializingBean {

    @Bean
    public OSSClient getOSSClient(){
        System.out.println("*******************************");
        System.out.println("初始化OSSClient");
        OSSClient ossClient = new OSSClient(end_point, keyId, keySecret);
        //判断Bucket是否存在
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建bucket
            ossClient.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        return ossClient;
    }


    @Value("${aliyun.oss.file.endpoint}")
    private String end_point;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    /*
     * ============= 配置上传过期时间、大小等等 =========
     */
    @Value("${aliyun.oss.file.expireTime}")
    private Long expireTime;

    /**
     * 上传文件的前缀、可忽略
     */
    @Value("${aliyun.oss.file.dir}")
    private String dir;

    /**
     * 上传文件最大（字节）
     */
    @Value("${aliyun.oss.file.max}")
    private Long max;

    /**
     * 上传文件最小（字节）
     */
    @Value("${aliyun.oss.file.min}")
    private Long min;


    /**
     * 回调地址
     */
    @Value("${aliyun.oss.file.callbackHost}")
    private String callbackHost;

    /**
     * 回调地址
     */
    @Value("${aliyun.oss.file.callBackInterFace}")
    private String callBackInterFace;




    public static String END_POINT;
    public static String ACCESS_ID;
    public static String ACCESS_KEY ;
    public static String BUCKET_NAME;
    public static String DIR;
    public static String HOST;
    public static Long EXPIRE_TIME;
    public static Long MIN;
    public static Long MAX;


    public static String CALLBACK_HOST;
    public static String CALLBACK_INTERFACE;

    @Override
    public void afterPropertiesSet() {
        System.out.println("*******************************");
        System.out.println("初始化OSSCProperties");
        END_POINT = end_point;
        ACCESS_ID  = keyId;
        ACCESS_KEY = keySecret;
        BUCKET_NAME  = bucketName;
        EXPIRE_TIME = expireTime;
        HOST = "http://" + bucketName + "." + end_point; // host的格式为 bucketname.endpoint
        MAX = max;
        MIN = min;
        DIR = dir;

        //回调配置
        CALLBACK_HOST = callbackHost;
        CALLBACK_INTERFACE = callBackInterFace;

    }
}
