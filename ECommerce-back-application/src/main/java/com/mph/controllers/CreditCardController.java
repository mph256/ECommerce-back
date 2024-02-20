package com.mph.controllers;

import java.util.List;

import java.util.Date;

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

import com.mph.services.interfaces.CreditCardService;

import com.mph.entities.CreditCard;

import com.mph.mappers.CreditCardMapper;

import com.mph.dto.CreditCardDto;

@RestController
@RequestMapping("/credit-cards")
@Tag(name="Credit card", description="Credit card management APIs")
public class CreditCardController {

	private static final Logger logger = LogManager.getLogger(CreditCardController.class);

	@Autowired
	private CreditCardService creditCardService;

	@GetMapping("/{creditCardId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get credit card by identifier")
	@Parameter(name="creditCardId", description="The identifier of the credit card to get")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Credit card found", content={@Content(mediaType="application/json", schema=@Schema(implementation=CreditCardDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Credit card not found", content=@Content)
	})
	public CreditCardDto getCreditCardById(@PathVariable long creditCardId) {
		return CreditCardMapper.convertToDto(creditCardService.getCreditCardById(creditCardId));
	}

	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get credit cards by username")
	@Parameter(name="username", description="The username of the user")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Credit cards found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=CreditCardDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<CreditCardDto> getCreditCardsByUserUsername(@PathVariable String username) {
		return CreditCardMapper.convertToDto(creditCardService.getCreditCardsByUserUsername(username));
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new credit card")
	@Parameters({
		@Parameter(name="number", description="The number"),
		@Parameter(name="holderName", description="The name of the holder"),
		@Parameter(name="expirationDate", description="The expiration date"),
		@Parameter(name="CVC", description="The card verification code"),
		@Parameter(name="username", description="The username of the user")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Credit card added", content={@Content(mediaType="application/json", schema=@Schema(implementation=CreditCardDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid credit card number or CVC format, or expired credit card", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Credit card type or user not found", content=@Content)
	})
	public ResponseEntity<CreditCardDto> addCreditCard(long number, String holderName, Date expirationDate, int CVC, String username) {

		CreditCard creditCard = creditCardService.addCreditCard(number, holderName, expirationDate, CVC, username);

		logger.info("Credit card adding: " + creditCard.getId());

		return new ResponseEntity<CreditCardDto>(CreditCardMapper.convertToDto(creditCard), HttpStatus.CREATED);

	}

	@PatchMapping("/{creditCardId}/default")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user's default credit card")
	@Parameter(name="creditCardId", description="The identifier of the credit card to set as default")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Credit card updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=CreditCardDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Credit card not found", content=@Content)
	})
	public ResponseEntity<CreditCardDto> updateUserDefaultCreditCard(@PathVariable long creditCardId) {

		CreditCard creditCard = creditCardService.updateUserDefaultCreditCard(creditCardId);

		logger.info("Credit card update: " + creditCard.getId());

		return ResponseEntity.ok().body(CreditCardMapper.convertToDto(creditCard));

	}

	@DeleteMapping("/{creditCardId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Delete credit card")
	@Parameter(name="creditCardId", description="The identifier of the credit card to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Credit card deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=CreditCardDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Credit card not found", content=@Content)
	})
	public ResponseEntity<CreditCardDto> deleteCreditCard(@PathVariable long creditCardId) {

		CreditCard creditCard = creditCardService.deleteCreditCard(creditCardId);

		logger.info("Credit card deletion: " + creditCardId);

		return ResponseEntity.ok().body(CreditCardMapper.convertToDto(creditCard));

	}

}