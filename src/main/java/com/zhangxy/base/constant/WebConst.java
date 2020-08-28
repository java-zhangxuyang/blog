package com.zhangxy.base.constant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebConst {

    public static Map<String, String> initConfig = new HashMap<>();

    public static String LOGIN_SESSION_KEY = "loginUser";
    
    public static String LOGIN_SESSION_USER = "user";

    public static final String USER_IN_COOKIE = "S_L_ID";
    
    public static final Integer INDEX = 1;
    public static final Integer SHARE = 2;
    public static final Integer ERROR = 3;
    public static final Integer NOTE = 4;
    public static final Integer RESUME = 5;
    public static final Integer MESSAGE = 6;

    /**
         * 管理帐号
     */
	public static String USERNAME = "zhangxy";
    /**
     * md5加密密码
     */
    public static String PWD_SALT = "ec350a3713ae2266e91c2c3aa141e129";//zhangxy

    /**
     * 最大获取文章条数
     */
    public static final int MAX_POSTS = 9999;

    /**
     * 最大页码
     */
    public static final int MAX_PAGE = 100;

    /**
     * 文章最多可以输入的文字数
     */
    public static final int MAX_TEXT_COUNT = 200000;

    /**
     * 文章标题最多可以输入的文字个数
     */
    public static final int MAX_TITLE_COUNT = 200;

    /**
     * 点击次数超过多少更新到数据库
     */
    public static final int HIT_EXCEED = 10;

    /**
     * 上传文件最大1M
     */
    public static Integer MAX_FILE_SIZE = 1048576;

    /**
     * 成功返回
     */
    public static String SUCCESS_RESULT = "SUCCESS";

    /**
     * 同一篇文章在2个小时内无论点击多少次只算一次阅读
     */
    public static Integer HITS_LIMIT_TIME = 7200;
}
