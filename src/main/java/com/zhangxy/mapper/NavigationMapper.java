package com.zhangxy.mapper;

import com.zhangxy.model.Navigation;
import com.zhangxy.model.NavigationExample;
import java.util.List;

public interface NavigationMapper {
    int countByExample(NavigationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Navigation record);

    int insertSelective(Navigation record);

    List<Navigation> selectByExample(NavigationExample example);

    Navigation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);
}