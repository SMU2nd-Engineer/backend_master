package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  선호도 체크에 생성할 체크리스 관련 정보를 담을 DTO
 *  idx : user favorite tbl에 넣을 idx 값
 *  name : 카테고리 이름
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCategorySubDTO {
    private int subIdx;
    private String name;
}
