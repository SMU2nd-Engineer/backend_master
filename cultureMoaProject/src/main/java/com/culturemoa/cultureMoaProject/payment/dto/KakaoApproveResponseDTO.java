package com.culturemoa.cultureMoaProject.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApproveResponseDTO {
    private String aid;  // 요청 고유번호
    private String tid;  // 결제 고유번호
    private String cid;  // 가맹점 코드
    private String sid;  // 정기 결제용 ID
    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("payment_method_type")
    private String paymentMethodType;

    @JsonProperty("amount")
    private Amount amount;  // 내부 클래스로 결제 금액 정보 매핑

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("approved_at")
    private String approvedAt;
    
    @JsonProperty("payload")
    private String payload;

//     --- 내부 클래스 : 금액 정보 ---
    @Getter
    @Setter
    public static class Amount {
        private int total;         // 전체 결제 금액
        @JsonProperty("tax_free")
        private int taxFree;       // 비과세 금액
        @JsonProperty("vat")
        private int vat;           // 부가세
        @JsonProperty("discount")
        private int discount;      // 할인 금액
    }
}
