package com.culturemoa.cultureMoaProject.payment.service.kakao;

import com.culturemoa.cultureMoaProject.payment.dto.*;
import com.culturemoa.cultureMoaProject.payment.entity.PaymentHistory;
import com.culturemoa.cultureMoaProject.payment.entity.PaymentStatus;
import com.culturemoa.cultureMoaProject.payment.repository.PaymentDAO;
import com.culturemoa.cultureMoaProject.payment.service.gateway.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * KakaoPaymentService
 * PaymentGatewayService 인터페이스를 구현한 클래스이다.
 * 카카오페이 결제 준비와 승인 작업을 처리하는 로직을 담당
 */

@Service("kakao")
@RequiredArgsConstructor
public class KakaoPaymentService implements PaymentGatewayService {
    // 스프링에서 HTTP 요청을 보내기 위해 사용되는 객체
    private final RestTemplate restTemplate = new RestTemplate();
    // 카카오페이 결제 준비 API의 URL
    private static final String KAKAO_READY_URL = "https://open-api.kakaopay.com/online/v1/payment/ready";
    // 카카오페이 결제 승인 API의 URL
    private static final String KAKAO_APROVE_URL = "https://open-api.kakaopay.com/online/v1/payment/approve";
    private final KakaoPayProperties kakaoPayProperties;
    private final PaymentDAO paymentDAO;

    // 카카오페이 결제 준비 API를 호출하는 역할
    @Override
    public PaymentResponseDTO readyToPay(PaymentReadyRequestDTO requestDTO) {

        // HTTP 요청 헤더를 설정하는 객체
        HttpHeaders headers = new HttpHeaders();
        // API 요청을 인증하기 위해 카카오페이 API키를 Authorization헤더에 추가
        headers.set("Authorization", "SECRET_KEY " + kakaoPayProperties.getSecretKey());
        // 요청 본문의 데이터 형식을 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 카카오페이 API에 요청할 파라미터
        Map<String, Object> params = new HashMap<>();
        params.put("cid", kakaoPayProperties.getCid());
        params.put("partner_order_id", requestDTO.getPartnerOrderId());
        params.put("partner_user_id", requestDTO.getPartnerUserId());
        params.put("item_name", requestDTO.getItemName());
        params.put("quantity", requestDTO.getQuantity());
        params.put("total_amount", requestDTO.getAmount());
        params.put("tax_free_amount", requestDTO.getTaxFreeAmount());
        params.put("approval_url", requestDTO.getApprovalUrl());
        params.put("cancel_url", requestDTO.getCancelUrl());
        params.put("fail_url", requestDTO.getFailUrl());

        // HTTP 요청에 필요한 본문과 헤더를 포함하는 객체
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);
        // 카카오페이 API의 POST 요청을 보냄
        ResponseEntity<KakaoReadyResponseDTO> response = restTemplate.postForEntity(
                KAKAO_READY_URL,
                request,
                KakaoReadyResponseDTO.class
        );
        // 카카오페이 API의 응답 데이터를 객체로 받기
        KakaoReadyResponseDTO body = response.getBody();
        System.out.println("itemID: " + requestDTO.getItemId());
        // DB 저장
        PaymentHistory history = new PaymentHistory();
        history.setTid(body.getTid());
        history.setAmount(requestDTO.getAmount());
        history.setPayMethod(getPayMethod());
        history.setProductId(requestDTO.getItemId());
        history.setTradeType(requestDTO.getTradeType());
        history.setDeliveryAddress(requestDTO.getDeliveryAddress());
        history.setBuyerId(requestDTO.getBuyerId());
        history.setSellerId(requestDTO.getSellerId());

        paymentDAO.insertPaymentHistory(history);

        PaymentStatus status = new PaymentStatus();
        status.setTid(body.getTid());
        status.setStatus("결제준비");
        status.setStatusAt(body.getCreatedAt());
        
        paymentDAO.insertPaymentStatus(status);

        return new PaymentResponseDTO(
                Objects.requireNonNull(body).getTid(),
                body.getNextRedirectPcUrl(),
                body.getNextRedirectMobileUrl(),
                body.getNextRedirectAppUrl(),
                body.getAndroidAppScheme(),
                body.getIosAppScheme(),
                body.getCreatedAt()
        );
    }

    @Override
    public KakaoApproveResponseDTO approvePayment(PaymentApproveRequestDTO approveDTO) {
        // 카카오페이 API 호출 -> 결제 승인
        // HTTP 요청 헤더를 설정하는 객체
        HttpHeaders headers = new HttpHeaders();
        // API 요청을 인증하기 위해 카카오페이 API키를 Authorization헤더에 추가
        headers.set("Authorization", "SECRET_KEY " + kakaoPayProperties.getSecretKey());
        // 요청 본문의 데이터 형식을 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 카카오페이 API에 요청할 파라미터
        Map<String, Object> params = new HashMap<>();
        params.put("cid", kakaoPayProperties.getCid());
        params.put("tid", approveDTO.getTid());
        params.put("partner_order_id", approveDTO.getPartnerOrderId());
        params.put("partner_user_id", approveDTO.getPartnerUserId());
        params.put("pg_token", approveDTO.getPgToken());

        // HTTP 요청에 필요한 본문과 헤더를 포함하는 객체
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);

        // 승인 호출 (성공 or 실패 판단용)
        KakaoApproveResponseDTO response = restTemplate.postForEntity(
                KAKAO_APROVE_URL,
                request,
                KakaoApproveResponseDTO.class
        ).getBody();

        // DB저장
        PaymentStatus status = new PaymentStatus();
        status.setTid(approveDTO.getTid());
        status.setStatus("결제승인");
        status.setStatusAt(response.getApprovedAt());

        paymentDAO.insertPaymentStatus(status);

        return response;
    }

    @Override
    public void cancelPayment(String tid) {
        // HTTP 요청 헤더를 설정하는 객체
        HttpHeaders headers = new HttpHeaders();
        // API 요청을 인증하기 위해 카카오페이 API키를 Authorization헤더에 추가
        headers.set("Authorization", "SECRET_KEY " + kakaoPayProperties.getSecretKey());
        // 요청 본문의 데이터 형식을 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> params = new HashMap<>();


    }

    @Override
    public void handleFailedPayment(String methodResultMessage) {

    }

    @Override
    public String getPayMethod() {
        return "kakao";
    }
}

