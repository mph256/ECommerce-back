package com.mph.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.CreditCardType;

public interface CreditCardTypeRepository extends JpaRepository<CreditCardType, Long> {

}