package com.mph.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.RegistrationService;
import com.mph.services.interfaces.UserService;
import com.mph.services.interfaces.ShoppingCartService;

import com.mph.entities.User;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService cartService;

	@Override
	public User registerUser(String username, String email, String encodedPassword) {

		User user = userService.addUser(username, email, encodedPassword);

		cartService.addCart(user);

		return user;

	}

}