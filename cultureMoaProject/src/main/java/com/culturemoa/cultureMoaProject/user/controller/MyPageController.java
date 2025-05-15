package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 소셜 여부를 확인
     * @param pRequest : 헤더에서 사용자 정보를 가져오기
     * @return 소셜 정보를 전달
     */
    @GetMapping("/checkSocial")
    public ResponseEntity<MyPageCheckSocialDTO> myInfoSocialCheck (HttpServletRequest pRequest) {
        MyPageCheckSocialDTO whereSocial = myPageService.myPageSocialCheck(pRequest);
        return ResponseEntity.ok(whereSocial);
    }

    /**
     * 사용자 정보를 가져오기(개인 정보 수정에서 사용)
     * @param pRequest : 헤더에서 정보 가져오기
     * @return : 개인정보를 MyPageGetUserInfoDTO 담아서 전달
     */
    @PostMapping("/getUserInfo")
    public ResponseEntity<MyPageGetUserInfoDTO> myPageGetUserInfo (HttpServletRequest pRequest) {
        return ResponseEntity.ok(myPageService.getUserInfo(pRequest));
    }

    /**
     * 개인 정보 수정에서 데이터를 받아서 정보를 전달
     * @param pRequest : 헤더에서 id를 추출
     * @param myPageUpdateUserInfoDTO : json 파싱하기 위한 dto
     * @return : 성공시 메시지를 전달
     */
    @PostMapping("/updateInfo")
    public ResponseEntity<?> myPageUpdateUserInfo (
            HttpServletRequest pRequest,
            @RequestBody MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        myPageService.updateUserInfoByToken(pRequest,myPageUpdateUserInfoDTO);
        return ResponseEntity.ok("Update Success");
    }

    /**
     * 찜 목록 조회 후 데이터 전달하는 컨트롤러
     * @param pRequest : 헤더에서 정보를 찾기
     * @return : MyPageWishListDTO 배열을 반환
     */
    @PostMapping("/wishListInfo")
    public ResponseEntity<List<MyPageWishListDTO>> getMyWishListInfo (HttpServletRequest pRequest) {
        List<MyPageWishListDTO> myPageWishListDTO = myPageService.getWishListByToken(pRequest);
        return ResponseEntity.ok(myPageWishListDTO);
    }
}
