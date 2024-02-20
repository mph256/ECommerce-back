package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.AddressService;
import com.mph.services.interfaces.CountryService;
import com.mph.services.interfaces.UserService;

import com.mph.services.exceptions.InvalidZipcodeFormatException;
import com.mph.services.exceptions.AddressNotFoundException;

import com.mph.repositories.interfaces.AddressRepository;

import com.mph.entities.Address;
import com.mph.entities.Country;
import com.mph.entities.User;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private CountryService countryService;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address addAddress(String name, String street, String city, int zipcode, long countryId, String username) {

		checkZipcode(zipcode);

		Country country = countryService.getCountryById(countryId);

		User user = userService.getUserByUsername(username);

		removeUserDefaultShippingAddress(username);
		removeUserDefaultBillingAddress(username);

		Address address = Address.of(name, street, city, zipcode, true, true, true, country, user);

		address = addressRepository.save(address);

		return address;

	}

	@Override
	public Address updateAddress(long addressId, String name, String street, String city, int zipcode, long countryId) {

		Address address = getAddressById(addressId);

		if(!address.getName().equals(name))
			address.setName(name);

		if(!address.getStreet().equals(street))
			address.setStreet(street);

		if(!address.getCity().equals(city))
			address.setCity(city);

		if(address.getZipcode() != zipcode) {

			checkZipcode(zipcode);

			address.setZipcode(zipcode);

		}

		if(countryId != address.getCountry().getId()) {

			Country country = countryService.getCountryById(countryId);

			address.setCountry(country);

		}	

		address = addressRepository.save(address);

		return address;

	}

	@Override
	public Address updateUserDefaultShippingAddress(long addressId) {

		Address address = getAddressById(addressId);

		removeUserDefaultShippingAddress(address.getUser().getUsername());

		address.setIsDefaultShipping(true);

		address = addressRepository.save(address);

		return address;

	}

	@Override
	public Address updateUserDefaultBillingAddress(long addressId) {

		Address address = getAddressById(addressId);

		removeUserDefaultBillingAddress(address.getUser().getUsername());

		address.setIsDefaultBilling(true);

		address = addressRepository.save(address);

		return address;

	}

	@Override
	public Address deleteAddress(long addressId) {

		Address address = getAddressById(addressId);

		if(addressRepository.isDeletable(addressId)) {

			addressRepository.deleteById(addressId);

			return address;

		}

		address.setIsDefaultShipping(false);
		address.setIsDefaultBilling(false);
		address.setIsActive(false);

		address = addressRepository.save(address);

		return address;

	}

	@Override
	public Address getAddressById(long addressId) {

		Optional<Address> optionalAddress = addressRepository.findFirstByIdAndIsActive(addressId, true);

		if(optionalAddress.isEmpty())
			throw new AddressNotFoundException();

		Address address = optionalAddress.get();

		return address;

	}

	@Override
	public List<Address> getAddressesByUserUsername(String username) {
		return addressRepository.findByIsActiveAndUserUsernameOrderByName(true, username);
	}

	private void checkZipcode(int zipcode) {

		if(zipcode <= 0 || String.valueOf(zipcode).length() != 5)
			throw new InvalidZipcodeFormatException();

	}

	private void removeUserDefaultShippingAddress(String username) {

		Optional<Address> optionalAddress = addressRepository.findFirstByIsDefaultShippingAndIsActiveAndUserUsername(true, true, username);

		if(optionalAddress.isPresent()) {

			Address address = optionalAddress.get();

			address.setIsDefaultShipping(false);

			addressRepository.save(address);

		}

	}

	private void removeUserDefaultBillingAddress(String username) {

		Optional<Address> optionalAddress = addressRepository.findFirstByIsDefaultBillingAndIsActiveAndUserUsername(true, true, username);

		if(optionalAddress.isPresent()) {

			Address address = optionalAddress.get();

			address.setIsDefaultBilling(false);

			addressRepository.save(address);

		}

	}

}