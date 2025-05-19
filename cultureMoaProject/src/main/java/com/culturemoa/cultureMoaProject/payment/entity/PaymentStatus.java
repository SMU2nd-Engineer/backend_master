package com.culturemoa.cultureMoaProject.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatus {
    private String tid;
    private String status;
    private LocalDateTime statusAt;
}
