package com.culturemoa.cultureMoaProject.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {
    /*
    * com.culturemoa.cultureMoaProject.user 하위 모든 경로 // 예시코드
    * */
    @Pointcut("execution(* com.culturemoa.cultureMoaProject.user..*.*(..))")
    private void userPointCut(){}

    // Around 에 적용할 포인트컷을 작성
    @Around("userPointCut()")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 포인트컷이 적용되는 메서드를 가져오기
        Method method = getMethod(proceedingJoinPoint);
        Logger log = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        log.info("========== method name {} ===========", method.getName());

        // 실행 전 로그 파라미터와 이름 등
        Object[] args = proceedingJoinPoint.getArgs();
        if(args.length == 0) log.info("no parameter");
        for(Object arg : args){
            log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg);
        }

        // 실행 결과 로그
        Object returnObj = proceedingJoinPoint.proceed();

        log.info("parameter type = {}", returnObj.getClass().getSimpleName());
        log.info("parameter value = {}", returnObj);

        return  returnObj;
    }

    // 포인트 컷 정보로 메소드의 정보를 가져오는 메서드
    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }
}
