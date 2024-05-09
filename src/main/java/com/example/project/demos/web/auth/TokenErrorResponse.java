package com.example.project.demos.web.auth;

import lombok.Data;

/**
 * @author guanc
 * @version 创建时间：2024/5/9 14:09
 * @describe
 */
@Data
public class TokenErrorResponse {
    private int code;
    private String message;

    public TokenErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
