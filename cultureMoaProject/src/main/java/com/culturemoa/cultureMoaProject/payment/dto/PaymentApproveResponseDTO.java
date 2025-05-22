package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PaymentApproveResponseDTO
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApproveResponseDTO {
    private String aid;  // 요청 고유번호
    private String tid;  // 결제 고유번호
    private String cid;  // 가맹점 코드
    private String sid;  // 정기 결제용 ID
    private String partnerOrderId;
    private String partnerUserId;
    private String paymentMethodType;
    private Amount amount;  // 내부 클래스로 결제 금액 정보 매핑
    private String itemName;
    private int quantity; // 상품 수량
    private String approvedAt; // 결제 승인 시각
    private String payload;

    // --- 내부 클래스 : 금액 정보 ---
    @Getter
    @Setter
    public static class Amount {
        private int total;         // 전체 결제 금액
        private int taxFree;       // 비과세 금액
        private int vat;           // 부가세
        private int discount;      // 할인 금액
    }
}
