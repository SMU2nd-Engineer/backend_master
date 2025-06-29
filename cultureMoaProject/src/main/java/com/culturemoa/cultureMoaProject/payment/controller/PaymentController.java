package com.culturemoa.cultureMoaProject.payment.controller;

import com.culturemoa.cultureMoaProject.payment.dto.*;
import com.culturemoa.cultureMoaProject.payment.service.gateway.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    // PG사 구분을 위한 서비스 매핑
    // 명시적 생성자 및 Autowired 써주세요
    private final Map<String, PaymentGatewayService> paymentGatewayServices;
    private final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/ready")
    public ResponseEntity<PaymentResponseDTO> readyPayment(
//            @RequestParam int payMethod,
            @RequestBody PaymentReadyRequestDTO request
            ){
        log.info("받은 요청: {}", request);
        // payMethod를 기반으로 어떤 PG사를 이용할지 선택
        PaymentGatewayService service = getPaymentService(6001);
        // service객체의 readyToPay 메서드 호출하고 결과를 PaymentResponseDTO로 반환
        PaymentResponseDTO response = service.readyToPay(request);
        // HTTP 상태코드 200(ok)과 함께 response를 JSON으로 변환하여 클라이언트에 응답
        return ResponseEntity.ok(response);

    }

    @PostMapping("/approve")
    public ResponseEntity<KakaoApproveResponseDTO> approvePayment(
            @RequestBody PaymentApproveRequestDTO request
//            @RequestParam int payMethod
    ) {
        PaymentGatewayService service = getPaymentService(6001);
        System.out.println("approvePayment called with pgToken: " + request.getPgToken());

        KakaoApproveResponseDTO response = service.approvePayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<KakaoCancelResponseDTO> cancel(
//            @RequestParam int payMethod
            @RequestBody KakaoCancelRequestDTO request
    ) {
        PaymentGatewayService service = getPaymentService(6001);
        KakaoCancelResponseDTO cancelInfo = service.cancelPayment(request);
        return ResponseEntity.ok(cancelInfo);
    }

    @PostMapping("/fail")
    public ResponseEntity<ErrorResponseDTO> fail(@RequestBody KakaoFailRequestDTO request) {
        PaymentGatewayService service = getPaymentService(6001);
        String failMessage = request.getReason() != null ? request.getReason() : "알 수 없는 오류";

        service.handleFailedPayment(request.getTid(), failMessage);

        ErrorResponseDTO response = new ErrorResponseDTO(-780, failMessage);
        return ResponseEntity.badRequest().body(response);
    }

    private PaymentGatewayService getPaymentService(int payMethod) {
        PaymentGatewayService service = paymentGatewayServices.get(PaymentGatewayService.getPayMethod(payMethod));
        if(service == null){
            throw new IllegalArgumentException("지원하지 않는 결제 수단입니다: " + payMethod);
        }
        return service;
    }

}
