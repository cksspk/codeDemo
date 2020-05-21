package com.ckss.project.blog.controller;

import com.ckss.common.constant.Constants;
import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.common.exception.BlogException;
import com.ckss.framework.web.controller.BaseController;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.page.TableDataInfo;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.blog.domain.Blog;
import com.ckss.project.blog.domain.Category;
import com.ckss.project.blog.service.CategoryService;
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
public class CategoryController extends BaseController {


    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询
     * @param category
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo getCategoryList(Category category){

//        //orderBy 条件拼接增加校验  TODO
//        orderBy = orderBy + (desc ? " ASC" : " DESC");
//        //拼接条件
//        PageMethod.startPage(pageNum, pageSize, orderBy);
//
//        //执行查询
//        List<Category> list = categoryService.getCategoryList(category);
//
//        PageInfo<Category> objectPageInfo = new PageInfo<>(list);
//        long total = objectPageInfo.getTotal();
//
//        return Result.ok().data("list",list).data("totalPage",total);


        startPage();
        List<Category> list = categoryService.getCategoryList(category);
        return getDataTable(list);
    }


    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping()
    public AjaxResult add(@RequestBody @Validated Category category) {
        //检查分类是否存在
        if(Constants.NOT_UNIQUE.equals(categoryService.checkCategoryNameUnique(category))){
            throw new BlogException(ResultCodeEnum.CATEGORY_TITLE_NOT_UNIQUE);
        }
//        category.setCreateBy(SecurityUtils.getUsername());
        //根据当前登陆账号获取创建者  TODO
        String createBy = "createBy_test";
        category.setCreateBy(createBy);

//        int result = categoryService.insertCategory(category);
//        return result > 0 ? Result.ok() : Result.error();
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping()
    public AjaxResult edit(@RequestBody @Validated Category category) {
        //检查分类是否存在
        if(Constants.NOT_UNIQUE.equals(categoryService.checkCategoryNameUnique(category))){
            throw new BlogException(ResultCodeEnum.CATEGORY_TITLE_NOT_UNIQUE);
        }
        //根据当前登陆账号获取更新  TODO
        String updateBy = "update_test";
        category.setUpdateBy(updateBy);

        return toAjax(categoryService.updateCategory(category));

//        int result = categoryService.updateCategory(category);
//        return result > 0 ? Result.ok() : Result.error();
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids) {
        return toAjax(categoryService.deleteCategoryByIds(ids));
//        int result = categoryService.deleteCategoryByIds(ids);
//        return result > 0 ? Result.ok() : Result.error();
    }

}
