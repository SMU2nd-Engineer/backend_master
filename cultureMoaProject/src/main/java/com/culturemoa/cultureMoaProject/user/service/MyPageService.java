package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.repository.MyPageDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 마이페이지 관련 서비스 클래스
 */
@Service
public class MyPageService {

    @Autowired
    MyPageDAO myPageDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean myPagePasswordCheck (
            HttpServletRequest pRequest,
            MyPagePasswordCheckDTO myPagePasswordCheckDTO){
        // 헤더에서 토큰으로 사용자 정보를 가져오기
        // String userId =
        // 가져온 id와 들어있는 데이터로 dao에 요청을 진행하여 password를 받아옴
//        String getDBPassword = myPageDAO.getPassword(userId);
        // 받아온 데이터에서 password를 reqeust의 패스워드와 매칭하기 다르면 오류 발생.
//        if(!passwordEncoder.matches(myPagePasswordCheckDTO.getPassword(), getDBPassword) {
//            throw new InvalidPasswordException(); // 비밀번호 일치하지 않음.
//        } else {
        // 정상적으로 진행되면 return true로 설정
        return true;


    }
}
