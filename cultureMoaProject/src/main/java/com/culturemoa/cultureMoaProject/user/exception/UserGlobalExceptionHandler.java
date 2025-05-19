package com.culturemoa.cultureMoaProject.user.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 전역 커스텀 예외 처리 클래스
 */
@RestControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPassword(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DontChangeException.class)
    public ResponseEntity<String> handleFailChangePassword (DontChangeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DontMatchUserInfoException.class)
    public ResponseEntity<String> handleDontMatchUserInfo(DontMatchUserInfoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFountSocialInfoException.class)
    public ResponseEntity<String> handleNotFountSocialInfo (NotFountSocialInfoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SocialNoAccessTokenException.class)
    public ResponseEntity<?> handleSocialNoAccessToken (SocialNoAccessTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WithdrawalUserException.class)
    public ResponseEntity<?> handleWithdrawalUser (WithdrawalUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    // 사용자 정보를 찾을 수 없으면 회원 가입 하도록 유도한 것
    @ExceptionHandler(SocialUserNotFoundException.class)
    public ResponseEntity<?> handleSocialUserNotFound (SocialUserNotFoundException e) {
        HashMap<String, String> bodyContainer = new HashMap<>();
        bodyContainer.put("socialId", e.getSOCIAL_ID());
        bodyContainer.put("provider", e.getPROVIDER());
        return ResponseEntity.status(402).body(bodyContainer);
    }

    // 토큰 검증에서 access가 만료되면 500을 던져서 401을 던지도록 만든 커스텀 예외
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException (ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("만료된 토큰입니다.");
    }

    // 업데이트 sql에서 문제가 생겼을 때 문제 생김.
    @ExceptionHandler(DontUpdateException.class)
    public ResponseEntity<?> handleDontUpdateException (DontUpdateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * 삽입 오류 방지
     * @param e : 에러
     * @return : 500번 에러던짐
     */
    @ExceptionHandler(DontInsertException.class)
    public ResponseEntity<?> handleDontInsertException (DontInsertException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }

}

