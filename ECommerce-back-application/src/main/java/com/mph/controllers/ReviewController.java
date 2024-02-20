package com.mph.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mph.services.interfaces.ReviewService;

import com.mph.entities.Review;

import com.mph.mappers.ReviewMapper;

import com.mph.dto.ReviewDto;

@RestController
@RequestMapping("/reviews")
@Tag(name="Review", description="Review management APIs")
public class ReviewController {

	private static final Logger logger = LogManager.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/products/{productId}")
	@Operation(summary="Get reviews by product identifier")
	@Parameter(name="productId", description="The identifier of the product")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Reviews found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ReviewDto.class)))}),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public List<ReviewDto> getReviewsByProductId(@PathVariable long productId) {
		return ReviewMapper.convertToDto(reviewService.getReviewsByProductId(productId));
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new review")
	@Parameters({
		@Parameter(name="content", description="The content"),
		@Parameter(name="rating", description="The rating"),
		@Parameter(name="productId", description="The identifier of the product")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Review added", content={@Content(mediaType="application/json", schema=@Schema(implementation=ReviewDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public ResponseEntity<ReviewDto> addReview(String content, int rating, String username, long productId) {

		Review review = reviewService.addReview(content, rating, username, productId);

		logger.info("Review adding: " + review.getId());

		return new ResponseEntity<ReviewDto>(ReviewMapper.convertToDto(review), HttpStatus.CREATED);

	}

	@PatchMapping("/{reviewId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update review")
	@Parameters({
		@Parameter(name="reviewId", description="The identifier of the review to update"),
		@Parameter(name="content", description="The content"),
		@Parameter(name="rating", description="The rating")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Review updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=ReviewDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Review not found", content=@Content)
	})
	public ResponseEntity<ReviewDto> updateReview(@PathVariable long reviewId, String content, int rating) {

		Review review = reviewService.updateReview(reviewId, content, rating);

		logger.info("Review update: " + reviewId);

		return ResponseEntity.ok(ReviewMapper.convertToDto(review));

	}

	@DeleteMapping("/{reviewId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Delete review")
	@Parameter(name="reviewId", description="The identifier of the review to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Review deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=ReviewDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Review not found", content=@Content)
	})
	public ResponseEntity<ReviewDto> deleteReview(@PathVariable long reviewId) {

		Review review = reviewService.deleteReview(reviewId);

		logger.info("Review deletion: " + reviewId);

		return ResponseEntity.ok(ReviewMapper.convertToDto(review));

	}

}