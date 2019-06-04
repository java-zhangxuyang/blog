package com.zhangxy.frontController;

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
import com.zhangxy.mapper.TagsMapper;
import com.zhangxy.model.Content;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;
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
	
	@GetMapping({"/","/index"})
	public String index(String likeName, Integer tid, Integer nid ,Integer pageNum, Model model,HttpServletRequest request) {
		nid = nid == null ? 1 : nid;
		String ip = IPUtils.getIpAddrByRequest(request);
		log.info("ip:" + ip + "访问博客");
		if(StringUtil.isNotBlank(likeName)) {
			PageInfo<Content> contentList = indexService.getContentLikeName(likeName, pageNum);
			model.addAttribute("conList", contentList);
			Navigation nav = new Navigation();
			nav.setName(likeName);
			model.addAttribute("nav", nav);
		} else if(tid != null) {
			Tags tag = indexService.getTagByTid(tid);
			PageInfo<Content> contentList = indexService.getTagListById(tid, pageNum);
			model.addAttribute("conList", contentList);
			Navigation nav = new Navigation();
			nav.setName(tag.getName());
			model.addAttribute("nav", nav);
		} else {
			Navigation nav = navService.getNavigationById(nid);
			model.addAttribute("nav", nav==null?new Navigation():nav);
			PageInfo<Content> contentList = indexService.getContentList(nid, pageNum);
			model.addAttribute("conList", contentList);
		}
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		return "front/index";
	}
	
	@GetMapping("/detailed")
	public String goDetailed(Model model,Integer id) {
		List<Tags> tagList = indexService.getTagList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		Content con = indexService.lookAdd(id);
		model.addAttribute("con", con);
		return "front/content";
	}

}
