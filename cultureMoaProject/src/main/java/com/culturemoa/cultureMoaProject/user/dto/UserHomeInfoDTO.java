package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 홈페이지 필요한 정보를 담을 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHomeInfoDTO {

//    List<>
    List<MyPageBoardDTO> boardList;

}
