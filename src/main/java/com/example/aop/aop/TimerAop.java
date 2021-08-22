package com.example.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {
    /*
    참고로 bean과 Component의 차이
    bean은 클래스를 붙일 수 없다. , bean은 메서드에서 어노테이션 사용가능
    Component를 통해서 클래스 단위로 bean을 등록 시킬 수 있다.
     */

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")   // controller의 하위에 있는 메서드들
    private void cut(){
    }

    @Pointcut("@annotation(com.example.aop.annotation.Timer)")
    private void enableTimer(){
    }

    // 전,후 가 있어야하는데 before 메서드 after 메서드가 있으면 타임을 공유 못해서 around를 사용한다.
    @Around("cut() && enableTimer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();
        // joinPoint의 proceed를 호출하면 실질적인 메서드가 실행된다

        stopWatch.stop();
        // 아하 지금 start, stop으로 시간 재는걸 RestApiController 에 있는 각 컨트롤러 3개에서 하면
        // 모든 메서드에 같은 기능들이 들어가면 낭비라서 aop로 뺀다 , 즉 여기서 처리한다
        System.out.println("total time : "+stopWatch.getTotalTimeSeconds());
    }
}
