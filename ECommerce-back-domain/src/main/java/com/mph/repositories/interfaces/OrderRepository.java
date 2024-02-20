package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mph.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findByUserUsernameOrderByIdDesc(String username);

	@Query("SELECT order FROM Order order "
		+ "WHERE order.id IN "
		+ "("
		+ "SELECT suborder.order.id FROM Suborder suborder "
		+ "WHERE suborder.seller.username = ?1 "
		+ "AND suborder.status.name != 'NEW'"
		+ ")")
	public List<Order> findBySellerUsername(String username);

}