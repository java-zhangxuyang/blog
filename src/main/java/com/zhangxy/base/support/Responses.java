package com.zhangxy.base.support;

import java.util.HashMap;
import java.util.Map;

import jodd.util.StringUtil;

/**
 * AJAX响应工具类
 * Created by fanlei on 2014/12/19.
 */
public class Responses {

    public static Map<String, Object> success(Object data) {
        return msg("1", data);
    }

    public static Map<String, Object> success() {
        return success(null);
    }

    public static Map<String, Object> error(String errorMsg) {
        return msg("-1", errorMsg);
    }

    public static Map<String, Object> msg(String code, Object data) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", code);
        if (data != null) {
            ret.put("data", data);
        } else {
            ret.put("data", "");
        }
        return ret;
    }

    public static Map<String, Object> grid(boolean success, int totalRows, int curPage, Object data) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("success", success);
        ret.put("totalRows", totalRows);
        ret.put("curPage", curPage);
        ret.put("data", data);
        return ret;
    }

    public static Map<String, Object> valid(boolean success, String msg) {
        Map<String, Object> ret = new HashMap<>();
        if (success) {
            ret.put("ok", " ");
        } else {
            ret.put("error", StringUtil.toSafeString(msg));
        }
        return ret;
    }


}
