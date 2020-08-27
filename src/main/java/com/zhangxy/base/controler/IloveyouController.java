package com.zhangxy.base.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IloveyouController {

	/*
	 * @GetMapping("/hxp") public String hxp() { return "hxp/index"; }
	 */
	
	@GetMapping("/ltq")
	public String ltq() {
		return "ltq/index";
	}
	
	@GetMapping("/wxy")
	public String wxy() {
		return "wxy/index";
	}
	
	@GetMapping("/love")
	public String love() {
		return "love/index";
	}
	
	/*
	 * @GetMapping("/xjr") public String xjr() { return "xjr/index"; }
	 */
}
