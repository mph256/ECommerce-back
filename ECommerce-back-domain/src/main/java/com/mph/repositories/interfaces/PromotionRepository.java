package com.mph.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	public Optional<Promotion> findByCode(String promotionCode);

}