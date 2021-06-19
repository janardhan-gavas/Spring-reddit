package com.boot.springReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
	private Long id;
	private Long postId;
	private String createdDate;
	private String text;
	private String userName;
}
