package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.InvalidWeightException;
import com.mph.services.exceptions.InvalidImageException;
import com.mph.services.exceptions.UserNotFoundException;
import com.mph.services.exceptions.ProductNotFoundException;

import com.mph.entities.Product;
import com.mph.entities.Image;
import com.mph.entities.Review;

public interface ProductService {

	/**
	 * Creates a new product.
	 * 
	 * @param name the name
	 * @param description the description
	 * @param dimensions the dimensions (optional, "N/A" if not specified)
	 * @param weight the weight (optional, 0 if not specified)
	 * @param countryOfOrigin the country of origin
	 * @param manufacturer the manufacturer
	 * @param quantityAvailable the quantity available
	 * @param price the price
	 * @param sellerUsername the seller's username
	 * @param images the images
	 * @param categories the categories
	 * 
	 * @return the new product created
	 * 
	 * @throws InvalidWeightException if the weight is less than 0
	 * @throws InvalidImageException if no image has been added
	 * @throws UserNotFoundException if no seller with this username is found
	 */
	public Product addProduct(String name, String description, String dimensions, float weight, String countryOfOrigin, String manufacturer,
		int quantityAvailable, float price, String username, List<Image> images, List<Long> categories) throws InvalidWeightException, 
		InvalidImageException, UserNotFoundException;

	/**
	 * Updates a product.
	 * 
	 * @param producId the identifier of the product to update
	 * @param name the new name
	 * @param description the new description
	 * @param dimensions the new dimensions (optional, "N/A" if not specified)
	 * @param weight the new weight (optional, 0 if not specified)
	 * @param countryOfOrigin the new country of origin
	 * @param manufacturer the new manufacturer
	 * @param quantityAvailable the new quantity available
	 * @param price the new price
	 * @param images the new images
	 * @param categories the identifiers of the new categories
	 * 
	 * @return the updated product
	 * 
	 * @throws InvalidWeightException if the weight is less than 0
	 * @throws InvalidImageException if no image has been added
 	 * @throws ProductNotFoundException if no product with this id is found
	 */
	public Product updateProduct(long producId, String name, String description, String dimensions, float weight, String countryOfOrigin, String manufacturer, 
		int quantityAvailable, float price, List<Image> images, List<Long> categories) throws InvalidWeightException, 
		InvalidImageException, ProductNotFoundException;

	/**
	 * Updates the rating of a product.
	 * 
	 * @param productId the identifier of the product to update
	 * @param reviews the reviews to calculate the new product rating
	 * 
	 * @return the updated product
	 * 
 	 * @throws ProductNotFoundException if no product with this id is found
	 */
	public Product updateProductRating(long productId, List<Review> reviews) throws ProductNotFoundException;

	/**
	 * Updates the quantity available of a product.
	 * 
	 * @param productId the identifier of the product to update
	 * @param quantityAvailable the new quantity available
	 * 
	 * @return the updated product
	 * 
 	 * @throws ProductNotFoundException if no product with this id is found
	 */
	public Product updateProductQuantityAvailable(long productId, int quantityAvailable) throws ProductNotFoundException;

	/**
	 * Deletes a product.
	 * 
	 * <br>
	 * The product is only deleted if it is not linked to any order or shopping cart.
	 * Otherwise it will no longer appear except for orders or shopping carts already linked to it.
	 * It will not be permanently deleted from the database, but only set as non-active.
	 * 
	 * @param productId the identifier of the product to delete
	 * 
	 * @return the deleted product
	 * 
	 * @throws ProductNotFoundException if no product with this id is found
	 */
	public Product deleteProduct(long productId) throws ProductNotFoundException;

	public Product getProductById(long productId);

	public Product getProductByReviewId(long reviewId);

	public List<Product> getProductsByCategoryName(String categoryName);

	public List<Product> getProductsByNameAndCategoryName(String productName, String categoryName);

	public List<Product> getProductsBySellerUsername(String username);

	public List<Product> getBestSellers();

	public List<Product> getNewReleases();

}