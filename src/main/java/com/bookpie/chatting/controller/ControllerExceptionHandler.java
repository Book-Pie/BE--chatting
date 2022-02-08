package com.bookpie.chatting.controller;

import com.bookpie.chatting.utils.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler({IllegalStateException.class,IllegalArgumentException.class})
    protected ResponseEntity handle(Exception e){
        log.info(e.getMessage());
        return new ResponseEntity(ApiUtil.error(e, HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
}
