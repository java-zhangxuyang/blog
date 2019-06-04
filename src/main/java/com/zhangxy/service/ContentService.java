package com.zhangxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangxy.mapper.ContentMapper;

@Service
public class ContentService {

	@Autowired
	private ContentMapper conMapper;
	
	
	
}
