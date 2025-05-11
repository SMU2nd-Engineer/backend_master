package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.jwt.JwtValidator;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtValidator jwtValidator;

    @Autowired
    private AuthJwtService authJwtService;

    /**
     * 회원 가입 페이지 정보를 이용 회원 가입 진행.
     * @param pRequest : 프론트에서 전달 받은 회원 가입 정보
     * @return : 정상적인 경우 ok 응답을 반환
     */
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistrationDTO (
            @RequestBody UserRegisterRequestDTO pRequest) {
        //회원 가입 진행.
        userService.registerUser(pRequest);
        return ResponseEntity.ok("User information registration completed ");
    }

    /**
     * loginAccess
     * 로그인 컨트롤러 메서드
     * @param pRequest : 요청의 다디를 받음
     * @param pResponse : 응답 헤더에 값을 저장하기 위해서 넣음.
     * @return : AccessToken을 반환
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAccess(@RequestBody UserLoginRequestDTO pRequest, HttpServletResponse pResponse) {
            // 가져온 유정 정보를 조회 하여 서비스에서 매칭 후 조회한 userId를 반환
            String userId = userService.loginAuth(pRequest.getId(), pRequest.getPassword());

            // 인증 성공하면 jwt 생성 및 리프레시 쿠키에 저장.
            JwtDTO jwtDTO = authJwtService.tokenCreateSave(pResponse, userId);

            return ResponseEntity.ok(jwtDTO);
    }

    /**
     * duplicateCheck
     * 중복 체크 요청 처리 컨트롤러 메서드
     * @pDuplicateDto :
     * @return 검증 결과에 따라 true | false 반환
     */
    @PostMapping("/duplicatecheck")
    public ResponseEntity<?> duplicateCheck(@RequestBody UserDuplicateCheckRequestDTO pDuplicateDto) {
        // 전달 받은 값 변수에 할당
        String pCheckList = pDuplicateDto.getName();
        String pCategory = pDuplicateDto.getCategory();

        // 검증 로직에서 true를 받으면 true를 반환
        if(userService.duplicateCheck(pCheckList, pCategory) > 0) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }

    @PostMapping("/idFind")
    public ResponseEntity<?> idFind (@RequestBody UserFindIdRequestDTO qFindIdInfo) {
        UserFindIdResponseDTO userFindIdResponseDTO = userService.findId(qFindIdInfo);
        return ResponseEntity.ok(userFindIdResponseDTO);
    }

    @PostMapping("/passwordFind")
    public ResponseEntity<?> passwordFind () {
        return ResponseEntity.ok("정보가 일치합니다.");
    }

    @PostMapping("/passwordChange")
    public ResponseEntity<?> passwordChange (@RequestBody UserChangePasswordRequestDTO pUserChangePasswordRequestDTO) {
        // 비밀 번호 변경 진행
        userService.changePassword(pUserChangePasswordRequestDTO);
        return ResponseEntity.ok("비밀 번호가 정상적으로 변경 되었습니다.");
    }

}
