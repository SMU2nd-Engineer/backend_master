package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.service.SocialLoginService;
import com.culturemoa.cultureMoaProject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 가입, 일반 또는 소셜 로그인, 아이디&닉네임 중복 체크, 비밀번호 변경, 아이디 찾기 컨트롤러
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final SocialLoginService socialLoginService;

    @Autowired
    public UserController(UserService userService, SocialLoginService socialLoginService) {
        this.userService = userService;
        this.socialLoginService = socialLoginService;
    }

    /**
     * 회원 가입 페이지 정보를 이용 회원 가입 진행.
     * @param pRequest : 프론트에서 전달 받은 회원 가입 정보
     * @return : 정상적인 경우 ok 응답을 반환
     */
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration (
            HttpServletResponse pResponse,
            @RequestBody UserRegisterRequestDTO pRequest) {
        //회원 가입 진행 토큰 발급하기
        JwtDTO jwtDto = userService.registerUser(pResponse, pRequest);
        return ResponseEntity.ok(jwtDto);
    }

    /**
     * loginAccess
     * 로그인 컨트롤러 메서드
     * @param pRequest : 요청의 다디를 받음
     * @param pResponse : 응답 헤더에 값을 저장하기 위해서 넣음.
     * @return : AccessToken을 반환
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAccess(@RequestBody UserLoginDTO pRequest, HttpServletResponse pResponse) {
            // 가져온 유정 정보를 조회 하여 서비스에서 매칭 후 조회한 userId를 반환
            JwtDTO jwtDTO = userService.loginAndIssuanceToken(pRequest, pResponse);

            return ResponseEntity.ok(jwtDTO);
    }

    /**
     * googleAuth
     * 소셜 구글 로그인 진행 컨트롤러
     * @param pResponse : 헤더에 refresh 토큰을 담기 위해서 넣음
     * @param pSocialAuthorizationCodeDTO : 소셜 인가 코드를 받기 위한 DTO
     * @return : 로그인이 정상 진행시 로그인 과정에서 토큰 발행
     */
    @PostMapping("/googleAuth")
    public ResponseEntity<?> googleAuth(
            HttpServletResponse pResponse,
            @RequestBody SocialAuthorizationCodeDTO pSocialAuthorizationCodeDTO) {

        JwtDTO jwtDTO = userService.socialLoginAndIssuanceToken(
                socialLoginService.googleGetUserId(pSocialAuthorizationCodeDTO),
                                                    pResponse, "google",
                                                    pSocialAuthorizationCodeDTO.getAutoLogin());

        return ResponseEntity.ok(jwtDTO);
    }

    /**
     * kakaoAuth
     * @param pResponse : 헤더에 refresh 토큰을 담기 위해서 넣음
     * @param pSocialAuthorizationCodeDTO : 소셜 인가 코드를 받기 위한 DTO
     * @return : 로그인이 정상 진행시 로그인 과정에서 토큰 발행
     */
    @PostMapping("/kakaoAuth")
    public ResponseEntity<?> kakaoAuth(
            HttpServletResponse pResponse,
            @RequestBody SocialAuthorizationCodeDTO pSocialAuthorizationCodeDTO) {

        JwtDTO jwtDTO = userService.socialLoginAndIssuanceToken(
                socialLoginService.kakaoGetUserId(pSocialAuthorizationCodeDTO),
                                                    pResponse, "kakao",
                                                    pSocialAuthorizationCodeDTO.getAutoLogin());
        return ResponseEntity.ok(jwtDTO);
    }

    /**
     * naverAuth
     * @param pResponse : 헤더에 refresh 토큰을 담기 위해서 넣음
     * @param pSocialAuthorizationCodeDTO : 소셜 인가 코드를 받기 위한 DTO
     * @return : 로그인이 정상 진행시 로그인 과정에서 토큰 발행
     */
    @PostMapping("/naverAuth")
    public ResponseEntity<?> naverAuth(
            HttpServletResponse pResponse,
            @RequestBody SocialAuthorizationCodeDTO pSocialAuthorizationCodeDTO) {
        JwtDTO jwtDTO = userService.socialLoginAndIssuanceToken(
                socialLoginService.naverGetUserId(pSocialAuthorizationCodeDTO),
                                                    pResponse, "naver",
                                                    pSocialAuthorizationCodeDTO.getAutoLogin());
        return ResponseEntity.ok(jwtDTO);
    }

    /**
     * duplicateCheck
     * 중복 체크 요청 처리 컨트롤러 메서드
     * @param pDuplicateDto : 중복 체크할 정보가 담긴 DTO(검색할 컬럼, 검색할 내용)
     * @return 검증 결과에 따라 true | false 반환
     */
    @PostMapping("/duplicatecheck")
    public ResponseEntity<?> duplicateCheck(@RequestBody UserDuplicateCheckRequestDTO pDuplicateDto) {

        // 검증 로직 진행 하여 전달 받은 true, false 반환
        return ResponseEntity.ok(userService.duplicateCheck(pDuplicateDto));
    }

    /**
     * idFind
     * 유저 정보를 받아서 아이디 찾기
     * @param qFindIdInfo : 유저 정보가 담긴 DTO
     * @return 아이디 찾기 정보 DTO(id가 담김)
     */
    @PostMapping("/idFind")
    public ResponseEntity<?> idFind (@RequestBody UserFindIdRequestDTO qFindIdInfo) {
        UserFindIdResponseDTO userFindIdResponseDTO = userService.findId(qFindIdInfo);
        return ResponseEntity.ok(userFindIdResponseDTO);
    }

    /**
     * passwordFind
     * 패스워드 찾기 (전달한 정보가 일치하면 패스워드 변경 페이지로 이동하게끔 만드는 컨트롤러)
     * @param userFindPasswordRequestDTO : 비밀번호찾기 요청 DTO(id, email, name)
     * @return : 200을 전달
     */
    @PostMapping("/passwordFind")
    public ResponseEntity<?> passwordFind (@RequestBody UserFindPasswordRequestDTO userFindPasswordRequestDTO) {
        userService.passwordFind(userFindPasswordRequestDTO);
        return ResponseEntity.ok("정보가 일치합니다.");
    }

    /**
     * passwordChange
     * 패스워드 변경 요청 처리
     * @param pUserChangePasswordRequestDTO : 변경할 패스워드가 담긴 DTO
     * @return : 정상적으로 변경되었으면 200 전달
     */
    @PostMapping("/passwordChange")
    public ResponseEntity<?> passwordChange (@RequestBody UserChangePasswordRequestDTO pUserChangePasswordRequestDTO) {
        // 비밀 번호 변경 진행
        userService.changePassword(pUserChangePasswordRequestDTO);
        return ResponseEntity.ok("비밀 번호가 정상적으로 변경 되었습니다.");
    }

    /**
     * 회원 탈퇴 컨트롤러
     * @return 응답 200
     */
    @PostMapping("/withdrawal")
    public ResponseEntity<?> userWithdrwal () {
        userService.userWithdrawal();
        return ResponseEntity.ok("회원탈퇴가 정상적으로 이루어졌습니다.");
    }


    /**
     * 선호도 넣기 컨트롤러
     * @param userRegisterFavoriteDTO : 프론트에서 받은 데이터를 저장할 DTO
     * @return : 성공했을 경우 message 전달
     */
    @PostMapping("/favoriteRegistration")
    public ResponseEntity<?> insertFavorites (@RequestBody UserRegisterFavoriteDTO userRegisterFavoriteDTO) {
        userService.insertUserFavoriteWithIdxAndDate(userRegisterFavoriteDTO);
        return ResponseEntity.ok("선호도가 정상적으로 등록되었습니다.");
    }

}
