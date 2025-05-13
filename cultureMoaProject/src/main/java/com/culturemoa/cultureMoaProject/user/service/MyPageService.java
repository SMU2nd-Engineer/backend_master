package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.JwtAuthenticationFilter;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.user.dto.MyPageCheckSocialDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.exception.UserNotFoundException;
import com.culturemoa.cultureMoaProject.user.repository.MyPageDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    JwtProvider jwtProvider;

    
    // 디버그 용
    private static final Logger logger = LoggerFactory.getLogger(MyPageService.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 마이페이지 비번과 헤더의 토큰을 이용하여 사용자 비번을 조회하여 매칭한 결과를 반환하는 메서드
     * @param pRequest : 헤더를 가져오기 위한 파라미터
     * @param myPagePasswordCheckDTO : 사용자가 입력한 정보가 담긴 DTO
     * @return : 프론트에서 boolean으로 값을 받기 위하여 전달함.
     */
    public Boolean myPagePasswordCheck(
            HttpServletRequest pRequest,
            MyPagePasswordCheckDTO myPagePasswordCheckDTO) {

        String userId = getUserIdByAccessToken(pRequest);

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

    /**
     * 토큰에서 헤더 정보를 추출하여 소셜 로그인 정보를 가져오는 메서드
     * @param pRequest : 헤더 정보를 추출하기 위한 파라미터
     * @return : MyPageCheckSocialDTO
     */
    public MyPageCheckSocialDTO myPageSocialCheck(HttpServletRequest pRequest) {
        String userId = getUserIdByAccessToken(pRequest);
        return myPageDAO.getSocialLogin(userId);
    }

    public String getUserIdByAccessToken(HttpServletRequest pRequest) {
        // 헤더에서 토큰으로 사용자 정보를 가져오기
        String acceeToken = jwtProvider.resolveToken(pRequest);

        //token이 null이 아니고, 공백이 아닌 텍스트를 가지고 있으면 true를 반환
        if(!StringUtils.hasText(acceeToken)) {
            throw new UserNotFoundException();
        }

        String userId = jwtProvider.getUserIdFromToken(acceeToken);
        // 토큰으로 사용자 정보를 가져오지 못 한 경우 사용자 정보 없음으로 예외 처리

        // 디버깅
        logger.debug("[Filter] userId 추출 확인 : {} ", userId);

        if(userId == null) {
            throw new UserNotFoundException();
        }

        return userId;
    }

}