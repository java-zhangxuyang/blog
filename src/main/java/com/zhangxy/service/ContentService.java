package com.zhangxy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxy.mapper.ContentMapper;
import com.zhangxy.model.Content;
import com.zhangxy.model.ContentExample;

@Service
public class ContentService {

	@Autowired
	private ContentMapper conMapper;

	public List<Content> getConHotList() {
		PageHelper.startPage(1, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" look desc, time desc");
		List<Content> list = conMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo.getList();
	}
	
	
	
}
