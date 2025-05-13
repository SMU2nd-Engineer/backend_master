package com.culturemoa.cultureMoaProject.user.exception;

/**
 * DB 조작(insert, delete, update) 시 오류가 나올 때
 */
public class DBManipulationFailException extends RuntimeException {
    public DBManipulationFailException () {
      super(" DB 조작을 실패했습니다.");
    }
}
