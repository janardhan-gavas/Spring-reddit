package com.boot.springReddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.springReddit.dto.VoteDto;
import com.boot.springReddit.exception.SpringRedditException;
import com.boot.springReddit.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<String> vote(@RequestBody VoteDto voteDto) {
		
		 // try {
			  voteService.save(voteDto);
				return new ResponseEntity<>(HttpStatus.OK);
		   // } catch (SpringRedditException ex) {
		      
		     // return (ResponseEntity<String>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
		    //} 
	}
}
 