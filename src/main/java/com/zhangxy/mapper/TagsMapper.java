package com.zhangxy.mapper;

import com.zhangxy.model.Tags;
import com.zhangxy.model.TagsExample;
import java.util.List;

public interface TagsMapper {
    int countByExample(TagsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tags record);

    int insertSelective(Tags record);

    List<Tags> selectByExample(TagsExample example);

    Tags selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);
}