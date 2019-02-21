package com.huifu.odin.biz.monitor.aop;

import com.huifu.odin.biz.base.BaseBiz;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author frank
 */
@Aspect
@Component
public class TransAspect extends BaseBiz {


    private Logger logger = LoggerFactory.getLogger(getClass());


    @Around("execution(public * *(..)) && @within(com.huifu.odin.biz.monitor.TransMonitor)")
    public Object transMonitor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean couldPrintMonitor = couldPrintMonitor();
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        monitorDebug("doTrans接口开始执行时间" + startTime + ",结束执行时间:" + endTime + "接口消耗总时间:[ " + String.valueOf(endTime - startTime) + "]ms ", logger, couldPrintMonitor);
        return proceed;
    }


}
