package com.ckss.project.blog.service.impl;

import com.ckss.common.constant.Constants;
import com.ckss.common.core.text.ConvertUtils;
import com.ckss.common.utils.DateUtils;
import com.ckss.common.utils.ObjectUtils;
import com.ckss.common.utils.StringUtils;
import com.ckss.project.blog.domain.Blog;
import com.ckss.project.blog.domain.Category;
import com.ckss.project.blog.mapper.BlogMapper;
import com.ckss.project.blog.mapper.CategoryMapper;
import com.ckss.project.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: CategoryServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/4
 **/

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public List<Category> getCategoryList(Category blCategory) {
        //1. 查询分类列表
        List<Category> categoryList = categoryMapper.selectCategoryList(blCategory);

        //2. 设置关联博客
        List<Long> categoryIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(categoryIds)) {
            return categoryList;
        }

        List<Blog> blogList = blogMapper.selectBlogListByCategoryIds(categoryIds);
        for (Category category : categoryList) {
            List<Blog> collect = blogList.stream().filter(e -> category.getId().equals(e.getCategoryId())).collect(Collectors.toList());
            category.setBlogList(collect);
        }

        return categoryList;
    }


    @Override
    public int insertCategory(Category bgCategory) {
        return categoryMapper.insertCategory(bgCategory);
    }



    @Override
    public int updateCategory(Category bgCategory) {

        bgCategory.setUpdateTime(DateUtils.getNowDate());
        return categoryMapper.updateCategory(bgCategory);
    }




    @Override
    public int deleteCategoryByIds(String ids) {
//        根据环境获取当前登录用户删除 TODO
//        String username = SecurityUtils.getUsername();
        String username = "delete_test";
        return categoryMapper.deleteCategoryByIds(ConvertUtils.toLongArray(ids), username);
    }

    @Override
    public String checkCategoryNameUnique(Category category) {
        //检查分类是否存在
        Long id =  StringUtils.isNull(category.getId()) ? -1L : category.getId();
        Category info = categoryMapper.checkCategoryNameUnique(category.getTitle());
        if(info != null && info.getId().longValue() != id.longValue()){
            //存在相同
            return Constants.NOT_UNIQUE;
        }else{
            return Constants.UNIQUE;
        }
    }
}
