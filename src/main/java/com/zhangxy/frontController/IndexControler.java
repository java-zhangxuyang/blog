package com.zhangxy.frontController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.pagehelper.PageInfo;
import com.zhangxy.base.utils.IPUtils;
import com.zhangxy.model.Content;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;
import com.zhangxy.service.ContentService;
import com.zhangxy.service.IndexService;
import com.zhangxy.service.NavigationService;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexControler {
	
	@Autowired
	private IndexService indexService;
	@Autowired
	private NavigationService navService;
	@Autowired
	private ContentService consService;
	
	@GetMapping({"/","/index"})
	public String index(String likeName,String time, Integer nid, Integer tid,Integer pageNum, Model model,HttpServletRequest request) {
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客");
		if(StringUtil.isNotBlank(likeName)) {
			PageInfo<Content> contentList = indexService.getSolrContentList(likeName, pageNum);
			model.addAttribute("conList", contentList);
			Navigation nav = new Navigation();
			nav.setId(1);
			nav.setName(likeName);
			model.addAttribute("nav", nav);
		} else if(tid != null) {
			Tags tag = indexService.getTagByTid(tid);
			PageInfo<Content> contentList = indexService.getTagListById(tid, pageNum);
			model.addAttribute("conList", contentList);
			Navigation nav = new Navigation();
			nav.setId(1);
			nav.setName(tag.getName());
			model.addAttribute("nav", nav);
		} else if(StringUtil.isNotBlank(time)){
			PageInfo<Content>  contentList = indexService.getContentListBytime(pageNum,time);
			Navigation nav = new Navigation();
			nav.setId(1);
			nav.setName(time);
			model.addAttribute("nav", nav);
			model.addAttribute("conList", contentList);
		} else {
			nid = nid == null? 1 : nid;
			PageInfo<Content>  contentList = indexService.getContentList(pageNum);
			Navigation nav = navService.getNavigationById(nid);
			nav.setName("最新文章");
			model.addAttribute("nav", nav);
			model.addAttribute("conList", contentList);
		}
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		return "front/index";
	}
	
	@GetMapping("/detailed")
	public String goDetailed(Model model,Integer id,HttpServletRequest request) {
		String ip = IPUtils.getIpAddrByRequest(request);
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		Content con = indexService.lookAdd(id,ip);
		model.addAttribute("con", con);
		List<Map<String, Object>> arrList = consService.selectCountYearMonth();
		model.addAttribute("arrList", arrList);
		log.info("ip:" + ip + "查看了"+con.getTitle());
		return "front/content";
	}

}
