package com.mph.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.PaymentStatus;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

}