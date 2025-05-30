package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.DontInsertException;
import com.culturemoa.cultureMoaProject.user.exception.DontUpdateException;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.repository.MyPageDAO;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 마이페이지 관련 서비스 클래스
 */
@Service
public class MyPageService {

    private final MyPageDAO myPageDAO;
    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final HandleAuthentication handleAuth;

    @Autowired
    public MyPageService (UserDAO userDAO, PasswordEncoder passwordEncoder, MyPageDAO myPageDAO, HandleAuthentication handleAuth ) {
        this.userDAO = userDAO;
        this.passwordEncoder=passwordEncoder;
        this.myPageDAO=myPageDAO;
        this.handleAuth=handleAuth;
    }


    /**
     * 마이페이지 비번과 헤더의 토큰을 이용하여 사용자 비번을 조회하여 매칭한 결과를 반환하는 메서드
     * @param myPagePasswordCheckDTO : 사용자가 입력한 정보가 담긴 DTO
     * @return : 프론트에서 boolean으로 값을 받기 위하여 전달함.
     */
    public Boolean myPagePasswordCheck(
            MyPagePasswordCheckDTO myPagePasswordCheckDTO) {
        String userId = handleAuth.getUserIdByAuth();
        // 가져온 id와 들어있는 데이터로 dao에 요청을 진행하여 password를 받아옴
        MyPagePasswordCheckDTO getDBPassword = myPageDAO.getPassword(userId);
        // 받아온 데이터에서 password를 reqeust의 패스워드와 매칭하기 다르면 오류 발생.
        if (!passwordEncoder.matches(myPagePasswordCheckDTO.getPassword(), getDBPassword.getPassword())) {
            throw new InvalidPasswordException(); // 비밀번호 일치하지 않음.
        } else {
            // 정상적으로 진행되면 return true로 설정
            return true;
        }
    }

    /**
     * 소셜 로그인 정보를 가져오는 메서드
     * @return : MyPageCheckSocialDTO
     */
    public MyPageCheckSocialDTO myPageSocialCheck() {
        String userId = handleAuth.getUserIdByAuth();
        return myPageDAO.getSocialLogin(userId);
    }

    /**
     * 개인 정보 페이지에 정보를 전달하기 위하여 정보를 추출하기 위한 서비스
     * @return : 개인 정보가 담긴 DTO 객체
     */
    public MyPageGetUserInfoDTO getUserInfo() {
        String userId = handleAuth.getUserIdByAuth();
        // 추출한 id로 정보를 요청
        return myPageDAO.getUserInfoById(userId);
    }

    /**
     * userId를 추출하여 토큰에 담고
     * @param myPageUpdateUserInfoDTO : 업데이트 한 개인정보가 담긴 값
     */
    public void updateUserInfoByAuth(MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        String userId = handleAuth.getUserIdByAuth();
        // 암호 공백 또는 빈값이 아닐 경우 암호화해서 넣기
        String password = myPageUpdateUserInfoDTO.getPassword();
        if(password != null && !password.isEmpty()){
            myPageUpdateUserInfoDTO.setPassword(passwordEncoder.encode(password));
        }
        // 수정 날짜 세팅
        myPageUpdateUserInfoDTO.setCDate(LocalDateTime.now().withNano(0)); // 나노초 제거하여 넣기

        // 유저 아이디 세팅
        myPageUpdateUserInfoDTO.setId(userId);
        // 반환 값에 따라 에러 처리 하기
        int updateResult = myPageDAO.updateUserInfo(myPageUpdateUserInfoDTO);
        if(updateResult == 0 || updateResult > 1) {
            throw new DontUpdateException();
        }

    }

    /**
     * userId를 추출하여 메인 페이지에 쓸 찜, 판매, 거래 후기 목록을 가져오는 서비스
     * @return MyPageMainDTO로 별점과 각 항목의 리스트가 담긴 DTO
     */
    public MyPageMainDTO getMainInfoListByAuth() {
        String userId = handleAuth.getUserIdByAuth();
        MyPageGetUserInfoDTO myPageGetUserInfo = myPageDAO.getUserInfoById(userId);
        MyPageAverageRatingDTO myPageAverageRating = myPageDAO.getAverageRatingByUserId(userId);
        List<MyPageSellListDTO> myMainSellProductList = myPageDAO.getMyMainSellListInfoByUserId(userId);
        List<MyPagePickProductListDTO> myMainPeakList = myPageDAO.getMyMainPeakListInfoByUserId(userId);
        List<ReviewListDTO> myMainReview = myPageDAO.getMyMainReviewListInfoByUserId(userId);
        return new MyPageMainDTO(myPageGetUserInfo, myPageAverageRating, myMainSellProductList , myMainPeakList, myMainReview);
    }

    /**
     * userId를 추출하여 찜 목록을 가져오는 서비스
     * @return List<MyPageWishListDTO>로 찜 목록에 배분할 값이 담긴 DTO가 0개이상 담긴 List
     */
    public List<MyPagePickProductListDTO> getWishListByAuth() {
        String userId = handleAuth.getUserIdByAuth();
        return myPageDAO.getMyWishListInfoByUserId(userId);
    }

    /**
     * 찜 목록 갱신(edate를 이용하여 삭제 구현)
     * @param myPickUpdateDTO : edate를 넣을 정보가 담긴 dto
     */
    public void updateMyPickList (MyPickUpdateDTO myPickUpdateDTO) {
        myPickUpdateDTO.setEDate(LocalDateTime.now().withNano(0));
        myPageDAO.updateMyPickByProductIdx(myPickUpdateDTO);

    }

    /**
     * 마이페이지 판매 내역을 위한 서비스
     * @return List<MyPageWishListDTO>로 조죄한 판매 내역 정보가 담김
     */
    public MyPageSellAndBuyListDTO getSellAndBuyListByAuth() {
        String userId = handleAuth.getUserIdByAuth();
        List<MyPageSellListDTO> sellInfoList = myPageDAO.getMySellProductByUserId(userId);
        List<MyPageBuyListDTO> buyInfoList = myPageDAO.getMyBuyProductByUserId(userId);
        return new MyPageSellAndBuyListDTO(sellInfoList, buyInfoList);
    }

    /**
     * 마이페이지 게시판 - 게시판 내용을 가져올 서비스
     * @return : List<MyPageBoardDTO>에 사용자가 작성한 게시판 정보가 담김
     */
    public MyPageBoardCommentListDTO getMyBoardAndCommentByAuth() {
        String userId = handleAuth.getUserIdByAuth();
        List<MyPageBoardDTO> myPageBoardDTOList = myPageDAO.getMyBoardByUserId(userId);
        List<MyPageCommentDTO> myPageCommentDTOList = myPageDAO.getMyCommentByUserId(userId);
        return new MyPageBoardCommentListDTO(myPageBoardDTOList, myPageCommentDTOList);
    }


    /**
     * 마이페이지 리뷰 정보를 전달하기 위한 서비스
     * @return : 별점 평균과 함께 리뷰 정보, 거래 평가 , 거래 평가 항목 정보를 전달하는 객체
     */
    public MyPageReviewDTO getMyReviewInfoByAuth() {
        String userId = handleAuth.getUserIdByAuth();
        // 평균 별점 점수 가져오기
        MyPageAverageRatingDTO averageRating = myPageDAO.getAverageRatingByUserId(userId);
        List<ReviewListDTO> reviewList = myPageDAO.getMyReviewInfoByUserId(userId);
        Map<String, Integer> myEvaluationList = myPageDAO.getMyEvaluationByUserIdx(userId);
        List<UserCategorySubDTO> evaluationList = myPageDAO.getEvaluationCategorySubInfo(5005);
        // 사용자 정보 가져오기
        MyPageGetUserInfoDTO myPageGetUserInfo = myPageDAO.getUserInfoById(userId);
        return new MyPageReviewDTO(reviewList, averageRating, myEvaluationList, evaluationList, myPageGetUserInfo);
    }


    /**
     * 카테고리 정보를 얻기 위한 서비스
     * @return : List<UserCategorySubDTO>
     */
    public List<UserCategorySubDTO> getUserCategoryInfo () {
        return myPageDAO.getCategorySubInfo();
    }

    /**
     * db에서 유저 선호도를 조사해서 dto로 반환
     * @return : 선호도 idx 값이 담긴 dto
     */
    public UserFavoriteResponseDTO getUserFavoritesInfo() {
        // 유저 정보에서 idx 추출하기
        String userId = handleAuth.getUserIdByAuth();
        int userIdx = userDAO.getUserIdx(userId);
        Map<String, Integer> favorites = myPageDAO.getUserFavoritesList(userIdx);
        return new UserFavoriteResponseDTO(favorites, myPageDAO.getCategorySubInfo());
    }

    /**
     * 유저 선호도 수정했을 때 정보를 받아와서 처리할 서비스
     * @param userRegisterFavoriteDTO : 유저 선호도를 담아서 사용함.
     */
    public void updateUserFavoriteInfo (UserRegisterFavoriteDTO userRegisterFavoriteDTO) {
        String userId = handleAuth.getUserIdByAuth();
        int userIdx = userDAO.getUserIdx(userId);
        userRegisterFavoriteDTO.setUserIdx(userIdx);
        myPageDAO.updateUserFavoritesList(userRegisterFavoriteDTO);

    }

    /**
     * 리뷰 남기기 페이지를 구성하는 자료 가져오기 service
     * @return : SellerAndEvaluationCategoriesInfoDTO
     */
    public ReviewWritingPageInfoDTO sellerAndEvaluationCategoriesInfo () {
        try {
            String userId = handleAuth.getUserIdByAuth();
            List<SellerInfoDTO> seller = myPageDAO.getSellerInfoByUserId(userId);
            List<UserCategorySubDTO> userCategorySubDTO = myPageDAO.getEvaluationCategorySubInfo(5010);
            return new ReviewWritingPageInfoDTO(seller, userCategorySubDTO);
        } catch (Exception e) {
            throw new RuntimeException("리뷰 페이지 판매자 및 거래 평가 항목 가져 오기 중에 오류 발생");
        }
    }

    /**
     * 사용자 작성한 리뷰 정보 db에 저장 처리 서비스
     * @param reviewRegisterDTO : 사용자가 작성한 리뷰 정보를 담은 dto
     */
    public void insertReviewInfoService (ReviewRegisterDTO reviewRegisterDTO) {
        String userId = handleAuth.getUserIdByAuth();
        int userIdx = userDAO.getUserIdx(userId);
        reviewRegisterDTO.setBuyerIdx(userIdx);
        // 등록날짜 넣기
        reviewRegisterDTO.setSDate(LocalDateTime.now().withNano(0));
        // user_review_tbl에 데이터 넣는 dao 호출 후 이 때 마이바티스로 자동으로 삽입된 행의 idx가 dto에 들어감
        int insertResult = myPageDAO.insertReviewInfo(reviewRegisterDTO);
        // user_review_evaluation 데이터 삽입 또는 업데이트 (판매자에 대한 평가를 남기기)
        int updateEvaluationResult = myPageDAO.updateReviewEvaluation(reviewRegisterDTO);
        // 거래 평가 항목을 기록하는 부분
        int updateRecordResult = myPageDAO.insertReviewEvaluationRecord(reviewRegisterDTO);
        // 정상적으로 진행할 경우 1을 반환하므로 0일 경우 예외 처리
        if(insertResult == 0) {
            throw new DontInsertException();
        }
    }

    /**
     * 찾은 정보를 전달할 ReviewDetailInfoDTO 에 담아서 전달할 서비스
     * @param reviewIdx : 경로 파라미터 값
     * @return : ReviewDetailInfoDTO
     */
    public ReviewDetailInfoDTO getReviewDetailInfo(int reviewIdx) {
        return new ReviewDetailInfoDTO(
                myPageDAO.searchReviewInfoByReviewIdx(reviewIdx),
                myPageDAO.searchEvaluationRecord(reviewIdx),
                myPageDAO.getEvaluationCategorySubInfo(5010)
        );
    }

    /**
     * 평가항목 수정시 작동할 서비스
     * @param updateReviewInfoDTO : 업데이트할 리뷰 정보가 담긴 dto
     */
    public void updateReviewAndEvaluation (UpdateReviewInfoDTO updateReviewInfoDTO) {
        // cDate 넣기
        updateReviewInfoDTO.setCDate(LocalDateTime.now().withNano(0));
        // 평가 항목 +/-를 위한 map 객체 생성
        Map<String, Object> userIdxAndEvaluationMap = new HashMap<>();
        userIdxAndEvaluationMap.put("sellerIdx", updateReviewInfoDTO.getSellerIdx());
        userIdxAndEvaluationMap.putAll(updateReviewInfoDTO.getChangeValueEvaluation());
        myPageDAO.updateReview(updateReviewInfoDTO);
        myPageDAO.updateReviewEvaluation(userIdxAndEvaluationMap);
        myPageDAO.updateReviewEvaluationRecode(updateReviewInfoDTO);

    }

}