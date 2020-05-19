package com.ckss.blog.home.blog.mapper;

import com.ckss.blog.home.blog.domain.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: CategoryMapper
 * @description: 分类Mapper接口
 * @author: cksspk
 * @date: 2020/4/4
 **/

@Component
public interface CategoryMapper {

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    List<Category> selectCategoryList(Category category);



    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    int insertCategory(Category category);


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
    int deleteCategoryByIds(@Param("ids") Long[] ids, @Param("username") String username);

    /**
     * 检查分类名称是否唯一
     * @param title
     * @return
     */
    Category checkCategoryNameUnique(String title);
}
