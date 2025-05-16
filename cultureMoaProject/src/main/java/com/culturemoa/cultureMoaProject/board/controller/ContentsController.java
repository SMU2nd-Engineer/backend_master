package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 콘텐츠와 user 유저 조인해서 모든 데이터 조회
    @GetMapping("/list")
    public List<ContentInfoDTO> getContentInfos() {
        System.out.println(contentsService.getContentInfos());

        return contentsService.getContentInfos();
    }

    // 콘텐츠와 user 유저 조인한  데이터 + 키워드 검색
    @GetMapping("/search")
    public List<ContentInfoDTO> getContentSearchs() {
        System.out.println(contentsService.getContentSearchs());

        return contentsService.getContentSearchs();
    }

}
