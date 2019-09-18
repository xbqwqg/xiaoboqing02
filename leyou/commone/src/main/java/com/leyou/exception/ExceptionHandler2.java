package com.leyou.exception;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandler2 {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResult> exceptionHandler(MyException e){
        return ResponseEntity.status(e.getCode()).body(new ExceptionResult(e.getMessage(),e.getCode()));
    }
}
