package com.mph.services.interfaces;

import java.util.List;

import com.mph.services.exceptions.InvalidZipcodeFormatException;
import com.mph.services.exceptions.CountryNotFoundException;
import com.mph.services.exceptions.UserNotFoundException;
import com.mph.services.exceptions.AddressNotFoundException;

import com.mph.entities.Address;

public interface AddressService {

	/**
	 * Creates a new address for a user.
	 * 
	 * <br>
	 * The created address will be set as the user's default shipping and billing address.
	 * 
	 * @param name the name
	 * @param street the street
	 * @param city the city
	 * @param zipcode the zipcode
	 * @param countryId the identifier of the country
	 * @param username the username of the user
	 * 
	 * @return the new address created
	 * 
 	 * @throws InvalidZipcodeFormatException if the zipcode format is invalid
	 * @throws CountryNotFoundException if no country with this id is found
	 * @throws UserNotFoundException if no user with this username is found
	 */
	public Address addAddress(String name, String street, String city, int zipcode, long countryId, String username)
		throws InvalidZipcodeFormatException, CountryNotFoundException, UserNotFoundException;

	/**
	 * Updates an address.
	 * 
	 * @param addressId the identifier of the address to update
	 * @param name the new name
	 * @param street the new street
	 * @param city the new city
	 * @param zipcode the new zipcode
	 * @param countryId the identifier of the new country
	 * 
	 * @return the updated address
	 * 
	 * @throws InvalidZipcodeFormatException if the zipcode format is invalid
	 * @throws AddressNotFoundException if no address with this id is found
	 * @throws CountryNotFoundException if no country with this id is found
	 */
	public Address updateAddress(long addressId, String name, String street, String city, int zipcode, long countryId)
		throws InvalidZipcodeFormatException, AddressNotFoundException, CountryNotFoundException;

	/**
	 * Updates a user's default shipping address.
	 * 
	 * @param addressId the identifier of the shipping address to set as default
	 * 
	 * @return the new default shipping address
	 * 
	 * @throws AddressNotFoundException if no address with this id is found
	 */
	public Address updateUserDefaultShippingAddress(long addressId) throws AddressNotFoundException;

	/**
	 * Updates a user's default billing address.
	 * 
	 * @param addressId the identifier of the billing address to set as default
	 * 
	 * @return the new default billing address
	 * 
	 * @throws AddressNotFoundException if no address with this id is found
	 */
	public Address updateUserDefaultBillingAddress(long addressId) throws AddressNotFoundException;

	/**
	 * Deletes an address.
	 * 
 	 * <br>
	 * The address is only deleted if it is not linked to any order. 
	 * Otherwise it will no longer appear except for orders already linked to it.
	 * It will not be permanently deleted from the database, but only set as non-active.
	 * 
	 * @param addressId the identifier of the address to delete
	 * 
	 * @return the deleted address
	 * 
 	 * @throws AddressNotFoundException if no address with this id is found
	 */
	public Address deleteAddress(long addressId) throws AddressNotFoundException;

	public Address getAddressById(long addressId);

	public List<Address> getAddressesByUserUsername(String username);

}