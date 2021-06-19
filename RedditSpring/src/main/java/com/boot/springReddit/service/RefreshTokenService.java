package com.boot.springReddit.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.springReddit.exception.SpringRedditException;
import com.boot.springReddit.model.RefreshToken;
import com.boot.springReddit.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedTime(Instant.now());

		return refreshTokenRepository.save(refreshToken);
	}

	void validateRefreshToken(String token) {
		refreshTokenRepository.findByToken(token)
		.orElseThrow(()-> new SpringRedditException("Invalid Refresh Token"));
	}
	
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);	}
}
