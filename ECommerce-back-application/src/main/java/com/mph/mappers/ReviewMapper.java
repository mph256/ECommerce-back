package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Review;

import com.mph.dto.ReviewDto;

public class ReviewMapper {

	public static ReviewDto convertToDto(Review review) {
		return ReviewDto.of(review.getId(), review.getContent(), review.getRating(), review.getPublicationDate(), review.getLastUpdate(),
			review.getProduct().getId(), UserMapper.convertToDto(review.getUser()));
	}
	
	public static List<ReviewDto> convertToDto(List<Review> reviews) {

		List<ReviewDto> list = new ArrayList<ReviewDto>();

		for(Review review: reviews) {
			list.add(convertToDto(review));
		}

		return list;

	}

}