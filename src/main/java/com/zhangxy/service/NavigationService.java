package com.zhangxy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangxy.mapper.NavigationMapper;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.NavigationExample;

@Service
public class NavigationService {
	
	@Autowired
	private NavigationMapper navMapper;
	
	public List<Navigation> getNavigationList(){
		NavigationExample example = new NavigationExample();
		example.setOrderByClause(" sort asc ");
		List<Navigation> list = navMapper.selectByExample(example);
		return list;
	}
	public Navigation getNavigationById(Integer nid){
		Navigation nav = navMapper.selectByPrimaryKey(nid);
		return nav;
	}

}
