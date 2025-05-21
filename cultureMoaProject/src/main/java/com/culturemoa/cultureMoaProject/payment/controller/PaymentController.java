package com.culturemoa.cultureMoaProject.payment.controller;

import com.culturemoa.cultureMoaProject.payment.dto.*;
import com.culturemoa.cultureMoaProject.payment.service.gateway.PaymentGatewayService;
import com.culturemoa.cultureMoaProject.payment.service.kakao.KakaoPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    // PG사 구분을 위한 서비스 매핑
    private final Map<String, PaymentGatewayService> paymentGatewayServices;

    @PostMapping("/ready")
    public ResponseEntity<PaymentResponseDTO> readyPayment(
            @RequestParam String payMethod,
            @RequestBody PaymentReadyRequestDTO request
            ){
        // payMethod를 기반으로 어떤 PG사를 이용할지 선택
        PaymentGatewayService service = getPaymentService(payMethod);
        // service객체의 readyToPay 메서드 호출하고 결과를 PaymentResponseDTO로 반환
        PaymentResponseDTO response = service.readyToPay(request);
        // HTTP 상태코드 200(ok)과 함께 response를 JSON으로 변환하여 클라이언트에 응답
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<KakaoApproveResponseDTO> approvePayment(
            @RequestBody PaymentApproveRequestDTO request,
            @RequestParam String payMethod
    ) {
        PaymentGatewayService service = getPaymentService(payMethod);
        System.out.println("approvePayment called with pgToken: " + request.getPgToken());

        KakaoApproveResponseDTO response = service.approvePayment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    public ResponseEntity<KakaoCancelResponseDTO> cancel(
            @RequestParam String payMethod,
            @RequestParam String tid
    ) {
        PaymentGatewayService service = getPaymentService(payMethod);
        KakaoCancelResponseDTO cancelInfo =((KakaoPaymentService) service).cancelPayment(tid);
        return ResponseEntity.ok(cancelInfo);
    }

    @GetMapping("/fail")
    public ResponseEntity<String> fail(
            @RequestParam String payMethod,
            @RequestParam(required = false) String reason,
            @RequestParam String tid
    ){
        PaymentGatewayService service = getPaymentService(payMethod);
        String failMessage = reason != null ? reason : "알 수 없는 오류";

        service.handleFailedPayment(tid, failMessage);
        return ResponseEntity.badRequest().body("결제가 실패했습니다: " + failMessage);
    }

    private PaymentGatewayService getPaymentService(String payMethod) {
        PaymentGatewayService service = paymentGatewayServices.get(payMethod.toLowerCase());
        if(service == null){
            throw new IllegalArgumentException("지원하지 않는 결제 수단입니다: " + payMethod);
        }
        return service;
    }
}
