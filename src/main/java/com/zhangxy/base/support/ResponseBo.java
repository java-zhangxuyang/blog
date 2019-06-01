package com.zhangxy.base.support;

/**
 * rest返回对象
 *^
 * @param <T>
 */
public class ResponseBo<T> {

    /**
     * 服务器响应数据
     */
    private T payload;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private int code = -1;

    /**
     * 服务器响应时间
     */
    private long timestamp;

    public ResponseBo() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public ResponseBo(boolean success) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
    }

    public ResponseBo(boolean success, T payload) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
    }

    public ResponseBo(boolean success, T payload, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
        this.code = code;
    }

    public ResponseBo(boolean success, String msg) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
        this.code = 1;
    }

    public ResponseBo(boolean success, String msg, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static ResponseBo ok() {
        return new ResponseBo(true);
    }

    public static ResponseBo ok(String msg) {
        return new ResponseBo(true,msg);
    }

    public static <T> ResponseBo ok(T payload) {
        return new ResponseBo(true, payload);
    }

    public static <T> ResponseBo ok(int code) {
        return new ResponseBo(true, null, code);
    }

    public static <T> ResponseBo ok(T payload, int code) {
        return new ResponseBo(true, payload, code);
    }

    public static ResponseBo fail() {
        return new ResponseBo(false);
    }

    public static ResponseBo fail(String msg) {
        return new ResponseBo(false, msg,-1);
    }

    public static ResponseBo fail(int code) {
        return new ResponseBo(false, null, code);
    }

    public static ResponseBo fail(int code, String msg) {
        return new ResponseBo(false, msg, code);
    }

}