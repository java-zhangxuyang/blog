package com.zhangxy.frontController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zhangxy.base.controler.BaseController;
import com.zhangxy.base.utils.IPUtils;
import com.zhangxy.model.Content;
import com.zhangxy.model.Message;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;
import com.zhangxy.service.ContentService;
import com.zhangxy.service.IndexService;
import com.zhangxy.service.MessageService;
import com.zhangxy.service.NavigationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/front")
public class FrontController  extends BaseController{

	@Autowired
	private IndexService indexService;
	@Autowired
	private NavigationService navService;
	@Autowired
	private MessageService messService;
	@Autowired
	private ContentService consService;
	
	@GetMapping("/share")
	public String share(Integer pageNum, Integer nid, Model model,HttpServletRequest request) {
		nid = nid == null ? 2 : nid;
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客分享");
		PageInfo<Content>  contentList = indexService.getContentListByNid(nid,pageNum);
		Navigation nav = navService.getNavigationById(nid);
		model.addAttribute("nav", nav);
		model.addAttribute("conList", contentList);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		return "front/index";
	}
	
	@GetMapping("/error")
	public String error(Integer pageNum, Integer nid, Model model,HttpServletRequest request) {
		nid = nid == null ? 3 : nid;
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客:错误笔记");
		PageInfo<Content>  contentList = indexService.getContentListByNid(nid,pageNum);
		Navigation nav = navService.getNavigationById(nid);
		model.addAttribute("nav", nav);
		model.addAttribute("conList", contentList);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		return "front/index";
	}
	
	@GetMapping("/note")
	public String note(Integer pageNum, Integer nid, Model model,HttpServletRequest request) {
		nid = nid == null ? 4 : nid;
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客:错误笔记");
		PageInfo<Content>  contentList = indexService.getContentListByNid(nid,pageNum);
		Navigation nav = navService.getNavigationById(nid);
		model.addAttribute("nav", nav);
		model.addAttribute("conList", contentList);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		return "front/index";
	}
	
	@GetMapping("/resume")
	public String resume(Integer pageNum, Integer nid, Model model,HttpServletRequest request) {
		nid = nid == null ? 5 : nid;
		Navigation nav = navService.getNavigationById(nid);
		model.addAttribute("nav", nav);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "试图访问简历");
		return "front/resumeCheck";
	}
	@PostMapping("resumeCheck")
	@ResponseBody
	public Integer resumeCheck(String pass) {
		if("zhangxy".equals(pass)) {
			cache.set("resume_pass_check", pass, 10 * 60);
			return 1;
		}
		return 0;
	}
	@GetMapping("toResume")
	public String toResume(Model model,HttpServletRequest request) {
		String pass = cache.get("resume_pass_check");
		if(null != pass) {
			List<Tags> tagList = indexService.getTagList();
			model.addAttribute("tagList", tagList);
			List<Navigation> navList = navService.getNavigationList();
			model.addAttribute("navList", navList);
			Navigation nav = navService.getNavigationById(5);
			model.addAttribute("nav", nav);
			String ip = IPUtils.getIpAddrByRequest(request);
			List<Map<String, Object>> arrList = consService.selectCountYearMonth();
			model.addAttribute("arrList", arrList);
			log.info("ip:" + ip + "查看了简历");
			return "front/resume";
		}
		return "redirect:/front/resume";
	}
	@PostMapping("addmessage")
	@ResponseBody
	public Integer addmessage(Message mess) {
		return messService.addMessage(mess);
	}
	
	@GetMapping("/message")
	public String message(Integer pageNum, Integer nid, Model model,HttpServletRequest request) {
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		nid = nid == null ? 6 : nid;
		Navigation nav = navService.getNavigationById(nid);
		model.addAttribute("nav", nav);
		PageInfo<Message> pageInfo = messService.getMessageListPage(pageNum);
		model.addAttribute("mess", pageInfo);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问微博留言");
		model.addAttribute("ip", ip);
		return "front/message";
	}
}
