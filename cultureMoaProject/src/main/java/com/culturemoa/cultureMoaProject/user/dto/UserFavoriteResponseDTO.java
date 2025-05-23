package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * db에 저장된 사용자가 선택한 선호도 값을 넣기 위한 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteResponseDTO {
    Map<String, Integer> userFavoriteMap;
}
