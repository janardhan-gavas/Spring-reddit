package com.boot.springReddit.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boot.springReddit.dto.CommentsDto;
import com.boot.springReddit.exception.PostNotFoundException;
import com.boot.springReddit.exception.UserNotFoundException;
import com.boot.springReddit.mapper.CommentMapper;
import com.boot.springReddit.model.Comment;
import com.boot.springReddit.model.NotificationEmail;
import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.User;
import com.boot.springReddit.repository.CommentRepository;
import com.boot.springReddit.repository.PostRepository;
import com.boot.springReddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String POST_URL ="";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentsDto commentsDto) {
		Post post=	postRepository.findById(commentsDto.getPostId()).
				orElseThrow(()->new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder.build(post.getUser().getUsername()+" posted a comment on your post."+POST_URL);
		sendCommentNotification(message,post.getUser());

	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()-> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post)
		.stream()
		.map(commentMapper::mapToDto)
		.collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(()->new UserNotFoundException(userName));
		
		return commentRepository.findAllByUser(user)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}

}
