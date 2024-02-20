package com.mph.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

	public Optional<ShoppingCart> findFirstByUserUsername(String username);

	public Optional<ShoppingCart> findFirstByItemsId(long itemId);

}