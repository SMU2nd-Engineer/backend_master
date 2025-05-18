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
     * @param myPagePasswordCheckDTO : 전달받은 비밀번호를 받을 dto
     * @return : boolean 값을 전달
     */
    @PostMapping("/passwordCheck")
    public ResponseEntity<Boolean> myInfoPasswordCheck (
            @RequestBody MyPagePasswordCheckDTO myPagePasswordCheckDTO) {

            boolean isMyInfoPasswordCheck = myPageService.myPagePasswordCheck(myPagePasswordCheckDTO);
            return ResponseEntity.ok(isMyInfoPasswordCheck);
    }

    /**
     * 소셜 여부를 확인
     * @return 소셜 정보를 전달
     */
    @GetMapping("/checkSocial")
    public ResponseEntity<MyPageCheckSocialDTO> myInfoSocialCheck () {
        MyPageCheckSocialDTO whereSocial = myPageService.myPageSocialCheck();
        return ResponseEntity.ok(whereSocial);
    }

    /**
     * 사용자 정보를 가져오기(개인 정보 수정에서 사용)
     * @return : 개인정보를 MyPageGetUserInfoDTO 담아서 전달
     */
    @GetMapping("/getUserInfo")
    public ResponseEntity<MyPageGetUserInfoDTO> myPageGetUserInfo () {
        return ResponseEntity.ok(myPageService.getUserInfo());
    }

    /**
     * 개인 정보 수정에서 데이터를 받아서 정보를 전달
     * @param myPageUpdateUserInfoDTO : json 파싱하기 위한 dto
     * @return : 성공시 메시지를 전달
     */
    @PostMapping("/updateInfo")
    public ResponseEntity<?> myPageUpdateUserInfo (
            @RequestBody MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        myPageService.updateUserInfoByAuth(myPageUpdateUserInfoDTO);
        return ResponseEntity.ok("Update Success");
    }

    /**
     * 찜 목록 조회 후 데이터 전달하는 컨트롤러
     * @return : MyPageWishListDTO 배열을 반환
     */
    @GetMapping("/peakListInfo")
    public ResponseEntity<List<MyPageProductListDTO>> getMyPeakListInfo () {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getWishListByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 메인페이지 용 으로 2개만 추출하기 위한 서비스
     * @return : 찜 목록이 최대 2개까지 담긴 데이터
     */
    @GetMapping("/getLastestPeak")
    public ResponseEntity<?> getLastestPeakInfo () {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getWishListByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 판매 내역 조회 위한 컨트롤러
     * @return 판매 내역이 담긴 리스트
     */
    @GetMapping("/getMySellList")
    public ResponseEntity<?> getSellProductInfo () {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getSellListByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 구매 내역 조회 위한 컨트롤러
     * @return 구매 내역이 담긴 리스트
     */
    @GetMapping("/getMyBuyList")
    public ResponseEntity<?> getBuyProductInfo () {
        List<MyPageProductListDTO> myPageProductListDTO = myPageService.getBuyListByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 마이페이지 게시판-게시판 내용 가져올 컨트롤러
     * @return 사용자 작성 게시판 정보가 담긴 리스트
     */
    @GetMapping("/getMyBoardList")
    public ResponseEntity<?> getMyBoardList () {
        List<MyPageBoardDTO> myPageProductListDTO = myPageService.getMyBoardByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 마이페이지 게시판-댓글 내용 가져올 컨트롤러
     * @return 사용자 작성 댓글 정보가 담긴 리스트
     */
    @GetMapping("/getMyCommentList")
    public ResponseEntity<?> getMyCommentList () {
        List<MyPageCommentDTO> myPageProductListDTO = myPageService.getMyCommentByAuth();
        return ResponseEntity.ok(myPageProductListDTO);
    }

    /**
     * 마이페이지 리뷰에 필요한 정보를 가져올 컨트롤러
     * @return : 평균 별점과 나머지 정보가 리스트 형태로 저장된 DTO 반환
     */
    @GetMapping("/getMyReviewList")
    public ResponseEntity<?> getMyReviewInfo () {
        MyPageReviewDTO myPageReviewDTO = myPageService.getMyReviewInfoByAuth();
        return ResponseEntity.ok(myPageReviewDTO);
    }


    /**
     * 선호도 카테고리 얻기 컨트롤러
     * @return : 카테고리 이름과 idx 정보를 전달
     */
    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategorySubInfo () {
        List<UserCategorySubDTO> userCategoryInfo = myPageService.getUserCategoryInfo();
        return ResponseEntity.ok(userCategoryInfo);
    }

    /**
     * 유저 선호도 정보를 가져오는 컨트롤러
     * @return : 유저 선호도 정보가 담긴 dto 나머지는 null 또는 ""
     */
    @GetMapping("/userFavorites")
    public ResponseEntity<?> getUserFavorites () {
        UserMyPageFavoriteDTO userMyPageFavoriteDTO = myPageService.getUserFavoritesInfo();
        return ResponseEntity.ok(userMyPageFavoriteDTO);
    }

    /**
     * 유저 선호도 변경 컨트롤러
     * @param userMyPageFavoriteDTO : 선호도 정보가 담긴 배열을 받을 dto
     * @return : 응답 메시지 송신
     */
    @PostMapping("/updateFavorites")
    public ResponseEntity<?> updateUserFavorites(@RequestBody UserMyPageFavoriteDTO userMyPageFavoriteDTO) {
        myPageService.updateUserFavoriteInfo(userMyPageFavoriteDTO);
        return ResponseEntity.ok("선호도 업데이트가 정상적으로 완료되었습니다.");
    }
}
