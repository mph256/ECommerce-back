package com.mph.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import com.mph.services.interfaces.CountryService;

import com.mph.mappers.CountryMapper;

import com.mph.dto.CountryDto;

@RestController
@RequestMapping("/countries")
@Tag(name="Country", description="Country management APIs")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@GetMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get all countries")
	@ApiResponse(responseCode="200", description="Countries found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=CountryDto.class)))})
	public List<CountryDto> getCountries() {
		return CountryMapper.convertToDto(countryService.getCountries());
	}

}