package com.boot.springReddit.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.springReddit.dto.PostResponse;
import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.Subreddit;
import com.boot.springReddit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>  {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);

}
