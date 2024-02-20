package com.mph.controllers;

import java.util.List;

import java.util.Date;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

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

import com.mph.services.interfaces.PromotionService;

import com.mph.entities.Promotion;

import com.mph.mappers.PromotionMapper;

import com.mph.dto.PromotionDto;

@RestController
@RequestMapping("/promotions")
@Tag(name="Promotion", description="Promotion management APIs")
public class PromotionController {

	private static final Logger logger = LogManager.getLogger(PromotionController.class);

	@Autowired
	private PromotionService promotionService;

	@GetMapping("/{promotionCode}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get promotion by code")
	@Parameter(name="promotionCode", description="The code of the promotion to get")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Ok", content={@Content(mediaType="application/json", schema=@Schema(implementation=PromotionDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public PromotionDto getPromotionByCode(@PathVariable String promotionCode) {

		Optional<Promotion> optionalPromotion = promotionService.getPromotionByCode(promotionCode);

		return (optionalPromotion.isPresent())?PromotionMapper.convertToDto(optionalPromotion.get()):null;

	}

	@GetMapping("")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Get all promotions")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Promotions found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=PromotionDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<PromotionDto> getPromotions() {
		return PromotionMapper.convertToDto(promotionService.getPromotions());
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Add new promotion")
	@Parameters({
		@Parameter(name="code", description="The code"),
		@Parameter(name="percentage", description="The percentage"),
		@Parameter(name="expirationDate", description="The expiration date")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Promotion added", content={@Content(mediaType="application/json", schema=@Schema(implementation=Promotion.class))}),
		@ApiResponse(responseCode="400", description="Invalid expiration date", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public ResponseEntity<PromotionDto> addPromotion(String code, int percentage, Date expirationDate) {

		Promotion promotion = promotionService.addPromotion(code, percentage, expirationDate);

		logger.info("Promotion adding: " + promotion.getId());

		return new ResponseEntity<PromotionDto>(PromotionMapper.convertToDto(promotion), HttpStatus.CREATED);

	}

	@PatchMapping("/{promotionId}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Update promotion")
	@Parameters({
		@Parameter(name="promotionId", description="The identifier of the promotion to update"),
		@Parameter(name="code", description="The code"),
		@Parameter(name="percentage", description="The percentage"),
		@Parameter(name="expirationDate", description="The expiration date")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Promotion updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=Promotion.class))}),
		@ApiResponse(responseCode="400", description="Invalid expiration date", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Promotion not found", content=@Content)
	})
	public ResponseEntity<PromotionDto> updatePromotion(@PathVariable long promotionId, String code, int percentage, Date expirationDate) {

		Promotion promotion = promotionService.updatePromotion(promotionId, code, percentage, expirationDate);

		logger.info("Promotion update: " + promotionId);

		return ResponseEntity.ok(PromotionMapper.convertToDto(promotion));

	}

	@DeleteMapping("/{promotionId}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Delete promotion")
	@Parameter(name="promotionId", description="The identifier of the promotion to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Promotion deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=Promotion.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Promotion not found", content=@Content)
	})
	public ResponseEntity<PromotionDto> deletePromotion(@PathVariable long promotionId) {

		Promotion promotion = promotionService.deletePromotion(promotionId);

		logger.info("Promotion deletion: " + promotionId);

		return ResponseEntity.ok(PromotionMapper.convertToDto(promotion));

	}

}