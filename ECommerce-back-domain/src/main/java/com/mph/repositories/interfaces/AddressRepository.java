package com.mph.repositories.interfaces;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mph.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT CASE "
		+ "WHEN COUNT(*) > 0 THEN false "
		+ "ELSE true "
		+ "END "
		+ "FROM "
		+ "("
		+ "SELECT order.id AS id FROM Order order "
		+ "WHERE order.shippingAddress.id = ?1 "
		+ "UNION "
		+ "SELECT order.id AS id FROM Order order "
		+ "WHERE order.billingAddress.id = ?1"
		+ ")")
	public boolean isDeletable(long addressId);

	public Optional<Address> findFirstByIdAndIsActive(long addressId, boolean isActive);

	public Optional<Address> findFirstByIsDefaultShippingAndIsActiveAndUserUsername(boolean isDefaultShipping, boolean isActive, String username);

	public Optional<Address> findFirstByIsDefaultBillingAndIsActiveAndUserUsername(boolean isDefaultBilling, boolean isActive, String username);

	public List<Address> findByIsActiveAndUserUsernameOrderByName(boolean isActive, String username);

}