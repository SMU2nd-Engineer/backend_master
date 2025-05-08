package com.culturemoa.cultureMoaProject.user.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DuplicateCheckService {
    public Boolean duplicateCheck(String pCheckList, String pCategory) {
        // DB 데이터가 없어서 하드코딩 -> DB 수정하면 로직 수정할 것(DB에서 입력받은 값에 따라서 체크함
        return switch (pCategory) {
            case ("id") ->
                // DAO를 통해서 받아온 값이랑 비교해야함. 현재는 test로 바뀌는 것 만 확인 중
                    true;
            case ("nickName") ->
                // DAO를 통해서 받아온 값이랑 비교해야함. 현재는 test로 바뀌는 것 만 확인 중
                    true;
            default -> false;
        };
    }
}