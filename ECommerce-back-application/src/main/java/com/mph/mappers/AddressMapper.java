package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Address;

import com.mph.dto.AddressDto;

public class AddressMapper {
	
	public static AddressDto convertToDto(Address address) {
		return AddressDto.of(address.getId(), address.getName(), address.getStreet(), address.getCity(), address.getZipcode(),
			address.getIsDefaultShipping(), address.getIsDefaultBilling(), CountryMapper.convertToDto(address.getCountry()));
	}

	public static List<AddressDto> convertToDto(List<Address> addresses) {

		List<AddressDto> list = new ArrayList<AddressDto>();

		for(Address address: addresses) {
			list.add(convertToDto(address));
		}

		return list;

	}

}