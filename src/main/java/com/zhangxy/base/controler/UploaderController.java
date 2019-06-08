package com.zhangxy.base.controler;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.zhangxy.base.utils.FtpUtil;
import com.zhangxy.base.utils.IPUtils;

import jodd.datetime.JDateTime;
import lombok.extern.slf4j.Slf4j;
/**
 * 照片上传工具类
 * @author admin
 *
 */
@Slf4j
@Controller
@RequestMapping("/img")
public class UploaderController extends BaseController{
	
	@Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;
    // 访问图片时的基础url
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;
    
    @ResponseBody
    @RequestMapping("/upload")
    public Object uploadPicture(@RequestParam(value="myFileName",required=false)MultipartFile file,HttpServletRequest request,HttpServletResponse response){
    	//1、给上传的图片生成新的文件名
        //1.1获取原始文件名
        String oldName = file.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = IPUtils.genImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        //1.3生成文件在服务器端存储的子目录
        String filePath = new JDateTime().toString("/YYYY/MM/DD");
        
        //2、把前端输入信息，包括图片的url保存到数据库
        String imageurl = baseUrl + filePath + "/" + newName;
        
        //3、把图片上传到图片服务器
        //3.1获取上传的io流
        InputStream input = null;
		try {
			input = file.getInputStream();
		} catch (IOException e) {
			log.info("图片上传出错！");
			e.printStackTrace();
		}
        
        //3.2调用FtpUtil工具类进行上传
        boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
        if(result) {
        	Map<String, Object> map = new HashMap();
            map.put("data", imageurl);
             String jo = JSONArray.toJSONString(map);
               return jo;
        }else {
            return "false";
        }
    }
}