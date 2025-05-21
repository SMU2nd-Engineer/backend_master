package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("board")
public class ContentsController {
    @Autowired
    private ContentsService contentsService;

//    // contents 모든 정보만 조회
//    @GetMapping("/board")
//    public List<ContentsDTO> contents() {
//        System.out.println(contentsService.getAllContents());
//
//        return contentsService.getAllContents();
//    }

    // 게시판 테이블 idx == user 테이블 idx 면
    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자-nickname) 데이터 출력
    @GetMapping("/list")
    public List<ContentInfoDTO> getContentInfos() {
        System.out.println(contentsService.getContentInfos());

        return contentsService.getContentInfos();
    }

    // 게시판의 정보와 user의 idx를 조인한 데이터목록 출력
    // + 카테고리와 키워드 검색
//    @GetMapping("/search")
//    public List<ContentInfoDTO> getContentSearchs(Long category_idx, String keyword) {
//        System.out.println(contentsService.getContentSearchs());
//
//        return contentsService.getContentSearchs();
//    }

    // 게시판의 정보와 user의 idx를 조인한 게시글(데이터)목록에서 카테고리와 키워드 검색
    // 검색 조건이 여러개 일때 파라미터를 선택적으로 받음 (required = false)
    // 제목+내용/작성자, 대분류 선택하고 검색어 입력하면 조건에 맞는 검색 데이터 조회하여 출력
    @GetMapping("/search")
    public List<ContentInfoDTO> getContentSearchs(
            @RequestParam(name = "category", required = false) Long category_idx,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType
    ) {
        // Map 사용하지 않고 파라미터를 서비스에 전달
        return contentsService.getContentSearchs(category_idx, keyword, searchType);
    }

    // 게시판 테이블 idx == user 테이블 idx 면
    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자-nickname) 데이터 출력
    @PostMapping ("/submit")
    public List<ContentInfoDTO> getContentInsert() {
        System.out.println(contentsService.getContentInsert());

        return contentsService.getContentInsert();
    }

}

