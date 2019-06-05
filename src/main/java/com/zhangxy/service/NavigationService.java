package com.zhangxy.service;

import java.util.ArrayList;
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
	public List<Navigation> getNavigationListByCon(){
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(1);intList.add(5);intList.add(6);
		NavigationExample example = new NavigationExample();
		example.setOrderByClause(" sort asc ");
		example.createCriteria().andIdNotIn(intList);
		List<Navigation> list = navMapper.selectByExample(example);
		return list;
	}
	public Navigation getNavigationById(Integer nid){
		Navigation nav = navMapper.selectByPrimaryKey(nid);
		return nav;
	}
	public Integer delNav(Integer id) {
		return navMapper.deleteByPrimaryKey(id);
	}
	public void updateNav(Navigation nav) {
		navMapper.updateByPrimaryKeySelective(nav);
	}
	public void addNav(Navigation nav) {
		navMapper.insertSelective(nav);
	}

}
