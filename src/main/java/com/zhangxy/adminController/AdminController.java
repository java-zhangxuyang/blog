package com.zhangxy.adminController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zhangxy.service.ContentService;
import com.zhangxy.service.NavigationService;
import com.zhangxy.service.TagsService;

@Controller
public class AdminController {
	
	@Autowired
	private NavigationService navService;
	@Autowired
	private ContentService conService;
	@Autowired
	private TagsService tagService;
	
	@GetMapping("/admin")
	public String admin() {
		return "/admin/login";
	}
	
	

}
