package com.mph.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import com.mph.services.interfaces.RoleService;

@RestController
@RequestMapping("/roles")
@Tag(name="Role", description="Role management APIs")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@GetMapping("/users/{username}")
	@Operation(summary="Get roles by username")
	@Parameter(name="username", description="The username of the user")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Roles found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=String.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<String> getRolesByUserUsername(@PathVariable String username) {
		return roleService.getRolesByUserUsername(username)
			.stream()
			.map(role -> role.getName().toString())
			.collect(Collectors.toList());
	}

	@GetMapping("")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Get all roles")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Roles found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=String.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<String> getRoles() {
		return roleService.getRoles()
			.stream()
			.map(role -> role.getName().toString())
			.collect(Collectors.toList());
	}

}