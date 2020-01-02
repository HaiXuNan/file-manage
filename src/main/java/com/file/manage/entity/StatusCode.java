package com.file.manage.entity;

public enum StatusCode {

    SUCCESS(0,"成功"),
    FAILURE(-9,"系统异常"),
    UPLOAD_FAIL(10010,"文件上传失败"),


    LOGIN_FAIL(20010,"登录失败"),
    REGISTER_FAIL(20011,"注册失败");

    int code;
    String msg;

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

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
