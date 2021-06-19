package com.boot.springReddit.dto;

import java.time.Instant;

import com.boot.springReddit.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {
	private Long id;
	private String name;
	private String description;
	private Integer numberOfPosts;
	private Instant createdDate;
}
