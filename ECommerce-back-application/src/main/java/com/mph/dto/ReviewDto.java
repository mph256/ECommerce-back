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
public class ReviewDto {

	@NonNull
	@Schema(description="Identifer of the review")
	private Long id;

	@Size(min=3, max=255)
	@NotBlank
	@NonNull
	@Schema(description="Content of the review")
	private String content;

	@Min(1)
	@Max(5)
	@NonNull
	@Schema(description="Rating of the review")
	private Integer rating;

	@NonNull
	@Schema(description="Publication date of the review")
	private Date publicationDate;

	@NonNull
	@Schema(description="Last update date of the review")
	private Date lastUpdate;

	@NonNull 
	@Schema(description="Identifer of the product")
	private Long productId;

	@NonNull
	@Schema(description="User of the review")
	private UserDto user;

}