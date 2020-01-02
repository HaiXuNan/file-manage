package com.file.manage.entity;


public enum StatusCode {

    SUCCESS(0,"成功"),
    FAILURE(-9,"系统异常");

    int code;
    String msg;

    StatusCode(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
