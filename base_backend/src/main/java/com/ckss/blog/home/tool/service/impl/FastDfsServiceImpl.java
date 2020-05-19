package com.ckss.blog.home.tool.service.impl;

import com.ckss.blog.common.enums.ResultCodeEnum;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.utils.StringUtils;
import com.ckss.blog.config.fastDsf.FastDfsUploadProperties;
import com.ckss.blog.home.tool.domain.FastDfsContent;
import com.ckss.blog.home.tool.mapper.FastDfsContentMapper;
import com.ckss.blog.home.tool.service.FastDfsService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @className: FastDfsServiceImpl
 * @description: FastDfs服务实现
 * @author: cksspk
 * @date: 2020/4/15
 **/

@Slf4j
@Service
public class FastDfsServiceImpl implements FastDfsService {

    @Autowired
    private FastDfsContentMapper fastDfsContentMapper;

    @Autowired
    private FastDfsUploadProperties prop;

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public FastDfsContent upload(MultipartFile file) {
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

        FastDfsContent fastDfsContent = null;
        //保存图片
        try {
            String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), extensionName, null);
            //返回保存图片的完整url
            String imgUrl = prop.getBaseUrl() + storePath.getFullPath();
            String group = storePath.getGroup();
            String size = String.valueOf(file.getSize());
            String fileName =  StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");

            fastDfsContent = new FastDfsContent();
            fastDfsContent.setGroup(group);
            fastDfsContent.setName(fileName);
            fastDfsContent.setUrl(imgUrl);
            fastDfsContent.setSuffix(extensionName);
            fastDfsContent.setSize(size);

            //获取当前操作用户 TODO
            fastDfsContent.setCreateBy("test_fastDfsImg_createBy");

            System.out.println(imgUrl);
            //存入数据库中
            fastDfsContentMapper.insertContent(fastDfsContent);
//            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            log.info("【文件上传】文件上传失败", e);
            throw new BlogException(ResultCodeEnum.UPLOAD_IMAGE_EXCEPTION);
        }
        return fastDfsContent;

    }




    @Override
    public void delFile(String url) {
//        url:group1/M00/00/00/wKh9eV6XJM2AUWFgAAOracP9ijk451.jpg
        try {
//            storageClient.deleteFile();
            storageClient.deleteFile(url);
            // 链接FastDFS服务器，创建tracker和Stroage

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<FastDfsContent> selectContentList(FastDfsContent fastDfsContent) {
        return fastDfsContentMapper.selectContentList(fastDfsContent);
    }

    @Override
    public int delFileByIds(String ids) {
        //1. 查询数据库中文件地址

        //2. 根据url地址删除
        return 0;
    }


}
