package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.user.dto.GoogleTokenResponseDTO;
import com.culturemoa.cultureMoaProject.user.dto.GoogleUserInfoDTO;
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

import java.util.HashMap;
import java.util.Map;

/**
 * GoogleLoginCotroller
 * 구글 로그인 컨트롤러
 * googleClientId : 구글 로그인을 위한 클라이언트 id
 * googleClientSecret : 구글 로그인을 위한 클라이언트 비밀키
 * googleRedirectUri : 구글 로그인을 위한 리다이렉트 경로
 * loginState : csfr 공격 방지 위해 프론트에서 지정한 state값
 */
@RestController
public class GoogleLoginCotroller {

    // 환경 변수 가져오기
    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${google.redirect.uri}")
    private String googleRedirectUri;

//    @Value("${login.state}")
//    private String loginState;

    @Autowired
    private AuthJwtService authJwtService;

    /**
     * 구글 소셜 로그인 백엔드 처리 컨트롤럴 역할(추가적으로 필요로직을 분리할 수 있음)
     * @param request : 전달 받은 요청
     * @param response : 요청할 것
     * @param resValue : body에서 정보를 가져올 맵
     * @return 응답을 반환
     */
    @PostMapping("/googleAuth")
    public ResponseEntity<?> googleAuth(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> resValue) {

        // 디버깅 코드
        System.out.println("[Controller] /googleAuth 요청 도착함");
        System.out.println("[Controller] 전달된 code 값: " + resValue.get("code"));

        System.out.println("인가 코드 body에서 가져오는지 확인 : " + resValue.get("code"));
        String code = resValue.get("code");

        // 네이버에서 코드를 이용하여 토큰을 발급해야함.

        // 코드를 사용하여 네이버 서버에 액세스 코드 요청
        // 요청하기 위한 MultiValueMap 객체 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code"); // 발급이므로 authorization_code
        formData.add("client_id", googleClientId);
        formData.add("client_secret", googleClientSecret);
        formData.add("redirect_uri", googleRedirectUri);
        formData.add("code", code);

        RestClient restClient = RestClient.create(); // 서버에서 네이버로 REST 요청을 위한 RestClient 인스턴스를 생성
        GoogleTokenResponseDTO googleTokenDto = restClient.post()
                .uri("https://oauth2.googleapis.com/token") // 전송할 대상 URL을 설정
                .body(formData)
                .retrieve()
                .body(GoogleTokenResponseDTO.class);
        System.out.println("====================================================");
        System.out.println("Google : " +googleTokenDto);
        if(googleTokenDto == null) {
            return ResponseEntity.status(400).body("사용자 정보 전달.");
        }
        String googleAccessToken = googleTokenDto.getAccess_token();
        System.out.println("googleAccessToken : " + googleAccessToken);
        // DTO 를 활용하여 데이터를 받아옮
        GoogleUserInfoDTO getUserInfo = restClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .header("Authorization", "Bearer "+ googleAccessToken)
                .retrieve()
                .body(GoogleUserInfoDTO.class);
        System.out.println("access로 요청 진행함");
        System.out.println("getUserInfo : " + getUserInfo);
        // 인텔리j에서 getResponse가 null exception 발생 시킬 수 있다고 해서 추천 방법 중 하나로 수정
        System.out.println("사용자 추출한 고유 id : " + (getUserInfo != null ? getUserInfo.getSub() : null));
        // 받아온 데이터를 활용하여 암호 검증 서비스 진행 - 테스트라서 컨트롤러에 모아서 진행
        if (getUserInfo != null) {
            // id를 활용하여 사용자 db와 조회하는 로직 설계할 것
            // 현재는 단순히 하드 코딩하여 로그인 진행행
            System.out.println("사용자 추출한 고유 id : " + getUserInfo.getSub());
            if (!"104753521565973169492".equals(getUserInfo.getSub())) {
                String socialId = getUserInfo.getSub();
                String provider = "google";
                HashMap<String, String> bodyContainer = new HashMap<>();
                bodyContainer.put("socialId", socialId);
                bodyContainer.put("provider", provider);
                return ResponseEntity.status(402).body(bodyContainer);
            }
            System.out.println("소셜 고유아이디가 매칭되었습니다.");
        } else {
            // 소셜 정보가 없음. 문제가 있다고 보고 400 에러를 던짐
            return ResponseEntity.status(400).body("소셜 정보가 매칭 되지 않습니다.");
        }
        // 토큰 발급
        try {
            System.out.println("여기를 진행함");
            if (getUserInfo.getSub() != null) {
                System.out.println("여기를 진행함 11111111111111111111111111" );
                JwtDTO jwtDTO = authJwtService.tokenCreateSave(response, getUserInfo.getSub());
                return ResponseEntity.ok(jwtDTO);
            } else {
                System.out.println("네이버 인증 후 토큰 발급 받는 과정에서 오류 발생.");
                return ResponseEntity.status(401).body("Google Invalid credentials");
            }

        } catch (Exception e) {

            ResponseEntity.status(400).body("네이버 인증 후 토큰 발급 받는 과정에서 오류 발생.");
        }



        return ResponseEntity.ok(getUserInfo);
    }


}
