package com.ckss.blog.home.blog.service;

import com.ckss.blog.home.blog.domain.Tag;
import com.ckss.blog.home.blog.domain.TagMapping;

import java.util.List;

/**
 * @className: TagService
 * @description: Tag Service接口
 * @author: cksspk
 * @date: 2020/4/11
 **/

public interface TagService {

    /**
     * 查找标签列表
     * @param tag 标签
     * @return 分标签集合
     */
    List<Tag> selectTagList(Tag tag);

    /**
     * 添加标签
     * @param tag tag
     * @return 受影响的行数
     */
    int insertTag(Tag tag);

    /**
     * 检查标签名字是否唯一
     * @param tag
     * @return 结果
     */
    String checkTagNameUnique(Tag tag);

    /**
     * 删除Tag
     *
     * @param ids tag的id
     * @return 受影响的行数
     */
    int deleteTagByIds(String ids);

    /**
     * 更新Tag
     *
     * @param tag tag
     * @return 受影响的行数
     */
    int updateTag(Tag tag);




    /**
     * 更新TagMapping
     *
     * @param type         type
     * @param id           id
     * @param tagTitleList list
     */
    void updateTagMapping(Integer type, Long id, List<String> tagTitleList);


    /**
     * 删除Tag的mapping,里面设置的有哪个字段的值,便以那个值作为条件进行删除
     *
     * @param tagMapping TagMapping
     * @return 受影响的行数
     */
    int deleteTagMapping(TagMapping tagMapping);


    /**
     * 根据Tag的title 和 type搜索Tag
     *
     * @param title Tag的title
     * @param type  Tag的类型
     * @return Tag
     */
    Tag selectTagByTitle(String title, Integer type);


    /**
     * 新增Tag Mapping映射关系
     *
     * @param tagMapping 映射关系
     * @return 受影响的行数
     */
    int insertTagMapping(TagMapping tagMapping);


    /**
     * 根据Tag的type和Id获取该Id下的所有Tag
     *
     * @param type Type
     * @param id   id
     * @return Tag list
     */
    List<Tag> selectTagListByTypeAndId(Integer type, Long id);

}
