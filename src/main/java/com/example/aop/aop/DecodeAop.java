package com.example.aop.aop;

import com.example.aop.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
public class DecodeAop {

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")   // controller의 하위에 있는 메서드들
    private void cut(){
    }

    @Pointcut("@annotation(com.example.aop.annotation.Decode)")
    private void enableDecode(){
    }

    // 전 decode, 후 encode

    @Before("cut() && enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();
        // encoding된 base64를 decode해서 셋팅했고
        for(Object arg: args){
            if(arg instanceof User){
                User user = User.class.cast(arg);
                String base64Email = user.getEmail();
                String email = new String(Base64.getDecoder().decode(base64Email), "UTF-8");
                user.setEmail(email);   // decoding 된 이메일을 user에 셋팅해준다
            }
        }
    }

    @AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        if(returnObj instanceof User){
            // Object에서 User를 찾아서 평문 이메일을 다시 인코딩해서 다시 셋팅 해주고 리턴한다
            User user = User.class.cast(returnObj);
            String email = user.getEmail();
            String base64email = Base64.getEncoder().encodeToString(email.getBytes());
            user.setEmail(base64email);   // decoding 된 이메일을 user에 셋팅해준다
        }
    }
}
