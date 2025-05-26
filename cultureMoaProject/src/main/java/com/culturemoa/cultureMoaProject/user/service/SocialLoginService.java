package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.NotFountSocialInfoException;
import com.culturemoa.cultureMoaProject.user.exception.SocialNoAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

/**
 * 소셜 로그인 인가 코드로 토큰 발행 및 사용자 정보 가져오기 서비스 
 * googleClientId : 구글 api key 
 * googleClientSecret : 구글 api 비밀키
 * googleRedirectUri : 구글 리다이텍트 주소
 * kakaoApiKey : 카카오 api key
 * kakoRedirectUri : 카카오 리다이렉트 주소
 * naverClientId : 네이버 api 키
 * naverClientSecret : 네이버 api 비밀키
 * naverRedirectUri : 네이버 리다이렉트 주소
 * loginState : state 값 (임의로 지정한 값)
 */
@Service
public class SocialLoginService {

    // 환경 변수 가져오기
    
    // 구글
    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    @Value("${google.redirect.uri}")
    private String googleRedirectUri;
    
    // 카카오
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect.uri}")
    private String kakoRedirectUri;

    // 환경 변수 가져오기
    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.redirect.uri}")
    private String naverRedirectUri;

    @Value("${login.state}")
    private String loginState;

    /**
     * 구글 로그인 진행하여 사용자 id 추출
     * @param socialAuthorizationCodeDTO : 코드 정보가 담긴 DTO
     * @return 사용자 id를 반환
     */
    public String googleGetUserId (SocialAuthorizationCodeDTO socialAuthorizationCodeDTO) {
        String googleCode = socialAuthorizationCodeDTO.getCode();
        // 코드를 사용하여 구글 서버에 액세스 코드 요청
        // 요청하기 위한 MultiValueMap 객체 생성
        GoogleTokenRequestDTO googleTokenRequestDTO = new GoogleTokenRequestDTO();
        googleTokenRequestDTO.setClientId(googleClientId);
        googleTokenRequestDTO.setClientSecret(googleClientSecret);
        googleTokenRequestDTO.setRedirectUri(googleRedirectUri);
        googleTokenRequestDTO.setCode(googleCode);

        MultiValueMap<String, String > googleFormData = googleTokenRequestDTO.requestToken();

        RestClient restClient = RestClient.create(); // 서버에서 네이버로 REST 요청을 위한 RestClient 인스턴스를 생성
        GoogleTokenResponseDTO googleTokenResponseDTO = restClient.post()
                .uri("https://oauth2.googleapis.com/token") // 전송할 대상 URL을 설정
                .body(googleFormData)
                .retrieve()
                .body(GoogleTokenResponseDTO.class); // 구글 토큰 ResponseDTO로 받음
        
        // 응답 DTO에서 구글 요청 토큰 추출
        String googleAccessToken = googleTokenResponseDTO != null ? googleTokenResponseDTO.getAccess_token() : null;
        if (googleAccessToken == null) {
            throw new SocialNoAccessTokenException();
        }

        // 토큰으로 구글에 사용자 정보 요청
        GoogleUserInfoDTO googleGetUserInfo = restClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                // Bearer 뒤에 빈칸 넣어야 한다!
                .header("Authorization", "Bearer "+ googleAccessToken)
                .retrieve()
                .body(GoogleUserInfoDTO.class);

        if (googleGetUserInfo != null) {
            return googleGetUserInfo.getSub();
        } else {
            throw new NotFountSocialInfoException();
        }
    }

    /**
     * 카카오 로그인 진행하여 사용자 id 추출
     * @param socialAuthorizationCodeDTO : 코드 정보가 담긴 DTO
     * @return 사용자 id를 반환
     */
    public String kakaoGetUserId (SocialAuthorizationCodeDTO socialAuthorizationCodeDTO) {
        String kakaoCode = socialAuthorizationCodeDTO.getCode();
        KakaoTokenRequestDTO kakaoTokenRequestDTO = new KakaoTokenRequestDTO();
        kakaoTokenRequestDTO.setClientId(kakaoApiKey);
        kakaoTokenRequestDTO.setRedirectUri(kakoRedirectUri);
        kakaoTokenRequestDTO.setCode(kakaoCode);

        MultiValueMap<String, String > kakaoFormData = kakaoTokenRequestDTO.requestToken();

        RestClient restClient = RestClient.create(); // 서버에서 카카오로 REST 요청을 위한 RestClient 인스턴스를 생성
        KakaoTokenResponseDTO kakaoTokenResponseDTO = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token") // 전송할 대상 URL을 설정
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(kakaoFormData)
                .retrieve()
                .body(KakaoTokenResponseDTO.class);

        // 응답 DTO에서 카카오 요청 토큰 추출
        String kakaoAccessToken = kakaoTokenResponseDTO != null ? kakaoTokenResponseDTO.getAccess_token() : null;

        if (kakaoTokenResponseDTO == null) {
            throw new SocialNoAccessTokenException();
        }

        KakaoUserInfoDTO kakaoGetUserInfo = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer "+ kakaoAccessToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .body(KakaoUserInfoDTO.class);

        if (kakaoGetUserInfo != null) {
            // 고유 아이디가 Long으로 반환되어 문자열로 반환하여 리턴
            return kakaoGetUserInfo.getId().toString();
        } else {
            // 토큰으로 정보를 가져오지 않았으므로 매칭 예외 발생.
            throw new NotFountSocialInfoException();
        }
    }

    /**
     * 네이버 로그인 진행하여 사용자 id 추출
     * @param socialAuthorizationCodeDTO : 코드 정보가 담긴 DTO
     * @return 사용자 id를 반환
     */
    public String naverGetUserId (SocialAuthorizationCodeDTO socialAuthorizationCodeDTO) {
        String naverCode = socialAuthorizationCodeDTO.getCode();

        NaverTokenRequestDTO naverTokenRequestDTO = new NaverTokenRequestDTO();
        naverTokenRequestDTO.setClientId(naverClientId);
        naverTokenRequestDTO.setClientSecret(naverClientSecret);
        naverTokenRequestDTO.setCode(naverCode);
        naverTokenRequestDTO.setState(loginState);

        MultiValueMap<String, String > naverFormData = naverTokenRequestDTO.requestToken();

        RestClient restClient = RestClient.create(); // 서버에서 네이버로 REST 요청을 위한 RestClient 인스턴스를 생성
        NaverTokenResponseDTO naverTokenResponseDTO = restClient.post()
                .uri("https://nid.naver.com/oauth2.0/token") // 전송할 대상 URL을 설정
                .body(naverFormData)
                .retrieve()
                .body(NaverTokenResponseDTO.class);

        // 응답 DTO에서 네이버 요청 토큰 추출
        String naverAccessToken = naverTokenResponseDTO != null ? naverTokenResponseDTO.getAccess_token() : null;

        if (naverTokenResponseDTO == null) {
            throw new SocialNoAccessTokenException();
        }

        NaverUserInfoDTO naverGetUserInfo = restClient.get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .header("Authorization", "Bearer "+ naverAccessToken)
                .retrieve()
                .body(NaverUserInfoDTO.class);

        if (naverGetUserInfo != null) {
            // 고유 아이디가 Long으로 반환되어 문자열로 반환하여 리턴
            return naverGetUserInfo.getResponse().getId();
        } else {
            // 토큰으로 정보를 가져오지 않았으므로 매칭 예외 발생.
            throw new NotFountSocialInfoException();
        }

    }

}
