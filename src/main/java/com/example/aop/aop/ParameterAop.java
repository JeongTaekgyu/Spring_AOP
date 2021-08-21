package com.example.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.SQLOutput;

@Aspect
@Component
public class ParameterAop {

    // 포인트 컷?
    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void cut(){

    }

    @Before("cut()")    // cut이 실행되는 지점에 Before 때 헤당 메서드를 실행시키겠다
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());

        Object[] args = joinPoint.getArgs();
        for(Object obj : args){
            System.out.println("type : "+obj.getClass().getSimpleName());
            System.out.println("value : "+obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        System.out.println("return obj");
        System.out.println(returnObj);
    }
}
