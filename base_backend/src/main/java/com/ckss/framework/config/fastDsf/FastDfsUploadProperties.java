package com.ckss.framework.config.fastDsf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: FastDfsUploadProperties
 * @description: 文件上传配置信息
 * @author: cksspk
 * @date: 2020/4/3
 **/

@Component
@Data
@ConfigurationProperties(prefix = "blog.upload")
public class FastDfsUploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
