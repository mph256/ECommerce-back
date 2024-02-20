package com.mph.services.interfaces;

import java.util.List;

import com.mph.entities.Country;

public interface CountryService {

	public Country getCountryById(long countryId);

	public List<Country> getCountries();

}