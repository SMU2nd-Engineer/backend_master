package com.culturemoa.cultureMoaProject.user.exception;

public class DontUpdateException extends RuntimeException {
    public DontUpdateException() {
        super("업데이트 중에 문제가 발생하여 정상적으로 업데이트가 진행되지 않았습니다.");
    }
}
