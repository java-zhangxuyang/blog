package com.zhangxy.mapper;

import com.zhangxy.model.Content;
import com.zhangxy.model.ContentExample;
import java.util.List;

public interface ContentMapper {
    int countByExample(ContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Content record);

    int insertSelective(Content record);

    List<Content> selectByExample(ContentExample example);

    Content selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKey(Content record);
}