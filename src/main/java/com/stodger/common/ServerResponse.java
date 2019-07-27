package com.stodger.common;

/**
 * 请求返回
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 16:33
 */
public class ServerResponse<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
