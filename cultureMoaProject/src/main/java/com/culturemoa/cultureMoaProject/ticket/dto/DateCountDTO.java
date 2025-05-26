package com.culturemoa.cultureMoaProject.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateCountDTO {
    private LocalDate date;
    private int performance_count;
    private int sports_count;
}