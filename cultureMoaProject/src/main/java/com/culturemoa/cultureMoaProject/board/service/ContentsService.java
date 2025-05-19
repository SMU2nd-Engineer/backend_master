package com.culturemoa.cultureMoaProject.board.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.repository.ContentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

@Service
public class ContentsService {
    private final ContentsDAO contentsDAO;

    @Autowired
    public ContentsService(ContentsDAO contentsDAO) {
        this.contentsDAO = contentsDAO;
    }

    public List<ContentsDTO> getAllContents() {
        return contentsDAO.getAllContents();
    }

    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자) 데이터 출력
    public List<ContentInfoDTO> getContentInfos() {
        return contentsDAO.getContentInfos();
    }
    // 제목+내용/작성자, 대분류 선택하고 검색어 입력하면 조건에 맞는 검색 데이터 조회하여 출력
    public List<ContentInfoDTO> getContentSearchs(Long category_idx, String keyword) {
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

//        // Map에 추가
//        if (searchType != null && !searchType.isEmpty()) {
//            searchMap.put("searchType", searchType);
//        }

        // DAO로 Map 전달
        return contentsDAO.getContentSearchs(searchMap);
    }

}
