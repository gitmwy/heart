package com.ksh.heart.common.aop;

import com.google.gson.Gson;
import com.ksh.heart.common.annotion.SysLog;
import com.ksh.heart.common.log.LogManager;
import com.ksh.heart.common.log.factory.LogTaskFactory;
import com.ksh.heart.common.utils.ToolUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(com.ksh.heart.common.annotion.SysLog)")
//    @Pointcut(value = "execution(public * com.ksh.heart.system.controller..*.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String bussinessName = "";

        //注解上的描述
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            bussinessName = syslog.value();
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        if(ToolUtil.isNotEmpty(args)){
            params = new Gson().toJson(args[0]);
        }
        LogManager.me().executeLog(LogTaskFactory.businessLog(null, bussinessName, className, methodName, params, time));
    }
}
