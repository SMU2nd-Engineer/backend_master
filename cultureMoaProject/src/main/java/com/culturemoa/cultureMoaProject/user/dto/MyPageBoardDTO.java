package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBoardDTO {
    public int idx;
    public String title;
    public int categoryIdx;
    public String categoryName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy/MM/dd") // 날짜 보내는 형식 지정
    public LocalDateTime sDate;
}
