package com.ckss.blog.home.system.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.ckss.blog.common.enums.ResultCodeEnum;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.utils.StringUtils;
import com.ckss.blog.config.aliyun.AliOssProperties;
import com.ckss.blog.config.fastDsf.FastDfsUploadProperties;
import com.ckss.blog.home.system.service.FileService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @className: FileServiceImpl
 * @description: 文件上传服务
 * @author: cksspk
 * @date: 2020/4/2
 **/

@Slf4j
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private AliOssProperties aliOssProperties;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FastDfsUploadProperties prop;

    //字段注入第二种方式
    @Override
    public String uploadAliOss(MultipartFile file) {

        String uploadUrl = null;

        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSSClient ossClient = new OSSClient(aliOssProperties.getEndpoint(),aliOssProperties.getKeyId(),aliOssProperties.getKeySecret());
            if (!ossClient.doesBucketExist(aliOssProperties.getBucketName())) {
                //创建bucket
                ossClient.createBucket(aliOssProperties.getBucketName());
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(aliOssProperties.getBucketName(), CannedAccessControlList.PublicRead);
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
            String fileUrl = aliOssProperties.getFileHost() + "/" + filePath + "/" + newName;

            //文件上传至阿里云
            ossClient.putObject(aliOssProperties.getBucketName(), fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //获取url地址
            uploadUrl = "http://" + aliOssProperties.getBucketName() + "." +aliOssProperties.getEndpoint() + "/" + fileUrl;

        } catch (IOException e) {
            throw new BlogException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return uploadUrl;


    }

    @Override
    public String uploadFDfs(MultipartFile file) {
            // 1、图片信息校验
            // 1)校验文件类型
            String contentType = file.getContentType();
            if (!prop.getAllowTypes().contains(contentType)) {
                log.info("上传失败，文件类型不匹配：{}", contentType);
                throw new BlogException(ResultCodeEnum.INVALID_FILE_FORMAT);
            }

            //检验文件内容
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image == null) {
                    log.info("【文件上传】上传文件格式错误");
                    throw new BlogException(ResultCodeEnum.INVALID_FILE_FORMAT);
                }
            } catch (IOException e) {
                log.info("【文件上传】文件上传失败", e);
                throw new BlogException(ResultCodeEnum.INVALID_FILE_FORMAT);
            }

            //保存图片
            try {
                String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                StorePath storePath = storageClient.uploadFile(
                        file.getInputStream(), file.getSize(), extensionName, null);
                //返回保存图片的完整url
                return prop.getBaseUrl() + storePath.getFullPath();
            } catch (IOException e) {
                log.info("【文件上传】文件上传失败", e);
                throw new BlogException(ResultCodeEnum.UPLOAD_IMAGE_EXCEPTION);
            }
    }

//    @Override
//    public String upload(MultipartFile file) {
//
//        //获取阿里云存储相关常量
//        String endPoint = AliOssPropertiesUtil.END_POINT;
//        String accessKeyId = AliOssPropertiesUtil.ACCESS_KEY_ID;
//        String accessKeySecret = AliOssPropertiesUtil.ACCESS_KEY_SECRET;
//        String bucketName = AliOssPropertiesUtil.BUCKET_NAME;
//        String fileHost = AliOssPropertiesUtil.FILE_HOST;
//
//        String uploadUrl = null;
//
//        try {
//            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
//            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
//            if (!ossClient.doesBucketExist(bucketName)) {
//                //创建bucket
//                ossClient.createBucket(bucketName);
//                //设置oss实例的访问权限：公共读
//                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//            }
//
//            //获取上传文件流
//            InputStream inputStream = file.getInputStream();
//
//            //构建日期路径：avatar/2019/02/26/文件名
//            String filePath = new DateTime().toString("yyyy/MM/dd");
//
//            //文件名：uuid.扩展名
//            String original = file.getOriginalFilename();
//            String fileName = UUID.randomUUID().toString();
//            String fileType = original.substring(original.lastIndexOf("."));
//            String newName = fileName + fileType;
//            String fileUrl = fileHost + "/" + filePath + "/" + newName;
//
//            //文件上传至阿里云
//            ossClient.putObject(bucketName, fileUrl, inputStream);
//
//            // 关闭OSSClient。
//            ossClient.shutdown();
//
//            //获取url地址
//            uploadUrl = "http://" + bucketName + "." + endPoint + "/" + fileUrl;
//
//        } catch (IOException e) {
//            throw new BlogException(ResultCodeEnum.FILE_UPLOAD_ERROR);
//        }
//
//        return uploadUrl;
//
//
//    }
}

