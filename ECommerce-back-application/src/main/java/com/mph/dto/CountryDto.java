package com.mph.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
public class CountryDto {

	@NonNull
	@Schema(description="Identifier of the country")
	private Long id;

	@Size(min=3, max=52)
	@NotBlank
	@NonNull
	@Schema(description="Name of the country")
	private String name;

	@Min(0)
	@Max(100)
	@NonNull
	@Schema(description="VAT (Value-Added Tax) of the country")
	private Float VAT;

}