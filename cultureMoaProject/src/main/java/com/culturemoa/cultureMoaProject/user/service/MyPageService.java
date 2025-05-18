package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.DontInsertException;
import com.culturemoa.cultureMoaProject.user.exception.DontUpdateException;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.exception.UserNotFoundException;
import com.culturemoa.cultureMoaProject.user.repository.MyPageDAO;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 마이페이지 관련 서비스 클래스
 */
@Service
public class MyPageService {

    @Autowired
    MyPageDAO myPageDAO;

    // 디버그 용
    private static final Logger logger = LoggerFactory.getLogger(MyPageService.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDAO userDAO;


    /**
     * 마이페이지 비번과 헤더의 토큰을 이용하여 사용자 비번을 조회하여 매칭한 결과를 반환하는 메서드
     * @param myPagePasswordCheckDTO : 사용자가 입력한 정보가 담긴 DTO
     * @return : 프론트에서 boolean으로 값을 받기 위하여 전달함.
     */
    public Boolean myPagePasswordCheck(
            MyPagePasswordCheckDTO myPagePasswordCheckDTO) {
        String userId = myPageGetUserId();
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
     * 토큰에서 헤더 정보를 추출하여 소셜 로그인 정보를 가져오는 메서드
     * @return : MyPageCheckSocialDTO
     */
    public MyPageCheckSocialDTO myPageSocialCheck() {
        String userId = myPageGetUserId();
        return myPageDAO.getSocialLogin(userId);
    }

    /**
     * 개인 정보 페이지에 정보를 전달하기 위하여 정보를 추출하기 위한 서비스
     * @return : 개인 정보가 담긴 DTO 객체
     */
    public MyPageGetUserInfoDTO getUserInfo() {
        String userId = myPageGetUserId();
        // 추출한 id로 정보를 요청
        return myPageDAO.getUserInfoById(userId);
    }

    /**
     * 헤더에서 userId를 추출하여 토큰에 담고
     * @param myPageUpdateUserInfoDTO : 업데이트 한 개인정보가 담긴 값
     */
    public void updateUserInfoByAuth(MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        String userId = myPageGetUserId();
        // 암호 공백 또는 빈값이 아닐 경우 암호화해서 넣기
        String password = myPageUpdateUserInfoDTO.getPassword();
        if(password != null && !password.isEmpty()){
            myPageUpdateUserInfoDTO.setPassword(passwordEncoder.encode(password));
        }
        // 수정 날짜 세팅
        myPageUpdateUserInfoDTO.setEDate(LocalDateTime.now().withNano(0)); // 나노초 제거하여 넣기

        // 유저 아이디 세팅
        myPageUpdateUserInfoDTO.setId(userId);
        
        // 반환 값에 따라 에러 처리 하기
        int updateResult = myPageDAO.updateUserInfo(myPageUpdateUserInfoDTO);

        if(updateResult == 0 || updateResult > 1) {
            throw new DontUpdateException();
        }

    }

    /**
     * 헤더에서 userId를 추출하여 찜 목록을 가져오는 서비스
     * @return List<MyPageWishListDTO>로 찜 목록에 배분할 값이 담긴 DTO가 0개이상 담긴 List
     */
    public List<MyPageProductListDTO> getWishListByAuth() {
        String userId = myPageGetUserId();
        return myPageDAO.getMyWishListInfoByUserId(userId);
    }

    /**
     * 헤더에서 userId를 추출하여 메인 페이지에 쓸 찜, 판매, 거래 후기 목록을 가져오는 서비스
     * @return MyPageMainDTO로 별점과 각 항목의 리스트가 담긴 DTO
     */
    public MyPageMainDTO getMainInfoListByAuth() {
        String userId = myPageGetUserId();
        MyPageAverageRatingDTO myPageAverageRating = myPageDAO.getAverageRatingByUserId(userId);
        List<MyPageProductListDTO> myMainSellProductList = myPageDAO.getMyMainSellListInfoByUserId(userId);
        List<MyPageProductListDTO> myMainPeakList = myPageDAO.getMyMainPeakListInfoByUserId(userId);
        List<ReviewListDTO> myMainReview = myPageDAO.getMyMainReviewListInfoByUserId(userId);
        return new MyPageMainDTO(myPageAverageRating, myMainSellProductList , myMainPeakList, myMainReview);
    }

    /**
     * 마이페이지 판매 내역을 위한 서비스
     * @return List<MyPageWishListDTO>로 조죄한 판매 내역 정보가 담김
     */
    public List<MyPageProductListDTO> getSellListByAuth() {
        String userId = myPageGetUserId();
        return myPageDAO.getMySellProductByUserId(userId);
    }

    /**
     * 마이페이지 구매 내역을 위한 서비스
     * @return List<MyPageWishListDTO>로 조죄한 구매 내역 정보가 담김
     */
    public List<MyPageProductListDTO> getBuyListByAuth() {
        String userId = myPageGetUserId();
        return myPageDAO.getMyBuyProductByUserId(userId);
    }

    /**
     * 마이페이지 게시판 - 게시판 내용을 가져올 서비스
     * @return : List<MyPageBoardDTO>에 사용자가 작성한 게시판 정보가 담김
     */
    public List<MyPageBoardDTO> getMyBoardByAuth() {
        String userId = myPageGetUserId();
        return myPageDAO.getMyBoardByUserId(userId);
    }

    /**
     * 마이페이지 게시판 - 댓글 내용을 가져올 서비스
     * @return : List<MyPageCommentDTO>에 사용자가 작성한 게시판 정보가 담김
     */
    public List<MyPageCommentDTO> getMyCommentByAuth() {
        String userId = myPageGetUserId();
        return myPageDAO.getMyCommentByUserId(userId);
    }

    /**
     * 마이페이지 리뷰에 정보를 전달하기 위한 서비스
     * @return : 별점 평균과 함께 리뷰 정보를 전달하는 객체
     */
    public MyPageReviewDTO getMyReviewInfoByAuth() {
        String userId = myPageGetUserId();
        // 평균 별점 점수 가져오기
        MyPageAverageRatingDTO averageRating = myPageDAO.getAverageRatingByUserId(userId);
        List<ReviewListDTO> ReviewList = myPageDAO.getMyReviewInfoByUserId(userId);
        return new MyPageReviewDTO(ReviewList, averageRating);
    }

    /**
     * 인증 객체는 스프링 빈으로 등록할 때 null이므로 생성자에서는 사용하지 못하고 꼭 메서드 안에서 써야 해서 userId를 공통 적용하기 위한 메서드 생성
     */
    private String myPageGetUserId () {
        // 사용자 정보를 인증 객체에서 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal(); // String 자료형으로 다운
    }

    /**
     * 카테고리 정보를 얻기 위한 서비스
     * @return : List<UserCategorySubDTO>
     */
    public List<UserCategorySubDTO> getUserCategoryInfo () {
        return myPageDAO.getCategorySubInfo();
    }

    /**
     * 유저 선호도 담아서 dto로 반환
     * @return : 선호도 idx 값이 담긴 dto
     */
    public UserMyPageFavoriteDTO getUserFavoritesInfo() {
        // 유저 정보에서 idx 추출하기
        String userId = myPageGetUserId();
        int userIdx = userDAO.getUserIdx(userId);
        List<Integer> favorites = myPageDAO.getUserFavoritesList(userIdx);
        UserMyPageFavoriteDTO userMyPageFavoriteDTO = new UserMyPageFavoriteDTO();
        userMyPageFavoriteDTO.setFavorites(favorites);
        return userMyPageFavoriteDTO;
    }

    public void updateUserFavoriteInfo (UserMyPageFavoriteDTO userMyPageFavoriteDTO) {
        String userId = myPageGetUserId();
        int userIdx = userDAO.getUserIdx(userId);
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        userMyPageFavoriteDTO.setUserIdx(userIdx);
        userMyPageFavoriteDTO.setSDate(localDateTime);
        if(myPageDAO.updateUserFavoritesList(userMyPageFavoriteDTO) == 0) {
            throw new DontUpdateException();
        }
    }
}