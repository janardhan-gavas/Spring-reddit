package com.boot.springReddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.springReddit.model.Subreddit;
import com.boot.springReddit.model.User;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {

	Optional<Subreddit> findByName(String subredditName);

	/*
	 * Optional<User> findByUsername(String username);
	 */	
}
 