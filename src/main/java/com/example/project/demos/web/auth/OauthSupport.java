package com.example.project.demos.web.auth;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.dao.SysUserDao;
import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;


/**
 * @author guanc
 * @version 创建时间：2024/5/9 9:41
 * @describe
 */
@Slf4j
@Component
public class OauthSupport {

    @Autowired
    private SysUserDao sysUserDao;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 校验token是否过期
     *
     * @param token
     * @return
     * @throws Throwable
     */
    public Boolean tokenIsExpired(String token) throws Throwable {
        SysUserInfo user = (SysUserInfo) redisTemplate.opsForValue().get(token);
        if (ObjectUtil.isEmpty(user)) {
            return true;
        }
        return false;
    }

    /**
     * 持久化token
     */
    public String persistenceToken(Long id) {
        //根据ID查询用户
        SysUserInfo user = sysUserDao.selectSysUserInfoById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException(500, ErrorCodeEnums.USER_IS_NOT_EXIST.getDesc(), null);
        }
        String token = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(token, user, 5, TimeUnit.MINUTES);
        return token;
    }


}