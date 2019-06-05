package com.zhangxy.mapper;

import com.zhangxy.model.Message;
import com.zhangxy.model.MessageExample;
import java.util.List;

public interface MessageMapper {
    int countByExample(MessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}