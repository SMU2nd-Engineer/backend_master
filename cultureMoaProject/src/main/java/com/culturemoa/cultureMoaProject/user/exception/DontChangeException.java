package com.culturemoa.cultureMoaProject.user.exception;

public class DontChangeException extends RuntimeException {
    public DontChangeException() {
      super("비밀번호 변경 실패하였습니다.");
    }
}
