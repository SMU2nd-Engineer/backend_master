package com.culturemoa.cultureMoaProject.board.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentsDetailLoadImageInfoDTO {
    // Contents(게시판) 정보
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @DateTimeFormat(pattern = "MM/dd")
    private LocalDateTime sdate; // 날짜 포맷된 sdate

    // 불러올 User 정보(작성자-회원 닉네임)
    private String nickname;

    //  이미지 저장경로 담겨 있는 리스트
    private List<ContentsDetailImageDTO> detailImageList;
}
