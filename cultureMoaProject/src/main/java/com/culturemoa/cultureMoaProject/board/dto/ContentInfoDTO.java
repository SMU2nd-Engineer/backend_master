package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * ContentsJoinUserDTO: 게시판 전체 정보와 user 테이블의 idx가
 일치하는 작성자(회원 닉네임) 정보를 전달하는 객체
 * user_idx : 회원 고유 번호
 * category_idx : 상품 카테고리 - 구분(팝니다/삽니다)
 * division : 구분(팝니다/삽니다) - 삭제 예정
 * content: 게시글 내용
 * sdate : 시작일자(데이터 포맷됨 '05/14')
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentInfoDTO {

    // Contents(게시판) 정보
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
    private int division;
    private String content;
    @DateTimeFormat(pattern = "MM/dd")
    private String sdate; // 날짜 포맷된 sdate

    // 불러올 User 정보(작성자-회원 닉네임)
    private String nickname;

}
