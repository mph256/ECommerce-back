package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.CreditCard;

import com.mph.dto.CreditCardDto;

public class CreditCardMapper {

	public static CreditCardDto convertToDto(CreditCard creditCard) {
		return CreditCardDto.of(creditCard.getId(), creditCard.getNumber(), creditCard.getHolderName(), creditCard.getExpirationDate(), 
			creditCard.getType().getName().toString(), creditCard.getIsDefault());
	}

	public static List<CreditCardDto> convertToDto(List<CreditCard> creditCards) {

		List<CreditCardDto> list = new ArrayList<CreditCardDto>();

		for(CreditCard creditCard: creditCards) {
			list.add(convertToDto(creditCard));
		}

		return list;

	}

}