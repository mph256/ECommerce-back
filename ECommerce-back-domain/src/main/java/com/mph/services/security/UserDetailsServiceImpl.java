package com.mph.services.security;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import com.mph.repositories.interfaces.UserRepository;

import com.mph.entities.security.UserDetailsImpl;

import com.mph.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUser = userRepository.findByUsername(username);

		if(optionalUser.isEmpty())
			throw new UsernameNotFoundException("User not found.");

		User user = optionalUser.get();

		return new UserDetailsImpl(user);

	}

}