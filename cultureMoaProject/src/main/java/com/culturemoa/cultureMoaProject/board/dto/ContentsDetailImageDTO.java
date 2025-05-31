package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentsDetailImageDTO {
    private Long idx;
    private Long contents_idx;
    private String image_url;
}
