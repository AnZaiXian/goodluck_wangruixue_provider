package com.bw.wangruixue.log;

import com.bw.wangruixue.entity.Loggers;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by lenovo on 2017/7/20.
 */
@Aspect//定义一个切面类
@Component
public class WebLogAspect {



    /**
     * 在mongodb中进行增删改查
     * 将日志中的信息存到mongodb日志中
     * 方法:通过获取日志中的各个属性的雷静并将该日志封装成一个实体类
     * 然后用mongodb自带的MongoTemplate中的增删改查的方法来操作mongodb
     *
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    private Logger logger = Logger.getLogger(getClass());
    @Pointcut("execution(public * com.bw.controller..*.*(..))")
    public void webLog(){}
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        //方法返回值的快捷键为:ctrl+alt+v
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        String s = Arrays.toString(joinPoint.getArgs());

        Loggers log = new Loggers();
        log.setURL(url);
        log.setCLASS_METHOD(declaringTypeName+"."+name);
        log.setHTTP_METHOD(method);
        log.setIP(ip);
        log.setARGS(s);
        System.out.println("=========================");
        System.out.println(log);

        //将log日志添加到mongodb数据库中
        mongoTemplate.save(log);
    }


    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }


}
