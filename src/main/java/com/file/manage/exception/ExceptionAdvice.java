package com.file.manage.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ServerException.class)
    public String castServerException(ServerException e) {

        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String castException(Exception e) {

        return e.getMessage();
    }
}
