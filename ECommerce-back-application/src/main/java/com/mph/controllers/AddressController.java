package com.mph.controllers;

import java.util.List;

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

import com.mph.services.interfaces.AddressService;

import com.mph.entities.Address;

import com.mph.mappers.AddressMapper;

import com.mph.dto.AddressDto;

@RestController
@RequestMapping("/addresses")
@Tag(name="Address", description="Address management APIs")
public class AddressController {

	private static final Logger logger = LogManager.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;

	@GetMapping("/{addressId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get address by identifier")
	@Parameter(name="addressId", description="The identifier of the address to get")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address found", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Address not found", content=@Content)
	})
	public AddressDto getAddressById(@PathVariable long addressId) {
		return AddressMapper.convertToDto(addressService.getAddressById(addressId));
	}

	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get addresses by username")
	@Parameter(name="username", description="The username of the user")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Addresses found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=AddressDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
	})
	public List<AddressDto> getAddressesByUserUsername(@PathVariable String username) {
		return AddressMapper.convertToDto(addressService.getAddressesByUserUsername(username));
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new address")
	@Parameters({
		@Parameter(name="name", description="The name"),
		@Parameter(name="street", description="The street"),
		@Parameter(name="city", description="The city"),
		@Parameter(name="zipcode", description="The zipcode"),
		@Parameter(name="countryId", description="The identifier of the country"),
		@Parameter(name="username", description="The username of the user")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address added", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid zipcode format", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Country or user not found", content=@Content)
	})
	public ResponseEntity<AddressDto> addAddress(String name, String street, String city, int zipcode, 
		long countryId, String username) {

		Address address = addressService.addAddress(name, street, city, zipcode, countryId, username);

		logger.info("Address adding: " + address.getId());

		return new ResponseEntity<AddressDto>(AddressMapper.convertToDto(address), HttpStatus.CREATED);

	}

	@PatchMapping("/{addressId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update address")
	@Parameters({
		@Parameter(name="addressId", description="The identifier of the address to update"),
		@Parameter(name="name", description="The name"),
		@Parameter(name="street", description="The street"),
		@Parameter(name="city", description="The city"),
		@Parameter(name="zipcode", description="The zipcode"),
		@Parameter(name="countryId", description="The identifier of the country"),
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Address or country not found", content=@Content)
	})
	public ResponseEntity<AddressDto> updateAddress(@PathVariable long addressId, String name, String street, String city, int zipcode, long countryId) {

		Address address = addressService.updateAddress(addressId, name, street, city, zipcode, countryId);

		logger.info("Address update: " + addressId);

		return ResponseEntity.ok().body(AddressMapper.convertToDto(address));

	}

	@PatchMapping("/{addressId}/shipping")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user's default shipping address")
	@Parameter(name="addressId", description="The identifier of the shipping address to set as default")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Address not found", content=@Content)
	})
	public ResponseEntity<AddressDto> updateUserDefaultShippingAddress(@PathVariable long addressId) {

		Address address = addressService.updateUserDefaultShippingAddress(addressId);

		logger.info("Shipping address update: " + addressId);

		return ResponseEntity.ok().body(AddressMapper.convertToDto(address));

	}

	@PatchMapping("/{addressId}/billing")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update user's default billing address")
	@Parameter(name="addressId", description="The identifier of the billing address to set as default")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Address not found", content=@Content)
	})
	public ResponseEntity<AddressDto> updateUserDefaultBillingAddress(@PathVariable long addressId) {

		Address address = addressService.updateUserDefaultBillingAddress(addressId);

		logger.info("Billing address update: " + addressId);

		return ResponseEntity.ok().body(AddressMapper.convertToDto(address));

	}

	@DeleteMapping("/{addressId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Delete address")
	@Parameter(name="addressId", description="The identifier of the address to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Address deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=AddressDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Address not found", content=@Content)
	})
	public ResponseEntity<AddressDto> deleteAddress(@PathVariable long addressId) {

		Address address = addressService.deleteAddress(addressId);

		logger.info("Address deletion: " + addressId);

		return ResponseEntity.ok().body(AddressMapper.convertToDto(address));

	}

}