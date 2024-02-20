package com.mph.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mph.services.interfaces.ShoppingCartService;

import com.mph.entities.ShoppingCart;

import com.mph.mappers.ShoppingCartMapper;
import com.mph.mappers.ShoppingItemMapper;

import com.mph.dto.ShoppingCartDto;
import com.mph.dto.ShoppingItemDto;

@RestController
@RequestMapping("/cart")
@Tag(name="Cart", description="Cart management APIs")
public class ShoppingCartController {

	private static final Logger logger = LogManager.getLogger(ShoppingCartController.class);

	@Autowired
	private ShoppingCartService cartService;

	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get cart by username")
	@Parameter(name="username", description="The username of the user")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Cart found", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="404", description="Cart not found", content=@Content)
	})
	public ShoppingCartDto getCardByUserUsername(@PathVariable String username) {
		return ShoppingCartMapper.convertToDto(cartService.getCartByUserUsername(username));
	}

	@PostMapping("/users/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new items")
	@Parameters({
		@Parameter(name="username", description="The username of the user"),
		@Parameter(name="items", description="The items to add")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Items added", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="400", description="Available quantity of a product is less than the quantity requested", content=@Content),
		@ApiResponse(responseCode="404", description="Cart not found", content=@Content)
	})
	public ResponseEntity<ShoppingCartDto> updateCart(@PathVariable String username, @RequestBody List<ShoppingItemDto> items) {

		ShoppingCart cart = cartService.updateCart(username, ShoppingItemMapper.convertToEntity(items));

		cart.getItems().forEach(item -> logger.info("Item adding: " + item.getId()));

		return ResponseEntity.ok(ShoppingCartMapper.convertToDto(cart));

	}

	@PostMapping("/{cartId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new item")
	@Parameters({
		@Parameter(name="cartId", description="The identifier of the cart"),
		@Parameter(name="quantity", description="The quantity"),
		@Parameter(name="isGift", description="If it's a gift or not"),
		@Parameter(name="productId", description="The identifier of the product to add")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Item added", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="400", description="Available quantity of the product is less than the quantity requested", content=@Content),
		@ApiResponse(responseCode="404", description="Cart or product not found", content=@Content)
	})
	public ResponseEntity<ShoppingCartDto> addItem(@PathVariable long cartId, int quantity, boolean isGift, long productId) {

		ShoppingCart cart = cartService.addItem(cartId, quantity, isGift, productId);

		logger.info("Item adding: " + cart.getItems()
			.stream()
			.filter(item -> productId == item.getProduct().getId())
			.findFirst()
			.get()
			.getId());

		return ResponseEntity.ok(ShoppingCartMapper.convertToDto(cart));

	}

	@PatchMapping("/{cartId}/items/{itemId}/quantity")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update item quantity")
	@Parameters({
		@Parameter(name="cartId", description="The identifier of the cart"),
		@Parameter(name="itemId", description="The identifier of the item to update"),
		@Parameter(name="quantity", description="The quantity")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Item updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="400", description="Available quantity of the product is less than the quantity requested", content=@Content),
		@ApiResponse(responseCode="404", description="Item not found", content=@Content)
	})
	public ResponseEntity<ShoppingCartDto> updateItemQuantity(@PathVariable long cartId, @PathVariable long itemId, int quantity) {

		ShoppingCart cart = cartService.updateItemQuantity(itemId, quantity);

		logger.info("Item quantity update: " + itemId);

		return ResponseEntity.ok(ShoppingCartMapper.convertToDto(cart));

	}

	@PatchMapping("/{cartId}/items/{itemId}/is-gift")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Update item is gift")
	@Parameters({
		@Parameter(name="cartId", description="The identifier of the cart"),
		@Parameter(name="itemId", description="The identifier of the item to update"),
		@Parameter(name="isGift", description="If it's a gift or not")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Item updated", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="404", description="Item not found", content=@Content)
	})
	public ResponseEntity<ShoppingCartDto> updateItemIsGift(@PathVariable long cartId, @PathVariable long itemId, boolean isGift) {

		ShoppingCart cart = cartService.updateItemIsGift(itemId, isGift);

		logger.info("Item is gift update: " + itemId);

		return ResponseEntity.ok(ShoppingCartMapper.convertToDto(cart));

	}

	@DeleteMapping("/{cartId}/items/{itemId}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Delete item")
	@Parameters({
		@Parameter(name="cartId", description="The identifier of the cart"),
		@Parameter(name="itemId", description="The identifier of the item to delete")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Item removed", content={@Content(mediaType="application/json", schema=@Schema(implementation=ShoppingCartDto.class))}),
		@ApiResponse(responseCode="404", description="Item not found", content=@Content)
	})
	public ResponseEntity<ShoppingCartDto> deleteItem(@PathVariable long cartId, @PathVariable long itemId) {

		ShoppingCart cart = cartService.deleteItem(itemId);

		logger.info("Item deletion: " + itemId);

		return ResponseEntity.ok(ShoppingCartMapper.convertToDto(cart));

	}

}