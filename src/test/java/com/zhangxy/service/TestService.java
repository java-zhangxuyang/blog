package com.zhangxy.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import com.zhangxy.base.utils.FtpUtil;

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
	@Test
	public void testFTP() {
		FTPClient ftp = new FTPClient();
		try {
		ftp.connect("www.youngzhang.cn");// 连接FTP服务器
        // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
        ftp.login("ftpuser", "ftpuser");// 登录
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            System.out.println("false");
        }
        String basePath = "/home/www/image";
        String filePath = "/2019/06/08";
            
        	//切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            System.out.println("失败");
                            break;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            System.out.println("成功");
	    }catch (Exception e) {
	      e.printStackTrace();
	    }finally {
	      if(ftp != null) {
	        try {
	          ftp.logout();
	          ftp.disconnect();
	        }
	        catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	  }


}
