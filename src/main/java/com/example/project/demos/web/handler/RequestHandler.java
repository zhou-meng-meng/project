package com.example.project.demos.web.handler;

import com.example.project.demos.web.auth.OauthSupport;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class RequestHandler {

    @Autowired
    private OauthSupport oauthSupport;

    public UserLoginOutDTO getUserInfo() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            return oauthSupport.getUserInfoByToken(token);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
