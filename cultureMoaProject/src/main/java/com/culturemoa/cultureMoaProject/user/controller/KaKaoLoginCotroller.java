package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.user.dto.KakaoTokenResponseDTO;
import com.culturemoa.cultureMoaProject.user.dto.KakaoUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;

@RestController

public class KaKaoLoginCotroller {

    // 환경 변수 가져오기
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect.uri}")
    private String kakoRedirectUri;

    @Autowired
    private AuthJwtService authJwtService;

    @PostMapping("/kakaoAuth")
    public ResponseEntity<?> requestMethodName(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> resValue) {

        // 디버깅 코드
        System.out.println("[Controller] /kakaoAuth 요청 도착함");
        System.out.println("[Controller] 전달된 code 값: " + resValue.get("code"));

        System.out.println("인가 코드 body에서 가져오는지 확인 : " + resValue.get("code"));
        String code = resValue.get("code");


        // 코드를 사용하여 카카오 서버에 액세스 코드 요청
        // 요청하기 위한 MultiValueMap 객체 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code"); // 고정값인 authorization_code
        formData.add("client_id", kakaoApiKey);
        formData.add("redirect_uri", kakoRedirectUri);
        formData.add("code", code);

        RestClient restClient = RestClient.create(); //RestClient 인스턴스를 생성
        KakaoTokenResponseDTO kakaoDto = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token") // 전송할 대상 URL을 설정
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(formData)
                .retrieve()
                .body(KakaoTokenResponseDTO.class);
        System.out.println("====================================================");
        System.out.println("kakaoDto : " +kakaoDto);
        if(kakaoDto == null) {
            return ResponseEntity.status(400).body("사용자 정보 전달.");
        }
        String kakaoAccessToken = kakaoDto.getAccess_token();
        System.out.println(kakaoAccessToken);
        // DTO 를 활용하여 데이터를 받아옮
        KakaoUserInfoDTO getUserInfo = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer "+ kakaoAccessToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .body(KakaoUserInfoDTO.class);

        // 받아온 데이터를 활용하여 암호 검증 서비스 진행 - 테스트라서 컨트롤러에 모아서 진행
        if (getUserInfo != null) {
            // id를 활용하여 사용자 db와 조회하는 로직 설계할 것
            // 현재는 단순히 하드 코딩하여 로그인 진행행
            System.out.println(getUserInfo.getId());
            if (!"4242246033".equals(getUserInfo.getId().toString())) {
                return ResponseEntity.status(401).body("비밀번호가 다릅니다.");
            }
            System.out.println("소셜 고유아이디가 매칭되었습니다.");
        }
        try {

            if (getUserInfo != null && getUserInfo.getId() != null) {
                JwtDTO jwtDTO = authJwtService.tokenCreateSave(response, getUserInfo.getId().toString());
                return ResponseEntity.ok(jwtDTO);
            } else {
                return ResponseEntity.status(401).body("KaKao Invalid credentials");
            }

        } catch (Exception e) {
            System.out.println("카카오 인증 후 토큰 발급 받는 과정에서 오류 발생.");
            ResponseEntity.status(400).body("카카오 인증 후 토큰 발급 받는 과정에서 오류 발생.");
        }



        return ResponseEntity.ok(getUserInfo);
    }


}
