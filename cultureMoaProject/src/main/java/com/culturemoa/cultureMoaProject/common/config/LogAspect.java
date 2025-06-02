package com.culturemoa.cultureMoaProject.common.config;

import com.culturemoa.cultureMoaProject.log.dto.LoggerDTO;
import com.culturemoa.cultureMoaProject.log.service.LoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LogAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /*
    * com.culturemoa.cultureMoaProject.user 하위 모든 경로 // 예시코드
    * */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void restApiMethods() {}

    /**
     * 유저 로그 Aspect
     * @param proceedingJoinPoint 포인트컷
     * @return ReturnObject
     * @throws Throwable
     */
    @Around("restApiMethods()")
    public Object restApiLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        return printLog(proceedingJoinPoint, "restApiMethods");
    }



    /**
     * 로그 작성 및 포인트컷 실행 메서드
     * @param proceedingJoinPoint 포인트컷
     * @param pointCutName 포인트컷 명
     * @return ReturnObject
     * @throws Throwable 실행 실패시 처리
     */
    private Object printLog (ProceedingJoinPoint proceedingJoinPoint, String pointCutName) throws Throwable{
        LoggerDTO loggerDTO = getLoggerDTO(proceedingJoinPoint, pointCutName);

        log.info("실행 메서드 : " + getMethod(proceedingJoinPoint).getName());

        Object returnObj = proceedingJoinPoint.proceed();
        setLogReturn(loggerDTO, returnObj);

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

        String requestParamsJson = null;
        try {
            requestParamsJson = objectMapper.writeValueAsString(proceedingJoinPoint.getArgs());
            loggerDTO.setParameter(requestParamsJson);
        } catch (Exception e) {
            loggerDTO.setParameter("[unserializable]");
        }

        return loggerDTO;
    }

    /**
     * 리턴 정보 추가 메서드
     * @param loggerDTO 로그 DTO
     * @param returnObj 리턴 객체
     */
    private void setLogReturn (LoggerDTO loggerDTO, Object returnObj){
        String responseJson = null;
        try {
            loggerDTO.setReturnType(returnObj.getClass().getSimpleName());
            responseJson = objectMapper.writeValueAsString(returnObj);
            loggerDTO.setReturnValue(responseJson);
        } catch (Exception e) {
            responseJson = "[unserializable]";
            loggerDTO.setReturnValue(responseJson);
        }
    }
}
