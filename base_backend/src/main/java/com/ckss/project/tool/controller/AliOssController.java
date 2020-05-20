package com.ckss.project.tool.controller;

import com.ckss.common.utils.StringUtils;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.tool.domain.AliOssContent;
import com.ckss.project.tool.service.AliOssService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @className: Ali
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/15
 **/

@RestController
@RequestMapping("/tool/aliOss")
public class AliOssController {

    @Autowired
    private AliOssService aliOssService;

    /**
     * 分页查询aliOss图片
     * @param aliOssContent
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @param desc
     * @return
     */
    @GetMapping("/list")
    public Result getList(AliOssContent aliOssContent,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "orderBy",defaultValue = "create_time") String orderBy,
                          @RequestParam(value = "IS_ASC",defaultValue = "false") Boolean desc
    ){

        //orderBy 条件拼接增加校验  TODO order By使用会异常，可以直接写到sql中去 2020年4月12日
        orderBy = orderBy + (desc ? " ASC" : " DESC");
        //拼接条件
//        PageMethod.startPage(pageNum, pageSize, orderBy);
        PageMethod.startPage(pageNum, pageSize);


        //执行查询
        List<AliOssContent> list = aliOssService.selectContentList(aliOssContent);

        PageInfo<AliOssContent> objectPageInfo = new PageInfo<>(list);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("list",list).data("totalPage",total);
    }


    @PostMapping
    public Result upload(@RequestParam MultipartFile file,
                         @RequestParam(value="host",defaultValue = "cover",required = false) String host){
        AliOssContent aliOssContent = aliOssService.upload(file,host);
        return StringUtils.isNotNull(aliOssContent) ?  Result.ok().data("data",aliOssContent) : Result.error();
    }



}
