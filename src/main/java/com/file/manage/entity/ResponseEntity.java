package com.file.manage.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseEntity<T> {

    private int code;

    private String msg;

    private T result;

    public static ResponseEntity<String> success() {
        return new ResponseEntity<>(StatusCode.SUCCESS.code, StatusCode.SUCCESS.msg, "");
    }

    public static <T> ResponseEntity<T> success(T result) {
        return new ResponseEntity<>(StatusCode.SUCCESS.code, StatusCode.SUCCESS.msg, result);
    }

    public static <T> ResponseEntity<T> success(StatusCode statusCode, T result) {
        return new ResponseEntity<>(statusCode.code, statusCode.msg, result);
    }

    public static <T> ResponseEntity<T> failure(T result) {
        return new ResponseEntity<>(StatusCode.FAILURE.code, StatusCode.FAILURE.msg, result);
    }

    public static ResponseEntity<String> failure() {
        return new ResponseEntity<>(StatusCode.FAILURE.code, StatusCode.FAILURE.msg, null);
    }
}
