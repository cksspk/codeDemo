package com.ckss.blog.home.blog.mapper;

import com.ckss.blog.home.blog.domain.Tag;
import com.ckss.blog.home.blog.domain.TagMapping;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: TagMapper
 * @description: Tag标签数据接口
 * @author: cksspk
 * @date: 2020/4/11
 **/

@Component
public interface TagMapper {
    /**
     * 根据条件查询所有tag
     * @param tag
     * @return List.Tag
     */
    List<Tag> selectTagList(Tag tag);

    /**
     * 检查标签名称是否唯一
     * @param title
     * @return Tag
     */
    Tag checkTagNameUnique(String title);


    /**
     * 新增Tag
     *
     * @param tag tag实体
     * @return 受影响的行数
     */
    int insertTag(Tag tag);


    /**
     * 根据Id批量删除Tag
     *
     * @param ids      id
     * @param username 操作者账号
     * @return
     */
    int deleteTagByIds(Long[] ids, String username);

    /**
     * 更新Tag
     *
     * @param tag tag实体
     * @return 受影响的行数
     */
    int updateTag(Tag tag);










    /**
     * 删除TagMapping关联
     *
     * @param tagMapping TagMapping关联
     * @return 受影响的行数
     */
    int deleteTagMapping(TagMapping tagMapping);

    /**
     * 根据Title查询Tag
     *
     * @param title title
     * @param type  类型
     * @return Tag tag
     */
    Tag selectTagByTitle(@Param("title") String title, @Param("type") Integer type);


    /**
     * 插入Tag关联
     *
     * @param tagMapping 关联
     * @return 受影响的行数
     */
    int insertTagMapping(TagMapping tagMapping);


    /**
     * 根据Tag的type和对应type的id获取TagList
     *
     * @param type 类型
     * @param id   对应类型的id
     * @return Tag list
     */
    List<Tag> selectTagListByType(@Param("type") Integer type, @Param("id") Long id);
}
