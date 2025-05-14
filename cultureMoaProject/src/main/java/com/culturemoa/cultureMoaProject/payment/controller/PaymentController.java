package com.culturemoa.cultureMoaProject.payment.controller;

import com.culturemoa.cultureMoaProject.payment.dto.PaymentReadyRequestDTO;
import com.culturemoa.cultureMoaProject.payment.dto.PaymentResponseDTO;
import com.culturemoa.cultureMoaProject.payment.service.gateway.PaymentGatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private  final List<PaymentGatewayService> paymentServices;

    public PaymentController(List<PaymentGatewayService> paymentServices){
        this.paymentServices = paymentServices;
    }

    @PostMapping("/ready")
    public ResponseEntity<PaymentResponseDTO> readyToPay(@RequestBody PaymentReadyRequestDTO requestDTO){
        PaymentGatewayService service = getServiceByPayMethod(requestDTO.getPayMethod());
        PaymentResponseDTO response = service.readyToPay(requestDTO);
        return ResponseEntity.ok(response);
    }

    private PaymentGatewayService getServiceByPayMethod(String payMethod) {
        return paymentServices.stream()
                .filter(service -> service.getPayMethod().equalsIgnoreCase(payMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 결제 방법"));
    }
}
