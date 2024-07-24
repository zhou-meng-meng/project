package com.example.project.demos.web.auth;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.dao.SysUserDao;
import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.exception.CustomException;
import com.example.project.demos.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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

    @Value("${token.expir}")
    public String expir;




    /**
     * 校验token是否过期
     *
     * @param token
     * @return
     * @throws Throwable
     */
    public boolean tokenIsExpired(String token) throws Throwable {
        UserLoginOutDTO user = (UserLoginOutDTO) redisTemplate.opsForValue().get(TokenUtils.buildToken(TokenUtils.USER_LOGIN_TOKEN, token));
        if (ObjectUtil.isEmpty(user)) {
            return true;
        }
        return false;
    }

    public UserLoginOutDTO getUserInfoByToken(String token) throws Throwable {
        return (UserLoginOutDTO) redisTemplate.opsForValue().get(TokenUtils.buildToken(TokenUtils.USER_LOGIN_TOKEN, token));
    }


    /**
     * 持久化token
     */
    @Deprecated
    public String persistenceToken(Long id) {
        //根据ID查询用户
        SysUserInfo user = sysUserDao.selectSysUserInfoById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException(500, ErrorCodeEnums.USER_IS_NOT_EXIST.getDesc(), null);
        }
        UserLoginOutDTO userDto = new UserLoginOutDTO();
        BeanUtils.copyProperties(user, userDto);
        String token = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(token, userDto, 5, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 持久化token
     */
    public String persistenceToken(UserLoginOutDTO dto, String token) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(TokenUtils.buildToken(TokenUtils.USER_LOGIN_TOKEN, token), dto, (StringUtils.isBlank(expir) ? 5 : Integer.parseInt(expir)), TimeUnit.MINUTES);
        return token;
    }


}
