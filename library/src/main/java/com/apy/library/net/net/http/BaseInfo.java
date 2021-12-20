package com.apy.library.net.net.http;

/**
 * Create by JohnRambo at 2018/8/29
 */
///TODO 这里需要和返回数据的格式一致
public class BaseInfo {

    private String data;
    private int code;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
