package com.boot.springReddit.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.springReddit.dto.AuthenticationResponse;
import com.boot.springReddit.dto.LoginRequest;
import com.boot.springReddit.dto.RefreshTokenRequest;
import com.boot.springReddit.dto.RegisterRequest;
import com.boot.springReddit.service.AuthService;
import com.boot.springReddit.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authservice;
	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authservice.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful",HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authservice.verifyAccount(token);
		return new ResponseEntity<>("Account activated successfully",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
		return authservice.login(loginRequest);
	}
	@PostMapping("refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest ) {
		return authservice.refreshToken(refreshTokenRequest);
			
	} 
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	}
	
}
