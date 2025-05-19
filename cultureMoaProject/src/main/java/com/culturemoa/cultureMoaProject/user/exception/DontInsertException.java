package com.culturemoa.cultureMoaProject.user.exception;

public class DontInsertException extends RuntimeException {
    public DontInsertException() {
        super("데이터 삽입에 실패했습니다");
    }
}
