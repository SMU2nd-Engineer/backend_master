package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsImageSubmitDTO {
    // Contents(게시판) 정보
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "MM/dd")
    private String sdate; // 날짜 포맷된 sdate

    // 불러올 User 정보(작성자-회원 닉네임)
    private String nickname;

    // 이미지 등록
    private String image_Url;
}

