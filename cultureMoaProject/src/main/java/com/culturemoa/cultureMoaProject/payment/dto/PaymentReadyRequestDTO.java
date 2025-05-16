package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * PaymentReadyRequestDTO
 * 결제 준비 요청 DTO
 * (사용자가 상품을 선택하고 결제 버튼을 클릭하면 이 DTO가 서버로 전달됩니다.)
 * (이 DTO를 기반으로 카카오페이 결제 준비 API를 호출합니다.)
 * cid : 가맹점 코드, 10자
 * partnerOrderId : 가맹점 주문번호, 최대 100자
 * partnerUserId : 가맹점 회원 id, 최대 100자 (실명, ID와 같은 개인정보가 포함되지 않도록 유의)
 * itemName : 상품명, 최대 100자
 * itemCode : 상품코드, 최대 100자
 * quantity : 상품수량
 * amount : 결제 금액(원)
 * taxFreeAmount : 상품 비과세 금액
 * approvalUrl : 결제 성공 시 redirect url, 최대 255자
 * cancelUrl : 결제 취소 시 redirect url, 최대 255자
 * failUrl : 결제 실패 시 redirect url, 최대 255자

 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReadyRequestDTO {
//    @Value("${kakao.pay.cid}")
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private int quantity;
    private int amount;
    private int taxFreeAmount;
    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
    private String payMethod;
}

