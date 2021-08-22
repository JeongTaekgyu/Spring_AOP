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

    // 포인트 컷? - 기능을 어디에 적용시킬지, 메서드? Annotation? 등 AOP를 적용 시킬 지점을 설정
    // 부가가능이 적용될 메소드를 선정하는 방법이다
    // 어드바이스가 적용될 조인포인트 중 실제 부가기능이 적용될 조인포인트를 선별하는 기능이라고 보면 된다.
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
