package com.mph.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;

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

import com.mph.services.interfaces.UserService;

import com.mph.entities.User;

import com.mph.mappers.UserMapper;

import com.mph.dto.UserDto;

@RestController
@RequestMapping("/users")
@Tag(name="User", description="User management APIs")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping("/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get user by username")
	@Parameter(name="username", description="The username of the user to get")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User found", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content)
	})
	public UserDto getUserByUsername(@PathVariable String username) {
		return UserMapper.convertToDto(userService.getUserByUsername(username));
	}

	@GetMapping("")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Get all users")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Users found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=UserDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<UserDto> getUsers() {
		return UserMapper.convertToDto(userService.getUsers());
	}

	@PatchMapping("/{username}/firstname")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user first name")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="firstname", description="The first name")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content)
	})
	public ResponseEntity<UserDto> updateUserFirstname(@PathVariable String username, String firstname) {

		User user = userService.updateUserFirstname(username, firstname);

		logger.info("User first name update: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/lastname")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user last name")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="lastname", description="The last name")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content)
	})
	public ResponseEntity<UserDto> updateUserLastname(@PathVariable String username, String lastname) {

		User user = userService.updateUserLastname(username, lastname);

		logger.info("User last name update: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/email")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user email")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="email", description="The email")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid email format", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content),
		@ApiResponse(responseCode="409", description="Email already in use", content=@Content)
	})
	public ResponseEntity<UserDto> updateUserEmail(@PathVariable String username, String email) {

		User user = userService.updateUserEmail(username, email);

		logger.info("User email update: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/phone")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user phone")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="phone", description="The phone")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid phone format", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content),
		@ApiResponse(responseCode="409", description="Phone already in use", content=@Content)
	})
	public ResponseEntity<UserDto> updateUserPhone(@PathVariable String username, String phone) {

		User user = userService.updateUserPhone(username, phone);

		logger.info("User phone update: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/password")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user password")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="password", description="The password"),
		@Parameter(name="confirmPassword", description="The password confirmation")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="400", description="Passwords do not match", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User not found", content=@Content)
	})
	public ResponseEntity<UserDto> updateUserPassword(@PathVariable String username, String password, String confirmPassword) {

		User user = userService.updateUserPassword(username, password, confirmPassword, passwordEncoder.encode(password));

		logger.info("User password update: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/roles/add")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Add role to user")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="role", description="The name of the role to add")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User or role not found", content=@Content)
	})
	public ResponseEntity<UserDto> addUserRole(@PathVariable String username, String role) {

		User user = userService.addUserRole(username, role);

		logger.info("User role adding: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

	@PatchMapping("/{username}/roles/remove")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Remove role to user")
	@Parameters({
		@Parameter(name="username", description="The username of the user to update"),
		@Parameter(name="role", description="The name of the role to remove")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="User updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="User or role not found", content=@Content)
	})
	public ResponseEntity<UserDto> removeUserRole(@PathVariable String username, String role) {

		User user = userService.removeUserRole(username, role);

		logger.info("User role deletion: " + username);

		return ResponseEntity.ok().body(UserMapper.convertToDto(user));

	}

}