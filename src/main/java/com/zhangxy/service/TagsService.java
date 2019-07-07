package com.zhangxy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxy.base.utils.BlogUtils;
import com.zhangxy.mapper.TagsMapper;
import com.zhangxy.model.Tags;
import com.zhangxy.model.TagsExample;

@Service
public class TagsService {

	@Autowired
	private TagsMapper tagsMapper;
	
	public List<Tags> getTagsList() {
		TagsExample example = new TagsExample();
		example.setOrderByClause(" count desc");
		List<Tags> list = tagsMapper.selectByExample(example);
		return list;
	}
	public PageInfo<Tags> getTagsListByPage(Integer pageNum) {
		pageNum = BlogUtils.page(pageNum, false);
		PageHelper.startPage(pageNum, 10);
		TagsExample example = new TagsExample();
		example.setOrderByClause(" count desc");
		List<Tags> list = tagsMapper.selectByExample(example);
		PageInfo<Tags> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	public Tags getTagsById(Integer id) {
		return tagsMapper.selectByPrimaryKey(id);
	}

	public Integer delTag(Integer id) {
		return tagsMapper.deleteByPrimaryKey(id);
	}

	public void updateTag(Tags tags) {
		tagsMapper.updateByPrimaryKeySelective(tags);
		
	}

	public void addTag(Tags tags) {
		tagsMapper.insertSelective(tags);
	}
	
}
