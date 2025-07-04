package com.culturemoa.cultureMoaProject.board.service;

import com.culturemoa.cultureMoaProject.board.dto.*;
import com.culturemoa.cultureMoaProject.board.repository.ContentsDAO;
import com.culturemoa.cultureMoaProject.common.service.S3Service;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContentsService {
    private final ContentsDAO contentsDAO;
    private final UserDAO userDAO;
    private final HandleAuthentication handleAuth;
    private final S3Service s3Service;

    @Autowired
    public ContentsService(ContentsDAO contentsDAO, UserDAO userDAO, HandleAuthentication handleAuth, S3Service s3Service) {
        this.contentsDAO = contentsDAO;
        this.userDAO = userDAO;
        this.handleAuth = handleAuth;
        this.s3Service = s3Service;
    }

    // 생성자 패턴이면 통합 하세요 생성자로 Autowired 여러개 하는거 아닙니다 생성자 패턴에서는 - 수정완료

    public List<ContentsDTO> getAllContents() {
        return contentsDAO.getAllContents();
    }

    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자) 데이터 출력
    public List<ContentInfoDTO> getContentInfos() {
        return contentsDAO.getContentInfos();
    }

    // 제목+내용/작성자, 대분류 선택하고 검색어 입력하면 조건에 맞는 검색 데이터 조회하여 출력
    public List<ContentInfoDTO> getContentsSearchCriteria(Long category_idx, String keyword, String searchType) {
        // searchMap에 값을 안넣으면 xml의 if 조건에 해당 파라미터가 존재하지 않아서 바인딩 에러 발생할 수 있음
        // SQL 에서 조건을 동적으로 처리할 수 있게 하기 위함
        Map<String, Object> searchMap = new HashMap<>();

        // 파라미터가 null이 아닌 경우에만 Map에 추가
        if (category_idx != null && category_idx != -1) {
            searchMap.put("category_idx", category_idx);
        }

        // 키워드 문자열이 실제로 값이 있는지 확인 후 Map에 추가
        if (keyword != null && !keyword.isEmpty()) {
            searchMap.put("keyword", keyword);
        }
        else {
            searchMap.put("keyword", "");
        }

        // 구분자 : 제목+내용 / 작성자 파라미터가 null 아닌 경우에만 Map에 추가
        if (searchType != null && !searchType.isEmpty()) {
            searchMap.put("searchType", searchType);
        }
        else  {
            searchMap.put("searchType", -1);
        }

        // DAO로 Map 전달
        return contentsDAO.getContentsSearchCriteria(searchMap);
    }

    // 게시판 등록 페이지 - 게시글 등록
    public Long getContentInsert(
            // 등록해야할 칼럼들, 불러와야 할 이미지 Url 주소를 리스트로 저장
            ContentsDTO contentsDTO, List<MultipartFile> files
    ) throws IOException {
        String uploadDir = "board/";
        List<ContentsImageSubmitDTO> boardImageList = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                // 이미지 선택하지 않아도 저장되는 조건 - 파일이 있을때만 처리
                String boardImagUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
                boardImageList.add(new ContentsImageSubmitDTO(boardImagUrl));
            }
        }

        // 사용자 인증해서 user id를 자동으로 불러옴(user 정보 담겨 있음)
        String userid = handleAuth.getUserIdByAuth();
        // 조회 해서 user id 불러옴
        int useridx = userDAO.getUserIdx(userid);
//      입력 화면에 없는 user_idx, sdate DB 저장되게 설정
        contentsDTO.setUser_idx((long) useridx);
        contentsDTO.setSdate(LocalDateTime.now());

        // 게시글 등록(카테고리(잡담/팝니다/삽니다/기타) 선택, 제목 입력, 글 내용(텍스트 에디터))
        if(contentsDAO.getContentInsert(contentsDTO) == 1){
            // 텍스트 에디터 quill 이미지 저장
            if (!boardImageList.isEmpty()) {
                for (ContentsImageSubmitDTO imageSubmitDTO : boardImageList ) {
                    ContentsDetailImageDTO detailImageDTO = new ContentsDetailImageDTO();
                    detailImageDTO.setContents_idx(contentsDTO.getIdx());
                    detailImageDTO.setImage_url(imageSubmitDTO.getImage_url());

                    contentsDAO.insertContentsImage(detailImageDTO);

                }
            }
            return contentsDTO.getIdx();
        };

        return 1L;
    }

    // 게시판 등록페이지 이미지 저장
    public String saveBoardImage(MultipartFile image, String uploadDir) throws IOException {

        String originalFilename = image.getOriginalFilename();


        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 파일 이름 생성
        String timestamp = LocalDateTime.now().toString().replace(":", "-");
        String boardImageUrl = UUID.randomUUID() + "_" + timestamp + extension; // UUID 동일 파일명 방지

        // quill 에디터에서 등록한 이미지를 Blob 파일형식으로 변환 => 서버에 저장된 이미지 파일 주소 경로
        String boardImagePath = uploadDir + boardImageUrl;
        
        // Blob 파일형식으로 변경한 이미지의 파일 주소 경로를 DB 저장
        String boardDbImagePath = "/board_upload_img/" + boardImageUrl;

        Path path = Paths.get(boardImagePath); // Path 객체 생성
        Files.createDirectories(path.getParent());
        Files.write(path,image.getBytes());

        return boardDbImagePath;
        
    }


    // 게시판 테이블(카테고리, 제목, 날짜), user 테이블(작성자) 데이터 출력
    // 게시글 상세페이지 게시글 정보 불러오기
    public ContentsDetailLoadImageInfoDTO getContentsParticular (Long idx) {
        ContentsDetailLoadImageInfoDTO contentsDTO = new ContentsDetailLoadImageInfoDTO();

        ContentInfoDTO contentInfoDTO = contentsDAO.getContentsParticular (idx);
        contentsDTO.setIdx(contentInfoDTO.getIdx());
        contentsDTO.setUser_idx(contentInfoDTO.getUser_idx());
        contentsDTO.setCategory_idx(contentInfoDTO.getCategory_idx());
        contentsDTO.setTitle(contentInfoDTO.getTitle());
        contentsDTO.setContent(contentInfoDTO.getContent());
        contentsDTO.setSdate(contentInfoDTO.getSdate());
        contentsDTO.setNickname(contentInfoDTO.getNickname());

        contentsDTO.setDetailImageList(contentsDAO.getContentsReadDetailImages(idx));
        return contentsDTO;

        }

    // 등록된 게시글 수정(상세페이지의 수정버튼) -- 밑에 합치는 중
    public void  postContentsModifyInformations(ContentsDetailModifyInfoDTO modifyInfoDTO) {
        // 입력화면에 없는 것을 DB에 저장
        modifyInfoDTO.setCdate(LocalDateTime.now());
    }

    // 등록된 게시글 (상세페이지 수정버튼)카테고리(잡담/팝니다/삽니다/기타)+제목+글내용, 이미지 수정
    public Long postModifyContentsImage(
            Long idx
            , ContentsDetailImageModifyDTO imageModifyDTO
            , List<MultipartFile> files
            ,List<String> currentUrls
    ) throws IOException {
        String uploadDir = "board/";
        List<ContentsImageSubmitDTO> boardImageList = new ArrayList<>();
        List<ContentsDetailImageDTO> toDelete;
        List<ContentsDetailImageDTO> savedUrls = contentsDAO.getContentsReadDetailImages(idx);

        if(currentUrls != null && !currentUrls.isEmpty()){
            for (String current : currentUrls) {
                if (current.startsWith("https://"))
                    boardImageList.add(new ContentsImageSubmitDTO(current));
                else {
                    MultipartFile file = files.get(0);
                    // 이미지 선택하지 않아도 저장되는 조건 - 파일이 있을때만 처리
                    String boardImagUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
                    boardImageList.add(new ContentsImageSubmitDTO(boardImagUrl));
                    files.remove(0);
                }
            }
            // 기존 저장된 S3 데이터 지워야햄
            List<String> incomingUrls = currentUrls.stream()
                    .filter(url -> url.startsWith("http"))
                    .toList();
            toDelete = savedUrls.stream()
                    .filter(url -> !incomingUrls.contains(url.getImage_url()))
                    .toList();
        }else {
            toDelete =  savedUrls.stream().filter(url -> !url.getImage_url().isEmpty()).toList();
        }

        for(ContentsDetailImageDTO detailImageDTO : toDelete){
            s3Service.deleteByUrl(detailImageDTO.getImage_url());
        }

        imageModifyDTO.setIdx(idx);
//       user 정보 담겨 있음
        // 사용자 인증해서 user id를 자동으로 불러옴
        String userid = handleAuth.getUserIdByAuth();
        // 조회 해서 user id 불러옴
        int useridx = userDAO.getUserIdx(userid);
//      입력 화면에 없는 cdate DB 저장되게 설정
        imageModifyDTO.setCdate(LocalDateTime.now());
        imageModifyDTO.setUser_idx((long) useridx);

        // 게시글 상세페이지(수정 버튼: 카테고리(잡담/팝니다/삽니다/기타) 선택, 제목 입력, 글 내용(텍스트 에디터))
        contentsDAO.postContentsModifyInformations(imageModifyDTO);

        // 게시글 상세페이지 (수정버튼 - 텍스트 에디터: 이미지)
        contentsDAO.deleteModifyContentsImage(imageModifyDTO);
        // 텍스트 에디터 quill 이미지 수정 저장
        if (!boardImageList.isEmpty()) {
            for (ContentsImageSubmitDTO imageSubmitDTO : boardImageList ) {
                ContentsDetailImageDTO detailImageDTO = new ContentsDetailImageDTO();
                detailImageDTO.setContents_idx(imageModifyDTO.getIdx());
                detailImageDTO.setImage_url(imageSubmitDTO.getImage_url());

                contentsDAO.insertContentsImage(detailImageDTO);

            }
        }
        return imageModifyDTO.getIdx();
    }


    // 등록된 게시글 삭제(상세페이지의 삭제버튼)
    public int deleteContents(Long idx){
        LocalDateTime localDateTime = LocalDateTime.now();
        ContentsDeleteInfoDTO deleteInfoDTO = new ContentsDeleteInfoDTO(idx, localDateTime);

        return contentsDAO.deleteContents(deleteInfoDTO);

    }

}
