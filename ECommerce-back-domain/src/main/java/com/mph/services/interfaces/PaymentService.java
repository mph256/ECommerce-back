package com.mph.services.interfaces;

import com.mph.entities.Payment;
import com.mph.entities.CreditCard;
import com.mph.entities.Order;

public interface PaymentService {

	/**
	 * Creates a new payment for an order.
	 * 
 	 * <br>
	 * Argument validity checks must be performed before this method is called.
	 * 
	 * @param amount the amount
	 * @param creditCard the credit card
	 * @param order the order
	 * 
	 * @return the new payment created
	 */
	public Payment addPayment(float amount, CreditCard creditCard, Order order);

}