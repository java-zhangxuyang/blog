package com.zhangxy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhangxy.model.center;

@Mapper
public interface CenterMapper {

	@Select("select * from center where cid = #{cid}")
	List<center> getTagListByCid(Integer cid);
	
	@Select("select * from center where tid = #{tid}")
	List<center> getTagListByTid(Integer tid);
	
	@Delete("delete from center where cid = #{cid}")
	Integer deleteTagByCid(Integer cid);
	
	@Insert("insert into center (cid,tid) values (#{cid},#{tid})")
	Integer insertTag(Integer cid,Integer tid);
	
	@Select("SELECT IFNULL(c.count,0) FROM\n" + 
			"`month` m LEFT JOIN (\n" + 
			"SELECT MONTH(c.time) as `month`,count(1) as count FROM content c WHERE YEAR(c.time)=\"2019\" GROUP BY MONTH(c.time)\n" + 
			") c on m.`month` = c.`month` ORDER BY m.`month` ")
	List<Integer> selectCountMonth();
	
}
