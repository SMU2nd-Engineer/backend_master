package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PaymentApproveRequestDTO
 * 결제 승인 DTO
 * (사용자가 카카오페이 결제 페이지에서 결제 완료 후, 리다이렉트된 페이지에서 호출)
 * (이 DTO로 카카오페이 결제 승인 API를 호출
 * cid : 가맹점 코드, 10자
 * tid : 결제 준비 시 발급받은 고유 번호
 * partnerOrderId : 가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
 * partnerUserId : 가맹점 회원id, 결제 준비 API 요청과 일치해야 함
 * pgToken : 카카오페이에서 리다이렉트 시 전달한 승인 토큰
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApproveRequestDTO {
    private String cid;
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private String pgToken;
}
