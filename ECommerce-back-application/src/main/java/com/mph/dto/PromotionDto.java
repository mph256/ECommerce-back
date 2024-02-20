package com.mph.dto;

import java.util.Date;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

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
public class PromotionDto {

	@NonNull
	@Schema(description="Identifer of the promotion")
	private Long id;

	@Size(min=3, max=20)
	@NotBlank
	@NonNull
	@Schema(description="Code of the promotion")
	private String code;

	@Min(5)
	@Max(50)
	@NonNull
	@Schema(description="Percentage of the promotion")
	private Integer percentage;

	@NonNull
	@Schema(description="Expiration date of the promotion")
	private Date expirationDate;

}