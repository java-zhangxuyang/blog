package com.zhangxy.adminController;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zhangxy.model.Content;
import com.zhangxy.model.User;
import com.zhangxy.service.ContentService;
import com.zhangxy.service.LoginSerivce;
import com.zhangxy.service.NavigationService;
import com.zhangxy.service.TagsService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private NavigationService navService;
	@Autowired
	private ContentService conService;
	@Autowired
	private TagsService tagService;
	@Autowired
	private LoginSerivce loginSerivce;
	
	@GetMapping("/index")
	public String index(Model model,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if(null == user) {
			return "/admin";
		}
		model.addAttribute("user", user);
		return "/admin/index";
	}
	
	@PostMapping("/loginOut")
	@ResponseBody
	public Object loginOut(HttpServletRequest request) {
        return loginSerivce.loginOut(request);
	}
	
	@GetMapping("/welcome")
	public String welcome(Model model,HttpServletRequest request) {
		List<Content> contentList = conService.getConHotList();
		model.addAttribute("conList", contentList);
		return "/admin/iframe/welcome";
	}
	

}
