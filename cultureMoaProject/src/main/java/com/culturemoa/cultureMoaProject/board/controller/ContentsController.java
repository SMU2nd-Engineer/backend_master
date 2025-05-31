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
    private final S3Service s3Service;

    @Autowired
    public ContentsController(ContentsService contentsService, ContentsCommentService contentsCommentService, S3Service s3Service) {
        this.contentsService = contentsService;
        this.contentsCommentService = contentsCommentService;
        this.s3Service = s3Service;
    }

    @GetMapping("/list")
    public List<ContentInfoDTO> getContentInfos() {
        System.out.println(contentsService.getContentInfos());

        return contentsService.getContentInfos();
    }

    @GetMapping("/search")
    public List<ContentInfoDTO> getContentInfos(
            @RequestParam(name = "category", required = false) Long category_idx,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType
    ) {
        // Map 사용하지 않고 파라미터를 서비스에 전달
        return contentsService.getContentSearchs(category_idx, keyword, searchType);
    }

    @GetMapping ("/detail/{idx}")
    public ContentsDetailLoadImageInfoDTO getParticular(
            @PathVariable Long idx
    ) {

        return contentsService.getParticular(idx);
    }

    @PostMapping("/submit")
    public ContentsDTO insertContents(@RequestPart("contents") ContentsDTO contentDTO, @RequestPart("files")List<MultipartFile> files) {
        try {
            String uploadDir = "board/";
            List<ContentsImageSubmitDTO> boardImageList = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!files.isEmpty()) {
                    String boardImagUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
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

    // 게시판 상세페이지(수정 버튼) - 이미지 수정
    @PostMapping("/edit")
    public ContentsDetailModifyInfoDTO updateContents(@RequestPart("contents") ContentsDetailModifyInfoDTO modifyInfoDTO, @RequestPart("files")List<MultipartFile> files) {
        try {
            String uploadDir = "board/";
            List<ContentsImageSubmitDTO> boardImageList = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!files.isEmpty()) {
                    String boardImagUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
                    boardImageList.add(new ContentsImageSubmitDTO(boardImagUrl));
                }
            }

            contentsService.postModifyContents(modifyInfoDTO,boardImageList);
            return modifyInfoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

