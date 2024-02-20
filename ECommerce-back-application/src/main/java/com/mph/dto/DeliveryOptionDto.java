package com.mph.dto;

import jakarta.validation.constraints.Min;
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
public class DeliveryOptionDto {

	@NonNull
	@Schema(description="Identifier of the delivery option")
	private Long id;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Name of the delivery option")
	private String name;

	@Min(1)
	@NonNull
	@Schema(description="Price of the delivery option")
	private Float price;

	@Min(1)
	@NonNull
	@Schema(description="Number of days of the delivery option")
	private Integer time;

}