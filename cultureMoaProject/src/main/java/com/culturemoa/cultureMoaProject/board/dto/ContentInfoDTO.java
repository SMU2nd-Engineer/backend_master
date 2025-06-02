package com.culturemoa.cultureMoaProject.board.dto;

import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ContentsJoinUserDTO: 게시판 전체 정보와 user 테이블의 idx가
 일치하는 작성자(회원 닉네임) 정보를 전달하는 객체
 * user_idx : 회원 고유 번호
 * category_idx : 상품 카테고리 - 구분(팝니다/삽니다)
 * content: 게시글 내용
 * sdate : 시작일자(데이터 포맷됨 '05/14')
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentInfoDTO {

    // Contents(게시판) 정보
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sdate; // 날짜 포맷된 sdate

    // 불러올 User 정보(작성자-회원 닉네임)
    private String nickname;



}
