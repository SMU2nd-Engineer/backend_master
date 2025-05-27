package com.culturemoa.cultureMoaProject.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoCancelRequestDTO {
    private String cid;
    private String tid;
    private int cancelAmount;
    private int cancelTaxFreeAmount;
}
