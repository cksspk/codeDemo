package com.ckss.project.blog.service.impl;

import com.ckss.common.constant.TagConstants;
import com.ckss.common.core.text.ConvertUtils;
import com.ckss.project.blog.domain.Blog;
import com.ckss.project.blog.domain.Comment;
import com.ckss.project.blog.domain.Tag;
import com.ckss.project.blog.mapper.BlogMapper;
import com.ckss.project.blog.mapper.CommentMapper;
import com.ckss.project.blog.service.BlogService;
import com.ckss.project.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: BlogServiceImpl
 * @description: Blog Service业务层处理
 * @author: cksspk
 * @date: 2020/4/12
 **/

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagService tagService;


    @Override
    public List<Blog> getBlogList(Blog blog) {
        List<Blog> blogList = blogMapper.selectBlogList(blog);
        if (blogList.isEmpty()) {
            return blogList;
        }
        List<Long> blogIdList = blogList.stream().map(Blog::getId).collect(Collectors.toList());
        //设置comment信息
        List<Comment> commentList = commentMapper.selectCommentListByPageIds(blogIdList);
        for (Blog temp : blogList) {
            temp.setCommentList(commentList.stream().filter(e -> e.getPageId().equals(temp.getId())).collect(Collectors.toList()));
            temp.setTagTitleList(getTagTitleListByBlogId(temp.getId()));
        }
        return blogList;
    }

    /**
     * 根据id获取title集合
     *
     * @param blogId blog的id
     * @return title集合
     */
    private List<String> getTagTitleListByBlogId(Long blogId) {
        List<Tag> tagList = tagService.selectTagListByTypeAndId(TagConstants.BLOG, blogId);
        return tagList.stream().map(Tag::getTitle).collect(Collectors.toList());
    }

    @Override
    public int insertBlog(Blog blog) {
        String create = "test_blog_create";
        blog.setCreateBy(create);
        int count = blogMapper.insertBlog(blog);
        //新增博客对tag进行关联，更新tagMapping表  --2020年4月12日
        tagService.updateTagMapping(TagConstants.BLOG, blog.getId(), blog.getTagTitleList());
        return count;
    }

    @Override
    public int deleteBlogByIds(String ids) {
//        String username = SecurityUtils.getUsername();
        //获取当前登录用户  TODO
        String username = "test_blog_delete";
        return blogMapper.deleteBlogByIds(ConvertUtils.toStrArray(ids), username);
    }

    @Override
    public int updateBlog(Blog blog) {
        //获取当前登录用户  TODO
        String updateName = "test_blog_update";
        blog.setUpdateBy(updateName);
        int count = blogMapper.updateBlog(blog);
        tagService.updateTagMapping(TagConstants.BLOG, blog.getId(), blog.getTagTitleList());
        return count;
    }

    @Override
    public List<String> selectBlogTagList(String query) {
        Tag tag = new Tag();
        tag.setTitle(query);
        List<Tag> tagList = tagService.selectTagList(tag);
        return tagList.stream().map(Tag::getTitle).collect(Collectors.toList());
    }

    @Override
    public Blog selectBlogById(Long id) {
        Blog blog = blogMapper.selectBlogById(id);
        blog.setTagTitleList(getTagTitleListByBlogId(id));
        return blog;
    }
}
