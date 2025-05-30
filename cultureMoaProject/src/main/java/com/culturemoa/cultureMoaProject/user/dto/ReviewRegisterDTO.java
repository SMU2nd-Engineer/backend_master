package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * user_review_tbl에 데이터를 넣기 위한 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterDTO {
    private double rating;
    private String reviewText;
    private int sellerIdx;
    private int buyerIdx;
    private List<String> evaluation;
    private int transactionIdx;
    private LocalDateTime sDate;
    private int reviewIdx;
}
