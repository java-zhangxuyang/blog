package com.zhangxy.adminController;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zhangxy.model.Content;
import com.zhangxy.model.Message;
import com.zhangxy.model.Navigation;
import com.zhangxy.model.Tags;
import com.zhangxy.model.User;
import com.zhangxy.service.ContentService;
import com.zhangxy.service.LoginSerivce;
import com.zhangxy.service.MessageService;
import com.zhangxy.service.NavigationService;
import com.zhangxy.service.SolrService;
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
	@Autowired
	private MessageService mesService;
	@Autowired
	private SolrService solrService;
	@Autowired
	private MessageService messService;
	
	/**
	 * 后端管理主页
	 */
	@GetMapping("/index")
	public String index(Model model,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if(null == user) {
			return "/admin";
		}
		model.addAttribute("user", user);
		return "/admin/index";
	}
	
	/**
	 * 后端管理推出登陆
	 */
	@PostMapping("/loginOut")
	@ResponseBody
	public Object loginOut(HttpServletRequest request) {
        return loginSerivce.loginOut(request);
	}
	
	/**
	 * TO 后端管理主页欢迎页面
	 */
	@GetMapping("/welcome")
	public String welcome(Model model,HttpServletRequest request) {
		List<Content> contentList = conService.getConHotList();
		model.addAttribute("conList", contentList);
		List<Integer> list = conService.selectCountMonth();
		model.addAttribute("data", list);
		Map<String, Object> dateMap = conService.selectIpCountWeek();
		model.addAttribute("dateMap", dateMap);
		List<Message> messlist = messService.getMessageListPage9();
		model.addAttribute("mess", messlist);
		return "/admin/iframe/welcome";
	}
	
	/**
	 * TO 后端管理文章列表页面
	 */
	@GetMapping("/contentList")
	public String contentList(Integer pageNum, Model model,HttpServletRequest request) {
		PageInfo<Content> info = conService.getConHotListByPage(pageNum);
		model.addAttribute("info", info);
		return "/admin/iframe/contentList";
	}
	/**
	 * TO 后端管理文章列表页面
	 */
	@GetMapping("/editOrAddView")
	public String editOrAddView(Integer id, Model model,HttpServletRequest request) {
		Content con = new Content();
		if(null != id) {
			con =  conService.getConById(id);
			model.addAttribute("sign", 1); //1 - 修改
		}else {
			model.addAttribute("sign", 0); //0 - 增加
		}
		model.addAttribute("con", con);
		List<Tags> tagList = tagService.getTagsList();
		model.addAttribute("tagList", tagList);
		List<Navigation> navList = navService.getNavigationListByCon();
		model.addAttribute("navList", navList);
		return "/admin/iframe/editOrAddView";
	}
	/**
	 * TO 添加或者修改文章
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@PostMapping("/editOrAddCom")
	public String editOrAddCom(Content con) throws SolrServerException, IOException {
		con.setTime(new Date());
		if(null != con.getId()) {
			conService.updateCom(con);
			solrService.deleteDocumentById(con.getId());
			solrService.addDoc(con);
		}else {
			conService.addCom(con);
			solrService.addDoc(con);
		}
		return "redirect:/admin/contentList";
	}
	/**
	 * 根据id
	 * 删除导航栏类别
	 */
	@PostMapping("delNav")
	@ResponseBody
	public Integer delNav(Integer id) {
		return navService.delNav(id);
	}
	/**
	 * 根据id
	 * 删除文章类别
	 */
	@PostMapping("delCon")
	@ResponseBody
	public Integer delCon(Integer id) {
		return conService.delCon(id);
	}
	/**
	 * 根据id
	 * 删除标签类别
	 */
	@PostMapping("delTag")
	@ResponseBody
	public Integer delTag(Integer id) {
		return tagService.delTag(id);
	}
	/**
	 * TO 后端管理文章列表页面
	 */
	@GetMapping("/navList")
	public String navList(Model model,HttpServletRequest request) {
		List<Navigation> navList = navService.getNavigationList();
		model.addAttribute("navList", navList);
		return "/admin/iframe/navList";
	}
	/**
	 * TO 后端管理文章列表页面
	 */
	@GetMapping("/editOrAddNav")
	public String editOrAddNav(Integer id, Model model,HttpServletRequest request) {
		Navigation nav = new Navigation();
		if(null != id) {
			nav = navService.getNavigationById(id);
			model.addAttribute("sign", 1); //1 - 修改
		}else {
			model.addAttribute("sign", 0); //0 - 增加
		}
		model.addAttribute("nav", nav);
		return "/admin/iframe/editOrAddNav";
	}
	/**
	 * TO 后端管理文章列表页面
	 */
	@GetMapping("/tagsList")
	public String tagsList(Integer pageNum,Model model,HttpServletRequest request) {
		PageInfo<Tags> pageInfo = tagService.getTagsListByPage(pageNum);
		model.addAttribute("info", pageInfo);
		return "/admin/iframe/tagsList";
	}
	/**
	 * TO 后端管理标签编辑页面
	 */
	@GetMapping("/editOrAddTag")
	public String editOrAddTag(Integer id, Model model,HttpServletRequest request) {
		Tags tag = new Tags();
		if(null != id) {
			tag = tagService.getTagsById(id);
			model.addAttribute("sign", 1); //1 - 修改
		}else {
			model.addAttribute("sign", 0); //0 - 增加
		}
		model.addAttribute("tag", tag);
		return "/admin/iframe/editOrAddTag";
	}
	/**
	 * TO 添加或者修改标签
	 */
	@PostMapping("/editOrAddTag")
	public String editOrAddTag(Tags tags) {
		if(null != tags.getId()) {
			tagService.updateTag(tags);
		}else {
			tagService.addTag(tags);
		}
		return "redirect:/admin/tagsList";
	}
	/**
	 * TO 添加或者修改导航
	 */
	@PostMapping("/editOrAddNav")
	public String editOrAddNav(Navigation nav) {
		if(null != nav.getId()) {
			navService.updateNav(nav);
		}else {
			navService.addNav(nav);
		}
		return "redirect:/admin/navList";
	}
	/**
	 * TO 后端留言列表页面
	 */
	@GetMapping("/messList")
	public String messList(Integer pageNum, Model model,HttpServletRequest request) {
		PageInfo<Message> info = mesService.getMessageListPage10(pageNum);
		model.addAttribute("info", info);
		return "/admin/iframe/messList";
	}
	
	/**
	 * 根据id
	 * 删除留言
	 */
	@PostMapping("delMess")
	@ResponseBody
	public Integer delMess(Integer id) {
		return mesService.delMess(id);
	}
	

}
