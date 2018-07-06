package com.bookstore.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookStoreHandlerAdvice {
	@ExceptionHandler(BookStoreExceptionHandler.class)
	public ResponseEntity handleException(BookStoreExceptionHandler e) {
		return ResponseEntity.status(e.getHttpStatus()).body(e.getMsg());
	}
}
