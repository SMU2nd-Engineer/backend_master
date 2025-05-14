package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PaymentResponseDTO
 * 결제 응답 DTO
 * 카카오페이에서 결제 고유번호와 결제 페이지 URL을 응답
 * 이 정보를 클라이언트에 전달해서 사용자가 카카오페이 결제 화면으로 이동하게 함
 * tid : 카카오페이에서 발급한 결제 고유 번호
 * nextRedirectUrl : 사용자가 결제하러 이동할 카카오페이 결제페이지 URL
 * createdAt : 결제 준비된 시각(서버 저장용)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String tid;
    private String nextRedirectUrl;
    private String createdAt;
}
