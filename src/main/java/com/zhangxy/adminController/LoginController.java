package com.zhangxy.adminController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangxy.base.controler.BaseController;
import com.zhangxy.base.support.ResponseBo;
import com.zhangxy.model.User;
import com.zhangxy.service.LoginSerivce;

@Controller
public class LoginController  extends BaseController{
	
	@Autowired
	private LoginSerivce loginSerivce;
	
	@GetMapping("/admin")
	public String admin() {
		return "/admin/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Object login(User user, HttpServletRequest request) {
		Integer error_count = cache.get("login_error_count");
        if (error_count==null || error_count < 3) {
            ResponseBo responseBo = loginSerivce.login(user,request);
            if (responseBo.getCode() == -1) {
                error_count = null == error_count ? 1 : error_count + 1;
                cache.set("login_error_count", error_count, 10 * 60);
            }else {
            	cache.del("login_error_count");
            }
            return responseBo;
        }
        return ResponseBo.fail("您输入密码已经错误超过3次，请10分钟后尝试");
	}

}
