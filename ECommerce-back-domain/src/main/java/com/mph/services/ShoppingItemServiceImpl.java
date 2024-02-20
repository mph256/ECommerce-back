package com.mph.services;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.ShoppingItemService;

import com.mph.repositories.interfaces.ShoppingItemRepository;

import com.mph.entities.ShoppingItem;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService {

	@Autowired
	private ShoppingItemRepository itemRepository;

	@Override
	public ShoppingItem deleteItem(ShoppingItem item) {

		itemRepository.deleteById(item.getId());

		return item;

	}

	@Override
	public List<ShoppingItem> getItemsBySuborderId(long suborderId) {
		return itemRepository.findBySuborderId(suborderId);
	}

}