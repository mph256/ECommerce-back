package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.DeliveryOptionService;

import com.mph.services.exceptions.DeliveryOptionNotFoundException;

import com.mph.repositories.interfaces.DeliveryOptionRepository;

import com.mph.entities.DeliveryOption;

@Service
public class DeliveryOptionServiceImpl implements DeliveryOptionService {

	@Autowired
	public DeliveryOptionRepository deliveryOptionRepository;

	@Override
	public DeliveryOption getDeliveryOptionById(long deliveryOptionId) {

		Optional<DeliveryOption> optionalDeliveryOption = deliveryOptionRepository.findById(deliveryOptionId);

		if(optionalDeliveryOption.isEmpty())
			throw new DeliveryOptionNotFoundException();

		DeliveryOption deliveryOption = optionalDeliveryOption.get();

		return deliveryOption;

	}

	@Override
	public List<DeliveryOption> getDeliveryOptions() {
		return deliveryOptionRepository.findAllByOrderByName();
	}

}