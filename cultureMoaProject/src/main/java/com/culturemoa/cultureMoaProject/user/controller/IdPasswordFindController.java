package com.culturemoa.cultureMoaProject.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 아이디/비밀번호 찾기 컨트롤러
 */
@RestController
public class IdPasswordFindController {

    /**
     * 전달 받은 name, email로 DB 조회하여 ID를 반환 만약 소셜 로그인의 경우 소셜 로그인 사실을 반환
     * @return
     */
    @PostMapping("/idFind")
    public ResponseEntity<?> idFind () {
        String findId = "test";
        return ResponseEntity.ok(findId);
    }

    @PostMapping("/passwordFind")
    public ResponseEntity<?> passwordFind () {
        return ResponseEntity.ok("정보가 일치합니다.");
    }

}
