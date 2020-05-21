package com.ckss.project.blog.controller;

import com.ckss.common.constant.Constants;
import com.ckss.common.constant.TagConstants;
import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.common.exception.BlogException;
import com.ckss.common.exception.CustomException;
import com.ckss.framework.web.controller.BaseController;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.page.TableDataInfo;
import com.ckss.project.blog.domain.Tag;
import com.ckss.project.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;


    /**
     * 分页查找标签
     * @param tag   标签
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo getTagList(Tag tag){

        //orderBy 条件拼接增加校验  TODO
//        orderBy = orderBy + (desc ? " ASC" : " DESC");
//        //拼接条件
//        PageMethod.startPage(pageNum, pageSize, orderBy);
//
//        //设置标签的类型
//        tag.setType(TagConstants.BLOG);
//
//        //执行查询
//        List<Tag> list = tagService.selectTagList(tag);
//
//        PageInfo<Tag> objectPageInfo = new PageInfo<>(list);
//        long total = objectPageInfo.getTotal();
//
//        return Result.ok().data("list",list).data("totalPage",total);
        startPage();
        List<Tag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @PutMapping()
    public AjaxResult edit(@RequestBody @Validated Tag tag) {
        //检查标签是否存在
        if(Constants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag))){
            throw new BlogException(ResultCodeEnum.TAG_TITLE_NOT_UNIQUE);
        }
        //根据当前登陆账号获取更新  TODO
        String updateBy = "update_tag_test";
        tag.setUpdateBy(updateBy);

        return toAjax(tagService.updateTag(tag));
//        int result = tagService.updateTag(tag);
//        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 添加标签
     * @param tag
     * @return
     */
    @PostMapping()
    public AjaxResult add(@RequestBody @Validated Tag tag) {
        //检查标签是否存在
        if(Constants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag))){
//            throw new BlogException(ResultCodeEnum.TAG_TITLE_NOT_UNIQUE);
            throw new CustomException("标签已存在", HttpStatus.BAD_REQUEST);
        }
//        category.setCreateBy(SecurityUtils.getUsername());
        //根据当前登陆账号获取创建者  TODO
        String createBy = "createBy_test";
        tag.setCreateBy(createBy);
        tag.setType(TagConstants.BLOG);

        return toAjax(tagService.insertTag(tag));
//        int result = tagService.insertTag(tag);
//        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 删除标签
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids) {
        return toAjax(tagService.deleteTagByIds(ids));
//        int result = tagService.deleteTagByIds(ids);
//        return result > 0 ? Result.ok() : Result.error();
    }




}
