package com.file.manage.exception;

import com.file.manage.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<String> castServerException(ServerException e) {
        log.debug(e.getMsg());
        return ResponseEntity.failure(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public String castException(Exception e) {
        e.printStackTrace();
        log.debug(e.getMessage());
        return e.getMessage();
    }
}
