package com.culturemoa.cultureMoaProject.user.repository;

import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 예시로 사용할 것 또는 마이바티스 사용할 예정이라 변경 될 수 도 있다.
@Repository
public class UserDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<UserDTO> getAllUser(int userIdx) {
        // userMapper 라는 부분과 5단계에 있는 mapper.xml 파일의 namespace를 동일하게 맞춰준다
        //.getUserInfo 와 5단계에 있는 <select id= 부분를 동일하게 맞춰준다.
        return sqlSessionTemplate.selectList("userMapper.getAllUser",userIdx);

    }
}
