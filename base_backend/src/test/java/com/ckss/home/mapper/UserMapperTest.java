package com.ckss.home.mapper;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.ckss.project.system.mapper.UserMapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * fastdfs  Test
     * @throws FileNotFoundException
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("D:\\study\\heima\\upload\\kenan.jpg");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File("D:\\test\\baby.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        // 获取缩略图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        List list = new ArrayList();
        List list1 = list;

        list.add("1");

        System.out.println(list ==list1);

        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.out::println);
    }



    @Test
    public void jsonTest(){

        String callbackUrl = "http://www.cksspk.site/aliOss/callback";
        JSONObject jasonCallback = new JSONObject();
        jasonCallback.put("callbackUrl", callbackUrl);
        jasonCallback.put("callbackBody",
                "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
        String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());

        System.out.println(base64CallbackBody);
//        respMap.put("callback", base64CallbackBody);
    }

    @Test
    public void jsonTest2() throws UnsupportedEncodingException {

        String callbackUrl = "http://www.cksspk.site/aliOss/callback";
        JSONObject jasonCallback = new JSONObject();
        jasonCallback.put("callbackUrl", callbackUrl);
        jasonCallback.put("callbackBody",
                "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
        String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());

        System.out.println(base64CallbackBody);


        System.out.println("--------------");

        String str2= jasonCallback.toString();
        System.out.println("str2"+str2);
        String base64CallbackBody2 = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
        System.out.println(base64CallbackBody2);

        System.out.println("--------------");

        Map<String, Object> callbackMap = new HashMap<String, Object>();
        callbackMap.put("callbackUrl", callbackUrl);
//        callbackMap.put("callbackHost", callBackHost);
        callbackMap.put("callbackBody", "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callbackMap.put("callbackBodyType", "application/x-www-form-urlencoded");

        String str3  = JSON.toJSONString(callbackMap);
        System.out.println("str3"+str3);

        byte[] callBack = JSON.toJSONString(callbackMap).getBytes("utf-8");
        String callBackString = BinaryUtil.toBase64String(callBack);
        System.out.println(callBackString);
    }



}
