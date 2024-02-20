package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.DeliveryOption;

import com.mph.dto.DeliveryOptionDto;

public class DeliveryOptionMapper {
	
	public static DeliveryOptionDto convertToDto(DeliveryOption deliveryOption) {
		return DeliveryOptionDto.of(deliveryOption.getId(), deliveryOption.getName().toString(), deliveryOption.getPrice(), deliveryOption.getTime());
	}

	public static List<DeliveryOptionDto> convertToDto(List<DeliveryOption> deliveryOptions) {

		List<DeliveryOptionDto> list = new ArrayList<DeliveryOptionDto>();

		for(DeliveryOption deliveryOption: deliveryOptions) {
			list.add(convertToDto(deliveryOption));
		}

		return list;

	}

}