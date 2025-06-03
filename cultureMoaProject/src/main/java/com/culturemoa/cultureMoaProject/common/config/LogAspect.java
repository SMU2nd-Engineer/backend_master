package com.culturemoa.cultureMoaProject.common.config;

import com.culturemoa.cultureMoaProject.log.dto.LoggerDTO;
import com.culturemoa.cultureMoaProject.log.service.LoggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
@Order(5000)
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
    @Pointcut("execution(* com.culturemoa.cultureMoaProject..controller..*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void restApiMethods() {}

    /**
     * REST API 로그 Aspect
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
        // WebSocket 클래스이면 제외
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        if (className.startsWith("Chat") || className.contains("WebSocket") || className.contains("WebSocketHandler")) {
            return proceedingJoinPoint.proceed();
        }

        // 메서드 전체 실행 후 로깅
        Object returnObj;
        try {
            returnObj = proceedingJoinPoint.proceed();
        } catch (Throwable t){
            throw t;
        }

        // 인증 안된 요청은 로깅 생략
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            log.warn("SecurityContext 접근 중 오류 발생: " + e.getMessage());
        }

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return proceedingJoinPoint.proceed();
        }

        LoggerDTO loggerDTO = getLoggerDTO(proceedingJoinPoint, pointCutName);

        setLogReturn(loggerDTO, returnObj);

        loggerService.insertLogger(loggerDTO);

        log.info("실행 메서드 : " + getMethod(proceedingJoinPoint).getName() +" / paramater : " + loggerDTO.getParameter() + " / return : " + loggerDTO.getReturnValue());

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

        try {
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuilder sb = new StringBuilder("[");
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                    sb.append("{\"type\":\"HttpServletObject\"},");
                } else {
                    try {
                        sb.append(objectMapper.writeValueAsString(arg)).append(",");
                    } catch (Exception e) {
                        sb.append("{\"error\":\"Unserializable argument\"},");
                    }
                }
            }
            sb.append("]");
            loggerDTO.setParameter(sb.toString());
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
            if (returnObj != null) {
                loggerDTO.setReturnType(returnObj.getClass().getSimpleName());
                responseJson = objectMapper.writeValueAsString(returnObj);
            } else {
                loggerDTO.setReturnType("null");
                responseJson = "null";
            }
        } catch (Exception e) {
            responseJson = "[unserializable]";
        }
        loggerDTO.setReturnValue(responseJson);
    }
}
