package com.ckss.project.tool.controller;

import com.ckss.common.utils.StringUtils;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.tool.domain.FastDfsContent;
import com.ckss.project.tool.service.FastDfsService;
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
@RequestMapping("/tool/fastDfs")
public class FastDfsController {


    @Autowired
    private FastDfsService fastDfsService;

    /**
     * 分页查询fastdfs图片
     * @param fastDfsContent
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @param desc
     * @return
     */
    @GetMapping("/list")
    public Result getList(FastDfsContent fastDfsContent,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "orderBy",defaultValue = "create_time") String orderBy,
                          @RequestParam(value = "IS_ASC",defaultValue = "false") Boolean desc
    ){

        //拼接条件
//        PageMethod.startPage(pageNum, pageSize, orderBy);
        PageMethod.startPage(pageNum, pageSize);


        //执行查询
        List<FastDfsContent> list = fastDfsService.selectContentList(fastDfsContent);

        PageInfo<FastDfsContent> objectPageInfo = new PageInfo<>(list);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("list",list).data("totalPage",total);
    }


    @PostMapping
    public Result upload(@RequestParam MultipartFile file){
        FastDfsContent fastDfsContent = fastDfsService.upload(file);
        return StringUtils.isNotNull(fastDfsContent) ?  Result.ok().data("data",fastDfsContent) : Result.error();
//        return Result.ok();
    }



    //删除逻辑 1. 根据图片id查找数据库中地址，根据imgurl删除fastdfs服务器中的数据
    @DeleteMapping
    public Result delFileByIds(@PathVariable String ids){
        int result = fastDfsService.delFileByIds(ids);
//        return StringUtils.isNotNull(aliOssContent) ?  Result.ok() : Result.error();
        return result > 0 ? Result.ok() : Result.error();
    }

}
