package com.sisp.beans;

/**
 * Http返回数据实例
 */
public class HttpResponseEntity {
    private String code; // 状态码
    private Object data; // 数据
    private String message; // 信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
