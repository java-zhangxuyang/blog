package com.zhangxy.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zhangxy.mapper.IpNoteMapper;
import com.zhangxy.model.IpNote;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IpNoteService {

	@Autowired
	private IpNoteMapper ipMapper;
	
	@Async
	public void handleIpNote(String ip) {
		if("0:0:0:0:0:0:0:1".equals(ip)) {
			return;
		}else {
			IpNote note = this.getCountByIp(ip);
			if(null == note) {
				this.addIpNote(ip);
			}else {
				this.updateIpNote(note);
			}
		}
	}
	
	public IpNote getCountByIp(String ip) {
		return ipMapper.getCountByIp(ip);
	}
	
	public Integer addIpNote(String ip) {
		IpNote note = new IpNote();
		note.setIp(ip);
		note.setTime(new Date());
		note.setCount(0);
		return ipMapper.insertSelective(note);
	}
	public Integer updateIpNote(IpNote note) {
		note.setTime(new Date());
		note.setCount(note.getCount()+1);
		return ipMapper.updateByPrimaryKeySelective(note);
	}
	
	
}
