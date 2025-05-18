package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.*;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 유저 서비스 클래스
 * userDAO : DB와 연동을 위해서 사용하는 DAO 변수
 * passwordEncoder : 암호화를 위해서 사용하는 변수 
 * authJwtService : JWT 토큰 발급을 위한 변수
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthJwtService authJwtService;

    /**
     * 프론트에서 전달받은 데이터를 활용하여 유저 정보 등록
     * 등록시 유저 비번의 경우 암호화해서 넣음
     * @param pUserDTO : josn 형태로 전달 받은 데이터를 dto를 활용하여 받아서 인자로 넣음
     */
    public JwtDTO registerUser(HttpServletResponse pResponse, UserRegisterRequestDTO pUserDTO) {

        // 비밀번호 암호화
        pUserDTO.setPassword(passwordEncoder.encode(pUserDTO.getPassword()));
        // 등록 날짜 넣기
        pUserDTO.setSDate(LocalDateTime.now().withNano(0)); // 나노초 제거
        // 일반 회원 가입이면 socialLogin에 일반 로그인을 알 수 있도록 값 추가 아니면 그대로 사용하기
        if(pUserDTO.getSocialLogin() == null || pUserDTO.getSocialLogin().isEmpty()) {
            pUserDTO.setSocialLogin("regularLogin");
        }
        // DAO로 데이터 넣기
        userDAO.insertUser(pUserDTO);

        // 회원 가입 후 로그인과 같은 효과를 주기 위하여 토큰을 발급하기
        return authJwtService.tokenCreateSave(pResponse, pUserDTO.getId());

    }

    /**
     * 로그인 전달 받은 아이디와 비밀번호를 매칭하여 값을 비교
     * @param pRequest : 사용자가 입력한 id와 비밀번호가 담긴 DTO
     * @param pResponse : 헤더에 쿠키 담기
     * @return : 조회한 사용자 정보를 담은 UserDTO 객체
     */
    public JwtDTO loginAndIssuanceToken (UserLoginDTO pRequest, HttpServletResponse pResponse) {
        // dao를 통하여 db의 패스워드 가져오기
        UserLoginDTO userLogin = userDAO.findByLoginInfo(pRequest);


        // 사용자 입력 값, 조회하여 가져온 passowrd를 매칭하여 다르면 오류 발생.
        if(!passwordEncoder.matches(pRequest.getPassword(), userLogin.getPassword())) {
            throw new InvalidPasswordException(); // 비밀번호 일치하지 않음.
        }
        // 토큰 발급을 위한 userId 추출하기 위하여 로그인 아이디 매칭해서 한 번 더 검증
        if(pRequest.getId().equals(userLogin.getId())) {
            String userId = userLogin.getId();
            // 문제 없으면 토큰 발급하기
            return authJwtService.tokenCreateSave(pResponse, userId);
        } else {
            throw new DontMatchUserInfoException();
        }

    }

    /**
     * 소셜 아이디를 조회하기
     * @param pUserId : 소셜 id
     * @param pResponse : refresh 토큰을 담을 헤더를 위해서 가져옴
     * @return : refresh 토큰은 쿠키에 담고 accessToken을 넘기기
     */
    public JwtDTO socialLoginAndIssuanceToken (String pUserId, HttpServletResponse pResponse, String socialProvider ) {
        // dao를 통하여 db의 id 가져오기 (id로 토큰 발급)
        SocialLoginResponseDTO userLogin = userDAO.socialFindByLoginInfo(pUserId);

        // 아이디 조회해서 가져오지 않은 경우 예외 던지기
        if(userLogin == null) {
            throw new SocialUserNotFoundException(pUserId, socialProvider); // 사용자 없음 예외, 회원 가입 유도
        }
        String userId = userLogin.getId();

        // 문제 없으면 토큰 발급하기
        return authJwtService.tokenCreateSave(pResponse, userId);
    }

    /**
     * 아이디, 닉네임 중복 체크
     * @param pDuplicateDto : 전달 받은 id, nickName
     * @return 중복 체크 후 중복이면 1이상 아니면 0
     */
    public boolean duplicateCheck(UserDuplicateCheckRequestDTO pDuplicateDto) {
        // 전달 받은 값 변수에 할당
        String pCheckList = pDuplicateDto.getName(); // 검사할 id, nickName 명
        String pCategory = pDuplicateDto.getCategory(); // 검사할 컬럼
        // sql injection 방지 위해 pCheckList 지정 위해 DTO에서 꺼내서 처리
        String columnName;
        switch (pCategory) {
            case ("id") :
                columnName = "ID";
                break;
            case ("nickName") :
                columnName = "NICKNAME";
                break;
            default :
                throw new IllegalArgumentException("컬럼 정보를 확인 할 수 없습니다.");
        }
        // 마이바티스틑 인자로 두번째 인자 하나만 받아서 Map 등을 이용해야 한다.
        Map<String, Object> duplicateMap = new HashMap<>();
        duplicateMap.put("pCheckList", pCheckList);
        duplicateMap.put("columnName", columnName);
        int duplicateCheckValue = userDAO.duplicateCheck(duplicateMap);

        return duplicateCheckValue == 0;
    }

    /**
     * 아이디 찾기 서비스
     * @param pFindIdInfo : 사용자가 입력한 이메일, 이름이 담긴 DTO
     * @return 사용자 아이디가 담긴 UserFindIdresponseDTO 객체
     */
    public UserFindIdResponseDTO findId (UserFindIdRequestDTO pFindIdInfo) {
        UserFindIdResponseDTO userFindIdResponseDTO = userDAO.findId(pFindIdInfo);
        if (userFindIdResponseDTO.getId() == null || userFindIdResponseDTO.getId().isEmpty()){
            throw new DontMatchUserInfoException();
        }
        return userFindIdResponseDTO;
    }

    /**
     * 패스워드 찾기 클릭시 처리할 서비스
     * @param pUserFindPasswordRequestDTO : 사용자가 입력한 이메일, 아이디, 이름 정보가 담긴 DTO
     */
    public void passwordFind (UserFindPasswordRequestDTO pUserFindPasswordRequestDTO) {
        UserFindPasswordResponseDTO userFindPasswordResponseDTO = userDAO.passwordFindMatch (pUserFindPasswordRequestDTO);
        // 가져온 정보가 없을 경우 매치되지 않았으므로 예외를 던짐.
        if(userFindPasswordResponseDTO.getPassword()==null || userFindPasswordResponseDTO.getPassword().isEmpty()) {
            throw new DontMatchUserInfoException();
        }
    }

    /**
     * 암호를 변경하기 위하여 사용하는 service
     * @param pChangeDto : 변경할 password가 담긴 DTO 변수
     */
    public void changePassword (UserChangePasswordRequestDTO pChangeDto) {

        // 비밀번호 암호화
        pChangeDto.setPassword(passwordEncoder.encode(pChangeDto.getPassword()));
        // 등록 날짜 넣기
        pChangeDto.setEDate(LocalDateTime.now().withNano(0)); // 나노초 제거
        // DAO로 비밀번호 업데이트하기
        int ChangeCheck = userDAO.updateUserPassword(pChangeDto);

        if (ChangeCheck == 0 ) {
            // 변경이 안되면 0을 받으므로 커스텀 에러 처리
            throw new DontChangeException();
        }

    }

    /**
     * 회원 탈퇴 서비스
     * @param userWithdrawalDTO : 탈퇴 회원의 정보가 담긴 DTO
     */
    public void userWithdrawal(UserWithdrawalDTO userWithdrawalDTO) {
        // 회원 탈퇴 날짜 넣기
        userWithdrawalDTO.setWDate(LocalDateTime.now().withNano(0)); // 나노초 제거

        // 회원 정보 수정 날짜 넣기
        userWithdrawalDTO.setEDate(LocalDateTime.now().withNano(0)); // 나노초 제거

        //  id를 기준으로 회원 날짜 넣기
        int userWithdrawalProcess = userDAO.updateWithdrawal(userWithdrawalDTO);

        if(userWithdrawalProcess == 0) {
            throw new DBManipulationFailException();
        }
    }

    /**
     * 카테고리 정보를 얻기 위한 서비스
     * @return : List<UserCategorySubDTO>
     */
    public List<UserCategorySubDTO> getUserCategoryInfo () {
        System.out.println( userDAO.getCategorySubInfo());
        return userDAO.getCategorySubInfo();
    }


    /**
     * 사용자 선호도 삽입 진행
     * @param userChooseFavoriteDTO : 프론트에서 받은 사용자 선호도 배열이 들어있는 dto
     */
    public void insertUserFavoriteWithIdxAndDate (UserChooseFavoriteDTO userChooseFavoriteDTO) {
        System.out.println("서비스 여기실행함.");
        String userId = myPageGetUserId();
        System.out.println("서비스 여기실행함. 아이디 확인하기" + userId);
        int userIdx = userDAO.getUserIdx(userId);
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        userChooseFavoriteDTO.setUserIdx(userIdx);
        userChooseFavoriteDTO.setSDate(localDateTime);
        System.out.println("서비스2 여기실행함.");
        System.out.println("dto 확인하기" + userChooseFavoriteDTO);
        if(userDAO.insertUserFavorites(userChooseFavoriteDTO) == 0) {
            throw new DontInsertException();
        }
    }


    /**
     * 인증 객체는 스프링 빈으로 등록할 때 null이므로 생성자에서는 사용하지 못하고 꼭 메서드 안에서 써야 해서 userId를 공통 적용하기 위한 메서드 생성
     */
    private String myPageGetUserId () {
        // 사용자 정보를 인증 객체에서 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal(); // String 자료형으로 다운
    }

}
