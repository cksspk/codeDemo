package com.ckss.project.tool.controller;

import com.ckss.common.utils.StringUtils;
import com.ckss.framework.web.controller.BaseController;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.page.TableDataInfo;
import com.ckss.project.tool.domain.AliOssContent;
import com.ckss.project.tool.service.AliOssService;
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
public class AliOssController extends BaseController {

    @Autowired
    private AliOssService aliOssService;

    /**
     * 分页查询aliOss图片
     * @param aliOssContent
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo getList(AliOssContent aliOssContent){

        //orderBy 条件拼接增加校验  TODO order By使用会异常，可以直接写到sql中去 2020年4月12日
//        orderBy = orderBy + (desc ? " ASC" : " DESC");
//        //拼接条件
////        PageMethod.startPage(pageNum, pageSize, orderBy);
//        PageMethod.startPage(pageNum, pageSize);
//
//
//        //执行查询
//        List<AliOssContent> list = aliOssService.selectContentList(aliOssContent);
//
//        PageInfo<AliOssContent> objectPageInfo = new PageInfo<>(list);
//        long total = objectPageInfo.getTotal();
//
//        return Result.ok().data("list",list).data("totalPage",total);
        startPage();
        List<AliOssContent> list = aliOssService.selectContentList(aliOssContent);
        return getDataTable(list);
    }


    @PostMapping
    public AjaxResult upload(@RequestParam MultipartFile file,
                             @RequestParam(value="host",defaultValue = "cover",required = false) String host){
        AliOssContent aliOssContent = aliOssService.upload(file,host);
        return StringUtils.isNotNull(aliOssContent) ?  AjaxResult.success(aliOssContent) : AjaxResult.error();
    }



}
