package com.culturemoa.cultureMoaProject.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatus {
    private String tid;
    private String status;
    private LocalDate statusAt;
}
