package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.JwtAuthenticationFilter;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.user.dto.MyPageCheckSocialDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPageGetUserInfoDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPageUpdateUserInfoDTO;
import com.culturemoa.cultureMoaProject.user.exception.DontUpdateException;
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

import java.time.LocalDateTime;

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

    /**
     * 개인 정보 페이지에 정보를 전달하기 위하여 정보를 추출하기 위한 서비스
     * @param pRequest : 헤더에서 userId를 추출하기 위한 메서드
     * @return
     */
    public MyPageGetUserInfoDTO getUserInfo(HttpServletRequest pRequest) {
        // 헤더에서 토큰으로 사용자 정보를 가져오기
        String userId = getUserIdByAccessToken(pRequest);
        // 추출한 id로 정보를 요청
        return myPageDAO.getUserInfoById(userId);
    }

    /**
     * 헤더에서 userId를 추출하여 토큰에 담고
     * @param pRequest : 헤더에서 토큰을 가져오기 위한 값
     * @param myPageUpdateUserInfoDTO : 업데이트 한 개인정보가 담긴 값
     */
    public void updateUserInfoByToken(HttpServletRequest pRequest, MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        // 암호 공백 또는 빈값이 아닐 경우 암호화해서 넣기
        String password = myPageUpdateUserInfoDTO.getPassword();
        if(password != null && !password.isEmpty()){
            myPageUpdateUserInfoDTO.setPassword(passwordEncoder.encode(password));
        }
        // 수정 날짜 세팅
        myPageUpdateUserInfoDTO.setEDate(LocalDateTime.now().withNano(0)); // 나노초 제거하여 넣기

        // 유저 아이디 세팅
        myPageUpdateUserInfoDTO.setId(getUserIdByAccessToken(pRequest));
        
        // 반환 값에 따라 에러 처리 하기
        int updateResult = myPageDAO.updateUserInfo(myPageUpdateUserInfoDTO);

        if(updateResult == 0 || updateResult > 1) {
            throw new DontUpdateException();
        }

    }

    /**
     * getUserIdByAccessToken
     * 헤더에서 userId를 추출해서 사용하기 위한 공통 함수
     * @param pRequest : 헤더에 토큰을 가져오기 위한 변수
     * @return userId를 반환
     */
    public String getUserIdByAccessToken(HttpServletRequest pRequest) {
        // 헤더에서 토큰으로 사용자 정보를 가져오기
        String acceeToken = jwtProvider.resolveToken(pRequest);

        //token이 null이 아니고, 공백이 아닌 텍스트를 가지고 있으면 true를 반환
        if(!StringUtils.hasText(acceeToken)) {
            throw new UserNotFoundException();
        }
        String userId = jwtProvider.getUserIdFromToken(acceeToken);
        // 토큰으로 사용자 정보를 가져오지 못 한 경우 사용자 정보 없음으로 예외 처리
        if(userId == null) {
            throw new UserNotFoundException();
        }
        return userId;
    }

}