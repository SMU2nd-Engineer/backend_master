package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.*;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 유저 서비스 클래스
 * userDAO : DB와 연동을 위해서 사용하는 DAO 변수
 * passwordEncoder : 암호화를 위해서 사용하는 변수 
 * authJwtService : JWT 토큰 발급을 위한 변수
 */
@Service
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthJwtService authJwtService;
    private final HandleAuthentication handleAuth;

    @Autowired
    public UserService (UserDAO userDAO, PasswordEncoder passwordEncoder, AuthJwtService authJwtService, HandleAuthentication handleAuth ) {
        this.userDAO = userDAO;
        this.passwordEncoder=passwordEncoder;
        this.authJwtService=authJwtService;
        this.handleAuth=handleAuth;
    }


    /**
     * 프론트에서 전달받은 데이터를 활용하여 유저 정보 등록
     * 등록시 유저 비번의 경우 암호화해서 넣음
     * @param pUserDTO : josn 형태로 전달 받은 데이터를 dto를 활용하여 받아서 인자로 넣음
     */
    public JwtDTO registerUser(HttpServletResponse pResponse, UserRegisterRequestDTO pUserDTO) {

        // 비밀번호 암호화
        pUserDTO.setPassword(passwordEncoder.encode(pUserDTO.getPassword()));
        // 등록 날짜 넣기
        pUserDTO.setSDate(LocalDateTime.now()); // 나노초 제거
        // 일반 회원 가입이면 socialLogin에 일반 로그인을 알 수 있도록 값 추가 아니면 그대로 사용하기
        if(pUserDTO.getSocialLogin() == null || pUserDTO.getSocialLogin().isEmpty()) {
            pUserDTO.setSocialLogin("regularLogin");
        }
        // DAO로 데이터 넣기
        userDAO.insertUser(pUserDTO);

        // 회원 가입 후 로그인과 같은 효과를 주기 위하여 토큰을 발급하기
        return authJwtService.tokenCreateSave(pResponse, pUserDTO.getId(), false);

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
            return authJwtService.tokenCreateSave(pResponse, userId, pRequest.getAutoLogin());
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
    public JwtDTO socialLoginAndIssuanceToken (String pUserId, HttpServletResponse pResponse, String socialProvider, Boolean autoLogin ) {
        // dao를 통하여 db의 id 가져오기 (id로 토큰 발급)
        SocialLoginResponseDTO userLogin = userDAO.socialFindByLoginInfo(pUserId);

        // 아이디 조회해서 가져오지 않은 경우 예외 던지기
        if(userLogin == null) {
            throw new SocialUserNotFoundException(pUserId, socialProvider); // 사용자 없음 예외, 회원 가입 유도
        }
        String userId = userLogin.getId();

        // 문제 없으면 토큰 발급하기
        return authJwtService.tokenCreateSave(pResponse, userId, autoLogin);
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
        pChangeDto.setCDate(LocalDateTime.now()); // 나노초 제거
        // DAO로 비밀번호 업데이트하기
        int ChangeCheck = userDAO.updateUserPassword(pChangeDto);

        if (ChangeCheck == 0 ) {
            // 변경이 안되면 0을 받으므로 커스텀 에러 처리
            throw new DontChangeException();
        }

    }

    /**
     * 회원 탈퇴 서비스
     */
    public void userWithdrawal() {
        // 유저 아이디 추출하기
        String userId = handleAuth.getUserIdByAuth();
        int userIdx = userDAO.getUserIdx(userId);

        // 회원 탈퇴, 정보 수정 날짜 생성하기
        LocalDateTime localDateTime = LocalDateTime.now();

        // 기존 아이디를 탈퇴회원 아이디 prefix 붙이기
        String deleteId = userId+"_delete_"+userIdx + "_" + localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);

        // 생성자를 사용하여 바로 값 넘겨 주기
        int userWithdrawalProcess = userDAO.updateWithdrawal(new UserWithdrawalDTO(userId, deleteId, localDateTime, localDateTime));

        if(userWithdrawalProcess == 0) {
            throw new DBManipulationFailException();
        }
    }


    /**
     * 사용자 선호도 삽입 진행
     * @param userRegisterFavoriteDTO : 프론트에서 받은 사용자 선호도 배열이 들어있는 dto
     */
    public void insertUserFavoriteWithIdxAndDate (UserRegisterFavoriteDTO userRegisterFavoriteDTO) {
        String userId = handleAuth.getUserIdByAuth();
        int userIdx = userDAO.getUserIdx(userId);
        userRegisterFavoriteDTO.setUserIdx(userIdx);
        if(userDAO.insertUserFavorites(userRegisterFavoriteDTO) == 0) {
            throw new DontInsertException();
        }
    }

    /**
     * 홈페이지에 사용할 유저 정보를 전달하기 위한 서비스
     * @return : 조회한 정보를 담은 HomePageInfoDTO를 반환
     */
    public HomePageInfoDTO getHomePageInfoByAuth () {
        String userId = handleAuth.getUserIdByAuth();
        return new HomePageInfoDTO(userDAO.getLatestProducts(),
                                   userDAO.getLatestContent(),
                                   userDAO.getUserInfoByUserId(userId));
    }

}
