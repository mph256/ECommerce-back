package com.mph.controllers;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.multipart.MultipartFile;

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

import com.mph.services.interfaces.ProductService;
import com.mph.services.interfaces.ReviewService;

import com.mph.entities.Product;
import com.mph.entities.Image;

import com.mph.mappers.ProductMapper;

import com.mph.dto.ProductDto;

@RestController
@RequestMapping("/products")
@Tag(name="Product", description="Product management APIs")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/{productId}")
	@Operation(summary="Get product by identifier")
	@Parameter(name="productId", description="The identifier of the product to get")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Product found", content={@Content(mediaType="application/json", schema=@Schema(implementation=ProductDto.class))}),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public ProductDto getProductById(@PathVariable long productId) {

		Product product = productService.getProductById(productId);

		product.addReviews(reviewService.getReviewsByProductId(productId));

		return ProductMapper.convertToDto(product);

	}

	@GetMapping("/categories/{categoryName}")
	@Operation(summary="Get products by category name")
	@Parameter(name="categoryName", description="The name of the category")
	@ApiResponse(responseCode="200", description="Products found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ProductDto.class)))})
	public List<ProductDto> getProductsByCategoryName(@PathVariable String categoryName) {
		return ProductMapper.convertToDto(productService.getProductsByCategoryName(categoryName.replaceAll("%20", " ")));
	}

	@GetMapping("/categories/{categoryName}/name/{productName}")
	@Operation(summary="Get products by name and category name")
	@Parameters({
		@Parameter(name="categoryName", description="The name of the category"),
		@Parameter(name="productName", description="The name of the product")
	})
	@ApiResponse(responseCode="200", description="Product found", content={@Content(mediaType="application/json", schema=@Schema(implementation=ProductDto.class))})
	public List<ProductDto> getProductsByNameAndCategoryName(@PathVariable String categoryName, @PathVariable String productName) {
		return ProductMapper.convertToDto(productService.getProductsByNameAndCategoryName(productName.replaceAll("%20", " "), categoryName.replaceAll("%20", " ")));
	}

	@GetMapping("/sellers/{username}")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Get products by seller username")
	@Parameter(name="username", description="The seller's username")
	@ApiResponse(responseCode="200", description="Products found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ProductDto.class)))})
	public List<ProductDto> getProductsBySellerUsername(@PathVariable String username) {
		return ProductMapper.convertToDto(productService.getProductsBySellerUsername(username));
	}

	@GetMapping("/best-sellers")
	@Operation(summary="Get best sellers")
	@ApiResponse(responseCode="200", description="Products found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ProductDto.class)))})
	public List<ProductDto> getBestSellers() {
		return ProductMapper.convertToDto(productService.getBestSellers());
	}

	@GetMapping("/new-releases")
	@Operation(summary="Get new releases")
	@ApiResponse(responseCode="200", description="Products found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ProductDto.class)))})
	public List<ProductDto> getNewReleases() {
		return ProductMapper.convertToDto(productService.getNewReleases());
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Add new product")
	@Parameters({
		@Parameter(name="name", description="The name"),
		@Parameter(name="description", description="The description"),
		@Parameter(name="dimensions", description="The dimensions"),
		@Parameter(name="countryOfOrigin", description="The country of origin"),
		@Parameter(name="manufacturer", description="The manufacturer"),
		@Parameter(name="quantityAvailable", description="The quantity available"),
		@Parameter(name="price", description="The price"),
		@Parameter(name="username", description="The seller's username"),
		@Parameter(name="files", description="The image files"),
		@Parameter(name="categories", description="The identifiers of the categories")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Product updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=ProductDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid image", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public ResponseEntity<ProductDto> addProduct(String name, String description, String dimensions, float weight, String countryOfOrigin, String manufacturer,
		int quantityAvailable, float price, String username, @RequestParam List<MultipartFile> files, @RequestParam List<Long> categories) throws IOException {

		List<Image> images = new ArrayList<Image>();

		for(MultipartFile file: files) {
			images.add(Image.of(file.getBytes()));
		}

		Product product = productService.addProduct(name, description, dimensions, weight,
			countryOfOrigin, manufacturer, quantityAvailable, price, username, images, categories);

		logger.info("Product adding: " + product.getId());

		return new ResponseEntity<>(ProductMapper.convertToDto(product), HttpStatus.CREATED);

	}

	@PatchMapping("/{productId}")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Update product")
	@Parameters({
		@Parameter(name="productId", description="The identifier of the product to update"),
		@Parameter(name="name", description="The name"),
		@Parameter(name="description", description="The description"),
		@Parameter(name="dimensions", description="The dimensions"),
		@Parameter(name="countryOfOrigin", description="The country of origin"),
		@Parameter(name="manufacturer", description="The manufacturer"),
		@Parameter(name="quantityAvailable", description="The quantity available"),
		@Parameter(name="price", description="The price"),
		@Parameter(name="files", description="The image files"),
		@Parameter(name="categories", description="The identifiers of the categories")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Product updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=ProductDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid image", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public ResponseEntity<ProductDto> updateProduct(@PathVariable long productId,
		String name, String description, String dimensions, float weight, String countryOfOrigin, String manufacturer,
		int quantityAvailable, float price, @RequestParam List<MultipartFile> files, @RequestParam List<Long> categories) throws IOException {

		List<Image> images = new ArrayList<Image>();

		for(MultipartFile file: files) {
			images.add(Image.of(file.getBytes()));
		}

		Product product = productService.updateProduct(productId, name, description, dimensions, weight, 
			countryOfOrigin, manufacturer, quantityAvailable, price, images, categories);

		logger.info("Product update: " + productId);

		return ResponseEntity.ok(ProductMapper.convertToDto(product));

	}

	@DeleteMapping("/{productId}")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Delete product")
	@Parameter(name="productId", description="The identifier of the product to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Product deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=ProductDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Product not found", content=@Content)
	})
	public ResponseEntity<ProductDto> deleteProduct(@PathVariable long productId) {

		Product product = productService.deleteProduct(productId);

		logger.info("Product deletion: " + productId);

		return ResponseEntity.ok(ProductMapper.convertToDto(product));

	}

}