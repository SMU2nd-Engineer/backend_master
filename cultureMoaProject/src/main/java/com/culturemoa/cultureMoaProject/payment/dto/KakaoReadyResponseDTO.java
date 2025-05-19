package com.culturemoa.cultureMoaProject.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoReadyResponseDTO {
    private String tid;

    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;

    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl;

    @JsonProperty("next_redirect_app_url")
    private String nextRedirectAppUrl;

    @JsonProperty("android_app_scheme")
    private String androidAppScheme;

    @JsonProperty("ios_app_scheme")
    private String iosAppScheme;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

