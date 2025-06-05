package com.culturemoa.cultureMoaProject.board.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentDeleteInfoDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentInfoDTO;
import com.culturemoa.cultureMoaProject.board.repository.ContentsCommentDAO;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContentsCommentService {
    private final ContentsCommentDAO contentsCommentDAO;
    private final UserDAO userDAO;
    // 사용자 인증 정보 가져오기
    private final HandleAuthentication handleAuth;

    @Autowired
    public ContentsCommentService(ContentsCommentDAO contentsCommentDAO, UserDAO userDAO, HandleAuthentication handleAuth) {
        this.contentsCommentDAO = contentsCommentDAO;
        this.userDAO = userDAO;
        this.handleAuth = handleAuth;
    }

    // 게시글 댓글 목록 가져오기
    public List<ContentsCommentInfoDTO> getComment(Long idx) {
        return contentsCommentDAO.getComment (idx);
    }

    // 게시판 상세 페이지 댓글 등록
    public Long insertComment(
            ContentsCommentDTO commentDTO
    ) {
//      입력 화면에 없는 user_idx, sdate DB 저장되게 설정
        // 사용자 인증해서 user id를 자동으로 불러옴(user 정보 담겨 있음)
        String userid = handleAuth.getUserIdByAuth();
        // 조회 해서 user id 불러옴
        int useridx = userDAO.getUserIdx(userid);
        commentDTO.setUser_idx((long) useridx);
        commentDTO.setSdate(LocalDateTime.now());

        if (contentsCommentDAO.getCommentInsert(commentDTO) == 1) {
            return commentDTO.getContents_idx();
        };
        return 0L;
    }

    // 게시글 상세페이지 - 댓글 삭제 기능
    // 서비스는 전달받은 값이 없어서 그 값을 채워주기 위한 서비스임
    public int deleteComment(
            ContentsCommentDeleteInfoDTO contentsCommentDeleteInfoDTO
    ) {
        // 현재 DTO에 입력 값이 없어서 입력하기 위해 오늘 날짜 생성해서 넣기 - 시분초까지 출력
        contentsCommentDeleteInfoDTO.setEdate(LocalDateTime.now());

        // idx, 전체 날짜 값이 들어간 DTO를 DAO로 전달 한다.
        return contentsCommentDAO.getCommentDelete(contentsCommentDeleteInfoDTO);
    }
}
