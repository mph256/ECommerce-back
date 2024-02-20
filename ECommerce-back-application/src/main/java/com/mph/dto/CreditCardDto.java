package com.mph.dto;

import java.util.Date;

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
public class CreditCardDto {

	@NonNull
	@Schema(description="Identifier of the credit card")
	private Long id;

	@NonNull
	@Schema(description="Number of the credit card")
	private Long number;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Holder name of the credit card")
	private String holderName;

	@NonNull
	@Schema(description="Expiration date of the credit card")
	private Date expirationDate;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Type of the credit card")
	private String type;

	@NonNull
	@Schema(description="If the credit card is the user's default credit card")
	private Boolean isDefault;

}