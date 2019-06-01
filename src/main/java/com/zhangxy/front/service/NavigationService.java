package com.zhangxy.front.service;

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
		List<Navigation> list = navMapper.selectByExample(new NavigationExample());
		return list;
	}

}
