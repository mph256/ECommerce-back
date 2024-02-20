package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.ShoppingCartService;
import com.mph.services.interfaces.ShoppingItemService;
import com.mph.services.interfaces.ProductService;

import com.mph.services.exceptions.ShoppingCartNotFoundException;
import com.mph.services.exceptions.InsufficientQuantityAvailableException;

import com.mph.repositories.interfaces.ShoppingCartRepository;

import com.mph.entities.ShoppingCart;
import com.mph.entities.ShoppingItem;
import com.mph.entities.Product;
import com.mph.entities.User;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingItemService itemService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ShoppingCartRepository cartRepository;

	@Override
	public ShoppingCart addCart(User user) {

		ShoppingCart cart = ShoppingCart.of(0F, user);

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart addItem(long cartId, int quantity, boolean isGift, long productId) {

		Product product = productService.getProductById(productId);

		if(product.getQuantityAvailable() < quantity)
			throw new InsufficientQuantityAvailableException();

		ShoppingCart cart = getCartById(cartId);

		float price = product.getPrice() * quantity;

		boolean isNewItem = true;

		for(ShoppingItem item: cart.getItems()) {

			if(product.equals(item.getProduct())) {

				int newQuantity = item.getQuantity() + quantity;

				if(product.getQuantityAvailable() < newQuantity)
					throw new InsufficientQuantityAvailableException();

				item.setQuantity(newQuantity);
				item.setPrice(item.getPrice() + price);
				item.setIsGift(isGift);

				isNewItem = false;

				break;

			}

		}

		if(isNewItem) {

			ShoppingItem item = ShoppingItem.of(quantity, isGift, price, product);

			item.setCart(cart);

			cart.addItem(item);

		}

		cart.setAmount(cart.getAmount() + price);

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart updateCart(String username, List<ShoppingItem> items) {

		ShoppingCart cart = getCartByUserUsername(username);

		if(items.size() > 0)
			resetCart(cart);

		int index = 0;

		for(ShoppingItem item: items) {

			if(index < items.size() - 1)
				addItem(cart.getId(), item.getQuantity(), item.getIsGift(), item.getProduct().getId());
			else
				cart = addItem(cart.getId(), item.getQuantity(), item.getIsGift(), item.getProduct().getId());

			index++;

		}

		return cart;

	}

	@Override
	public ShoppingCart updateItemQuantity(long itemId, int quantity) {

		ShoppingCart cart = getCartByItemId(itemId);

		for(ShoppingItem item: cart.getItems()) {

			if(itemId == item.getId()) {

				Product product = item.getProduct();

				if(product.getQuantityAvailable() < quantity)
					throw new InsufficientQuantityAvailableException();

				float oldPrice = item.getPrice();

				item.setQuantity(quantity);
				item.setPrice(product.getPrice() * quantity);

				cart.setAmount(cart.getAmount() - oldPrice + item.getPrice());

				break;

			}

		}

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart updateItemIsGift(long itemId, boolean isGift) {

		ShoppingCart cart = getCartByItemId(itemId);

		for(ShoppingItem item: cart.getItems()) {

			if(itemId == item.getId()) {

				item.setIsGift(isGift);

				break;

			}

		}

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart deleteItem(long itemId) {

		ShoppingCart cart = getCartByItemId(itemId);

		for(ShoppingItem item: cart.getItems()) {

			if(itemId == item.getId()) {

				cart.setAmount(cart.getAmount() - item.getPrice());

				cart.removeItem(item);

				itemService.deleteItem(item);

				break;

			}

		}

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart resetCart(ShoppingCart cart) {

		List<ShoppingItem> items = cart.getItems();

		items.forEach(item -> item.setCart(null));

		cart.setAmount(0F);
		cart.clearItems();

		cart = cartRepository.save(cart);

		return cart;

	}

	@Override
	public ShoppingCart getCartById(long cartId) {

		Optional<ShoppingCart> optionalCart = cartRepository.findById(cartId);

		if(optionalCart.isEmpty())
			throw new ShoppingCartNotFoundException();

		ShoppingCart cart = optionalCart.get();

		return cart;

	}

	@Override
	public ShoppingCart getCartByUserUsername(String username) {

		Optional<ShoppingCart> optionalCart = cartRepository.findFirstByUserUsername(username);

		if(optionalCart.isEmpty())
			throw new ShoppingCartNotFoundException();

		ShoppingCart cart = optionalCart.get();

		return cart;

	}

	@Override
	public ShoppingCart getCartByItemId(long itemId) {

		Optional<ShoppingCart> optionalCart = cartRepository.findFirstByItemsId(itemId);

		if(optionalCart.isEmpty())
			throw new ShoppingCartNotFoundException();

		ShoppingCart cart = optionalCart.get();

		return cart;

	}

}