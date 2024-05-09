package com.example.project.demos.web.exception;

import lombok.Data;

/**
 * @author guanc
 * @version 创建时间：2024/5/9 14:24
 * @describe
 */
@Data
public class CustomException extends RuntimeException {
    private int code;
    private String message;
    private String reqId;

    public CustomException(int code, String message,String reqId) {
        super(message);
        this.code = code;
        this.message = message;
        this.reqId = reqId;
    }

}