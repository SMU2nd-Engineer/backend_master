package com.culturemoa.cultureMoaProject.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 인증 객체를 다루는 클래스
 */
@Component
public class HandleAuthentication {
    /**
     * jwt 필터링 과정에서 생성한 인증 객체에서 userId를 받아오는 함수
     * @return : userId를 반환
     */
    public String getUserIdByAuth () {
        // 사용자 정보를 인증 객체에서 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal(); // String 자료형으로 다운
    }
}
