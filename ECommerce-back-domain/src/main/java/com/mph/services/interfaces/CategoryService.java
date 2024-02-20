package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.InvalidImageException;
import com.mph.services.exceptions.CategoryNotFoundException;

import com.mph.entities.Category;

public interface CategoryService {

	/**
	 * Creates a new category.
	 * 
	 * @param name the name
	 * @param image the image
	 * 
	 * @return the new category created
	 * 
	 * @throws InvalidImageException if no image has been added
	 */
	public Category addCategory(String name, byte[] image) throws InvalidImageException;

	/**
	 * Updates a category.
	 * 
	 * @param categoryId the identifier of the category to update
	 * @param name the new name
	 * @param image the new image
	 * 
	 * @return the updated category
	 * 
	 * @throws InvalidImageException if no image has been added
 	 * @throws CategoryNotFoundException if no category with this id is found
	 */
	public Category updateCategory(long categoryId, String name, byte[] image) throws InvalidImageException, CategoryNotFoundException;

	/**
	 * Deletes a category.
	 * 
 	 * <br>
	 * The category is only deleted if it is not linked to any product. 
	 * Otherwise it will no longer appear except for products already linked to it.
	 * It will not be permanently deleted from the database, but only set as non-active.
	 * 
	 * @param categoryId the identifier of the category to delete
	 * 
	 * @return the deleted category
	 * 
	 * @throws CategoryNotFoundException if no category with this id is found
	 */
	public Category deleteCategory(long categoryId) throws CategoryNotFoundException;

	public Category getCategoryById(long categoryId);

	public List<Category> getCategoriesById(List<Long> categories);

	public List<Category> getCategories();

}