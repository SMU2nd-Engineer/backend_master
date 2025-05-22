package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 프론트에서 한 번에 구매 / 판매 내역을 조회후 정보를 넘기기 위하여 생성한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSellAndBuyListDTO {
    private List<MyPageSellListDTO> sellInfoList;
    private List<MyPageBuyListDTO> buyInfoList;


}
