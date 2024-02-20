package com.mph.controllers;

import java.util.List;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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

import com.mph.services.interfaces.CategoryService;

import com.mph.entities.Category;

import com.mph.mappers.CategoryMapper;

import com.mph.dto.CategoryDto;

@RestController
@RequestMapping("/categories")
@Tag(name="Category", description="Category management APIs")
public class CategoryController {

	private static final Logger logger = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	@Operation(summary="Get all categories")
	@ApiResponse(responseCode="200", description="Categories found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=CategoryDto.class)))})
	public List<CategoryDto> getCategories() {
		return CategoryMapper.convertToDto(categoryService.getCategories());
	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Add new category")
	@Parameters({
		@Parameter(name="name", description="The name"),
		@Parameter(name="file", description="The image file")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Category added", content={@Content(mediaType="application/json", schema=@Schema(implementation=CategoryDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid image", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public ResponseEntity<CategoryDto> addCategory(String name, MultipartFile file) throws IOException {

		Category category = categoryService.addCategory(name, file.getBytes());

		logger.info("Category adding: " + category.getId());

		return new ResponseEntity<>(CategoryMapper.convertToDto(category), HttpStatus.CREATED);

	}

	@PatchMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Update category")
	@Parameters({
		@Parameter(name="categoryId", description="The identifier of the category to update"),
		@Parameter(name="name", description="The name"),
		@Parameter(name="file", description="The image file")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Category updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=CategoryDto.class))}),
		@ApiResponse(responseCode="400", description="Invalid image", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Category not found", content=@Content)
	})
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable long categoryId, String name, MultipartFile file) throws IOException {

		Category category = categoryService.updateCategory(categoryId, name, file.getBytes());

		logger.info("Category update: " + categoryId);

		return ResponseEntity.ok().body(CategoryMapper.convertToDto(category));

	}

	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	@Operation(summary="Delete category")
	@Parameter(name="categoryId", description="The identifier of the category to delete")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Category deleted", content={@Content(mediaType="application/json", schema=@Schema(implementation=CategoryDto.class))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Category not found", content=@Content)
	})
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable long categoryId) {

		Category category = categoryService.deleteCategory(categoryId);

		logger.info("Category deletion: " + categoryId);

		return ResponseEntity.ok().body(CategoryMapper.convertToDto(category));

	}

}