package com.boot.springReddit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.springReddit.dto.VoteDto;
import com.boot.springReddit.exception.PostNotFoundException;
import com.boot.springReddit.exception.SpringRedditException;
import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.Vote;
import com.boot.springReddit.repository.PostRepository;
import com.boot.springReddit.repository.VoteRepository;
import static com.boot.springReddit.model.VoteType.UPVOTE;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	 private final VoteRepository voteRepository;
	    private final PostRepository postRepository;
	    private final AuthService authService;

	    @Transactional
	    public void save(VoteDto voteDto) {
	        Post post = postRepository.findById(voteDto.getPostId())
	                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
	        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
	        if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(voteDto.getVoteType())) {
	            throw new SpringRedditException("You have already "
	                    + voteDto.getVoteType() + "D for this post");
	        }
	        if (UPVOTE.equals(voteDto.getVoteType())) {
	            post.setVoteCount(post.getVoteCount() + 1);
	        } else {
	            post.setVoteCount(post.getVoteCount() - 1);
	        }
	        voteRepository.save(mapToVote(voteDto, post));
	        postRepository.save(post);
	    }

	    private Vote mapToVote(VoteDto voteDto, Post post) {
	        return Vote.builder()
	                .voteType(voteDto.getVoteType())
	                .post(post)
	                .user(authService.getCurrentUser())
	                .build();
	    }
}
