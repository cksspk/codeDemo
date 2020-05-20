package com.ckss.project.blog.service;

import com.ckss.project.blog.domain.Category;

import java.util.List;

/**
 * @className: CategoryService
 * @description: Cate Service接口
 * @author: cksspk
 * @date: 2020/4/4
 **/

public interface CategoryService {



    /**
     * 新增分类
     *
     * @param bgCategory 分类
     * @return 结果
     */
    int insertCategory(Category bgCategory);


    /**
     * 查找分类列表
     * @param category 分类
     * @return 分类集合
     */
    List<Category> getCategoryList(Category category);


    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    int updateCategory(Category category);


    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCategoryByIds(String ids);

    /**
     * 检查分类名字是否唯一
     * @param category
     * @return
     */
    String checkCategoryNameUnique(Category category);
}
