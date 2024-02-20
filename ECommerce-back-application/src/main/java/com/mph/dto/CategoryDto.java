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
public class CategoryDto {

	@NonNull
	@Schema(description="Identifier of the category")
	private Long id;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Name of the category")
	private String name;

	@NonNull
	@Schema(description="Byte stream of the image file")
	private byte[] image;

}