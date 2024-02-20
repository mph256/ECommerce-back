package com.mph.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}