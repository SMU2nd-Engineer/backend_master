package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.JwtAuthenticationFilter;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.exception.UserNotFoundException;
import com.culturemoa.cultureMoaProject.user.repository.MyPageDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 마이페이지 관련 서비스 클래스
 */
@Service
public class MyPageService {

    @Autowired
    MyPageDAO myPageDAO;

    JwtAuthenticationFilter jwtAuthenticationFilter;
    JwtProvider jwtProvider;

    public MyPageService ( JwtAuthenticationFilter jwtAuthenticationFilter, JwtProvider jwtProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtProvider = jwtProvider;
    }



    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean myPagePasswordCheck(
            HttpServletRequest pRequest,
            MyPagePasswordCheckDTO myPagePasswordCheckDTO) {
        // 헤더에서 토큰으로 사용자 정보를 가져오기
        String acceeToken = jwtAuthenticationFilter.resolveToken(pRequest);

        String userId = jwtProvider.getUserInfoByToken(acceeToken);
        // 토큰으로 사용자 정보를 가져오지 못 한 경우 사용자 정보 없음으로 예외 처리

        // 디버깅
        System.out.println(userId);

        if(userId == null) {
            throw new UserNotFoundException();
        }

        // 가져온 id와 들어있는 데이터로 dao에 요청을 진행하여 password를 받아옴
        MyPagePasswordCheckDTO getDBPassword = myPageDAO.getPassword(userId);
        // 받아온 데이터에서 password를 reqeust의 패스워드와 매칭하기 다르면 오류 발생.
        if (!passwordEncoder.matches(myPagePasswordCheckDTO.getPassword(), getDBPassword.getPassword())) {
            throw new InvalidPasswordException(); // 비밀번호 일치하지 않음.
        } else {
            // 정상적으로 진행되면 return true로 설정
            return true;
        }
    }
}