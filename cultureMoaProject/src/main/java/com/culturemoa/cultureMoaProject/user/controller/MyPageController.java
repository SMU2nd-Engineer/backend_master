package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 마이페이지 관련 컨트롤러
 */
@RestController
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    @PostMapping("/passwordCheck")
    public ResponseEntity<?> myInfoPasswordCheck (
            HttpServletRequest pRequest,
            @RequestBody MyPagePasswordCheckDTO myPagePasswordCheckDTO) {

             boolean isMyInfoPasswordCheck = myPageService.myPagePasswordCheck(pRequest, myPagePasswordCheckDTO);
            return ResponseEntity.ok(isMyInfoPasswordCheck);
    }
}
