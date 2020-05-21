package com.ckss.project.blog.controller;

import com.ckss.framework.web.controller.BaseController;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.page.TableDataInfo;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.blog.domain.Blog;
import com.ckss.project.blog.service.BlogService;
import com.ckss.project.system.domain.User;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: BlogController
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/4
 **/

@RestController
@RequestMapping("/blog/blog")
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;


    /**
     * 分页查找博客
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo getList(Blog blog){

//        //orderBy 条件拼接增加校验  TODO order By使用会异常，可以直接写到sql中去 2020年4月12日
//        orderBy = orderBy + (desc ? " ASC" : " DESC");
//        //拼接条件
////        PageMethod.startPage(pageNum, pageSize, orderBy);
//        PageMethod.startPage(pageNum, pageSize);


        //执行查询
//        List<Blog> list = blogService.getBlogList(blog);
//
//        PageInfo<Blog> objectPageInfo = new PageInfo<>(list);
//        long total = objectPageInfo.getTotal();
//
//        return Result.ok().data("list",list).data("totalPage",total);

        startPage();
        List<Blog> list = blogService.getBlogList(blog);
        return getDataTable(list);
    }


    /**
     * 添加博客
     * @param blog
     * @return
     */
    @PostMapping()
    public AjaxResult add(@RequestBody @Validated Blog blog) {
//        int result = blogService.insertBlog(blog);
//        return result > 0 ? Result.ok() : Result.error();
        return toAjax(blogService.insertBlog(blog));
    }


    /**
     * 删除博客
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids) {
//        int result = blogService.deleteBlogByIds(ids);
//        return result > 0 ? Result.ok() : Result.error();
        return toAjax(blogService.deleteBlogByIds(ids));
    }


    /**
     * 更新博客
     * @param blog
     * @return
     */
    @PutMapping()
    public AjaxResult edit(@RequestBody @Validated Blog blog) {
//        int result = blogService.updateBlog(blog);
//        return result > 0 ? Result.ok() : Result.error();
        return toAjax(blogService.updateBlog(blog));
    }

    /**
     * 更新推荐  开启推荐
     * @param blog
     * @return
     */
    @PutMapping("support")
    public AjaxResult editSupport(@RequestBody Blog blog) {
        return toAjax(blogService.updateBlog(blog));
    }

    /**
     * 更新博客 开启评论
     * @param blog
     * @return
     */
    @PutMapping("comment")
    public AjaxResult editComment(@RequestBody Blog blog) {
        return toAjax(blogService.updateBlog(blog));
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult query(@PathVariable Long id) {
        Blog blog = blogService.selectBlogById(id);
//        return  Result.ok().data("data",blog);
        return AjaxResult.success(blog);
    }

    /**
     * 根据输入的标签查询标签集合
     * @param query tag title
     * @return tag 集合
     */
    @GetMapping("tag/{query}")
    public TableDataInfo getCommonTag(@PathVariable String query) {
//        List<String> list = blogService.selectBlogTagList(query);
//        return Result.ok().data("list",list);
        return getDataTable(blogService.selectBlogTagList(query));

    }

}
