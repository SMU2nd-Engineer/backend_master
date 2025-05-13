package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 탈퇴한 회원으로 로그인 했을 때 예외
 * (DB에 WDATE 컬럼에 정보가 있을 경우 처리)
 */
public class WithdrawalUserException extends RuntimeException {
    public WithdrawalUserException() {
        super("탈퇴한 회원 입니다.");
    }
}
