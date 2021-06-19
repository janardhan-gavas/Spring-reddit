package com.boot.springReddit.exception;

import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SpringRedditException extends RuntimeException {
	public SpringRedditException(String exMessage) {
		super(exMessage);
	}

	public SpringRedditException(String exMessage,Exception e) {
		super(exMessage);
	}
}
