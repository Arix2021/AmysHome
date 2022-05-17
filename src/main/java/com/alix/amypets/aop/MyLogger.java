package com.alix.amypets.aop;

import com.alix.amypets.bean.RequestLog;
import com.alix.amypets.bean.user.User;
import com.alix.amypets.mapper.RequestLogMapper;
import com.alix.amypets.service.ex.base.InsertException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * 记录器 - 日志
 */
@Aspect // 标记为切面类
@Component
public class MyLogger {

    @Autowired
    private RequestLogMapper mapper;

    @Around("execution(* com.alix.amypets.service.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("*********************************");
        System.out.println("*************logAround***********");
        System.out.println("*********************************");
//        // 执行业务
//        Object res = pjp.proceed();
//        // 返回业务
//        return res;
        return pjp.proceed();
    }

    private long start;

    private long end;

    private RequestLog reqLog;

    @Before("log()")
    public void before() {
        System.out.println("*********************************logBefore");
        // 开始时间
        start = System.currentTimeMillis();
    }

    @After("log()")
    public void after(JoinPoint joinPoint) {
        System.out.println("*********************************logAfter");
        // 结束时间
        end = System.currentTimeMillis();
        // 记录
        // 考虑new一个线程来做，不占用业务时间
        RequestLog log = new RequestLog();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Date date = new Date();
        log.setIp(request.getRemoteAddr());
        log.setUrl(request.getRequestURL() + "");
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.setMethod(method.substring(method.indexOf("impl.")+5));
        log.setArgs(Arrays.toString(joinPoint.getArgs()));
        log.setRes(logger.getName());
        log.setLocalPort(request.getLocalPort());
        log.setServerPort(request.getServerPort());
        log.setClientPort(request.getRemotePort());
        log.setRuntime((int) (end - start));
        Object user = request.getSession().getAttribute("user");
        // 为不为null说明该访问者为已登录用户
        if (user != null) {
            String username = ((User) user).getUsername();
            log.setCreator(username);
            System.out.println("已登录");
        }
        log.setCreatedTime(date);
        // 做一些处理 例如将该类访问者视为游客用户（或不做处理）

        // 将日志保存到数据库
        if (mapper.insert(log) != 1) {
            // 保存失败
            throw new InsertException("在保存请求日志到数据库时失败");
        }

        // 打印展示
        System.out.println("*-------------------------- LOG : REQUEST -------------------------*"
                            + "\n" + log + "\n" +
                           "*------------------------------------------------------------------*");
    }


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(returning = "res", pointcut = "log()")
    public void afterReturn(Object res) {
        logger.info("res:{}", res);
    }

    @Pointcut("execution(* com.alix.amypets.service.*.*.*(..))")
    public void log() {

    }


}
