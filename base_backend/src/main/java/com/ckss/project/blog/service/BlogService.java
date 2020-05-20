package com.ckss.project.blog.service;

import com.ckss.project.blog.domain.Blog;

import java.util.List;

/**
 * @className: BlogService
 * @description: Blog Service接口
 * @author: cksspk
 * @date: 2020/4/12
 **/

public interface BlogService {

    /**
     * 查询博客列表
     *
     * @param blog 博客
     * @return 博客集合
     */
    List<Blog> getBlogList(Blog blog);

    /**
     * 新增博客
     *
     * @param blog 博客
     * @return 结果
     */
    int insertBlog(Blog blog);


    /**
     * 删除博客信息
     *
     * @param ids 博客ID
     * @return 结果
     */
    int deleteBlogByIds(String ids);


    /**
     * 修改博客
     *
     * @param blog 博客
     * @return 结果
     */
    int updateBlog(Blog blog);




    /**
     * 获取blog的标签
     *
     * @param query 查询条件
     * @return 标签名集合
     */
    List<String> selectBlogTagList(String query);

    /**
     * 查询博客
     *
     * @param id 博客ID
     * @return 博客
     */
    Blog selectBlogById(Long id);
}
