package com.example.project.demos.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author guanc
 * @version 创建时间：2024/5/9 14:44
 * @describe
 */

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String errorMsg;
}
