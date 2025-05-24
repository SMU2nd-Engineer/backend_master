package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.service.MyPageService;
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
     * 메인페이지 데이터를 추출하기 위한 컨트롤러
     * @return : 찜 목록이 최대 2개까지 담긴 데이터
     */
    @GetMapping("/getMyMainInfo")
    public ResponseEntity<?> getLastestPeakInfo () {
        MyPageMainDTO myPageMainDTO = myPageService.getMainInfoListByAuth();
        return ResponseEntity.ok(myPageMainDTO);
    }


    /**
     * 찜 목록 조회 후 데이터 전달하는 컨트롤러
     * @return : MyPageWishListDTO 배열을 반환
     */
    @GetMapping("/peakListInfo")
    public ResponseEntity<List<MyPagePickProductListDTO>> getMyPeakListInfo () {
        List<MyPagePickProductListDTO> myPagePickProductListDTO = myPageService.getWishListByAuth();
        return ResponseEntity.ok(myPagePickProductListDTO);
    }

    /**
     * 찜 목록에서 삭제 버튼을 눌렀을 때 eDate를 삽입하기 위한 컨트롤러
     * @param myPickUpdateDTO : 프론트에서 전달받은 productIdx가 들어간다.
     * @return : 응답 메시지 출력
     */
    @PostMapping("/updateUserPeak")
    public ResponseEntity<?> updateMyPickList (@RequestBody MyPickUpdateDTO myPickUpdateDTO) {
        myPageService.updateMyPickList(myPickUpdateDTO);
        return ResponseEntity.ok("정상적으로 업데이트 되었습니다.");
    }


    /**
     * 구매 / 판매 내역 조회 위한 컨트롤러
     * @return 판매 내역이 담긴 리스트
     */
    @GetMapping("/getMyTransactionList")
    public ResponseEntity<?> getSellAndBuyProductInfo () {
        MyPageSellAndBuyListDTO myPagePeakProductListDTO = myPageService.getSellAndBuyListByAuth();
        return ResponseEntity.ok(myPagePeakProductListDTO);
    }

    /**
     * 마이페이지 게시판-게시판 내용 가져올 컨트롤러
     * @return 사용자 작성 게시판 정보가 담긴 리스트
     */
    @GetMapping("/getMyBoardAndCommentList")
    public ResponseEntity<?> getMyBoardAndCommentList () {
        MyPageBoardCommentListDTO myPageBoardCommentListDTO = myPageService.getMyBoardAndCommentByAuth();
        return ResponseEntity.ok(myPageBoardCommentListDTO);
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
        UserFavoriteResponseDTO userFavoriteResponseDTO = myPageService.getUserFavoritesInfo();
        return ResponseEntity.ok(userFavoriteResponseDTO);
    }

    /**
     * 유저 선호도 변경 컨트롤러
     * @param userRegisterFavoriteDTO : 선호도 정보가 담긴 배열을 받을 dto
     * @return : 응답 메시지 송신
     */
    @PostMapping("/updateFavorites")
    public ResponseEntity<?> updateUserFavorites(@RequestBody UserRegisterFavoriteDTO userRegisterFavoriteDTO) {
        myPageService.updateUserFavoriteInfo(userRegisterFavoriteDTO);
        return ResponseEntity.ok("선호도 업데이트가 정상적으로 완료되었습니다.");
    }

    /**
     * 리뷰 남기기에서 판매자 정보와 거래 평가 항목을 요청시 처리하는 컨트롤러
     * @return 판매자 이름과 거래평가를 전달
     */
    @GetMapping("/getSellerAndCategoryInfo")
    public ResponseEntity<?> getSellerAndCategoryInfo (){
        return ResponseEntity.ok(myPageService.sellerAndEvaluationCategoriesInfo());
    }

    /**
     * 사용자가 작성한 리뷰 정보 등록하기
     * @param reviewRegisterDTO : body 에 들어있는 정보를 받을 dto
     * @return : 정상적으로 진행 될 경우 메시지를 반환
     */
    @PostMapping("registReview")
    public ResponseEntity<?> insertOrUpdateReviewInfo(@RequestBody ReviewRegisterDTO reviewRegisterDTO) {
        myPageService.insertReviewInfoService(reviewRegisterDTO);
        return ResponseEntity.ok("정상적으로 리뷰 정보가 입력되었습니다.");
    }


    /**
     * 사용자가 작성한 리뷰 정보 가져오기
     * @param reviewIdx : 경로에 있는 reviewIdx 값
     * @return : ReviewDetailInfoDTO(리뷰 정보 및 평가 항목 정보가 담김)
     */
    @GetMapping("getReviewInfo/{reviewIdx}")
    public ResponseEntity<?> getReviewInfo (@PathVariable("reviewIdx") int reviewIdx) {
        return ResponseEntity.ok(myPageService.getReviewDetailInfo(reviewIdx));
    }

    /**
     * 사용자가 수정한 리뷰 저장하기 위한 컨트롤러
     * @param updateReviewInfoDTO : 보낸 데이터를 담을 DTO
     * @return : 응답을 반환
     */
    @PostMapping("updateReview")
    public ResponseEntity<?> updateReviewAndEvaluation (@RequestBody UpdateReviewInfoDTO updateReviewInfoDTO) {
        myPageService.updateReviewAndEvaluation(updateReviewInfoDTO);
        return ResponseEntity.ok("정상적으로 업데이트가 진행되었습니다.");
    }

}
