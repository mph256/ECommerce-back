package com.mph.services.interfaces;

import java.util.List;

import com.mph.entities.ShoppingItem;

public interface ShoppingItemService {

	/**
	 * Deletes a shopping item from the database.
	 * 
	 * @param item the item to delete
	 * 
	 * @return the deleted item
	 */
	public ShoppingItem deleteItem(ShoppingItem item);

	public List<ShoppingItem> getItemsBySuborderId(long suborderId);

}