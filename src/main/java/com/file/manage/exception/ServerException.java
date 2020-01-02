package com.file.manage.exception;

import com.file.manage.entity.StatusCode;

public class ServerException extends RuntimeException {

    private int code;

    private String msg;

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

    public ServerException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServerException(int code,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServerException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.msg = statusCode.getMsg();
        this.code = statusCode.getCode();
    }
}
