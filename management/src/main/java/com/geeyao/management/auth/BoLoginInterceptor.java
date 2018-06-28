package com.geeyao.management.auth;

import com.corundumstudio.socketio.SocketIOClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BoLoginInterceptor {
    @Around("@annotation(com.corundumstudio.socketio.annotation.OnEvent) " +
            "&& within(com.geeyao.management..*) && !@annotation(com.geeyao.management.auth.NotNeedLoggedIn)")
    public Object checkBoLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        SocketIOClient client = (SocketIOClient) joinPoint.getArgs()[1];
        //TODO 在这里写判断有没有登录的代码
        return joinPoint.proceed();
    }
}
