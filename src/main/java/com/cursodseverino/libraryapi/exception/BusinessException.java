package com.cursodseverino.libraryapi.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String msn) {
        super(msn);
    }
}
