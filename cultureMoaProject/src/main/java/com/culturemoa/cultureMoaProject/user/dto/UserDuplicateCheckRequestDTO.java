package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  중복 체크할 데이터를 전달 받은 DTO
 *  name
 *  category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDuplicateCheckRequestDTO {
    private String name;
    private String category;
}
