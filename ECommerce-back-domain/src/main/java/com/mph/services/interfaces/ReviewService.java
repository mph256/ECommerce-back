package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.UserNotFoundException;
import com.mph.services.exceptions.ProductNotFoundException;
import com.mph.services.exceptions.ReviewNotFoundException;

import com.mph.entities.Review;

public interface ReviewService {

	/**
	 * Creates a new review for a product.
	 * 
	 * @param content the content
	 * @param rating the rating
	 * @param username the username of the user
	 * @param productId the identifier of the product
	 * 
	 * @return the new review created
	 * 
	 * @throws UserNotFoundException if no user with this username is found
	 * @throws ProductNotFoundException if no product with this id is found
	 */
	public Review addReview(String content, int rating, String username, long productId) throws UserNotFoundException, 
		ProductNotFoundException;

	/**
	 * Updates a review.
	 * 
	 * <br>
	 * Updates the product rating if necessary.
	 * 
	 * @param reviewId the identifier of the review to update
	 * @param content the new content
	 * @param rating the new rating
	 * 
	 * @return the updated review
	 * 
	 * @throws ReviewNotFoundException if no review with this id is found
	 */
	public Review updateReview(long reviewId, String content, int rating) throws ReviewNotFoundException;

	/**
	 * Deletes a review.
	 * 
 	 * <br>
	 * Updates the product rating.
	 * 
	 * @param reviewId the identifier of the review to delete
	 * 
	 * @return the deleted review
	 * 
 	 * @throws ReviewNotFoundException if no review with this id is found
	 */
	public Review deleteReview(long reviewId) throws ReviewNotFoundException;

	public Review getReviewById(long reviewId);

	public List<Review> getReviewsByProductId(long productId);

}