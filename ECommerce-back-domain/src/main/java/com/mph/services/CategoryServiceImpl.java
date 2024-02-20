package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.CategoryService;

import com.mph.services.exceptions.InvalidImageException;
import com.mph.services.exceptions.CategoryNotFoundException;

import com.mph.repositories.interfaces.CategoryRepository;

import com.mph.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category addCategory(String name, byte[] image) {

		checkImage(image);

		Category category = Category.of(name, image, true);

		category = categoryRepository.save(category);

		return category;

	}

	@Override
	public Category updateCategory(long categoryId, String name, byte[] image) {

		checkImage(image);

		Category category = getCategoryById(categoryId);

		if(!category.getName().equals(name))
			category.setName(name);

		if(!category.getImage().equals(image))
			category.setImage(image);

		category = categoryRepository.save(category);

		return category;

	}

	@Override
	public Category deleteCategory(long categoryId) {

		Category category = getCategoryById(categoryId);

		if(categoryRepository.isDeletable(categoryId)) {

			categoryRepository.deleteById(categoryId);

			return category;

		}

		categoryRepository.deleteById(categoryId);

		category.setIsActive(false);

		category = categoryRepository.save(category);

		return category;

	}

	@Override
	public Category getCategoryById(long categoryId) {

		Optional<Category> optionalCategory = categoryRepository.findFirstByIdAndIsActive(categoryId, true);

		if(optionalCategory.isEmpty())
			throw new CategoryNotFoundException();

		Category category = optionalCategory.get();

		return category;

	}

	@Override
	public List<Category> getCategoriesById(List<Long> categories) {
		return categoryRepository.findByIdInAndIsActiveOrderByName(categories, true);
	}

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findByIsActiveOrderByName(true);
	}

	private void checkImage(byte[] image) {

		if(image == null)
			throw new InvalidImageException();

	}

}