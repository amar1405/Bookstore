package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class BookStoreExceptionHandler extends RuntimeException {
	private HttpStatus httpStatus = null;
	String msg = "";

	public BookStoreExceptionHandler(HttpStatus httpStatus, String msg) {
		this.httpStatus = httpStatus;
		this.msg = msg;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMsg() {
		return httpStatus + " : " +msg;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
