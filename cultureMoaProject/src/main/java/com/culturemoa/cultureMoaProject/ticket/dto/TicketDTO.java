package com.culturemoa.cultureMoaProject.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long idx;
    private Long sub_idx;
    private String title;
    private String company;
    private String link;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
    private String price;
    private String grade;
    private String cast;
    private String runningTime;
    private String img;
    private String genre;
    private String etc;
}
