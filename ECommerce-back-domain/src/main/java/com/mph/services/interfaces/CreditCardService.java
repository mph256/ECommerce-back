package com.mph.services.interfaces;

import java.util.List;

import java.util.Date;

import com.mph.services.exceptions.InvalidCardNumberFormatException;
import com.mph.services.exceptions.InvalidCVCFormatException;
import com.mph.services.exceptions.ExpiredCreditCardException;
import com.mph.services.exceptions.UserNotFoundException;
import com.mph.services.exceptions.CreditCardTypeNotFoundException;
import com.mph.services.exceptions.CreditCardNotFoundException;

import com.mph.entities.CreditCard;

public interface CreditCardService {

	/**
	 * Creates a new credit card for a user.
	 * 
 	 * <br>
	 * The created credit card will be set as the user's default credit card.
	 * 
	 * @param number the number
	 * @param holderName the holder name
	 * @param expirationDate the expiration date
	 * @param CVC the card verification code
	 * @param username the username of the user
	 * 
	 * @return the new credit card created
	 * 
	 * @throws InvalidCardNumberFormatException if the credit card number format is invalid
	 * @throws InvalidCVCFormatException if the CVC format is invalid
	 * @throws ExpiredCreditCardException if the credit card has expired
	 * @throws UserNotFoundException if no user with this username is found
	 * @throws CreditCardTypeNotFoundException if no credit card type is found from this number
	 */
	public CreditCard addCreditCard(long number, String holderName, Date expirationDate, int CVC, String username)
		throws InvalidCardNumberFormatException, InvalidCVCFormatException, ExpiredCreditCardException, UserNotFoundException, CreditCardTypeNotFoundException;

	/**
	 * Updates a user's default credit card.
	 * 
	 * @param creditCardId the identifier of the credit card to set as default
	 * 
	 * @return the new default credit card
	 * 
	 * @throws CreditCardNotFoundException if no credit card with this id is found
	 */
	public CreditCard updateUserDefaultCreditCard(long creditCardId) throws CreditCardNotFoundException;

	/**
	 * Deletes a credit card.
	 * 
	 * <br>
	 * The address is only deleted if it is not linked to any payment. 
	 * Otherwise it will no longer appear except for payments already linked to it.
	 * It will not be permanently deleted from the database, but only set as non-active.
	 * 
	 * @param creditCardId the identifier of the credit card to delete
	 * 
	 * @return the deleted credit card
	 * 
	 * @throws CreditCardNotFoundException if no credit card with this id is found
	 */
	public CreditCard deleteCreditCard(long creditCardId) throws CreditCardNotFoundException;

	public CreditCard getCreditCardById(long creditCardId);

	public List<CreditCard> getCreditCardsByUserUsername(String username);

}