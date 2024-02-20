package com.mph.dto;

import java.util.List;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
public class ProductDto {

	@NonNull
	@Schema(description="Identifer of the product")
	private Long id;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Name of the product")
	private String name;

	@Size(min=3)
	@NotBlank
	@NonNull
	@Schema(description="Description of the product")
	private String description;

	@Schema(description="Dimensions of the product (optional)")
	private String dimensions;

	@Schema(description="Weight of the product (optional)")
	private Float weight;

	@Size(min=3, max=52)
	@NotBlank
	@NonNull
	@Schema(description="Country of origin of the product")
	private String countryOfOrigin;

	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	@Schema(description="Manufacturer of the product")
	private String manufacturer;

	@Min(0)
	@NonNull
	@Schema(description="Quantity available of the product")
	private Integer quantityAvailable;

	@Min(1)
	@NonNull
	@Schema(description="Price of the product")
	private Float price;

	@Min(0)
	@Max(5)
	@NonNull
	@Schema(description="Rating of the product")
	private Float rating;

	@NonNull
	@Schema(description="Creation date of the product")
	private Date creationDate;

	@NonNull
	@Schema(description="Last update date of the product")
	private Date lastUpdate;

	@NonNull
	@Schema(description="Seller of the product")
	private UserDto seller;

	@NotEmpty
	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Images of the product"), schema=@Schema(description="Image"))
	private List<ImageDto> images;

	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Categories of the product"), schema=@Schema(description="Category"))
	private List<CategoryDto> categories;

	@NonNull
	@ArraySchema(uniqueItems=true, arraySchema=@Schema(description="Reviews of the product"), schema=@Schema(description="Review"))
	private List<ReviewDto> reviews;

	public ProductDto(long id) {
		this.id = id;
	}

	public void addImage(ImageDto image) {
		images.add(image);
	}

	public void removeImage(ImageDto image) {
		images.remove(image);
	}

	public void addReviews(List<ReviewDto> reviews) {
		this.reviews.addAll(reviews);
	}

}