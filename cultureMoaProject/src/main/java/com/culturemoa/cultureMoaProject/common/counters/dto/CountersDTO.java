package com.culturemoa.cultureMoaProject.common.counters.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 몽고DB 스퀀스 DTO
 */
@Document(collection = "counters")
public class CountersDTO {
    @Id
    private String id;
    private Long seq;

    public CountersDTO(String id, Long seq) {
        this.id = id;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
