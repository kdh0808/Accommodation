package com.koreait.bnb.controller;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> conversion_exception(ConversionException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
