package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Category;

import com.mph.dto.CategoryDto;

public class CategoryMapper {

	public static CategoryDto convertToDto(Category category) {
		return CategoryDto.of(category.getId(), category.getName(), category.getImage());
	}

	public static List<CategoryDto> convertToDto(List<Category> categories) {

		List<CategoryDto> list = new ArrayList<CategoryDto>();

		for(Category category: categories) {
			list.add(convertToDto(category));
		}

		return list;

	}

}