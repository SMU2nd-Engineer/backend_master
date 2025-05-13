package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 구글 인가 코드로 토큰 요청시 사용할 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleTokenRequestDTO {

    private String grantType = "authorization_code";
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String code;


    public MultiValueMap<String, String> requestToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType); // 발급이므로 authorization_code
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);
        return formData;
    }
}
