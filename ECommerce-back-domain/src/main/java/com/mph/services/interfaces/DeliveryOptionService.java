package com.mph.services.interfaces;

import java.util.List;

import com.mph.entities.DeliveryOption;

public interface DeliveryOptionService {

	public DeliveryOption getDeliveryOptionById(long deliveryOptionId);

	public List<DeliveryOption> getDeliveryOptions();

}