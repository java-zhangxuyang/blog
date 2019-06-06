package com.zhangxy.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import jodd.datetime.JDateTime;

public class TestService {

	@Test
	public void test() {
		String md5 = "zhangxy" + "199405";
		String encodeStr=DigestUtils.md5DigestAsHex(md5.getBytes());
		System.out.println(encodeStr);
	}
	@Test
	public void testTime() {
		String strDate="2019-06";
        //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
		JDateTime datetime = new JDateTime(strDate);
		System.out.println(datetime.getYear());
		System.out.println(datetime.getMonth());
	}
}
