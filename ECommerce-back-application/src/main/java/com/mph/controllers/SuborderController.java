package com.mph.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mph.services.interfaces.SuborderService;

import com.mph.entities.Suborder;

import com.mph.mappers.SuborderMapper;

import com.mph.dto.SuborderDto;

@RestController
@RequestMapping("/suborders")
@Tag(name="Suborder", description="Suborder management APIs")
public class SuborderController {

	private static final Logger logger = LogManager.getLogger(SuborderController.class);

	@Autowired
	private SuborderService suborderService;

	@PatchMapping("/{suborderId}/ship")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Ship suborder")
	@Parameter(name="suborderId", description="The identifier of the suborder to ship")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Suborder shipped", content={@Content(mediaType="application/json", schema=@Schema(implementation=SuborderDto.class))}),
		@ApiResponse(responseCode="400", description="Non-shippable suborder", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Suborder not found", content=@Content)
	})
	public ResponseEntity<SuborderDto> shipSuborder(@PathVariable long suborderId) {

		Suborder suborder = suborderService.shipSuborder(suborderId);

		logger.info("Suborder shipping: " + suborderId);

		return ResponseEntity.ok().body(SuborderMapper.convertToDto(suborder));

	}

}