package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistReviewDTO {
    private double rating;
    private String reviewText;
    private int sellerIdx;
    private List<String> evaluation;
}
