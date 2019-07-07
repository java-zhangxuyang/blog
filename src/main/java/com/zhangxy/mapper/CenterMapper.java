package com.zhangxy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhangxy.model.Content;
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
	
	@Select("SELECT DATE_FORMAT(time,'%Y-%m') time,count(0) count \n" + 
			"from content GROUP BY DATE_FORMAT(time,'%Y-%m')")
	List<Map<String, Object>> selectCountYearMonth();

	@Select("SELECT * FROM content WHERE YEAR(time)=#{year} AND MONTH(time)=#{month}")
	List<Content> ContentListBytime(int year, int month);
	
	@Select("select DATE_FORMAT(ADDDATE(subdate(curdate(),date_format(curdate(),'%w')), INTERVAL help_topic_id DAY),'%Y-%m-%d')\n" + 
			"day from mysql.help_topic where help_topic_id <7 ORDER BY day")
	List<String> selectWeekDate();
	
	@Select("select sum(case when a.day=date_format(b.time,'%Y-%m-%d') then 1 else 0 end) cnt\n" + 
			"from\n" + 
			"(select DATE(ADDDATE(subdate(curdate(),date_format(curdate(),'%w')), INTERVAL help_topic_id DAY)) \n" + 
			"day from mysql.help_topic where help_topic_id <7) a\n" + 
			"left join ipnote b on a.day=date_format(b.time,'%Y-%m-%d')\n" + 
			"group by a.day")
	List<Integer> selectCountWeekDate();
	
}
