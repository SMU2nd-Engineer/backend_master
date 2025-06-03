package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.*;
import com.culturemoa.cultureMoaProject.board.service.ContentsCommentService;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import com.culturemoa.cultureMoaProject.common.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("board")
public class ContentsController {
    private final ContentsService contentsService;
    private final ContentsCommentService contentsCommentService;

    @Autowired
    public ContentsController(ContentsService contentsService, ContentsCommentService contentsCommentService) {
        this.contentsService = contentsService;
        this.contentsCommentService = contentsCommentService;
    }

    // 게시판 리스트 페이지 - 게시글 등록한 목록 확인
    @GetMapping("/list")
    public List<ContentInfoDTO> getContentInfos() {

        return contentsService.getContentInfos();
    }

    // 게시글 리스트페이지 - 제목+내용/작성자, 카테고리선택, 검색
    @GetMapping("/search")
    public List<ContentInfoDTO> getContentInfos(
            @RequestParam(name = "category", required = false) Long category_idx,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType
    ) {
        // Map 사용하지 않고 파라미터를 서비스에 전달
        return contentsService.getContentsSearchCriteria(category_idx, keyword, searchType);
    }

    // 게시글 상세페이지 - 등록된 상세내용 확인
    @GetMapping ("/detail/{idx}")
    public ContentsDetailLoadImageInfoDTO getParticular(
            @PathVariable Long idx
    ) {

        return contentsService.getContentsParticular(idx);
    }

    // 게시글 등록페이지 - 카테고리 선택, 제목, 텍스트 에디터 (글내용 + 이미지) 등록
    @PostMapping("/submit")
    public ContentsDTO insertContents(@RequestPart("contents") ContentsDTO contentDTO, @RequestPart(value = "files", required = false)List<MultipartFile> files) {
        try {
            contentsService.getContentInsert(contentDTO, files);
            return contentDTO;
        } catch (Exception e) {
            e.printStackTrace();
        throw new RuntimeException(e);
        }
    }

    // 등록된 게시글 삭제(상세페이지의 삭제버튼)
    @PutMapping("/delete/{idx}")
    public ResponseEntity<?> deleteContents(@PathVariable Long idx) {
        contentsService.deleteContents(idx);
        return  ResponseEntity.ok("게시글 삭제 성공");
    }

    // 게시판 댓글 contents_idx, text만 불러오게 설정 - 게시글 댓글 목록 조회
    @GetMapping("/comment")
    public List<ContentsCommentInfoDTO> getComment(@RequestParam("idx") Long idx) {
        return contentsCommentService.getComment(idx);
    }

    // 게시글 댓글 등록
    @PostMapping("/comment")
    public Long insertComment(
            @RequestBody ContentsCommentDTO commentDTO
    ) {
        return contentsCommentService.insertComment(commentDTO);
    }

    // 게시글 댓글 삭제
    @PostMapping("/commentdelete")
    public int deleteComment(
            @RequestBody ContentsCommentDeleteInfoDTO contentsCommentDeleteInfoDTO
    ) {
        return contentsCommentService.deleteComment(contentsCommentDeleteInfoDTO);
    }

    // 게시판 상세페이지(수정 버튼) - 카테고리 선택, 제목, 글내용 수정 + 이미지 수정
    @PostMapping("/edit")
    public ContentsDetailImageModifyDTO postModifyContentsImage(
            @RequestParam("idx") Long idx
            , @RequestPart("contents") ContentsDetailImageModifyDTO imageModifyDTO
            , @RequestPart(value = "files", required = false)List<MultipartFile> files
            , @RequestParam("current") List<String> currentUrls) {
        try {
            contentsService.postModifyContentsImage(idx, imageModifyDTO, files, currentUrls);
            return imageModifyDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

