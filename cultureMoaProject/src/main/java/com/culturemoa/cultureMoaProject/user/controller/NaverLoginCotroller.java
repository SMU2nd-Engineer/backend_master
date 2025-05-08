package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.user.dto.NaverTokenResponseDTO;
import com.culturemoa.cultureMoaProject.user.dto.NaverUserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * NaverLoginCotroller
 */
@RestController
public class NaverLoginCotroller {

    // 환경 변수 가져오기
    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.redirect.uri}")
    private String naverRedirectUri;

    @Value("${login.state}")
    private String loginState;

    @Autowired
    private AuthJwtService authJwtService;

    @PostMapping("/naverAuth")
    public ResponseEntity<?> requestMethodName(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> resValue) {

        // 디버깅 코드
        System.out.println("[Controller] /naverAuth 요청 도착함");
        System.out.println("[Controller] 전달된 code 값: " + resValue.get("code"));

        System.out.println("인가 코드 body에서 가져오는지 확인 : " + resValue.get("code"));
        String code = resValue.get("code");

        // 네이버에서 코드를 이용하여 토큰을 발급해야함.

        // 코드를 사용하여 네이버 서버에 액세스 코드 요청
        // 요청하기 위한 MultiValueMap 객체 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code"); // 발급이므로 authorization_code
        formData.add("client_id", naverClientId);
        formData.add("client_secret", naverClientSecret);
        formData.add("code", code);
        formData.add("state", loginState);

        RestClient restClient = RestClient.create(); // 서버에서 네이버로 REST 요청을 위한 RestClient 인스턴스를 생성
        NaverTokenResponseDTO NaverDto = restClient.post()
                .uri("https://nid.naver.com/oauth2.0/token") // 전송할 대상 URL을 설정
                .body(formData)
                .retrieve()
                .body(NaverTokenResponseDTO.class);
        System.out.println("====================================================");
        System.out.println("NaverDto : " +NaverDto);
        if(NaverDto == null) {
            return ResponseEntity.status(400).body("사용자 정보 전달.");
        }
        String googleAccessToken = NaverDto.getAccess_token();
        System.out.println("googleAccessToken : " + googleAccessToken);
        // DTO 를 활용하여 데이터를 받아옮
        NaverUserInfoDTO getUserInfo = restClient.get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer "+ googleAccessToken)
                .retrieve()
                .body(NaverUserInfoDTO.class);
        System.out.println("여기까지 진행");
        System.out.println("getUserInfo : " + getUserInfo);
        // 인텔리j에서 getResponse가 null exception 발생 시킬 수 있다고 해서 추천 방법 중 하나로 수정
        System.out.println("사용자 추출한 고유 id : " + (getUserInfo != null ? getUserInfo.getResponse().getId() : null));
        // 받아온 데이터를 활용하여 암호 검증 서비스 진행 - 테스트라서 컨트롤러에 모아서 진행
        if (getUserInfo != null) {
            // id를 활용하여 사용자 db와 조회하는 로직 설계할 것
            // 현재는 단순히 하드 코딩하여 로그인 진행행
            System.out.println("사용자 추출한 고유 id : " + getUserInfo.getResponse().getId());
            if (!"YW_8tOJnONdvRSY1Xjr6Fx6s_72afOE8h-q_nFac32Q".equals(getUserInfo.getResponse().getId())) {
                System.out.println("여기를 진행함 000000000000000000000000000");
                return ResponseEntity.status(402).body("비밀번호가 다릅니다.");
            }
            System.out.println("소셜 고유아이디가 매칭되었습니다.");
        }
        // 토큰 발급
        try {
            System.out.println("여기를 진행함");
            if (getUserInfo != null && getUserInfo.getResponse().getId() != null) {
                System.out.println("여기를 진행함 11111111111111111111111111" );
                JwtDTO jwtDTO = authJwtService.tokenCreateSave(response, getUserInfo.getResponse().getId());
                return ResponseEntity.ok(jwtDTO);
            } else {
                System.out.println("네이버 인증 후 토큰 발급 받는 과정에서 오류 발생.");
                return ResponseEntity.status(401).body("Naver Invalid credentials");
            }

        } catch (Exception e) {

            ResponseEntity.status(400).body("네이버 인증 후 토큰 발급 받는 과정에서 오류 발생.");
        }



        return ResponseEntity.ok(getUserInfo);
    }


}
