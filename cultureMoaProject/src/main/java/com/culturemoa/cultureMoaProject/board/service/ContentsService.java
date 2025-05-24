package com.culturemoa.cultureMoaProject.board.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsImageSubmitDTO;
import com.culturemoa.cultureMoaProject.board.repository.ContentsDAO;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ContentsService {
    private final ContentsDAO contentsDAO;

    @Autowired
    public ContentsService(ContentsDAO contentsDAO, UserDAO userDAO) {
        this.contentsDAO = contentsDAO;
        this.userDAO = userDAO;
    }
    @Autowired
    private final UserDAO userDAO;

    // 사용자 인증 정보 가져오기
    @Autowired
    private HandleAuthentication handleAuth;

    public List<ContentsDTO> getAllContents() {
        return contentsDAO.getAllContents();
    }

    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자) 데이터 출력
    public List<ContentInfoDTO> getContentInfos() {
        return contentsDAO.getContentInfos();
    }
    // 제목+내용/작성자, 대분류 선택하고 검색어 입력하면 조건에 맞는 검색 데이터 조회하여 출력
    public List<ContentInfoDTO> getContentSearchs(Long category_idx, String keyword, String searchType) {
        // searchMap에 값을 안넣으면 xml의 if 조건에 해당 파라미터가 존재하지 않아서 바인딩 에러 발생할 수 있음
        // SQL 에서 조건을 동적으로 처리할 수 있게 하기 위함
        Map<String, Object> searchMap = new HashMap<>();

        // 파라미터가 null이 아닌 경우에만 Map에 추가
        if (category_idx != null) {
            searchMap.put("category_idx", category_idx);
        }

        // 키워드 문자열이 실제로 값이 있는지 확인 후 Map에 추가
        if (keyword != null && !keyword.isEmpty()) {
            searchMap.put("keyword", keyword);
        }

        // 구분자 : 제목+내용 / 작성자 파라미터가 null 아닌 경우에만 Map에 추가
        if (searchType != null && !searchType.isEmpty()) {
            searchMap.put("searchType", searchType);
        }

        // DAO로 Map 전달
        return contentsDAO.getContentSearchs(searchMap);
    }

    // 게시판 등록 페이지 - 게시글 등록
    public int getContentInsert(
            ContentInfoDTO contentInfoDTO
    ) {
        // 로그인이 되어 있어야 사용가능한데 userId 가져올 수 있음
//        String userId = handleAuth.getUserIdByAuth();
//        // 입력 화면에 없는 user_idx, sdate DB 저장되게 설정
//        // user 정보 담겨 있음
//        int userIdx = userDAO.getUserIdx(userId);
//        contentInfoDTO.setUser_idx((long) userIdx);
        contentInfoDTO.setUser_idx((long) 1.00);
        contentInfoDTO.setSdate(LocalDateTime.now().withNano(0));

        System.out.println("여기까지 실행 됨" + contentInfoDTO);

        return contentsDAO.getContentInsert(contentInfoDTO);
    }

}
