package com.zhangxy.service;

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

import jodd.datetime.JDateTime;

@Service
public class IndexService {
	
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private CenterMapper centerMapper;
	@Autowired
	private TagsMapper tagsMapper;
	@Autowired
	private SolrService solrService;
	
	/**
	  * 根据导航栏id获取文章列表  （分页）
	 * @param pageNum 页数
	 * @param pageNum 
	 * @return
	 */
	public PageInfo<Content> getContentListByNid(Integer nid, Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" top desc, time desc");
		example.createCriteria().andNidEqualTo(nid);
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
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
	public PageInfo<Content> getContentList(Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" top desc, time desc");
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
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
	public PageInfo<Content> getContentListBytime(Integer pageNum,String time){
		pageNum = pageNum == null ? 1 :pageNum;
		PageHelper.startPage(pageNum, 10);
		JDateTime date = new JDateTime(time);
		List<Content> list = centerMapper.ContentListBytime(date.getYear(),date.getMonth());
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
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
	 * 根据title模糊查询 获取文章列表  （分页）
	 * @param pageNum 页数
	 * @param pageNum 
	 * @return
	 */
	public PageInfo<Content> getContentLikeName(String likeName, Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" top desc, time desc");
		example.createCriteria().andTitleLike("%"+likeName+"%");
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
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
	
	public PageInfo<Content> getTagListById(Integer tid,Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		List<center> cenList = centerMapper.getTagListByTid(tid);
		List<Integer> cidList = this.getCidByCenList(cenList);
		PageHelper.startPage(pageNum, 10);
		ContentExample example = new ContentExample();
		example.setOrderByClause(" top desc, time desc");
		if(cidList!=null&&cidList.size()>0) {
			example.createCriteria().andIdIn(cidList);
		}
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
			if(!tidList.isEmpty() && tidList.size() > 0) {
				for (center cen : tidList) {
					Tags tag = tagsMapper.selectByPrimaryKey(cen.getTid());
					if(null != tag) {
						tagList.add(tag.getName());
					}
				}
			}
			content.setContent(content.getContent().substring(0, 100));
			content.setTagList(tagList);
		}
		return pageInfo;
	}
	
	public List<Integer> getCidByCenList(List<center> list){
		List<Integer> arrlist = new ArrayList<>();
		for (center cen : list) {
			arrlist.add(cen.getCid());
		}
		return arrlist;
	}
	public Tags getTagByTid(Integer tid){
		Tags tag = tagsMapper.selectByPrimaryKey(tid);
		return tag;
	}
	public Content getContentById(Integer id){
		Content con = contentMapper.selectByPrimaryKey(id);
		return con;
	}
	public Content lookAdd(Integer id) {
		Content con = this.getContentById(id);
		con.setLook(con.getLook() + 1);
		contentMapper.updateByPrimaryKeySelective(con);
		return con;
	}
	
	public PageInfo<Content> getSolrContentList(String likeName,Integer pageNum){
		pageNum = pageNum == null ? 1 :pageNum;
		PageHelper.startPage(pageNum, 10);
		List<Content> conList=new ArrayList<Content>();
		try {
			conList = solrService.querySolr(likeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<Content> pageInfo = new PageInfo<>(conList);
		List<Content> resule = pageInfo.getList();
		for (Content content : resule) {
			List<String> tagList = new ArrayList<>();
			List<center> tidList = centerMapper.getTagListByCid(content.getId());
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

}
