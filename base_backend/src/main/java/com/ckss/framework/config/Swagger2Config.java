package com.ckss.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @className: Swagger2Config
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/28
 **/

@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有 .apis(RequestHandlerSelectors.any())
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }


//    private ApiInfo webApiInfo(){
//
//        return new ApiInfoBuilder()
//                .title("网站-课程中心API文档")
//                .description("本文档描述了课程中心微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))
//                .build();
//    }
    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：ckss后台管理系统")
                // 描述
                .description("ckss后台系统接口定义")
                // 作者信息
                .contact(new Contact("cksspk", "https://www.cksspk.com", "cksspk@163.com"))
                // 版本
//                .version("版本号:" + dimpleBlogConfig.getVersion())
                .build();
    }
}
