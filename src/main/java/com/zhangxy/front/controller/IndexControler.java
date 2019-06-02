package com.zhangxy.front.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zhangxy.base.utils.IPUtils;
import com.zhangxy.front.service.IndexService;
import com.zhangxy.front.service.NavigationService;
import com.zhangxy.model.Content;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexControler {
	
	@Autowired
	private IndexService indexService;
	@Autowired
	private NavigationService navService;
	
	@GetMapping("/")
	public String index(Integer nid ,Integer pageNum, Model model,HttpServletRequest request) {
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客");
		PageInfo<Content> contentList = indexService.getContentList(nid, pageNum);
		model.addAttribute("conList", contentList);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		return "front/index";
	}
	

}
