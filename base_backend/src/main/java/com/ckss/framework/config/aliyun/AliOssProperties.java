package com.ckss.framework.config.aliyun;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @className: AliOssConfig
 * @description: 阿里云OSS配置类2
 * @author: cksspk
 * @date: 2020/4/2
 **/

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class AliOssProperties{

    private String endpoint;

    private String keyId;

    private String keySecret;

    private String fileHost;

    private String bucketName;


}
