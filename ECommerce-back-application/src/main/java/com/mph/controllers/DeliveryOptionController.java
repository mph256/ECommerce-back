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

import com.mph.services.interfaces.DeliveryOptionService;

import com.mph.mappers.DeliveryOptionMapper;

import com.mph.dto.DeliveryOptionDto;

@RestController
@RequestMapping("/delivery-options")
@Tag(name="Delivery option", description="Delivery option management APIs")
public class DeliveryOptionController {

	@Autowired
	private DeliveryOptionService deliveryOptionService;

	@GetMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get all delivery options")
	@ApiResponse(responseCode="200", description="Delivery options found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=DeliveryOptionDto.class)))})
	public List<DeliveryOptionDto> getDeliveryOptions() {
		return DeliveryOptionMapper.convertToDto(deliveryOptionService.getDeliveryOptions());
	}

}