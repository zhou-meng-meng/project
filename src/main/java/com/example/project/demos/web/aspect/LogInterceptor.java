package com.example.project.demos.web.aspect;

import cn.hutool.core.date.StopWatch;
import com.alibaba.fastjson.JSON;
import com.example.project.demos.web.auth.OauthSupport;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.exception.CustomException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author guanc
 * @version 创建时间：2024/4/18 13:54
 * @describe 请求响应日志 AOP
 */
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    @Autowired
    private OauthSupport oauthSupport;

    /**
     * 忽略校验token
     */
    private static List<String> ignorePaths = Lists.newArrayList();
    /**
     * 绕过AOP export
     */
    private final static String PASS_AOP_EXPORT = "export";
    private final static String PASS_AOP_IMPORT = "import";
    private final static String PASS_AOP_UPLOAD = "uploadFile";

    static {
        ignorePaths.add("userLogin");
        //ignorePaths.add("resetPwd");
    }

    /**
     * 执行拦截
     */
    @Around("execution(* com.example.project.demos.web.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        log.info(" ");
        log.info("████████████████START████████████████");
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求参数
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        // 输出请求日志
        log.info("█ request start，id: {}, path: {}, ip: {}, requestType: {}", requestId, url,
                httpServletRequest.getRemoteHost(), method);
        if (Constants.REQ_TYPE_GET.equals(method)) {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            log.info("█ param={}", JSON.toJSONString(parameterMap));
        }
        if (Constants.REQ_TYPE_POST.equals(method) && !(url.contains(PASS_AOP_EXPORT)) && !(url.contains(PASS_AOP_IMPORT)) && !(url.contains(PASS_AOP_UPLOAD))) {
            for (Object arg : point.getArgs()) {
                log.info("█ paramClass={}", JSON.toJSONString(arg.getClass()));
                log.info("█ param={}", JSON.toJSONString(arg));
            }
        }
        //String token = httpServletRequest.getHeader(Constants.TOKEN);
        String token = this.getToken();
        //校验token
        this.checkToken(token, url,requestId);
        try {
            this.putUserInfo(token, url);
        } catch (Exception e) {
            log.error("█ putUserInfo error e", e);
        }
        // 执行原方法
        Object result = point.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("█ response  result: {}", JSON.toJSONString(result));
        log.info("█ request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        log.info("████████████████  END  ████████████████");
        return result;
    }

    private String getToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(Constants.TOKEN);
    }

    /**
     * 校验 token
     */
    private void checkToken(String token, String path, String reqId) throws Throwable {
        for (String ignorePath : ignorePaths) {
            if (path.contains(ignorePath)) {
                return;
            }
        }
        if (StringUtils.isBlank(token) || oauthSupport.tokenIsExpired(token)) {
            throw new CustomException(401, ErrorCodeEnums.TOKEN_IS_INVALID.getDesc(), reqId);
        }
        //刷新token时间
        oauthSupport.expireKey(token);
    }

    private Boolean putUserInfo(String token, String path) {
        if(StringUtils.isBlank(token)){
            return Boolean.TRUE;
        }
        for (String ignorePath : ignorePaths) {
            if (path.contains(ignorePath)) {
                return Boolean.TRUE;
            }
        }
        try {
            UserLoginOutDTO userInfo = oauthSupport.getUserInfoByToken(token);
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("user", userInfo);
        } catch (Throwable e) {
             e.printStackTrace();
        }
        return Boolean.TRUE;
    }


}

