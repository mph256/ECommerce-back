package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.ShoppingItem;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

	public List<ShoppingItem> findBySuborderId(long suborderId);

}