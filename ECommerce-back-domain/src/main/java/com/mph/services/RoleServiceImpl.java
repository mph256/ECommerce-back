package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.RoleService;

import com.mph.services.exceptions.RoleNotFoundException;

import com.mph.repositories.interfaces.RoleRepository;

import com.mph.entities.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	public RoleRepository roleRepository;

	@Override
	public Role getRoleById(long roleId) {

		Optional<Role> optionalRole = roleRepository.findById(roleId);

		if(optionalRole.isEmpty())
			throw new RoleNotFoundException();

		Role role = optionalRole.get();

		return role;

	}

	@Override
	public Role getRoleByName(String roleName) {

		Role role = null;

		switch(roleName.toUpperCase()) {
			case "CUSTOMER" -> role = getRoleById(1);
			case "SELLER" -> role = getRoleById(2);
			case "ADMIN" -> role = getRoleById(3);	
			default -> throw new RoleNotFoundException();
		}

		return role;

	}

	@Override
	public List<Role> getRolesByUserUsername(String username) {
		return roleRepository.findByUsersUsernameOrderByName(username);
	}

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

}