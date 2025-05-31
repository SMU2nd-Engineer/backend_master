package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailInfoDTO {
    private FetchReviewRegisterInfoDTO fetchReviewRegisterInfo;
    private Map<String, Integer> ReviewEvaluationRecord;
    private List<UserCategorySubDTO> evaluationCategories;
    private FetchReviewProductInfoDTO fetchReviewProductInfoDTO;

}
