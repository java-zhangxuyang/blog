package com.zhangxy.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxy.base.utils.BlogUtils;
import com.zhangxy.mapper.MessageMapper;
import com.zhangxy.model.Message;
import com.zhangxy.model.MessageExample;

@Service
public class MessageService {

	@Autowired
	private MessageMapper messMapper;
	
	public PageInfo<Message> getMessageListPage(Integer pageNum){
		pageNum = BlogUtils.page(pageNum, false);
		PageHelper.startPage(pageNum, 6);
		List<Message> list = messMapper.selectByExample(new MessageExample());
		PageInfo<Message> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	public List<Message> getMessageListPage9(){
		PageHelper.startPage(1, 9);
		List<Message> list = messMapper.selectByExample(new MessageExample());
		PageInfo<Message> pageInfo = new PageInfo<>(list);
		return pageInfo.getList();
	}
	public PageInfo<Message> getMessageListPage10(Integer pageNum){
		pageNum = BlogUtils.page(pageNum, false);
		PageHelper.startPage(pageNum, 10);
		List<Message> list = messMapper.selectByExample(new MessageExample());
		PageInfo<Message> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public Integer addMessage(Message mess) {
		mess.setTime(new Date());
		return messMapper.insertSelective(mess);
	}

	public Integer delMess(Integer id) {
		return messMapper.deleteByPrimaryKey(id);
	}

}
