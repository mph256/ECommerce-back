package com.mph.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class AddressDto {

	@NonNull
	@Schema(description="Identifier of the address")
	private Long id;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Name of the address")
	private String name;

	@Size(min=3, max=255)
	@NotBlank
	@NonNull
	@Schema(description="Street of the address")
	private String street;

	@Size(min=3, max=139)
	@NotBlank
	@NonNull
	@Schema(description="City of the address")
	private String city;

	@NonNull
	@Schema(description="Zipcode of the address")
	private Integer zipcode;

	@NonNull
	@Schema(description="If the address is the user's default shipping address")
	private Boolean isDefaultShipping;

	@NonNull
	@Schema(description="If the address is the user's default billing address")
	private Boolean isDefaultBilling;

	@NonNull
	@Schema(description="Country of the address")
	private CountryDto country;

}