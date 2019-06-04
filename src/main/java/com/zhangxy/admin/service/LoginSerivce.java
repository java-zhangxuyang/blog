package com.zhangxy.admin.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.zhangxy.base.constant.WebConst;
import com.zhangxy.base.support.ResponseBo;
import com.zhangxy.model.User;

@Service
public class LoginSerivce {
	
	
	@SuppressWarnings("rawtypes")
	public ResponseBo login(User user, HttpServletRequest request){
		
		if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            return ResponseBo.fail("用户名和密码不能为空");
        }
		
		String md5 = user.getUserName() + user.getPassword();
		String encodeStr=DigestUtils.md5DigestAsHex(md5.getBytes());
		
		if(user.getUserName().equals(WebConst.USERNAME) && WebConst.PWD_SALT.equals(encodeStr)){
			request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, "admin");
			request.getSession().setAttribute(WebConst.LOGIN_SESSION_USER, user);
			return ResponseBo.ok();
		}
		ResponseBo responseBo = ResponseBo.fail(-1);
        responseBo.setMsg("用户名或密码错误");
        return responseBo;
	}
}
