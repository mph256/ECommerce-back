package com.mph.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByPhone(String phone);

}