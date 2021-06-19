package com.boot.springReddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.User;
import com.boot.springReddit.model.Vote;


@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

	void save(Post post);

	

}
