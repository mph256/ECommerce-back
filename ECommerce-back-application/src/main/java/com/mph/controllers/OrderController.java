package com.mph.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mph.services.interfaces.OrderService;
import com.mph.services.interfaces.ReviewService;

import com.mph.entities.Order;
import com.mph.entities.Suborder;
import com.mph.entities.ShoppingItem;
import com.mph.entities.Product;

import com.mph.comparators.OrderComparator;

import com.mph.mappers.OrderMapper;

import com.mph.dto.OrderDto;

@RestController
@RequestMapping("/orders")
@Tag(name="Order", description="Order management APIs")
public class OrderController {

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Get orders by username")
	@Parameter(name="username", description="The buyer's username")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Orders found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=OrderDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<OrderDto> getOrdersByUserUsername(@PathVariable String username) {

		List<Order> orders = orderService.getOrdersByUserUsername(username);

		for(Order order: orders) {
			
			for(Suborder suborder: order.getSuborders()) {

				List<ShoppingItem> items = suborder.getItems();
	
				Collections.sort(items, ((x, y) -> x.getProduct().getName().compareTo(y.getProduct().getName())));
	
				for(ShoppingItem item: items) {
	
					Product product = item.getProduct();
	
					product.addReviews(reviewService.getReviewsByProductId(product.getId()));
	
				}
				
			}

		}

		return OrderMapper.convertToDto(orders);

	}

	@GetMapping("/sellers/{username}")
	@PreAuthorize("hasAuthority('SCOPE_SELLER')")
	@Operation(summary="Get orders by seller username")
	@Parameter(name="username", description="The seller's username")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Orders found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=OrderDto.class)))}),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content)
	})
	public List<OrderDto> getOrdersBySellerUsername(@PathVariable String username) {

		List<Order> orders = orderService.getOrdersBySellerUsername(username);

		OrderComparator comparator = new OrderComparator();

		return OrderMapper.convertToDto(orders
			.stream()
			.sorted((x, y) -> comparator.compare(x, y))
			.collect(Collectors.toList()));

	}

	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Add new order")
	@Parameters({
		@Parameter(name="shippingAddressId", description="The identifier of the shipping address"),
		@Parameter(name="billingAddressId", description="The identifier of the billing address"),
		@Parameter(name="creditCardId", description="The identifier of the credit card"),
		@Parameter(name="promotionId", description="The identifier of the promotion"),
		@Parameter(name="deliveryOptionId", description="The identifier of the delivery option"),
		@Parameter(name="username", description="The buyer's username")
	})
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Order added", content={@Content(mediaType="application/json", schema=@Schema(implementation=OrderDto.class))}),
		@ApiResponse(responseCode="400", description="Available quantity of a product is less than the requested quantity or expired credit card or promotion", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Shipping address, billing address, credit card, delivery option or user not found", content=@Content)
	})
	public ResponseEntity<OrderDto> addOrder(long shippingAddressId, long billingAddressId, long creditCardId, long promotionId, long deliveryOptionId, String username) {

		Order order = orderService.addOrder(shippingAddressId, billingAddressId, creditCardId, promotionId, deliveryOptionId, username);

		logger.info("Order adding: " + order.getId());

		return new ResponseEntity<OrderDto>(OrderMapper.convertToDto(order), HttpStatus.CREATED);

	}

	@PatchMapping("/{orderId}/cancel")
	@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
	@Operation(summary="Cancel order")
	@Parameter(name="orderdId", description="The identifier of the order to cancel")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Order canceled", content={@Content(mediaType="application/json", schema=@Schema(implementation=OrderDto.class))}),
		@ApiResponse(responseCode="400", description="Non-cancellable order", content=@Content),
		@ApiResponse(responseCode="401", description="Unauthorized", content=@Content),
		@ApiResponse(responseCode="404", description="Order not found", content=@Content)
	})
	public ResponseEntity<OrderDto> cancelOrder(@PathVariable long orderId) {

		Order order = orderService.cancelOrder(orderId);

		logger.info("Order cancellation: " + orderId);

		return ResponseEntity.ok().body(OrderMapper.convertToDto(order));

	}

}