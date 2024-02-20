package com.mph.dto;

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
public class ImageDto {

	@NonNull
	@Schema(description="Identifier of the image")
	private Long id;

	@NonNull
	@Schema(description="Byte stream of the image file")
	private byte[] file;

}