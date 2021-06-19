package com.boot.springReddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.springReddit.dto.AuthenticationResponse;
import com.boot.springReddit.dto.LoginRequest;
import com.boot.springReddit.dto.RefreshTokenRequest;
import com.boot.springReddit.dto.RegisterRequest;
import com.boot.springReddit.exception.SpringRedditException;
import com.boot.springReddit.model.NotificationEmail;
import com.boot.springReddit.model.User;
import com.boot.springReddit.model.VerificationToken;
import com.boot.springReddit.repository.UserRepository;
import com.boot.springReddit.repository.VerificationTokenRepository;
import com.boot.springReddit.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {


	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository; 
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;


	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
			throw new SpringRedditException("Username is taken");
		}
		else if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
			throw new SpringRedditException("Email is taken");
		}
		else
		userRepository.save(user);

		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate Your Account",
				user.getEmail(),
				"Thank you for signing up to Fake Reddit, "+"please Click on the below URL to activate your account: "
						+ "http://localhost:8080/api/auth/accountVerification/"+ token));
	}

	private String generateVerificationToken(User user) {
		String token =UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken =  verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(()->new SpringRedditException("Token not found"));
		fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {

		String username = verificationToken.getUser().getUsername();
		User user= userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("User with name "+username+" not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword()));
		User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User name not found"));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(loginRequest.getUsername())
				.id(user.getUserId())
				.build();
	}

	   @Transactional(readOnly = true)
	    public User getCurrentUser() {
	        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
	                getContext().getAuthentication().getPrincipal();
	        return userRepository.findByUsername(principal.getUsername())
	                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	    }

	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		 refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		 String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		 return AuthenticationResponse.builder()
				 .authenticationToken(token)
				 .refreshToken(refreshTokenRequest.getRefreshToken())
				 .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				 .username(refreshTokenRequest.getUsername())
				 .build();
	}
}
