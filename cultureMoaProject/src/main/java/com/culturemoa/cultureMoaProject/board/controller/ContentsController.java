package com.culturemoa.cultureMoaProject.board.controller;

import com.culturemoa.cultureMoaProject.board.dto.*;
import com.culturemoa.cultureMoaProject.board.service.ContentsCommentService;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("board")
public class ContentsController {
    // 생성자 패턴을 사용한 경우 final로 변수 선언
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

    // List<ContentInfoDTO> 라면 별도의 기능이 없는 경우 단순 리스트 조회의 경우
    // getContentInfos 가 맞는 네이밍

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

    //주석 삭제

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

    //위 주석과 마찬가지 ContentInfoDTO를 조회하여 리턴 받는다면 getContentInfo 가 맞음

    // 게시판 테이블 idx == user 테이블 idx 면
    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자-nickname) 데이터 출력
    // 게시글 상세페이지로 이동
    @GetMapping ("/detail/{idx}")
    public ContentsDetailLoadImageInfoDTO getParticular(
            @PathVariable Long idx
    ) {

        return contentsService.getParticular(idx);
    }

    // ContentsDTO 삽입이니 insertContents 가 맞음
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

    // 게시글 이미지 - 상세페이지에서 보이는 화면에 전달
    @GetMapping("/board_upload_img/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("C:/board_upload_img").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 파일 확장자에 따라 Content-Type 지정
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // 기본값
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (
                MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 등록된 게시글 삭제(상세페이지의 삭제버튼)
    @PutMapping("/delete/{idx}")
    public ResponseEntity<?> contentDelete(@PathVariable Long idx) {
        contentsService.deleteContents(idx);
        return  ResponseEntity.ok("게시글 삭제 성공");
    }

    // 게시판 댓글 contents_idx, text만 불러오게 설정 - 게시글 댓글 목록 조회
    @GetMapping("/comment")
    public List<ContentsCommentInfoDTO> getComment(@RequestParam("idx") Long idx) {
//        System.out.println(contentsCommentService.getComment());
        return contentsCommentService.getComment(idx);
    }

    // get~~insert 는 말이 안되는 네이밍 다 고치세요 insertComment 가 맞음
    // 게시글 댓글 등록
    @PostMapping("/comment")
    public Long getCommentInsert(
            @RequestBody ContentsCommentDTO commentDTO
    ) {
//        System.out.println(contentsCommentService.getContentInsert(contentInfoDTO));

        return contentsCommentService.getCommentInsert(commentDTO);
    }

    // get~~delete 마찬가지
    // 게시글 댓글 삭제
    @PostMapping("/commentdelete")
    public int getCommentDelete (
            @RequestBody ContentsCommentDeleteInfoDTO contentsCommentDeleteInfoDTO
    ) {
        return contentsCommentService.getCommentDelete(contentsCommentDeleteInfoDTO);
    }
    // 게시판 상세페이지(수정 버튼) - 이미지 수정
    @PostMapping("/edit")
    public ContentsDetailModifyInfoDTO ContentsModify (@RequestPart("contents") ContentsDetailModifyInfoDTO modifyInfoDTO, @RequestPart("files")List<MultipartFile> files) {
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

            contentsService.postModifyContents(modifyInfoDTO,boardImageList);
            return modifyInfoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

