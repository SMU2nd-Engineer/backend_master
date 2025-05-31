package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 마이페이지 관련 DAO 클래스
 */
@Repository
public class MyPageDAO {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public MyPageDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    /**
     * 아이디를 사용하여 패스워드 체크를 위한 단방향 암호 가져오기
     * @param userId : 토큰에서 추출한 아이디
     * @return 단방향 암호값을 담을 MyPagePasswordCheckDTO
     */
    public MyPagePasswordCheckDTO getPassword(String userId) {
        return sqlSessionTemplate.selectOne("myPageMapper.findPasswordByTokenId", userId);
    }

    /**
     * 아이디를 이용하여 소셜 로그인 정보를 가져오기
     * @param tokenUserId : 토큰에서 추출한 아이디
     * @return : 소셜 정보를 담을 DTO
     */
    public MyPageCheckSocialDTO getSocialLogin(String tokenUserId) {
        return sqlSessionTemplate.selectOne("myPageMapper.findSocialLoginByTokenId", tokenUserId);
    }

    /**
     * 아이디 정보를 이용하여 개인 정보 수정에 넘길 데이터 가져오기
     * @param tokenUserId : 토큰에서 추출한 id
     * @return MyPageGetUserInfoDTO에 데이터 담아서 반환.
     */
    public MyPageGetUserInfoDTO getUserInfoById(String tokenUserId) {
        return sqlSessionTemplate.selectOne("myPageMapper.findUserInfoByTokenId", tokenUserId);
    }

    /**
     * 사용자가 입력한 개인 정보 수정 정보를 이용하여 수정 진행
     * @param myPageUpdateUserInfoDTO : 수정한 개인 정보가 담김(password 빈값일 수 있음)
     * @return 성공 여부를 판단한기 위하여 int로 반환.
     */
    public int updateUserInfo(MyPageUpdateUserInfoDTO myPageUpdateUserInfoDTO) {
        return sqlSessionTemplate.update("myPageMapper.updateUserInfo", myPageUpdateUserInfoDTO);
    }

    /**
     * 마이페이지 메인에 전달할 찜 목록 2개 DAO
     * @param userId : 조회할 userID
     * @return 최대 2개가 담긴 DTO 리스트
     */
    public List<MyPagePickProductListDTO> getMyMainPeakListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMainPeakListInfo", userId);
    }


    /**
     * 마이페이지 메인에 전달할 판매 목록 4개 DAO
     * @param userId : 조회할 userID
     * @return 최대 4개가 담긴 DTO 리스트
     */
    public List<MyPageSellListDTO> getMyMainSellListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMainSellListInfo", userId);
    }


    /**
     * 마이페이지 메인에 전달할 리뷰 정보 5개가 담긴 DAO
     * @param userId : 조회할 userID
     * @return 최대 5개가 담긴 DTO 리스트
     */
    public List<ReviewListDTO> getMyMainReviewListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMainReviewListInfo", userId);
    }



    /**
     * 찜 목록 데이터 가져오기 dao
     * @param userId : 토큰에서 추출한 id
     * @return List<MyPageWishListDTO> : 찜으로 선택한 상품 정보가 리스트로 담김
     */
    public List<MyPagePickProductListDTO> getMyWishListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getWishListInfo", userId);
    }

    /**
     * 찜 목록 edate 추가하기
     * @param myPickUpdateDTO : 찜 목록을 변경하기 위한 정보가 담긴 dto
     */
    public void updateMyPickByProductIdx (MyPickUpdateDTO myPickUpdateDTO) {
        sqlSessionTemplate.update("myPageMapper.updateMyPick", myPickUpdateDTO);
    }


    /**
     * 마이페이지 판매 내역쪽에 전달할 dao
     * @param userId : 조회할 userID
     * @return 판매 내역이 담긴 DTO 리스트
     */
    public List<MyPageSellListDTO> getMySellProductByUserId(String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMySellByListInfo", userId);
    }


    /**
     * 마이페이지 구매 내역쪽에 전달할 dao
     * @param userId : 조회할 userID
     * @return 판매 내역이 담긴 DTO 리스트
     */
    public List<MyPageBuyListDTO> getMyBuyProductByUserId(String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMyBuyListInfo", userId);
    }

    /**
     * 마이페이지 게시판 탭의 게시판 부분의 정보를 가져올 dao
     * @param userId : 조회할 userID
     * @return : 사용자가 작성한 게시판 정보가 담긴 DTO 리스트
     */
    public List<MyPageBoardDTO> getMyBoardByUserId(String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMyBoardListInfo", userId);
    }

    /**
     * 마이페이지 게시판 탭의 댓글 부분의 정보를 가져올 dao
     * @param userId : 조회할 userID
     * @return : 사용자가 작성한 게시판 정보가 담긴 DTO 리스트
     */
    public List<MyPageCommentDTO> getMyCommentByUserId(String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMyCommentListInfo", userId);
    }

    /**
     * 총 별점 점수를 가져오기 위한 dao
     * @param userId : 토큰에서 추출한 id
     * @return : 총 별점의 점수와, 총 개수가 담긴 dto
     */
    public MyPageAverageRatingDTO getAverageRatingByUserId (String userId) {
        return sqlSessionTemplate.selectOne("myPageMapper.getTotalRating", userId);
    }


    /**
     * 별점을 제외한 나머지 리뷰 탭의 정보를 가져오는 DAO
     * @param userId : 토큰에서 추출한 id
     * @return : List<ReviewListDTO>
     */
    public List<ReviewListDTO> getMyReviewInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMyReviewListInfo", userId);
    }

    public Map<String, Integer> getMyEvaluationByUserIdx (String userId) {
        return sqlSessionTemplate.selectOne("myPageMapper.getMyEvaluationInfo", userId);
    }

    /**
     * 리뷰 페이지 거래 평가 정보를 위한 쿼리
     * @return : List<UserCategorySubDTO> 카테고리 서브 정보가 담김
     */
    public List<UserCategorySubDTO> getEvaluationCategorySubInfo (int maxRange) {
        return sqlSessionTemplate.selectList("myPageMapper.getEvaluationCategorySubInfo", maxRange);
    }

    /**
     * 선호도 조사 페이지에 카테고리를 위한 쿼리
     * @return : List<UserCategorySubDTO> 카테고리 서브 정보가 담김
     */
    public List<UserCategorySubDTO> getCategorySubInfo () {
        return sqlSessionTemplate.selectList("myPageMapper.getCategorySubInfo");
    }

    /**
     * 유저가 선택한 선호도를 키와 value 형태로 받아오기
     * @param UserIdx : 사용자 idx
     * @return : 선호도 정보 map
     */
    public Map<String, Integer> getUserFavoritesList (int UserIdx) {
        return sqlSessionTemplate.selectOne("myPageMapper.getUserFavorites", UserIdx);
    }


    /**
     * 유저 선호도 업데이트 - update( 500에러 문제로 insert / update 분리)
     * @param userRegisterFavoriteDTO : 유저 선호도 데이터를 가지고 있는 dto
     */
    public void updateUserFavoritesList (UserRegisterFavoriteDTO userRegisterFavoriteDTO) {
        sqlSessionTemplate.update("myPageMapper.insertOrUpdateFavorite", userRegisterFavoriteDTO);
    }

    /**
     * 리뷰 페이지에서 사용할 거래 상대 이름을 가져올 dao
     * @param reviewInitInfoDTO : 판매자 idx,
     * @return : 이름이 반환
     */
    public SellerInfoDTO getSellerInfoByDTO (ReviewInitInfoDTO reviewInitInfoDTO) {
        return sqlSessionTemplate.selectOne("myPageMapper.getSellerInfo", reviewInitInfoDTO);
    }


    /**
     * 구매자가 작성한 리뷰 정보를 넣기
     * @param reviewRegisterDTO : 사용자가 작성한 리뷰 데이터가 담긴 DTO
     * @return : 성공시 1 실패시 0
     */
    public int insertReviewInfo(ReviewRegisterDTO reviewRegisterDTO) {
        return sqlSessionTemplate.insert("myPageMapper.insertReviewTbl", reviewRegisterDTO);
    }

    /**
     * 구매자가 남긴 거래 평가에 따라 거래 평가 데이터 관리하기
     * @param reviewRegisterDTO : 사용자가 작성한 리뷰 데이터가 담긴 DTO
     * @return : 성공시 1 실패시 0
     */
    public int updateReviewEvaluation (ReviewRegisterDTO reviewRegisterDTO) {
        return sqlSessionTemplate.update("myPageMapper.insertOrUpdateEvaluation", reviewRegisterDTO);
    }

    /**
     * 리뷰 평가 기록 테이블에 값을 넣을 dao
     * @param reviewRegisterDTO : 사용자가 작성한 리뷰의 idx와 리뷰 평가 항목이 기록된 dto
     * @return : sql 실행 결과
     */
    public int insertReviewEvaluationRecord (ReviewRegisterDTO reviewRegisterDTO) {
        return sqlSessionTemplate.insert("myPageMapper.insertReviewEvaluationRecord", reviewRegisterDTO);
    }

    /**
     * 리뷰 idx를 이용하여 리뷰 내용을 찾아서 가져오기
     * @param reviewIdx : 경로 파라미터 값
     * @return : 찾은 정보가 담긴 dto
     */
    public FetchReviewRegisterInfoDTO searchReviewInfoByReviewIdx ( int reviewIdx) {
        return sqlSessionTemplate.selectOne("myPageMapper.getReviewInfo", reviewIdx);
    }


    /**
     * 리뷰 idx를 이용하여 거래 평가 내용을 찾아서 가져오기
     * @param reviewIdx : 경로 파라미터 값
     * @return : map 항목별 1 또는 0이 담김
     */
    public Map<String, Integer> searchEvaluationRecord(int reviewIdx){
        return sqlSessionTemplate.selectOne("myPageMapper.getReviewEvaluationRecord", reviewIdx);
    }

    /**
     * 리뷰 정보를 업데이트할 dao
     * @param updateReviewInfoDTO : 업데이트 리뷰 정보를 담고 있는 dto
     */
    public int updateReview (UpdateReviewInfoDTO updateReviewInfoDTO) {
        return sqlSessionTemplate.update("myPageMapper.updateReview", updateReviewInfoDTO);
    }

    /**
     * 사용자 평가 항목 누적 값을 조절하는 위한 dao
     * @param updateReviewEvaluationInfo : 쿼리 문에 맞춰 서비스에 생성한 map 객체
     */
    public int updateReviewEvaluation (Map<String, Object> updateReviewEvaluationInfo) {
        return sqlSessionTemplate.update("myPageMapper.changeCountUserEvaluation", updateReviewEvaluationInfo);
    }

    /**
     * 사용자의 리뷰 기록 테이블의 정보를 수정하기 위한 dao
     * @param updateReviewInfoDTO : 업데이트 리뷰 정보를 담고 있는 dto
     */
    public int updateReviewEvaluationRecode (UpdateReviewInfoDTO updateReviewInfoDTO) {
        return sqlSessionTemplate.update("myPageMapper.updateReviewEvaluationRecord", updateReviewInfoDTO);
    }

    /**
     * 리뷰 idx를 이용하여 상품 정보를 가져오기.
     * @param reviewIdx
     * @return
     */
    public FetchReviewProductInfoDTO getProductInfoByReviewIdx (int reviewIdx) {
        return sqlSessionTemplate.selectOne("myPageMapper.getProductInfoByReviewIdx", reviewIdx);
    }

    /**
     * 찜 정보를 확인하기 위해서 찜 정보를 얻기 위한 dao
     * @param userPickInfoDTO : userIdx와 productIdx값이 담긴 dto
     * @return : UserPickInfoDTO 반환
     */
    public UserPickInfoDTO getUserPeakInfoByProductAndUserIdx(UserPickInfoDTO userPickInfoDTO) {
        return sqlSessionTemplate.selectOne("myPageMapper.getUserPeakProductInfo", userPickInfoDTO);
    }

    /**
     * 찜 선택시 삽입하는 dto 추가
     * @param userPickInfoDTO : 찜 목록에 추가할 정보가 담긴 dto
     * @return : 삽입행 개수 반환
     */
    public int insertUserPickByDTO(UserPickInfoDTO userPickInfoDTO) {
        return sqlSessionTemplate.insert("myPageMapper.insertUserPeakInfo", userPickInfoDTO);
    }

}
