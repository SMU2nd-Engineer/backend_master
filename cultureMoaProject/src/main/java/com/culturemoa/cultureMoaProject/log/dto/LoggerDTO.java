package com.culturemoa.cultureMoaProject.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "logger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggerDTO {
    @Id
    private Long id;
    private String pointCut;
    private String className;
    private String methodName;
    private String parameter;
    private String returnType;
    private String returnValue;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
}
