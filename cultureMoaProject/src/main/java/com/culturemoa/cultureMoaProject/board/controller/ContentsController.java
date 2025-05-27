package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.*;
import com.culturemoa.cultureMoaProject.board.service.ContentsCommentService;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("board")
public class ContentsController {
    private ContentsService contentsService;
    private ContentsCommentService contentsCommentService;

    @Autowired
    public ContentsController(ContentsService contentsService, ContentsCommentService contentsCommentService) {
        this.contentsService = contentsService;
        this.contentsCommentService = contentsCommentService;
    }

    // 게시판 테이블 idx == user 테이블 idx 면
    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자-nickname) 데이터 출력
    @GetMapping("/list")
    public List<ContentInfoDTO> getContentInfos() {
        System.out.println(contentsService.getContentInfos());

        return contentsService.getContentInfos();
    }


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
    // 게시글 등록
//    @PostMapping ("/submit")
//    public Long getContentInsert(
//            @RequestBody ContentsDTO contentsDTO
//    ) {
////        System.out.println(contentsService.getContentInsert(contentInfoDTO));
//
//        return contentsService.getContentInsert(contentsDTO);
//    }

    // 게시판 테이블 idx == user 테이블 idx 면
    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자-nickname) 데이터 출력
    // 게시글 상세페이지로 이동
    @GetMapping ("/detail/{idx}")
    public ContentInfoDTO getParticular(
            @PathVariable Long idx
    ) {
//        System.out.println(contentsService.getParticular(contentInfoDTO));

        return contentsService.getParticular(idx);
    }

    // 게시판 등록페이지 이미지 업로드
    @PostMapping("/submit")
    public ContentsDTO ContentsUpload (@RequestPart("contents") ContentsDTO contentDTO, @RequestPart("files")List<MultipartFile> files) {
        System.out.println("이미지 업로드 실행");
        try {
            String uploadDir = "C:/board_upload_img/";
            List<ContentsImageSubmitDTO> boardImageList = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!files.isEmpty()) {
                    String boardImagUrl = contentsService.saveBoardImage(file, uploadDir);
                    boardImageList.add(new ContentsImageSubmitDTO(boardImagUrl));
                }
            }

            contentsService.getContentInsert(contentDTO,boardImageList);
            return contentDTO;
        } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
        }
    }


    // 게시판 댓글 contents_idx, text만 불러오게 설정 - 게시글 댓글 목록 조회
    @GetMapping("/comment")
    public List<ContentsCommentInfoDTO> getComment(@RequestParam("idx") Long idx) {
//        System.out.println(contentsCommentService.getComment());
        return contentsCommentService.getComment(idx);
    }

    // 게시글 댓글 등록
    @PostMapping("/comment")
    public Long getCommentInsert(
            @RequestBody ContentsCommentDTO commentDTO
    ) {
//        System.out.println(contentsCommentService.getContentInsert(contentInfoDTO));

        return contentsCommentService.getCommentInsert(commentDTO);
    }

    // 게시글 댓글 삭제
    @PostMapping("/commentdelete")
    public int getCommentDelete (
            @RequestBody ContentsCommentDeleteInfoDTO contentsCommentDeleteInfoDTO
    ) {
        return contentsCommentService.getCommentDelete(contentsCommentDeleteInfoDTO);
    }

}

