package com.mph.services;

import java.util.List;
import java.util.ArrayList;

import java.util.Date;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.ProductService;
import com.mph.services.interfaces.CategoryService;
import com.mph.services.interfaces.UserService;

import com.mph.services.exceptions.InvalidWeightException;
import com.mph.services.exceptions.InvalidImageException;
import com.mph.services.exceptions.ProductNotFoundException;

import com.mph.repositories.interfaces.ProductRepository;

import com.mph.entities.Product;
import com.mph.entities.User;
import com.mph.entities.Image;
import com.mph.entities.Review;
import com.mph.entities.Category;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	public ProductRepository productRepository;

	@Override
	public Product addProduct(String name, String description, String dimensions, float weight,
		String countryOfOrigin, String manufacturer, int quantityAvailable, float price, String username,
		List<Image> images, List<Long> categories) {

		checkImages(images);

		User seller = userService.getUserByUsername(username);

		Product product = Product.of(name, description,
			countryOfOrigin, manufacturer, quantityAvailable, price, 0F, new Date(), new Date(), true,
			seller, new ArrayList<Image>(), categoryService.getCategoriesById(categories));

		if(!"N/A".equals(dimensions))
			product.setDimensions(dimensions);

		if(weight != 0) {

			checkWeight(weight);

			product.setWeight(weight);

		}

		for(Image image: images) {

			image.setProduct(product);
			product.addImage(image);

		}

		product = productRepository.save(product);

		return product;

	}

	@Override
	public Product updateProduct(long productId, String name, String description, String dimensions, float weight,
		String countryOfOrigin, String manufacturer, int quantityAvailable, float price, 
		List<Image> images, List<Long> categories) {

		Product product = getProductById(productId);

		if(!product.getName().equals(name))
			product.setName(name);

		if(!product.getDescription().equals(description))
			product.setDescription(description);

		if(!"N/A".equals(dimensions) && !product.getDimensions().equals(dimensions))
			product.setDimensions(dimensions);

		if(weight != 0 && weight != product.getWeight()) {

			checkWeight(weight);

			product.setWeight(weight);

		}

		if(!product.getCountryOfOrigin().equals(countryOfOrigin))
			product.setCountryOfOrigin(countryOfOrigin);

		if(!product.getManufacturer().equals(manufacturer))
			product.setManufacturer(manufacturer);

		if(product.getQuantityAvailable() != quantityAvailable)
			product.setQuantityAvailable(quantityAvailable);

		if(product.getPrice() != price)
			product.setPrice(price);

		if(!product.getImages().equals(images)) {

			checkImages(images);

			product.clearImages();

			for(Image image: images) {

				image.setProduct(product);
				product.addImage(image);

			}

		}

		List<Category> tmp = categoryService.getCategoriesById(categories);

		if(tmp.equals(product.getCategories()))
			product.setCategories(tmp);

		product = productRepository.save(product);

		return product;

	}

	@Override
	public Product updateProductRating(long productId, List<Review> reviews) {

		Product product = getProductById(productId);

		float rating = 0;

		for(Review review: reviews) {
			rating += review.getRating();
		}

		if(!reviews.isEmpty())
			rating /= reviews.size();

		product.setRating(rating);

		product = productRepository.save(product);

		return product;

	}

	@Override
	public Product updateProductQuantityAvailable(long productId, int quantityAvailable) {

		Product product = getProductById(productId);

		product.setQuantityAvailable(quantityAvailable);

		product = productRepository.save(product);

		return product;

	}

	@Override
	public Product deleteProduct(long productId) {

		Product product = getProductById(productId);

		if(productRepository.isDeletable(productId)) {

			productRepository.deleteById(productId);

			return product;

		}

		product.setIsActive(false);

		product = productRepository.save(product);

		return product;

	}

	@Override
	public Product getProductById(long productId) {

		Optional<Product> optionalProduct = productRepository.findById(productId);

		if(optionalProduct.isEmpty())
			throw new ProductNotFoundException();

		Product product = optionalProduct.get();

		return product;

	}

	@Override
	public Product getProductByReviewId(long reviewId) {

		Optional<Product> optionalProduct = productRepository.findFirstByIsActiveAndReviewsId(true, reviewId);

		if(optionalProduct.isEmpty())
			throw new ProductNotFoundException();

		Product product = optionalProduct.get();

		return product;

	}

	@Override
	public List<Product> getProductsByCategoryName(String categoryName) {
		return productRepository.findByIsActiveAndCategoriesNameIgnoreCaseOrderByName(true, categoryName);
	}

	@Override
	public List<Product> getProductsByNameAndCategoryName(String productName, String categoryName) {
		return productRepository.findByNameContainingIgnoreCaseAndIsActiveAndCategoriesNameIgnoreCaseOrderByName(productName, true, categoryName);
	}

	@Override
	public List<Product> getProductsBySellerUsername(String username) {
		return productRepository.findByIsActiveAndSellerUsernameOrderByName(true, username);
	}

	@Override
	public List<Product> getBestSellers() {
		return productRepository.findBestSellers();
	}

	@Override
	public List<Product> getNewReleases() {
		return productRepository.findFirst10ByIsActiveOrderByLastUpdateDesc(true);
	}

	private void checkWeight(float weight) {

		if(weight < 0)
			throw new InvalidWeightException();

	}

	private void checkImages(List<Image> images) {

		if(images.isEmpty())
			throw new InvalidImageException();

	}

}