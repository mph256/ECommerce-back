package com.mph.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.CreditCardTypeService;

import com.mph.services.exceptions.CreditCardTypeNotFoundException;

import com.mph.repositories.interfaces.CreditCardTypeRepository;

import com.mph.entities.CreditCardType;

@Service
public class CreditCardTypeServiceImpl implements CreditCardTypeService {

	@Autowired
	private CreditCardTypeRepository creditCardTypeRepository;

	@Override
	public CreditCardType getCreditCardTypeById(long creditCardTypeId) {

		Optional<CreditCardType> optionalCreditCardType = creditCardTypeRepository.findById(creditCardTypeId);

		if(optionalCreditCardType.isEmpty())
			throw new CreditCardTypeNotFoundException();

		CreditCardType creditCardType = optionalCreditCardType.get();

		return creditCardType;

	}

}