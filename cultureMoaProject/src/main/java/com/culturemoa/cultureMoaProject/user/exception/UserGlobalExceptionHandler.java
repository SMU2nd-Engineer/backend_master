package com.culturemoa.cultureMoaProject.user.exception;

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
    public ResponseEntity<String> invalidPassword(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DontChangeException.class)
    public ResponseEntity<String> failChangePassword (DontChangeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DontMatchUserInfoException.class)
    public ResponseEntity<String> dontMatchUserInfo(DontMatchUserInfoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFountSocialInfoException.class)
    public ResponseEntity<String> notFountSocialInfo (NotFountSocialInfoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SocialNoAccessTokenException.class)
    public ResponseEntity<?> socialNoAccessToken (SocialNoAccessTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WithdrawalUserException.class)
    public ResponseEntity<?> withdrawalUser (WithdrawalUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SocialUserNotFoundException.class)
    public ResponseEntity<?> socialUserNotFound (SocialUserNotFoundException e) {
        HashMap<String, String> bodyContainer = new HashMap<>();
        bodyContainer.put("socialId", e.getSOCIAL_ID());
        bodyContainer.put("provider", e.getPROVIDER());
        return ResponseEntity.status(402).body(bodyContainer);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }

}

