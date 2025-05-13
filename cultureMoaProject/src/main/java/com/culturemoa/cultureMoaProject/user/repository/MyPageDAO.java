package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.MyPageCheckSocialDTO;
import com.culturemoa.cultureMoaProject.user.dto.MyPagePasswordCheckDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 마이페이지 관련 DAO 클래스
 */
@Repository
public class MyPageDAO {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public MyPagePasswordCheckDTO getPassword(String userId) {
        return sqlSessionTemplate.selectOne("myPageMapper.findPasswordByTokenId", userId);
    }
    public MyPageCheckSocialDTO getSocialLogin(String tokenUserId) {
        return sqlSessionTemplate.selectOne("myPageMapper.findSocialLoginByTokenId", tokenUserId);
    }
}
