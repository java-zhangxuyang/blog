package com.zhangxy.service;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class TestService {

	@Test
	public void test() {
		String md5 = "zhangxy" + "199405";
		String encodeStr=DigestUtils.md5DigestAsHex(md5.getBytes());
		System.out.println(encodeStr);
	}
}
