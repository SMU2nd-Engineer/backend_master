package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 리뷰 정보를 담을 dto
 * idx : key 값으로 idx
 * evaluation : 평가 항목
 * review : 텍스트 형태의 리뷰
 * sDate : 리뷰 작성일
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDTO {
    private int idx;
    private String review;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy/MM/dd") // 날짜 보내는 형식 지정
    private LocalDateTime sDate;
}
