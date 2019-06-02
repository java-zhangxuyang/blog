package com.zhangxy.front.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxy.mapper.CenterMapper;
import com.zhangxy.mapper.ContentMapper;
import com.zhangxy.mapper.TagsMapper;
import com.zhangxy.model.Content;
import com.zhangxy.model.ContentExample;
import com.zhangxy.model.Tags;
import com.zhangxy.model.TagsExample;
import com.zhangxy.model.center;

@Service
public class IndexService {
	
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private CenterMapper centerMapper;
	@Autowired
	private TagsMapper tagsMapper;
	
	/**
	  * 获取文章列表  （分页）
	 * @param pageNum 页数
	 * @param pageNum 
	 * @return
	 */
	public PageInfo<Content> getContentList(Integer nid, Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		nid = nid == null ? 1 : nid;
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" top desc, time desc");
		example.createCriteria().andNidEqualTo(nid);
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByTid(content.getId());
			if(!tidList.isEmpty() && tidList.size() > 0) {
				for (center cen : tidList) {
					Tags tag = tagsMapper.selectByPrimaryKey(cen.getTid());
					if(null != tag) {
						tagList.add(tag.getName());
					}
				}
			}
			content.setTagList(tagList);
		}
		return pageInfo;
	}
	
	/**
	  * 获取使用量前20的标签
	 * @return
	 */
	public List<Tags> getTagList(){
		PageHelper.startPage(1, 20);
		TagsExample example = new TagsExample();
		example.setOrderByClause(" count desc");
		List<Tags> list = tagsMapper.selectByExample(example);
		PageInfo<Tags> pageInfo = new PageInfo<>(list);
		return pageInfo.getList();
	}

}
