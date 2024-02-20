package com.mph.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.PaymentStatusService;

import com.mph.services.exceptions.PaymentStatusNotFoundException;

import com.mph.repositories.interfaces.PaymentStatusRepository;

import com.mph.entities.PaymentStatus;

@Service
public class PaymentStatusServiceImpl implements PaymentStatusService {

	@Autowired
	private PaymentStatusRepository paymentStatusRepository;

	@Override
	public PaymentStatus getPaymentStatusById(long paymentStatusId) {

		Optional<PaymentStatus> optionalPaymentStatus = paymentStatusRepository.findById(paymentStatusId);

		if(optionalPaymentStatus.isEmpty())
			throw new PaymentStatusNotFoundException();

		PaymentStatus paymentStatus = optionalPaymentStatus.get();

		return paymentStatus;

	}

}