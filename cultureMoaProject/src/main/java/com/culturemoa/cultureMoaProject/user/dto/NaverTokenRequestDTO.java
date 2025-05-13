package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverTokenRequestDTO {

    String grantType = "authorization_code";
    String clientId;
    String clientSecret;
    String state;
    String code;

    public MultiValueMap<String, String> requestToken () {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType); // 발급이므로 authorization_code
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("code", code);
        formData.add("state", state);
        return formData;
    }
}
