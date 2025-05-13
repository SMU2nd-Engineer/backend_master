package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoTokenRequestDTO {
    private String grantType = "authorization_code";
    private String clientId;
    private String redirectUri;
    private String code;

    public MultiValueMap<String, String> requestToken () {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType); // 고정값인 authorization_code
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);
        return formData;
    }
}
