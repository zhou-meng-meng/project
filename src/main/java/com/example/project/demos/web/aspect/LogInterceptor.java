package com.example.project.demos.web.aspect;

import cn.hutool.core.date.StopWatch;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private static final String GET = "GET";
    private static final String POST = "POST";

    /**
     * 执行拦截
     */
    @Around("execution(* com.example.project.demos.web.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        log.info(" ");
        log.info("████████████████ START████████████████");
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        // 输出请求日志
        log.info("█ request start，id: {}, path: {}, ip: {}, requestType: {}", requestId, url,
                httpServletRequest.getRemoteHost(), method);
        if (GET.equals(method)) {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            log.info("█ param={}", JSON.toJSONString(parameterMap));
        }
        if (POST.equals(method)) {
            for (Object arg : point.getArgs()) {
                log.info("█ paramClass={}", JSON.toJSONString(arg.getClass()));
                log.info("█ param={}", JSON.toJSONString(arg));
            }
        }
        // 执行原方法
        Object result = point.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("█ response  result: {}", JSON.toJSONString(result));
        log.info("█ request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        log.info("████████████████  END ████████████████");
        return result;
    }

}

