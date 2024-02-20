package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Country;

import com.mph.dto.CountryDto;

public class CountryMapper {

	public static CountryDto convertToDto(Country country) {
		return CountryDto.of(country.getId(), country.getName(), country.getVAT());
	}

	public static List<CountryDto> convertToDto(List<Country> countries) {

		List<CountryDto> list = new ArrayList<CountryDto>();

		for(Country country: countries) {
			list.add(convertToDto(country));
		}

		return list;

	}

}