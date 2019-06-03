package com.zhangxy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhangxy.model.center;

@Mapper
public interface CenterMapper {

	@Select("select * from center where cid = #{cid}")
	List<center> getTagListByCid(Integer cid);
	
	@Select("select * from center where tid = #{tid}")
	List<center> getTagListByTid(Integer tid);
	
}
