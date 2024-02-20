package com.mph.services;

import java.util.List;

import java.util.Date;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.ReviewService;
import com.mph.services.interfaces.UserService;
import com.mph.services.interfaces.ProductService;

import com.mph.services.exceptions.ReviewNotFoundException;

import com.mph.repositories.interfaces.ReviewRepository;

import com.mph.entities.Review;
import com.mph.entities.User;
import com.mph.entities.Product;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public Review addReview(String content, int rating, String username, long productId) {

		User user = userService.getUserByUsername(username);

		Product product = productService.getProductById(productId);

		Review review = Review.of(content, rating, new Date(), new Date(), user, product);

		review = reviewRepository.save(review);

		productService.updateProductRating(productId, reviewRepository.findByProductIdOrderByLastUpdateDesc(productId));

		return review;

	}

	@Override
	public Review updateReview(long reviewId, String content, int rating) {

		Review review = getReviewById(reviewId);

		long productId = productService.getProductByReviewId(reviewId).getId();

		boolean updateProductRating = false;

		if(!review.getContent().equals(content))
			review.setContent(content);

		if(review.getRating() != rating) {

			review.setRating(rating);

			updateProductRating = true;

		}

		review.setLastUpdate(new Date());

		review = reviewRepository.save(review);

		if(updateProductRating)
			productService.updateProductRating(productId, reviewRepository.findByProductIdOrderByLastUpdateDesc(productId));

		return review;

	}

	@Override
	public Review deleteReview(long reviewId) {

		Review review = getReviewById(reviewId);

		long productId = productService.getProductByReviewId(reviewId).getId();

		reviewRepository.deleteById(reviewId);

		productService.updateProductRating(productId, reviewRepository.findByProductIdOrderByLastUpdateDesc(productId));

		return review;

	}

	@Override
	public Review getReviewById(long reviewId) {

		Optional<Review> optionalReview = reviewRepository.findById(reviewId);

		if(optionalReview.isEmpty())
			throw new ReviewNotFoundException();

		Review review = optionalReview.get();

		return review;

	}

	@Override
	public List<Review> getReviewsByProductId(long productId) {
		return reviewRepository.findByProductIdOrderByLastUpdateDesc(productId);
	}

}