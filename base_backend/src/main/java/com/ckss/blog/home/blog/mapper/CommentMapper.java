package com.ckss.blog.home.blog.mapper;

import com.ckss.blog.home.blog.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: CommentMapper
 * @description: 评论管理mapper接口
 * @author: cksspk
 * @date: 2020/4/12
 **/

@Component
public interface CommentMapper {



    /**
     * 获取博客对应的评论的Map
     *
     * @param blogIdList blog的Id
     * @return map
     */
    List<Comment> selectCommentListByPageIds(@Param("blogIdList") List<Long> blogIdList);
}
