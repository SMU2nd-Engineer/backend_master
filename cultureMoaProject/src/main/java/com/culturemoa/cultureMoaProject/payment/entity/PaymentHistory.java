package com.culturemoa.cultureMoaProject.payment.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * PaymentHistory
 * DB에 저장할 결제 이력 데이터 관리
 * idx : DB에서 AUTO_INCREMENT
 * tid : PG사에서 받은 결제 고유번호
 * amount : 결제 금액
 * payMethod : 결제 수단(kakao 외에 다른 결제 기능 확장성을 고려)
 * status : 상태(READY, APPROVED, FAILED 등)
 * createdAt : 생성일
 * buyerId : 구매자 회원 ID
 * sellerId : 판매자 회원 ID
 * productId : 상품 ID
 * tradeType : DIRECT, DELIVERY
 * deliveryAddress : 배송지(필요시)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {
    private int idx;
    private String tid;
    private int amount;
    private int payMethod;
    private int buyerIdx;
    private int sellerIdx;
    private int productIdx;
    private int tradeType; // 0 - 배달, 1 - 직거래
    private String deliveryAddress;
}
