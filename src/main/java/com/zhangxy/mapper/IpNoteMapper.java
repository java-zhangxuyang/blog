package com.zhangxy.mapper;

import com.zhangxy.model.IpNote;
import com.zhangxy.model.IpNoteExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IpNoteMapper {
    int countByExample(IpNoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IpNote record);

    int insertSelective(IpNote record);

    List<IpNote> selectByExample(IpNoteExample example);

    IpNote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IpNote record);

    int updateByPrimaryKey(IpNote record);
    
    @Select("select * from ipnote where date(time) = curdate() and ip = #{ip}")
    public IpNote getCountByIp(String ip);
}