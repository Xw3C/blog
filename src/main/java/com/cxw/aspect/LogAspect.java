package com.cxw.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.cxw.web.*.*(..))")
    public void log() {}


    //切面之前的动作
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //通过request获取URL
        String url = request.getRequestURL().toString();
        //通过request获取IP地址
        String ip = request.getRemoteAddr();
        //通过JointPoint获取 getDeclaringTypeName()是获取类名  getSignature().getName()是获取方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        //通过joinpoint.getArgs获得请求的参数
        Object[] args =joinPoint.getArgs();
        //把值都传递到requestLog中
        RequestLog  requestLog = new RequestLog(url,ip,classMethod,args);
        //
        logger.info("Request : {}",requestLog);
    }

    //切面之后的动作
    @After("log()")
    public void doAfter() {
//        logger.info("-----doAfter-----");
    }

    //返回切入点方法的信息
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}",result);
    }

    //封装成一个类
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
