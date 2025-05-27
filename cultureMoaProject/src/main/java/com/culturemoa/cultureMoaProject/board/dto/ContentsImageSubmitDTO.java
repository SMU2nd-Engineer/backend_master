package com.culturemoa.cultureMoaProject.board.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentsImageSubmitDTO {
    // 이미지 등록
    private String image_Url;


}

