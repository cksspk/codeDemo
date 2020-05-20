package com.ckss.project.blog.controller;

import com.ckss.common.constant.Constants;
import com.ckss.common.constant.TagConstants;
import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.common.exception.BlogException;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.blog.domain.Tag;
import com.ckss.project.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: TagController
 * @description: 标签管理接口.
 * @author: cksspk
 * @date: 2020/4/11
 **/

@RestController
@RequestMapping("blog/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 分页查找标签
     * @param tag   标签
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param orderBy 排序
     * @param desc 规则
     * @return
     */
    @GetMapping("/list")
    public Result getTagList(Tag tag,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "orderBy",defaultValue = "create_time") String orderBy,
                             @RequestParam(value = "IS_ASC",defaultValue = "false") Boolean desc
                            ){

        //orderBy 条件拼接增加校验  TODO
        orderBy = orderBy + (desc ? " ASC" : " DESC");
        //拼接条件
        PageMethod.startPage(pageNum, pageSize, orderBy);

        //设置标签的类型
        tag.setType(TagConstants.BLOG);

        //执行查询
        List<Tag> list = tagService.selectTagList(tag);

        PageInfo<Tag> objectPageInfo = new PageInfo<>(list);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("list",list).data("totalPage",total);
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @PutMapping()
    public Result edit(@RequestBody @Validated Tag tag) {
        //检查标签是否存在
        if(Constants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag))){
            throw new BlogException(ResultCodeEnum.TAG_TITLE_NOT_UNIQUE);
        }
        //根据当前登陆账号获取更新  TODO
        String updateBy = "update_tag_test";
        tag.setUpdateBy(updateBy);

        int result = tagService.updateTag(tag);
        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 添加标签
     * @param tag
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody @Validated Tag tag) {
        //检查标签是否存在
        if(Constants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag))){
            throw new BlogException(ResultCodeEnum.TAG_TITLE_NOT_UNIQUE);
        }
//        category.setCreateBy(SecurityUtils.getUsername());
        //根据当前登陆账号获取创建者  TODO
        String createBy = "createBy_test";
        tag.setCreateBy(createBy);
        tag.setType(TagConstants.BLOG);

        int result = tagService.insertTag(tag);

        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 删除标签
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String ids) {
        int result = tagService.deleteTagByIds(ids);
        return result > 0 ? Result.ok() : Result.error();
    }




}
