package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	public List<Review> findByProductIdOrderByLastUpdateDesc(long productId);

}