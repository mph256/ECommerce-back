package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public List<Role> findByUsersUsernameOrderByName(String username);

}