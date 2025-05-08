package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.security.CredentialsEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IdPasswordMatchService
 * 아이디 비밀번호 비교 맞춰서 일치하면 토큰 생성을 위해서 id 반환
 * encoder : 암호화 및 매치 칭을 위해 가져 의존성 주입
 */

@Service
public class IdPasswordMatchService {

    @Autowired
    private CredentialsEncoder encoder;

    /**
     * login
     * 사용자가 입력한 아이디 비밀번호를 DB 데이터와 비교
     * @param pUserId : 사용자가 입력한 id
     * @param pPassword : 사용자가 입력한 Password
     * @return 값에 따라 입력한 값 반환
     */
    // 테스트 용으로 id 비번 test로 지정함.
    // 실제로는 db와 로그인 아이디와 비번 매칭 작업 해야함
    public String login (String pUserId, String pPassword) {
        // 임시로 지정한 아이디 (test) 와 비번(1234) 암호화를 위해서 변수 생성
        // 암호화 및 암호화 매칭하는 것 사용으로 바꿈
        // 매번 새로운 암호문을 만들어서 오류가 난다고 찾아서 하드 코딩 방식으로 변경
//        String encodeTest = encoder.encodeInfo("test");
//        String encodePwd = encoder.encodeInfo("1234");
//        if(encoder.matchEncodeInfo(pUserId, encodeTest) &&
//                encoder.matchEncodeInfo(pPassword, encodePwd)) {
//            return pUserId;
//        }
        System.out.println("전달 받은 id, password : " + pUserId + ", " + pPassword);
        if ("test".equals(pUserId) && "1234".equals(pPassword)) {
            return pUserId;
        }
        return "";
    }
}
