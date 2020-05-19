package com.ckss.blog.home.blog.service.impl;

import com.ckss.blog.common.constant.Constants;
import com.ckss.blog.common.constant.TagConstants;
import com.ckss.blog.common.utils.ConvertUtils;
import com.ckss.blog.common.utils.ObjectUtils;
import com.ckss.blog.common.utils.StringUtils;
import com.ckss.blog.home.blog.domain.Tag;
import com.ckss.blog.home.blog.domain.TagMapping;
import com.ckss.blog.home.blog.mapper.TagMapper;
import com.ckss.blog.home.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @className: TagServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/4/11
 **/

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> selectTagList(Tag tag) {
        return tagMapper.selectTagList(tag);
    }

    @Override
    public int insertTag(Tag tag) {
        return tagMapper.insertTag(tag);
    }

    @Override
    public String checkTagNameUnique(Tag tag) {
        //检查id是否存在
        Long id =  StringUtils.isNull(tag.getId()) ? -1L : tag.getId();
        Tag info = tagMapper.checkTagNameUnique(tag.getTitle());
        if(info != null && info.getId().longValue() != id.longValue()){
            //存在相同
            return Constants.NOT_UNIQUE;
        }else{
            return Constants.UNIQUE;
        }
    }

    @Transactional
    @Override
    public int deleteTagByIds(String ids) {
        Long[] longs = ConvertUtils.toLongArray(ids);
        //获取登录删用户 TODO
        String deleteName = "test_tag_delName";
        return tagMapper.deleteTagByIds(longs,deleteName);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }



    @Override
    public void updateTagMapping(Integer type, Long id, List<String> tagTitleList) {
        //删除该Id下的所有关联
        TagMapping tagMapping = TagMapping.builder()
                .blogId(TagConstants.BLOG.equals(type) ? id : null)
                .noteId(TagConstants.NOTE.equals(type) ? id : null)
                .bookId(TagConstants.BOOK.equals(type) ? id : null)
                .build();
        deleteTagMapping(tagMapping);

        if (ObjectUtils.isNotEmpty(tagTitleList)) {
            for (String title : tagTitleList) {
                //搜索所有的tag
                Tag tag = selectTagByTitle(title.trim(), TagConstants.BLOG);
                if (tag != null) {
                    //与原先tag进行关联
                    tagMapping.setTagId(tag.getId());
                    insertTagMapping(tagMapping);
                } else {
                    //新建tag进行关联
                    Tag temp = new Tag(title.trim(), StringUtils.format("rgba({}, {}, {}, {})", getRandomNum(255), getRandomNum(255), getRandomNum(255), 1), TagConstants.BLOG);
                    insertTag(temp);
                    tagMapping.setTagId(temp.getId());
                    insertTagMapping(tagMapping);
                }
            }
        }
    }

    @Override
    public int deleteTagMapping(TagMapping tagMapping) {
        return tagMapper.deleteTagMapping(tagMapping);
    }


    @Override
    public Tag selectTagByTitle(String title, Integer type) {
        return tagMapper.selectTagByTitle(title, type);
    }

    @Override
    public int insertTagMapping(TagMapping tagMapping) {
        return tagMapper.insertTagMapping(tagMapping);
    }

    @Override
    public List<Tag> selectTagListByTypeAndId(Integer type, Long id) {
        return tagMapper.selectTagListByType(type, id);
    }


    /**
     * 获取随机数
     *
     * @param num 最大范围
     * @return 随机数
     */
    private int getRandomNum(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }
}
