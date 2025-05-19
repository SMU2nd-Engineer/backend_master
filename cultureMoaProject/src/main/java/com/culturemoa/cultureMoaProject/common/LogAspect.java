package com.culturemoa.cultureMoaProject.common;

import com.culturemoa.cultureMoaProject.log.dto.LoggerDTO;
import com.culturemoa.cultureMoaProject.log.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {
    private final LoggerService loggerService;

    @Autowired
    public LogAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /*
    * com.culturemoa.cultureMoaProject.user 하위 모든 경로 // 예시코드
    * */
    @Pointcut("execution(* com.culturemoa.cultureMoaProject.user..*.*(..))")
    private void userPointCut(){}
    @Pointcut("execution(* com.culturemoa.cultureMoaProject.chat.*..*.*(..))")
    private void chatPointCut(){}

    /**
     * 유저 로그 Aspect
     * @param proceedingJoinPoint 포인트컷
     * @return ReturnObject
     * @throws Throwable
     */
//    @Around("userPointCut()")
//    public Object userLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        return printLog(proceedingJoinPoint, "userPointCut");
//    }
//
//    /**
//     * 채팅 로그 Aspect
//     * @param proceedingJoinPoint 포인트컷
//     * @return ReturnObject
//     * @throws Throwable
//     */
//    @Around("chatPointCut()")
//    public Object chatLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        return printLog(proceedingJoinPoint, "chatPointCut");
//    }

    /**
     * 로그 작성 및 포인트컷 실행 메서드
     * @param proceedingJoinPoint 포인트컷
     * @param pointCutName 포인트컷 명
     * @return ReturnObject
     * @throws Throwable 실행 실패시 처리
     */
    private Object printLog (ProceedingJoinPoint proceedingJoinPoint, String pointCutName) throws Throwable{
        LoggerDTO loggerDTO = getLoggerDTO(proceedingJoinPoint, pointCutName);

        log.info(loggerDTO.getClassName());
        Logger log = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        log.info("method name = {}", loggerDTO.getMethodName());

        // 실행 전 로그 파라미터와 이름 등
        Object[] args = proceedingJoinPoint.getArgs();
        if(args.length == 0) log.info("no parameter");
        else log.info("parameter = {} ", loggerDTO.getParameter());

        Object returnObj = proceedingJoinPoint.proceed();
        setLogReturn(loggerDTO, returnObj);

        if (returnObj != null) {
            log.info("return type = {}", loggerDTO.getReturnType());
            log.info("return value = {}", loggerDTO.getReturnValue());
        } else {
            log.info("return value is null");
        }

        loggerService.insertLogger(loggerDTO);

        return returnObj;
    }

    /**
     * 포인트컷의 메서드를 가져오는 메서드
     * @param proceedingJoinPoint 포인트컷
     * @return Method
     */
    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }

    /**
     * 포인트 컷으로 LoggerDTO를 생성하는 메서드
     * @param proceedingJoinPoint 포인트컷
     * @param pointCutName 포인트컷 명
     * @return LoggerDTO
     */
    private LoggerDTO getLoggerDTO(ProceedingJoinPoint proceedingJoinPoint, String pointCutName){
        LoggerDTO loggerDTO = new LoggerDTO();
        Method method = getMethod(proceedingJoinPoint);
        //포인트컷 명 설정
        loggerDTO.setPointCut(pointCutName);
        loggerDTO.setClassName(proceedingJoinPoint.getTarget().getClass().toString());
        loggerDTO.setMethodName(method.getName());

        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder params = new StringBuilder();

        if(args.length == 0) log.info("no parameter");
        for(Object arg : args) {
            params.append(arg.getClass().getSimpleName() );
            params.append(" : ");
            params.append(arg);
            params.append(", ");
        }
        loggerDTO.setParameter(params.length() > 2 ? params.substring(0,params.lastIndexOf(", ")) : params.toString());

        return loggerDTO;
    }

    /**
     * 리턴 정보 추가 메서드
     * @param loggerDTO 로그 DTO
     * @param returnObj 리턴 객체
     */
    private void setLogReturn (LoggerDTO loggerDTO, Object returnObj){
        if (returnObj != null) {
            loggerDTO.setReturnType(returnObj.getClass().getSimpleName());
            loggerDTO.setReturnValue(returnObj.toString());
        } else {
            loggerDTO.setReturnType("null");
        }
    }
}
