package com.example.project.demos.web.utils;

/**
 * @author guanc
 * @version 创建时间：2024/6/3 8:38
 * @describe
 */
public class TokenUtils {
    /**
     * 用户登录token
     */
    public static final String USER_LOGIN_TOKEN = "USER:LOGIN:%s";

    /**
     * 获取token前缀
     * @param tokenClass 大类
     * @param tokenType 小类
     * @return
     */
    public static String buildToken(String tokenClass,String tokenType) {
        return String.format(tokenClass, tokenType);
    }


}
