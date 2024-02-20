package com.mph.dto;

import java.util.List;

import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public class ShoppingCartDto {

	@NonNull
	@Schema(description="Identifer of the cart")
	private Long id;

	@Min(0)
	@NonNull
	@Schema(description="Amount of the cart")
	private Float amount;

	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Items of the cart"), schema=@Schema(description="Item"))
	private List<ShoppingItemDto> items;

}