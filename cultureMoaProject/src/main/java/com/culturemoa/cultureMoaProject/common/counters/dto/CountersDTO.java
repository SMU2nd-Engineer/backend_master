package com.culturemoa.cultureMoaProject.common.counters.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 몽고DB 스퀀스 DTO
 */
@Document(collection = "counters")
@Getter
@Setter
public class CountersDTO {
    @Id
    private String id;
    private Long seq;

    public CountersDTO(String id, Long seq) {
        this.id = id;
        this.seq = seq;
    }
}
