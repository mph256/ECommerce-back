package com.mph.repositories.interfaces;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mph.entities.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

	@Query("SELECT CASE "
		+ "WHEN COUNT(*) > 0 THEN false "
		+ "ELSE true "
		+ "END "
		+ "FROM "
		+ "("
		+ "SELECT payment.id id FROM Payment payment "
		+ "WHERE payment.creditCard.id = ?1"
		+ ")")
	public boolean isDeletable(long creditCardId);

	public Optional<CreditCard> findFirstByIdAndIsActive(long creditCardId, boolean isActive);

	public Optional<CreditCard> findFirstByIsDefaultAndUserUsername(boolean isDefault, String username);

	public List<CreditCard> findByIsActiveAndUserUsernameOrderByExpirationDateDesc(boolean isActive, String username);

}