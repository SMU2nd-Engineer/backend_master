package com.culturemoa.cultureMoaProject.common.counters.service;

import com.culturemoa.cultureMoaProject.common.counters.dto.CountersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class CountersService {
    private final MongoOperations mongoOperations;

    @Autowired
    public CountersService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    /**
     * 시퀀스 반환 메서드
     * 시퀀스 값이 없는 경우 새롭게 Document 생성
     * @param type Document Type
     * @return 시퀀스 값
     */
    public long getId(String type){
        CountersDTO countersDTO = mongoOperations.findAndModify(
                    Query.query(Criteria.where("_id").is(type)),    // _id 값이 type 인 값 조회
                    new Update().inc("seq", 1), // seq 값이 있는지 확인 있으면 1증가
                    FindAndModifyOptions.options().returnNew(true).upsert(true),    // 조회 내용이 없는 경우 생성
                    CountersDTO.class
                );

        return !Objects.isNull(countersDTO) ? countersDTO.getSeq() : 1;

    }
}
