package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 마이페이지 관련 DAO 클래스
 */
@Repository
public class MyPageDAO {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

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
     * 찜 목록 데이터 가져오기 dao
     * @param userId : 토큰에서 추출한 id
     * @return List<MyPageWishListDTO> : 찜으로 선택한 상품 정보가 리스트로 담김
     */
    public List<MyPageProductListDTO> getMyWishListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getWishListInfo", userId);
    }

    /**
     * 총 별점 점수를 가져오기 위한 dao
     * @param userId : 토큰에서 추출한 id
     * @return : 총 별점의 점수와, 총 개수가 담긴 dto
     */
    public MyPageTotalRatingDTO getTotalRatingByUserId (String userId) {
        return sqlSessionTemplate.selectOne("myPageMapper.getTotalRating", userId);
    }

    /**
     * 마이페이지 메인에 전달할 찜 목록 2개 DAO
     * @param userId : 조회할 userID
     * @return 최대 2개가 담긴 DTO 리스트
     */
    public List<MyPageProductListDTO> getMyMainPeakListInfoByUserId (String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMainPeakListInfo", userId);
    }

    /**
     * 마이페이지 판매 내역쪽에 전달할 dao
     * @param userId : 조회할 userID
     * @return 판매 내역이 담긴 DTO 리스트
     */
    public List<MyPageProductListDTO> getMySellProductByUserId(String userId) {
        return sqlSessionTemplate.selectList("myPageMapper.getMySellListInfo", userId);
    }

    /**
     * 마이페이지 구매 내역쪽에 전달할 dao
     * @param userId : 조회할 userID
     * @return 구매 내역이 담긴 DTO 리스트
     */
    public List<MyPageProductListDTO> getMyBuyProductByUserId(String userId) {
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

}
