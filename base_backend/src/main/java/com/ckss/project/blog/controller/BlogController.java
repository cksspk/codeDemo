package com.ckss.project.blog.controller;

import com.ckss.framework.web.controller.BaseController;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.blog.domain.Blog;
import com.ckss.project.blog.service.BlogService;
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
     * @param blog   博客
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param orderBy 排序
     * @param desc 规则
     * @return
     */
    @GetMapping("/list")
    public Result getList(Blog blog,
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
        List<Blog> list = blogService.getBlogList(blog);

        PageInfo<Blog> objectPageInfo = new PageInfo<>(list);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("list",list).data("totalPage",total);
    }


    /**
     * 添加博客
     * @param blog
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody @Validated Blog blog) {
        int result = blogService.insertBlog(blog);
        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 删除博客
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String ids) {
        int result = blogService.deleteBlogByIds(ids);
        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 更新博客
     * @param blog
     * @return
     */
    @PutMapping()
    public Result edit(@RequestBody @Validated Blog blog) {
        int result = blogService.updateBlog(blog);
        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result query(@PathVariable Long id) {
        Blog blog = blogService.selectBlogById(id);
        return  Result.ok().data("data",blog);
    }

    /**
     * 根据输入的标签查询标签集合
     * @param query tag title
     * @return tag 集合
     */
    @GetMapping("tag/{query}")
    public Result getCommonTag(@PathVariable String query) {
        List<String> list = blogService.selectBlogTagList(query);
        return Result.ok().data("list",list);
    }

}
