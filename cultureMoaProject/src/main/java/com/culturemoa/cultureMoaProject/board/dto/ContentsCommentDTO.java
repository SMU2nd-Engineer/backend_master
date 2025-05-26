package com.culturemoa.cultureMoaProject.board.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContentsCommentDTO {
    private Long idx;
    private Long user_idx;
    private Long contents_idx;
    private String text;
    private LocalDateTime sdate;
    private LocalDateTime cdate;
    private LocalDateTime edate;



}
