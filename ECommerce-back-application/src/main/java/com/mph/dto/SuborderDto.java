package com.mph.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

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
public class SuborderDto {

	@NonNull
	@Schema(description="Identifier of the suborder")
	private Long id;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Status of the suborder")
	private String status;
	
	@NonNull 
	@Schema(description="Identifer of the order")
	private Long orderId;

	@NotEmpty
	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Items of the suborder"), schema=@Schema(description="Item"))
	private List<ShoppingItemDto> items;

}