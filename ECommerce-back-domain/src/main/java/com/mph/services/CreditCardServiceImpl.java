package com.mph.services;

import java.util.List;

import java.util.Date;

import java.util.Optional;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.CreditCardService;
import com.mph.services.interfaces.CreditCardTypeService;
import com.mph.services.interfaces.UserService;

import com.mph.services.exceptions.InvalidCardNumberFormatException;
import com.mph.services.exceptions.InvalidCVCFormatException;
import com.mph.services.exceptions.ExpiredCreditCardException;
import com.mph.services.exceptions.CreditCardNotFoundException;
import com.mph.services.exceptions.CreditCardTypeNotFoundException;

import com.mph.repositories.interfaces.CreditCardRepository;

import com.mph.entities.CreditCard;
import com.mph.entities.CreditCardType;
import com.mph.entities.User;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	private static final String VISA_REGEX = "^4\\d{3}(| |-)(?:\\d{4}\\1){2}\\d{4}$";
	private static final String MASTERCARD_REGEX = "^5[1-5]\\d{2}(| |-)(?:\\d{4}\\1){2}\\d{4}$";
	private static final String AMERICAN_EXPRESS_REGEX = "^3[47]\\d{1,2}(| |-)\\d{6}\\1\\d{6}$";

	@Autowired
	private CreditCardTypeService creditCardTypeService;

	@Autowired
	private UserService userService;

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Override
	public CreditCard addCreditCard(long number, String holderName, Date expirationDate, int CVC, String username) {

		checkCardNumber(number);
		checkCVC(CVC);
		checkExpirationDate(expirationDate);

		User user = userService.getUserByUsername(username);

		CreditCardType type = null;

		if(isVisa(number))
			type = creditCardTypeService.getCreditCardTypeById(1);
		else if(isMastercard(number))
			type = creditCardTypeService.getCreditCardTypeById(2);
		else if(isAmericanExpress(number))
			type = creditCardTypeService.getCreditCardTypeById(3);
		else
			throw new CreditCardTypeNotFoundException();

		removeUserDefaultCreditCard(username);

		CreditCard creditCard = CreditCard.of(number, holderName, expirationDate, CVC, true, true, type, user);

		creditCard = creditCardRepository.save(creditCard);

		return creditCard;

	}

	@Override
	public CreditCard updateUserDefaultCreditCard(long creditCardId) {

		CreditCard creditCard = getCreditCardById(creditCardId);

		removeUserDefaultCreditCard(creditCard.getUser().getUsername());

		creditCard.setIsDefault(true);

		creditCardRepository.save(creditCard);

		return creditCard;

	}

	@Override
	public CreditCard deleteCreditCard(long creditCardId) {

		CreditCard creditCard = getCreditCardById(creditCardId);

		if(creditCardRepository.isDeletable(creditCardId)) {

			creditCardRepository.deleteById(creditCardId);

			return creditCard;

		}

		creditCard.setIsDefault(false);
		creditCard.setIsActive(false);

		creditCardRepository.save(creditCard);

		return creditCard;

	}

	@Override
	public CreditCard getCreditCardById(long creditCardId) {

		Optional<CreditCard> optionalCreditCard = creditCardRepository.findFirstByIdAndIsActive(creditCardId, true);

		if(optionalCreditCard.isEmpty())
			throw new CreditCardNotFoundException();

		CreditCard creditCard = optionalCreditCard.get();

		return creditCard;

	}

	@Override
	public List<CreditCard> getCreditCardsByUserUsername(String username) {
		return creditCardRepository.findByIsActiveAndUserUsernameOrderByExpirationDateDesc(true, username);
	}

	private void removeUserDefaultCreditCard(String username) {

		Optional<CreditCard> optionalCreditCard = creditCardRepository.findFirstByIsDefaultAndUserUsername(true, username);

		if(optionalCreditCard.isPresent()) {

			CreditCard creditCard = optionalCreditCard.get();

			creditCard.setIsDefault(false);

			creditCardRepository.save(creditCard);

		}

	}

	private void checkCardNumber(long cardNumber) {

		int cardNumberLength = String.valueOf(cardNumber).length();

		if(cardNumberLength < 8 || cardNumberLength > 19)
			throw new InvalidCardNumberFormatException();

	}

	private void checkCVC(int CVC) {

		if(String.valueOf(CVC).length() != 3)
			throw new InvalidCVCFormatException();

	}

	private void checkExpirationDate(Date expirationDate) {

		if(expirationDate.compareTo(new Date()) < 0)
			throw new ExpiredCreditCardException();

	}

	private boolean isVisa(long cardNumber) {
		return Pattern.matches(VISA_REGEX, Long.toString(cardNumber));
	}

	private boolean isMastercard(long cardNumber) {
		return Pattern.matches(MASTERCARD_REGEX, Long.toString(cardNumber));
	}

	private boolean isAmericanExpress(long cardNumber) {
		return Pattern.matches(AMERICAN_EXPRESS_REGEX, Long.toString(cardNumber));
	}

}