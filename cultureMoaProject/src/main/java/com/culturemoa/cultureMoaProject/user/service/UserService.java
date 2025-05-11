package com.culturemoa.cultureMoaProject.user.service;

import com.culturemoa.cultureMoaProject.user.dto.*;
import com.culturemoa.cultureMoaProject.user.exception.DontChangeException;
import com.culturemoa.cultureMoaProject.user.exception.InvalidPasswordException;
import com.culturemoa.cultureMoaProject.user.exception.UserNotFoundException;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 프론트에서 전달받은 데이터를 활용하여 유저 정보 등록
     * 등록시 유저 비번의 경우 암호화해서 넣음
     * @param pUserDTO : josn 형태로 전달 받은 데이터를 dto를 활용하여 받아서 인자로 넣음
     */
    public void registerUser(UserRegisterRequestDTO pUserDTO) {

        // 비밀번호 암호화
        pUserDTO.setPassword(passwordEncoder.encode(pUserDTO.getPassword()));
        // 등록 날짜 넣기
        pUserDTO.setSDate(LocalDateTime.now().withNano(0)); // 나노초 제거
        // 일반 회원 가입이므로 socialLogin에 일반 로그인을 알 수 있도록 값 추가
        pUserDTO.setSocialLogin("regularLogin");
        // DAO로 데이터 넣기
        userDAO.insertUser(pUserDTO);
    }

    /**
     * 로그인 전달 받은 아이디와 비밀번호를 매칭하여 값을 비교
     * @param pId : 사용자가 입력한 아이디
     * @param pPassWord : 사용자가 입력한 비밀번호
     * @return : 조회한 사용자 정보를 담은 UserDTO 객체
     */
    public String loginAuth (String pId, String pPassWord) {
        UserLoginRequestDTO userLogin = userDAO.findByLoginId(pId);

        if(userLogin == null) {
            throw new UserNotFoundException(); // 사용자 없음 예외
        }

        if(!passwordEncoder.matches(pPassWord, userLogin.getPassword())) {
            throw new InvalidPasswordException(); // 비밀번호 일치하지 않음.
        }
        return userLogin.getId();
    }

    /**
     * 아이디, 닉네임 중복 체크
     * @param pCheckList : 전달 받은 id, nickName
     * @param pCategory : 중복 검사할 컬럼
     * @return 중복 체크 후 중복이면 1이상 아니면 0
     */
    public int duplicateCheck(String pCheckList, String pCategory) {
        return userDAO.duplicateCheck(pCheckList, pCategory);
    }

    public UserFindIdResponseDTO findId (UserFindIdRequestDTO findIdInfo) {
        return userDAO.findId (findIdInfo);
    }

    public void changePassword (UserChangePasswordRequestDTO ChangeDto) {

        // 비밀번호 암호화
        ChangeDto.setPassword(passwordEncoder.encode(ChangeDto.getPassword()));
        // 등록 날짜 넣기
        ChangeDto.setEDate(LocalDateTime.now().withNano(0)); // 나노초 제거
        // DAO로 비밀번호 업데이트하기
        int ChangeCheck = userDAO.updateUserPassword(ChangeDto);

        if (ChangeCheck == 0 ) {
            // 변경이 안되면 0을 받으므로 커스텀 에러 처리
            throw new DontChangeException();
        }

    }
}
