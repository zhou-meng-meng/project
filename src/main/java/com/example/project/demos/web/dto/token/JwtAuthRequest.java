package com.example.project.demos.web.dto.token;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guanc
 * @version 创建时间：2024/5/8 16:16
 * @describe
 */
@Data
public class JwtAuthRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 工号/登录名
     */
    private String userLogin;
    /**
     * 密码
     */
    private String password;
}
