package com.mph.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

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
public class OrderDto {

	@NonNull
	@Schema(description="Identifier of the order")
	private Long id;

	@Min(1)
	@NonNull
	@Schema(description="Amount of the order")
	private Float amount;

	@NonNull
	@Schema(description="Date of the order")
	private Date orderDate;

	@NonNull
	@Schema(description="Estimated delivery date of the order")
	private Date deliveryDate;

	@NonNull
	@Schema(description="Shippping address of the order")
	private AddressDto shippingAddress;

	@NonNull
	@Schema(description="Delivery option of the order")
	private DeliveryOptionDto deliveryOption;

	@NonNull
	@Schema(description="Buyer of the order")
	private UserDto user;

	@NotEmpty
	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Suborders of the order"), schema=@Schema(description="Suborder"))
	private List<SuborderDto> suborders;

}