package com.example.shoppingmanager.aspect;

import com.example.shoppingmanager.utils.LogMessages;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.shoppingmanager.controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info(LogMessages.ACCESSING + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.example.shoppingmanager.controller..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info(LogMessages.COMPLETED_WITH_RESULT + joinPoint.getSignature().toShortString() + " with result = " + result);
    }
}