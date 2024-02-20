package com.mph.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mph.services.interfaces.RegistrationService;
import com.mph.services.interfaces.ConnectionService;
import com.mph.services.interfaces.UserService;

@RestController
@RequestMapping("/auth")
@Tag(name="Authentication", description="Authentication management APIs")
public class AuthController {

	private static final Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private UserService userService;

	@PostMapping("/sign-up")
	@Operation(summary="Sign-up new user")
	@Parameters({
		@Parameter(name="username", description="The username entered"),
		@Parameter(name="password", description="The password entered"),
		@Parameter(name="confirmPassword", description="The password confirmation entered"),
		@Parameter(name="email", description="The email entered")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Ok", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=String.class)))}),
		@ApiResponse(responseCode="400", description="Invalid username or email format, or passwords do not match", content=@Content),
		@ApiResponse(responseCode="409", description="Username or email already in use", content=@Content)
	})
	public Map<String, String> signUp(String username, String password, String confirmPassword, String email) {

		userService.checkUser(username, email, password, confirmPassword);

		registrationService.registerUser(username, email, passwordEncoder.encode(password));

		logger.info("User registration: " + username);

		return signIn(username, password);

	}

	@PostMapping("/sign-in")
	@Operation(summary="Sign-in user")
	@Parameters({
		@Parameter(name="username", description="The username entered"),
		@Parameter(name="password", description="The password entered")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Ok", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=String.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public Map<String, String> signIn(String username, String password) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));	

		Instant instant = Instant.now();

		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
			.issuedAt(instant)
			.expiresAt(instant.plus(30, ChronoUnit.MINUTES))
			.subject(username)
			.claim("scope", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
			.build();

		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
			JwsHeader.with(MacAlgorithm.HS512).build(),
			jwtClaimsSet
		);

		String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

		connectionService.connectUser(username);

		logger.info("User connection: " + username);

		return Map.of("access-token", jwt);

	}

	@PostMapping("/sign-out")
	@Operation(summary="Sign-out user")
	@Parameter(name="username", description="The username of the user")
	@ApiResponse(responseCode="200", description="Ok", content={@Content(mediaType="application/json", schema=@Schema(implementation=String.class))})
	public ResponseEntity<String> signOut(String username) {

		logger.info("User disconnection: " + username);

		return ResponseEntity.noContent().build();

	}

}