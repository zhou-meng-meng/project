package com.example.project.demos.web.handler;

import com.alibaba.fastjson.JSON;
import com.example.project.demos.web.exception.CustomException;
import com.example.project.demos.web.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author guanc
 * @version 创建时间：2024/5/9 14:26
 * @describe
 */
@Slf4j
@ControllerAdvice
public class TokenExceptionHandler {


    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
        ResponseEntity<ErrorResponse> result = new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
        log.error("░" + ex.getMessage());
        log.info("░ response  result: {}", JSON.toJSONString(result));
        log.info("░ request end, id: {}", ex.getReqId());
        log.info("░░░░░░░░░░░░░░░░  END BY EXCEPTION  ░░░░░░░░░░░░░░░░");
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
    }
}

