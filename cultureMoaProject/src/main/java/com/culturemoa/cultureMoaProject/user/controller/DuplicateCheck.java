package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.service.DuplicateCheckService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 중복 체크 컨트롤러
 * duplicateCheckService : 중복 체크 서비스 멤버 변수
 */
@RestController
public class DuplicateCheck {

    @Autowired
    DuplicateCheckService duplicateCheckService;

    /**
     * duplicateCheck
     * 중복 체크 요청 처리 컨트롤러 메서드
     * @param pCheckList : 전달 받은 id, nickName
     * @param pCategory : 중복 검사할 컬럼
     * @return 검증 결과에 따라 true | false 반환
     */
    @GetMapping("/duplicatecheck")
    public ResponseEntity<?> duplicateCheck(@RequestParam("checklist") String pCheckList,
                                               @RequestParam("category") String pCategory ) {
        // 검증 로직에서 true를 받으면 true를 반환
        if(duplicateCheckService.duplicateCheck(pCheckList, pCategory) == true) {
            return ResponseEntity.ok(true);

        } else {
        return ResponseEntity.ok(false);
        }
    }
}
