package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import com.culturemoa.cultureMoaProject.user.dto.UserLoginRequestDTO;
import com.culturemoa.cultureMoaProject.user.dto.UserRegisterRequestDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 예시로 사용할 것 또는 마이바티스 사용할 예정이라 변경 될 수 도 있다.
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
        sqlSessionTemplate.selectOne("userMapper.insertUser",pUserDTO);
    }

    /**
     * 아이디로 유정 정보를 가져오는 DAO
     * @param pId : 입력 받은 id 정보
     * @return 아이디로 매칭된 유저 정보를 담은 dto
     */
    public UserLoginRequestDTO findByLoginId (String pId) {
        return sqlSessionTemplate.selectOne("userMapper.findByLoginId",pId);
    }

    /**
     * 회원 가입에서 중복을 처리하는 DAO
     * @param pCheckList : 회원 가입에서 입력 받은 id 또는 nickName
     * @param pCategory : 중복체크가 어디에서 눌렸는지 입력 받은 카테고리
     * @return 쿼리문에서 찾은 id/ nickName의 값 으로 1이상이면 중복이라는 의미
     */
    public int duplicateCheck (String pCheckList, String pCategory) {
        // sql injection 방지 위해 pCheckList 지정
        String columnName;
        switch (pCategory) {
            case ("id") :
                columnName = "ID";
                break;
            case ("nickName") :
                columnName = "NICKNAME";
                break;
            default :
                throw new IllegalArgumentException("컬럼 정보를 확인 할 수 없습니다.");
        }
        // 마이바티스틑 인자로 두번째 인자 하나만 받아서 Map 등을 이용해야 한다.
        Map<String, Object> duplicateMap = new HashMap<>();
        duplicateMap.put("pCheckList", pCheckList);
        duplicateMap.put("columnName", columnName);
        return  sqlSessionTemplate.selectOne("userMapper.duplicateIdCheck",duplicateMap);
    }
}
