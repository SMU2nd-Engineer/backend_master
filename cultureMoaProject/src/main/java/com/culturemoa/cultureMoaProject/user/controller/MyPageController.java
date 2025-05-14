package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.dto.MyPageCheckSocialDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPageGetUserInfoDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPageUpdateUserInfoDTO;
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

    /**
     * 마이페이지 접속할 때 비번 확인
     * @param pRequest : 헤더에서 토큰 추출하기 위한 파라미터
     * @param myPagePasswordCheckDTO : 전달받은 비밀번호를 받을 dto
     * @return : boolean 값을 전달
     */
    @PostMapping("/passwordCheck")
    public ResponseEntity<Boolean> myInfoPasswordCheck (
            HttpServletRequest pRequest,
            @RequestBody MyPagePasswordCheckDTO myPagePasswordCheckDTO) {

            boolean isMyInfoPasswordCheck = myPageService.myPagePasswordCheck(pRequest, myPagePasswordCheckDTO);
            return ResponseEntity.ok(isMyInfoPasswordCheck);
    }

    @GetMapping("/checkSocial")
    public ResponseEntity<MyPageCheckSocialDTO> myInfoSocialCheck (HttpServletRequest pRequest) {
        MyPageCheckSocialDTO whereSocial = myPageService.myPageSocialCheck(pRequest);
        return ResponseEntity.ok(whereSocial);
    }

    @PostMapping("/getUserInfo")
    public ResponseEntity<MyPageGetUserInfoDTO> myPageGetUserInfo (HttpServletRequest pRequest) {
        return ResponseEntity.ok(myPageService.getUserInfo(pRequest));
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<?> myPageUpdateUserInfo (
            HttpServletRequest pRequest,
            @RequestBody MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        myPageService.updateUserInfoByToken(pRequest,myPageUpdateUserInfoDTO);
        return ResponseEntity.ok("Update Success");
    }
}
