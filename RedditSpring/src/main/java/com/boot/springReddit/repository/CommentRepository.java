package com.boot.springReddit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.springReddit.model.Comment;
import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);

}
