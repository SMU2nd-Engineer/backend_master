package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PaymentResponseDTO
 * 결제 응답 DTO
 * 카카오페이에서 결제 고유번호와 결제 페이지 URL을 응답
 * 이 정보를 클라이언트에 전달해서 사용자가 카카오페이 결제 화면으로 이동하게 함
 * tid : 카카오페이에서 발급한 결제 고유 번호
 * nextRedirectPcUrl : 사용자가 결제하러 이동할 카카오페이 결제페이지 URL - PC웹
 * nextRedirectMobileUrl : 사용자가 결제하러 이동할 카카오페이 결제페이지 URL - 모바일웹
 * nextRedirectAppUrl : 사용자가 결제하러 이동할 카카오페이 결제페이지 URL - App
 * androidAppScheme : 카카오페이 결제 화면으로 이동하는 Android 앱 스킴(Scheme) - 내부 서비스용
 * iosAppScheme : 카카오페이 결제 화면으로 이동하는 iOS 앱 스킴 - 내부 서비스용
 * createdAt : 결제 준비된 시각(서버 저장용)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String tid;
    private String nextRedirectPcUrl;
    private String nextRedirectMobileUrl;
    private String nextRedirectAppUrl;
    private String androidAppScheme;
    private String iosAppScheme;
    private LocalDateTime createdAt;
}
