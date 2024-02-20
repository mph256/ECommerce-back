package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.CountryService;

import com.mph.services.exceptions.CountryNotFoundException;

import com.mph.repositories.interfaces.CountryRepository;

import com.mph.entities.Country;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Country getCountryById(long countryId) {

		Optional<Country> optionalCountry = countryRepository.findById(countryId);

		if(optionalCountry.isEmpty())
			throw new CountryNotFoundException();

		Country country = optionalCountry.get();

		return country;

	}

	@Override
	public List<Country> getCountries() {
		return countryRepository.findAllByOrderByName();
	}

}