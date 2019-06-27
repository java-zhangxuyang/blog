package com.zhangxy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxy.mapper.CenterMapper;
import com.zhangxy.mapper.ContentMapper;
import com.zhangxy.mapper.NavigationMapper;
import com.zhangxy.model.Content;
import com.zhangxy.model.ContentExample;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;
import com.zhangxy.model.center;

import jodd.datetime.JDateTime;

@Service
public class ContentService {

	@Autowired
	private ContentMapper conMapper;
	@Autowired
	private NavigationMapper navMapper;
	@Autowired
	private CenterMapper cenmMapper;
	@Autowired
	private TagsService tagService;

	/**
	 * 获取最热10篇文章 后端主页显示
	 */
	public List<Content> getConHotList() {
		PageHelper.startPage(1, 9);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" look desc, time desc");
		List<Content> list = conMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo.getList();
	}
	
	/**
	 * @return 后端获取文章列表 分页
	 */
	public PageInfo<Content> getConHotListByPage(Integer pageNum) {
		pageNum = pageNum == null ? 1 : pageNum;
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause("time desc");
		List<Content> list = conMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> conlist = pageInfo.getList();
		for (Content con : conlist) {
			Navigation nav = navMapper.selectByPrimaryKey(con.getNid());
			if(null != nav) {
				con.setNav(nav.getName());
			}
		}
		pageInfo.setList(conlist);
		return pageInfo;
	}
	
	public Content getConById(Integer id) {
		Content con = conMapper.selectByPrimaryKey(id);
		List<center> list = cenmMapper.getTagListByCid(id);
		List<Integer> tidList = new ArrayList<>();
		for (center center : list) {
			tidList.add(center.getTid());
		}
		con.setTidList(tidList);
		return con;
	}

	public Integer delCon(Integer id) {
		List<center> list =cenmMapper.getTagListByCid(id);
		for (center center : list) {
			Tags tag = tagService.getTagsById(center.getTid());
			tag.setCount(tag.getCount() - 1);
			tagService.updateTag(tag);
		}
		return conMapper.deleteByPrimaryKey(id);
	}

	public Integer updateCom(Content con) {
		int i = conMapper.updateByPrimaryKeySelective(con);
		if(con.getTidList() != null && con.getTidList().size() > 0) {
			cenmMapper.deleteTagByCid(con.getId());
			for (Integer tid : con.getTidList()) {
				cenmMapper.insertTag(con.getId(), tid);
			}
		}
		return i;
	}

	public Integer addCom(Content con) {
		con.setTime(new Date());
		int i = conMapper.insertSelective(con);
		if(i > 0 && con.getTidList() != null && con.getTidList().size() > 0) {
			for (Integer tid : con.getTidList()) {
				cenmMapper.insertTag(con.getId(), tid);
				Tags tag = tagService.getTagsById(tid);
				tag.setCount(tag.getCount() + 1);
				tagService.updateTag(tag);
			}
		}
		return i;
	}
	
	public List<Integer> selectCountMonth(){
		return cenmMapper.selectCountMonth();
	}
	
	public List<Map<String, Object>> selectCountYearMonth(){
		List<Map<String, Object>> list = cenmMapper.selectCountYearMonth();
		PageHelper.startPage(1, 12);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		return pageInfo.getList();
	}
	
	public Map<String, Object> selectIpCountWeek(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> datelist = cenmMapper.selectWeekDate();
		StringBuffer dateJson = new StringBuffer();
		dateJson.append("['");
		for (String date : datelist) {
			dateJson.append(date+"','") ;
		}
		dateJson.deleteCharAt(dateJson.length() - 1);
		dateJson.deleteCharAt(dateJson.length() - 1);
		dateJson.append("]");
		List<Integer> countlist = cenmMapper.selectCountWeekDate();
		map.put("datelist", dateJson);
		map.put("count", countlist);
		return map;
	}
	
	public List<Content> getConListAll() {
		ContentExample example = new ContentExample();
		example.setOrderByClause(" look desc, time desc");
		List<Content> list = conMapper.selectByExample(example);
		return list;
	}
	
	
}
