package com.boot.springReddit.model;

import java.util.Arrays;

import com.boot.springReddit.exception.SubredditNotFoundException;

public enum VoteType {
	UPVOTE(1),DOWNVOTE(-1),;
	private int direction;
	
	VoteType(int direction){

	}
	
	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values())
				.filter(value-> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(()-> new SubredditNotFoundException("Vote Not Found"));
	}

	public Integer getDirection() {
		return direction;
	}
}
