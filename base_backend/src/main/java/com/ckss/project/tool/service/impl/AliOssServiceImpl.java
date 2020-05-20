package com.ckss.project.tool.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.common.exception.BlogException;
import com.ckss.framework.config.aliyun.AliOssPropertiesUtil;
import com.ckss.project.tool.domain.AliOssContent;
import com.ckss.project.tool.mapper.AliOssContentMapper;
import com.ckss.project.tool.service.AliOssService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @className: AliOssServiceImpl
 * @description: AliOss图片管理服务实现层
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Service
public class AliOssServiceImpl implements AliOssService {

    @Autowired
    private AliOssContentMapper aliOssContentMapper;


    @Override
    public AliOssContent upload(MultipartFile file, String host) {
//        获取阿里云存储相关常量
        String endPoint = AliOssPropertiesUtil.END_POINT;
        String accessKeyId = AliOssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = AliOssPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = AliOssPropertiesUtil.BUCKET_NAME;
        String fileHost = AliOssPropertiesUtil.FILE_HOST;

        String uploadUrl = null;
        //构建存入数据库对象
        AliOssContent aliOssContent = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            //获取上传文件流
            InputStream inputStream = file.getInputStream();

            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            //判断那种类型的文件上传到不同的文件夹
            if(!StringUtils.isEmpty(host)) { //不为空
                //上传封面
                fileHost = fileHost + "/" + host;
            }
            String fileUrl = fileHost + "/" + filePath + "/" + newName;

            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            uploadUrl = "http://" + bucketName + "." + endPoint + "/" + fileUrl;
            System.out.println(uploadUrl);


            aliOssContent = new AliOssContent();
            aliOssContent.setBucket(bucketName);
            aliOssContent.setName(original);
            aliOssContent.setSuffix(fileType.substring(fileType.indexOf(".")+1));
            aliOssContent.setSize(String.valueOf(file.getSize())); //文件大小待修改 TODO
            aliOssContent.setUrl(uploadUrl);

            //获取当前操作用户 TODO
            aliOssContent.setCreateBy("test_aliOssImg_createBy");

            aliOssContentMapper.insertContent(aliOssContent);
        } catch (IOException e) {
            throw new BlogException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return aliOssContent;
    }

    @Override
    public List<AliOssContent> selectContentList(AliOssContent aliOssContent) {
        return aliOssContentMapper.selectContentList(aliOssContent);
    }


}
