package com.boot.springReddit.exception;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException(String exMessage) {
		super(exMessage);
	}

}
