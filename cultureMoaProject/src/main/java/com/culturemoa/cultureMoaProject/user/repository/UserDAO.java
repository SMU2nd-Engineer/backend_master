package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * UserDAO
 * 마이바티스와 연동하여 DB에서 데이터를 조회할 Data Access Object, 마이페이지 제외 DAO
 * sqlSessionTemplate : 마이바티스용 변수
 */
@Repository
public class UserDAO {
    
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

        // userMapper 라는 부분과 5단계에 있는 mapper.xml 파일의 namespace를 동일하게 맞춰준다
        //.getUserInfo 와 5단계에 있는 <select id= 부분를 동일하게 맞춰준다.

    /**
     * 회원정보 등록 DAO
     * @param pUserDTO 회원 가입에 입력한 유저 정보가 담긴 dto
     */
    public void insertUser(UserRegisterRequestDTO pUserDTO) {
        sqlSessionTemplate.insert("userMapper.insertUser",pUserDTO);
    }

    /**
     * 일반 로그인 시 입력한 아이디로 패스워드를 DAO
     * @param pRequest : 입력 받은 사용자 정보 DTO
     * @return 아이디로 매칭된 유저 정보를 담은 dto
     */
    public UserLoginDTO findByLoginInfo (UserLoginDTO pRequest) {
        return sqlSessionTemplate.selectOne("userMapper.findPasswordByLoginId",pRequest);
    }

    /**
     * 아이디로 유정 정보를 가져오는 DAO
     * @param userId : 입력 받은 사용자 정보 DTO
     * @return 아이디로 매칭된 유저 정보를 담은 dto
     */
    public SocialLoginResponseDTO socialFindByLoginInfo (String userId) {
        return sqlSessionTemplate.selectOne("userMapper.socialFindByLoginId",userId);
    }

    /**
     * 회원 가입에서 중복을 처리하는 DAO
     * @param duplicateMap : 회원 가입에서 입력 받은 id 또는 nickName 과 어디를 검색할지 내용이 들어가 있음
     * @return 쿼리문에서 찾은 id/ nickName의 값 으로 1이상이면 중복이라는 의미
     */
    public int duplicateCheck (Map<String, Object> duplicateMap) {
        return  sqlSessionTemplate.selectOne("userMapper.duplicateIdCheck",duplicateMap);
    }

    /**
     * 아이디 찾기 위한 DAO
     * @param findIdInfo : ID 찾기 위해 사용자에게 받은 name, email 정보
     * @return UserFindIdResponseDTO 반환(위에서 사용시 void로 의미없음)
     */
    public UserFindIdResponseDTO findId (UserFindIdRequestDTO findIdInfo) {
        return sqlSessionTemplate.selectOne("userMapper.findId", findIdInfo);
    }

    /**
     * 비밀 번호 찾기시 비밀번호 업데이트 DAO
     * @param changePasswordRequestDTO : 암호화된 새로운 비밀번호 정보가 들어있는 DTO
     * @return 비밀번호 변경 확인을 위하여 1(정상 변경) 또는 0(변경 안됨)
     */
    public int updateUserPassword (UserChangePasswordRequestDTO changePasswordRequestDTO) {
        return sqlSessionTemplate.update("userMapper.changePassword", changePasswordRequestDTO);
    }

    /**
     * 비밀번호 찾기 입력시 데이터가 일치하는지 DB와 조회하는 DAO
     * @param userFindPasswordRequestDTO : 사용자가 입력한 이메일, 아이디, 이름 정보가 담김
     * @return 응답DTO
     */
    public UserFindPasswordResponseDTO passwordFindMatch (UserFindPasswordRequestDTO userFindPasswordRequestDTO) {
        return sqlSessionTemplate.selectOne("userMapper.passwordFindMatch", userFindPasswordRequestDTO);
    }

    /**
     * 회원 탈퇴 로직
     * @param userWithdrawalDTO : 탈퇴 회원 정보를 담을 DTO
     * @return : userWithdrawalDTO
     */
    public int updateWithdrawal(UserWithdrawalDTO userWithdrawalDTO) {
        return sqlSessionTemplate.update("userMapper.updateWithdrawal", userWithdrawalDTO);
    }

    /**
     * 선호도 조사 페이지를 위한 쿼리
     * @return : List<UserCategorySubDTO> 카테고리 서브 정보가 담김
     */
    public List<UserCategorySubDTO> getCategorySubInfo () {
        return sqlSessionTemplate.selectList("userMapper.getCategorySubInfo");
    }

    /**
     * 유저 선호도 값을 DB에 넣기
     * @param userChooseFavoriteDTO : 유전 선호도 배열과 IDX, SDATE
     * @return : 성공적으로 들어갔는지 아닌지 확인하기 위한 INT
     */
    public int insertUserFavorites (UserChooseFavoriteDTO userChooseFavoriteDTO) {
        return sqlSessionTemplate.insert("userMapper.insertFavorite", userChooseFavoriteDTO);
        
    }

    /**
     * 선호도에 넣을 USER_IDX를 얻기 위한 DAO
     * @param userId : 인증객체에서 추출한 유저 아이디
     * @return : USER_IDX
     */
    public int getUserIdx (String userId) {
        return sqlSessionTemplate.selectOne("userMapper.getUserIdx", userId);
    }


}
