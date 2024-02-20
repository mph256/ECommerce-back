package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Product;
import com.mph.entities.Review;

import com.mph.dto.ProductDto;
import com.mph.dto.ReviewDto;

public class ProductMapper {

	public static ProductDto convertToDto(Product product) {

		ProductDto productDto = ProductDto.of(product.getId(), product.getName(), product.getDescription(), 
			product.getCountryOfOrigin(), product.getManufacturer(),
			product.getQuantityAvailable(), product.getPrice(), product.getRating(),
			product.getCreationDate(), product.getLastUpdate(),
			UserMapper.convertToDto(product.getSeller()), 
			ImageMapper.convertToDto(product.getImages()),
			CategoryMapper.convertToDto(product.getCategories()),
			new ArrayList<ReviewDto>()
		);

		if(product.getDimensions() != null)
			productDto.setDimensions(product.getDimensions());

		if(product.getWeight() != null)
			productDto.setWeight(product.getWeight());

		List<Review> reviews = product.getReviews();

		if(reviews != null && !reviews.isEmpty())
			productDto.addReviews(ReviewMapper.convertToDto(reviews));

		return productDto;

	}

	public static List<ProductDto> convertToDto(List<Product> products) {

		List <ProductDto> list = new ArrayList<ProductDto>();

		for(Product product: products) {
			list.add(convertToDto(product));
		}

		return list;

	}

	public static Product convertToEntity(ProductDto productDto) {
		return new Product(productDto.getId());
	}

}