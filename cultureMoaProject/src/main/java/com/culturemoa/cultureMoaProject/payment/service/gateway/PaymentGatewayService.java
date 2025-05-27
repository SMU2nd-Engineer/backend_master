package com.culturemoa.cultureMoaProject.payment.service.gateway;

import com.culturemoa.cultureMoaProject.payment.dto.*;

/**
 * PaymentGatewayService
 * 모든 PG사(Kakao, Naver, Toss 등)의 결제 기능을 똑같은 규칙(형식)으로 사용하기 위해 만든 규격서
 * getPayMethod : 해당 서비스가 담당하는 결제 수단 반환 (ex. kakao, toss, naver)
 */
public interface PaymentGatewayService {
    // 결제 준비 단계(결제 페이지 이동 링크 발급, TID 발급 등)
    PaymentResponseDTO readyToPay(PaymentReadyRequestDTO request);
    // 사용자가 결제 완료 후, 결제 승인 처리(최종 결제 확정)
    KakaoApproveResponseDTO approvePayment(PaymentApproveRequestDTO request);
    // 결제 수단 문자열로 반환
    KakaoCancelResponseDTO cancelPayment(KakaoCancelRequestDTO request);
    void handleFailedPayment(String tid, String methodResultMessage);
    static String getPayMethod(int payMethod){
        return switch (payMethod) {
            case 6001 -> "kakao";
            case 6002 -> "toss";
            case 6003 -> "naver";
            default -> "error";
        };
    }
}