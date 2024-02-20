package com.mph.dto;

import jakarta.validation.constraints.Min;

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
public class ShoppingItemDto {

	@NonNull
	@Schema(description="Identifer of the item")
	private Long id;

	@Min(1)
	@NonNull
	@Schema(description="Quantity of the item")
	private Integer quantity;

	@NonNull
	@Schema(description="If the item is a gift or not")
	private Boolean isGift;

	@Min(1)
	@NonNull
	@Schema(description="Price of the item")
	private Float price;

	@NonNull
	@Schema(description="Product of the item")
	private ProductDto product;
	
	public ShoppingItemDto(int quantity, float price, boolean isGift, ProductDto product) {

		this.quantity = quantity;
		this.price = price;
		this.isGift = isGift;
		this.product = product;

	}

}