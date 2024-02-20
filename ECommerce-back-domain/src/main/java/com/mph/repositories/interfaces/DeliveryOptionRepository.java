package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.DeliveryOption;

public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, Long> {

	public List<DeliveryOption> findAllByOrderByName();

}