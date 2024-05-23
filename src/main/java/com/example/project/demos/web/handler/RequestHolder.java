package com.example.project.demos.web.handler;

import cn.hutool.http.server.HttpServerRequest;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author guanc
 * @version 创建时间：2024/5/20 16:56
 * @describe
 */
public class RequestHolder {

    public  static UserLoginOutDTO getUserInfo()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            return null;
        }
        Object userInfo = attributes.getRequest().getAttribute("user");
        if(ObjectUtils.isEmpty(userInfo)){
            return null;
        }
         return (UserLoginOutDTO) userInfo;
    }
}
