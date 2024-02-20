package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.ShoppingCartNotFoundException;
import com.mph.services.exceptions.InsufficientQuantityAvailableException;
import com.mph.services.exceptions.ProductNotFoundException;

import com.mph.entities.ShoppingCart;
import com.mph.entities.ShoppingItem;
import com.mph.entities.User;

public interface ShoppingCartService {

	/**
	 * Creates a new shopping cart for a user.
	 * 
	 * @param user the user
	 * 
	 * @return the new cart created
	 */
	public ShoppingCart addCart(User user);

	/**
	 * Empties a shopping cart and adds a list of shopping items to it.
	 * 
	 * <br>
	 * Updates its amount.
	 * 
	 * @param username the username of the user
	 * @param items the items to add
	 * 
	 * @return the updated cart
	 * 
 	 * @throws ShoppingCartNotFoundException if no shopping cart with this user username is found
 	 * @throws InsufficientQuantityAvailableException if the available quantity of a product is less than the quantity requested
	 */
	public ShoppingCart updateCart(String username, List<ShoppingItem> items) throws ShoppingCartNotFoundException,
		InsufficientQuantityAvailableException;

	/**
	 * Empties a shopping cart.
	 * 
	 * <br>
	 * Updates its amount.
	 * 
	 * @param cart the cart to update
	 * 
	 * @return the updated cart
	 */
	public ShoppingCart resetCart(ShoppingCart cart);

	/**
	 * Adds a shopping item to a shopping cart.
	 * 
 	 * <br>
	 * Updates its amount.
	 *
	 * @param cartId the identifier of the cart
	 * @param quantity the quantity
	 * @param isGift if it's a gift or not
	 * @param productId the identifier of the product to add
	 * 
	 * @return the updated cart
	 * 
	 * @throws ShoppingCartNotFoundException if no shopping cart with this id is found
 	 * @throws ProductNotFoundException if no product with this id is found
	 * @throws InsufficientQuantityAvailableException if the available quantity of the product is less than the quantity requested
	 */
	public ShoppingCart addItem(long cartId, int quantity, boolean isGift, long productId) throws ShoppingCartNotFoundException,
		ProductNotFoundException, InsufficientQuantityAvailableException;

	/**
	 * Updates the quantity of a shopping item in a shopping cart.
	 * 
	 * <br>
	 * Updates its amount.
	 * 
	 * @param itemId the identifier of the item to update
	 * @param quantity the new quantity
	 * 
	 * @return the updated cart
	 * 
	 * @throws ShoppingCartNotFoundException if no shopping cart with this item id is found
	 * @throws InsufficientQuantityAvailableException if the available quantity of the product is less than the quantity requested
	 */
	public ShoppingCart updateItemQuantity(long itemId, int quantity) throws ShoppingCartNotFoundException, InsufficientQuantityAvailableException;

	/**
	 * Updates a shopping item in a shopping cart, declare it as a gift or not.
	 * 
	 * @param itemId the identifier of the item to update
	 * @param isGift the new is gift
	 * 
	 * @return the updated cart
 	 * 
	 * @throws ShoppingCartNotFoundException if no shopping cart with this item id is found
	 */
	public ShoppingCart updateItemIsGift(long itemId, boolean isGift) throws ShoppingCartNotFoundException;

	/**
	 * Deletes a shopping item from a shopping cart.
	 * 
	 * <br>
	 * Updates its amount.
	 * 
	 * @param itemId the identifier of the item to delete
	 * 
	 * @return the updated cart
	 * 
	 * @throws ShoppingCartNotFoundException if no shopping cart with this item id is found
	 */
	public ShoppingCart deleteItem(long itemId) throws ShoppingCartNotFoundException;

	public ShoppingCart getCartById(long cartId);

	public ShoppingCart getCartByUserUsername(String username);

	public ShoppingCart getCartByItemId(long itemId);

}