package com.mph.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.ConnectionService;
import com.mph.services.interfaces.UserService;

import com.mph.entities.User;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	private UserService userService;

	@Override
	public User connectUser(String username) {

		User user = userService.getUserByUsername(username);

		user = userService.updateUserLastConnection(username, new Date());

		return user;

	}

}