package com.ckss.blog.home.blog.controller;

import com.ckss.blog.common.constant.Constants;
import com.ckss.blog.common.enums.ResultCodeEnum;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.web.vo.Result;
import com.ckss.blog.home.blog.domain.Category;
import com.ckss.blog.home.blog.service.CategoryService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: CateController
 * @description: 博客分类接口
 * @author: cksspk
 * @date: 2020/4/4
 **/

@RestController
@RequestMapping("/blog/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询
     * @param category
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @param desc
     * @return
     */
    @GetMapping("/list")
    public Result getCategoryList(Category category,
          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
          @RequestParam(value = "orderBy",defaultValue = "create_time") String orderBy,
          @RequestParam(value = "IS_ASC",defaultValue = "false") Boolean desc
          ){

        //orderBy 条件拼接增加校验  TODO
        orderBy = orderBy + (desc ? " ASC" : " DESC");
        //拼接条件
        PageMethod.startPage(pageNum, pageSize, orderBy);

        //执行查询
        List<Category> list = categoryService.getCategoryList(category);

        PageInfo<Category> objectPageInfo = new PageInfo<>(list);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("list",list).data("totalPage",total);
    }


    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody @Validated Category category) {
        //检查分类是否存在
        if(Constants.NOT_UNIQUE.equals(categoryService.checkCategoryNameUnique(category))){
            throw new BlogException(ResultCodeEnum.CATEGORY_TITLE_NOT_UNIQUE);
        }
//        category.setCreateBy(SecurityUtils.getUsername());
        //根据当前登陆账号获取创建者  TODO
        String createBy = "createBy_test";
        category.setCreateBy(createBy);
        int result = categoryService.insertCategory(category);

        return result > 0 ? Result.ok() : Result.error();
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping()
    public Result edit(@RequestBody @Validated Category category) {
        //检查分类是否存在
        if(Constants.NOT_UNIQUE.equals(categoryService.checkCategoryNameUnique(category))){
            throw new BlogException(ResultCodeEnum.CATEGORY_TITLE_NOT_UNIQUE);
        }
        //根据当前登陆账号获取更新  TODO
        String updateBy = "update_test";
        category.setUpdateBy(updateBy);

        int result = categoryService.updateCategory(category);
        return result > 0 ? Result.ok() : Result.error();
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String ids) {
        int result = categoryService.deleteCategoryByIds(ids);
        return result > 0 ? Result.ok() : Result.error();
    }

}
