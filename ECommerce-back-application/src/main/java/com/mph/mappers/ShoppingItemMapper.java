package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.ShoppingItem;

import com.mph.dto.ShoppingItemDto;

public class ShoppingItemMapper {

	public static ShoppingItemDto convertToDto(ShoppingItem shoppingItem) {
		return ShoppingItemDto.of(shoppingItem.getId(), shoppingItem.getQuantity(), shoppingItem.getIsGift(), shoppingItem.getPrice(),
			 ProductMapper.convertToDto(shoppingItem.getProduct()));
	}

	public static List<ShoppingItemDto> convertToDto(List<ShoppingItem> shoppingItems) {

		List<ShoppingItemDto> list = new ArrayList<ShoppingItemDto>();

		for(ShoppingItem shoppingItem: shoppingItems) {
			list.add(convertToDto(shoppingItem));
		}

		return list;

	}

	public static ShoppingItem convertToEntity(ShoppingItemDto shoppingItemDto) {
		return ShoppingItem.of(shoppingItemDto.getQuantity(), shoppingItemDto.getIsGift(),
			shoppingItemDto.getPrice(), ProductMapper.convertToEntity(shoppingItemDto.getProduct()));
	}

	public static List<ShoppingItem> convertToEntity(List<ShoppingItemDto> shoppingItemsDto) {

		List<ShoppingItem> list = new ArrayList<ShoppingItem>();

		for(ShoppingItemDto shoppingItemDto: shoppingItemsDto) {
			list.add(convertToEntity(shoppingItemDto));
		}

		return list;

	}

}