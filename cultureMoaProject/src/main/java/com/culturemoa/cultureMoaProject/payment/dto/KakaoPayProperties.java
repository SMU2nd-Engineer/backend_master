package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakao.pay")
public class KakaoPayProperties {
    private String secretKey;
    private String cid;
}

