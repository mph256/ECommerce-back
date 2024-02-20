package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.AddressNotFoundException;
import com.mph.services.exceptions.CreditCardNotFoundException;
import com.mph.services.exceptions.DeliveryOptionNotFoundException;
import com.mph.services.exceptions.UserNotFoundException;
import com.mph.services.exceptions.InsufficientQuantityAvailableException;
import com.mph.services.exceptions.ExpiredCreditCardException;
import com.mph.services.exceptions.ExpiredPromotionException;
import com.mph.services.exceptions.PaymentFailedException;
import com.mph.services.exceptions.OrderNotFoundException;
import com.mph.services.exceptions.NonCancellableOrderException;

import com.mph.entities.Order;

public interface OrderService {

	/**
	 * Creates a new order.
	 * 
	 * @param shippingAddressId the identifier of the shipping address
	 * @param billingAddressId the identifier of the billing address
	 * @param creditCardId the identifier of the credit card
	 * @param promotionId the identifier of the promotion (optional, 0 if not specified)
	 * @param deliveryOptionId the identifier of the delivery option
	 * @param username the buyer's username
	 * 
	 * @return the new order created
	 * 
	 * @throws AddressNotFoundException if no shipping or billing address with these identifiers is found
	 * @throws CreditCardNotFoundException if no credit card with this id is found
	 * @throws DeliveryOptionNotFoundException if no delivery option with this id is found
	 * @throws UserNotFoundException if no user with this username is found
	 * @throws InsufficientQuantityAvailableException if the available quantity of a product is less than the requested quantity
	 * @throws ExpiredCreditCardException if the credit card has expired
	 * @throws ExpiredPromotionException if the promotion has expired
	 * @throws PaymentFailedException if the payment has failed
	 */
	public Order addOrder(long shippingAddressId, long billingAddressId, long creditCardId, long promotionId, long deliveryOptionId, String username)
		throws AddressNotFoundException, CreditCardNotFoundException, DeliveryOptionNotFoundException, UserNotFoundException, InsufficientQuantityAvailableException,
		ExpiredCreditCardException, ExpiredPromotionException, PaymentFailedException;

	/**
	 * Cancels an order.
	 * 
	 * @param orderId the identifier of the order to cancel
	 * 
	 * @return the canceled order
	 * 
	 * @throws OrderNotFoundException if no order with this id is found
	 * @throws NonCancellableOrderException if the order cannot be canceled
	 */
	public Order cancelOrder(long orderId) throws OrderNotFoundException, NonCancellableOrderException;

	public Order getOrderById(long orderId);

	public List<Order> getOrdersByUserUsername(String username);

	public List<Order> getOrdersBySellerUsername(String username);

}