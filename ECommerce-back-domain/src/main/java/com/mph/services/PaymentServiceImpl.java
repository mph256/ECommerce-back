package com.mph.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.PaymentService;
import com.mph.services.interfaces.PaymentStatusService;

import com.mph.repositories.interfaces.PaymentRepository;

import com.mph.entities.Payment;
import com.mph.entities.CreditCard;
import com.mph.entities.Order;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentStatusService paymentStatusService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Payment addPayment(float amount, CreditCard creditCard, Order order) {

		Payment payment = Payment.of(amount, new Date(), creditCard, paymentStatusService.getPaymentStatusById(1), order);

		boolean result = callPaymentApi(amount, creditCard);

		if(result)
			payment.setStatus(paymentStatusService.getPaymentStatusById(2));
		else
			payment.setStatus(paymentStatusService.getPaymentStatusById(3));

		payment = paymentRepository.save(payment);

		return payment;

	}

	private boolean callPaymentApi(float amount, CreditCard creditCard) {
		return true;
	}

}