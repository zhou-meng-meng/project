package com.example.project.demos.web.controller;

import com.alibaba.fastjson.JSON;
import com.example.project.demos.web.Response.ResponseResult;
import com.example.project.demos.web.auth.OauthSupport;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.handler.RequestHandler;
import com.example.project.demos.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanc
 * @version 创建时间：2024/5/9 18:02
 * @describe
 */
@Slf4j
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private OauthSupport oauthSupport;

    @Autowired
    private RequestHandler requestHandler;

    @GetMapping("getUserInfoByToken")
    public ResponseResult<?> getUserInfoByToken(String token) throws Throwable {
        return ResponseResult.Builder.buildOk(Constants.SUCCESS_STR, oauthSupport.getUserInfoByToken(token));
    }

    @GetMapping("testGetUserInfo")
    public ResponseResult<?> testGetUserInfo() throws Throwable {
        UserLoginOutDTO userInfo = RequestHolder.getUserInfo();
        return ResponseResult.Builder.buildOk(JSON.toJSONString(userInfo));
    }
}
