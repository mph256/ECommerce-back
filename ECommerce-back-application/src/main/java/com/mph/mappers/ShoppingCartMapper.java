package com.mph.mappers;

import com.mph.entities.ShoppingCart;

import com.mph.dto.ShoppingCartDto;

public class ShoppingCartMapper {
	
	public static ShoppingCartDto convertToDto(ShoppingCart shoppingCart) {
		return ShoppingCartDto.of(shoppingCart.getId(), shoppingCart.getAmount(), ShoppingItemMapper.convertToDto(shoppingCart.getItems()));
	}

}