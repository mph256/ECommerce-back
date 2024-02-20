package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.SuborderNotFoundException;
import com.mph.services.exceptions.NonShippableSuborderException;

import com.mph.entities.Suborder;
import com.mph.entities.Order;
import com.mph.entities.User;
import com.mph.entities.ShoppingItem;

public interface SuborderService {

	/**
	 * Creates a new suborder.
	 * 
	 * @param order the order
	 * @param seller the seller
	 * @param items the items
	 * 
	 * @return the new suborder created
	 */
	public Suborder addSuborder(Order order, User user, List<ShoppingItem> items);

	/**
	 * Ships a suborder.
	 * 
	 * @param suborderId the identifier of the suborder to ship
	 * 
	 * @return the shipped suborder
	 * 
	 * @throws SuborderNotFoundException if no suborder with this id is found
	 * @throws NonShippableSuborderException if the suborder cannot be shipped
	 */
	public Suborder shipSuborder(long suborderId) throws SuborderNotFoundException, NonShippableSuborderException;

	public Suborder getSuborderById(long suborderId) throws SuborderNotFoundException;

}