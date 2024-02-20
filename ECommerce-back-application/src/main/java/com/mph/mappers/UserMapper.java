package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import com.mph.entities.User;
import com.mph.entities.Address;

import com.mph.dto.UserDto;

public class UserMapper {

	public static UserDto convertToDto(User user) {

		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String phone = user.getPhone();

		Optional<Address> shipppingAddressOptional = user.getAddresses().stream().filter(address -> address.getIsDefaultShipping()).findFirst();
		Optional<Address> billingAddressOptional = user.getAddresses().stream().filter(address -> address.getIsDefaultBilling()).findFirst();

		UserDto userDto = UserDto.of(user.getUsername(), user.getEmail(), 
			user.getRoles().stream().map(role -> role.getName().toString()).toList());

		if(firstname != null)
			userDto.setFirstname(firstname);

		if(lastname != null)
			userDto.setLastname(lastname);

		if(phone != null)
			userDto.setPhone(phone);

		if(shipppingAddressOptional.isPresent())
			userDto.setShippingAddress(AddressMapper.convertToDto(shipppingAddressOptional.get()));

		if(billingAddressOptional.isPresent())
			userDto.setBillingAddress(AddressMapper.convertToDto(billingAddressOptional.get()));

		return userDto;

	}

	public static List<UserDto> convertToDto(List<User> users) {

		List<UserDto> list = new ArrayList<UserDto>();

		for(User user: users) {
			list.add(convertToDto(user));
		}

		return list;

	}

}