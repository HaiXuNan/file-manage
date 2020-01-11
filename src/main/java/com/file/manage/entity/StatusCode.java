package com.file.manage.entity;

public enum StatusCode {

    SUCCESS(0, "成功"),

    UPLOAD_FAIL(10010, "文件上传失败"),
    UPDATE_FILE_FAIL(10011, "更新文件失败"),
    UPDATE_TYPE_FAIL(10012, "文件格式不正确"),

    LOGIN_FAIL(20010, "登录失败"),
    USERNAME_PASSWORD_ERROR(20011, "用户名或密码错误"),
    REGISTER_FAIL(20012, "注册失败"),
    RESULT_FAIL(20013, "查询结果失败"),
    USERNAME_EXIST(20014, "用户名已存在"),
    ACCESS_LIMIT(20015, "接口访问频率限制"),

    FAILURE(-9, "系统异常");

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
