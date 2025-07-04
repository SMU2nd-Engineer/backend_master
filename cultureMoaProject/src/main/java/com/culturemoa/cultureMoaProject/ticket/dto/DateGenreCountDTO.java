package com.culturemoa.cultureMoaProject.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateGenreCountDTO {
    private LocalDateTime sDate;
    private String genre;
    private int count;
}