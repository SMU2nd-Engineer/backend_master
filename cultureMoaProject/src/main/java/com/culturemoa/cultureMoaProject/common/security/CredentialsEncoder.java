package com.culturemoa.cultureMoaProject.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * CredentialsEncoder
 * 암호화를 하거나 암호화를 매칭하는 메서드를 가지고 있는 클래스
 * 
 */
@Component
public class CredentialsEncoder {
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 카카오 처럼 소셜 로그인시 아이디와 같이 1개만 암호화할 수 있으므로 1인자는 1개만 받음

    /**
     * encodeInfo
     * 기본 방식인 bcrypt 암호화(강도10)으로 해시 암호화 (단방향 암호화)
     * @param pUserInfo : 암호화 하고 싶은 정보 
     * @return : 암호화 된 값 반환
     */
    public String encodeInfo(String pUserInfo) {
        return passwordEncoder.encode(pUserInfo);
    }

    /**
     * matchEncodeInfo
     * 사용자가 입력한 정보를 자동으로 암호화해서 DB에 있는 값이랑 비교하는 메서드
     * @param pUserInfo : 사용자가 입력한 정보
     * @param pDbUserInfo : DB에 저장된 암호화된 정보:
     * @return : boolean
     */
    public boolean matchEncodeInfo ( String pUserInfo, String pDbUserInfo ) {
        return passwordEncoder.matches(pUserInfo, pDbUserInfo);
    }
}
