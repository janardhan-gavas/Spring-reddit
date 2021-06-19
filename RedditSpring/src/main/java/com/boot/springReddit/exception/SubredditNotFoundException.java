package com.boot.springReddit.exception;

public class SubredditNotFoundException extends RuntimeException {
	public SubredditNotFoundException(String exMessage) {
		super(exMessage);
	}

}
