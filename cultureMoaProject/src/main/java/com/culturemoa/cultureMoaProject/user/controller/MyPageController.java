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
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
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
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return 소셜 정보를 전달
     */
    @GetMapping("/checkSocial")
    public ResponseEntity<MyPageCheckSocialDTO> myInfoSocialCheck (HttpServletRequest pRequest) {
        MyPageCheckSocialDTO whereSocial = myPageService.myPageSocialCheck(pRequest);
        return ResponseEntity.ok(whereSocial);
    }

    /**
     * 사용자 정보를 가져오기(개인 정보 수정에서 사용)
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return : 개인정보를 MyPageGetUserInfoDTO 담아서 전달
     */
    @GetMapping("/getUserInfo")
    public ResponseEntity<MyPageGetUserInfoDTO> myPageGetUserInfo (HttpServletRequest pRequest) {
        return ResponseEntity.ok(myPageService.getUserInfo(pRequest));
    }

    /**
     * 개인 정보 수정에서 데이터를 받아서 정보를 전달
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
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
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return : MyPageWishListDTO 배열을 반환
     */
    @GetMapping("/peakListInfo")
    public ResponseEntity<List<MyPageProductListDTO>> getMyPeakListInfo (HttpServletRequest pRequest) {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getWishListByToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 총합 점수와 개수를 조회 후 데이터 전달하는 컨트롤러
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return : 정보가 담긴 MyPageTotalRatingDTO를 반환
     */
    @GetMapping("/myTotalRating")
    public ResponseEntity<?> getMyTotalRating (HttpServletRequest pRequest) {
        MyPageTotalRatingDTO MyPageTotalRatingDTO = myPageService.getTotalRatingByToken(pRequest);
        return ResponseEntity.ok(MyPageTotalRatingDTO);
    }

    /**
     * 메인페이지 용 으로 2개만 추출하기 위한 서비스
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return : 찜 목록이 최대 2개까지 담긴 데이터
     */
    @GetMapping("/getLastestPeak")
    public ResponseEntity<?> getLastestPeakInfo (HttpServletRequest pRequest) {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getWishListByToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 판매 내역 조회 위한 컨트롤러
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return 판매 내역이 담긴 리스트
     */
    @GetMapping("/getMySellList")
    public ResponseEntity<?> getSellProductInfo (HttpServletRequest pRequest) {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getSellListByToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 구매 내역 조회 위한 컨트롤러
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return 구매 내역이 담긴 리스트
     */
    @GetMapping("/getMyBuyList")
    public ResponseEntity<?> getBuyProductInfo (HttpServletRequest pRequest) {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getBuyListByToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 마이페이지 게시판-게시판 내용 가져올 컨트롤러
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return 사용자 작성 게시판 정보가 담긴 리스트
     */
    @GetMapping("/getMyBoardList")
    public ResponseEntity<?> getMyBoardList (HttpServletRequest pRequest) {
        List<MyPageBoardDTO> myPageProductListDTO = myPageService.getMyBoardByToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 마이페이지 게시판-댓글 내용 가져올 컨트롤러
     * @param pRequest : 헤더에서 id 찾기 위해 넣는 변수
     * @return 사용자 작성 댓글 정보가 담긴 리스트
     */
    @GetMapping("/getMyCommentList")
    public ResponseEntity<?> getMyCommentList (HttpServletRequest pRequest) {
        List<MyPageCommentDTO> myPageProductListDTO = myPageService.getMyCommentToken(pRequest);
        return ResponseEntity.ok(myPageProductListDTO);
    }

}
