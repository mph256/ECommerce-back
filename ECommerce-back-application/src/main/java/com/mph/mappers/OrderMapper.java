package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Order;

import com.mph.dto.OrderDto;

public class OrderMapper {

	public static OrderDto convertToDto(Order order) {
		return OrderDto.of(order.getId(), order.getAmount(), order.getOrderDate(), order.getDeliveryDate(),
			AddressMapper.convertToDto(order.getShippingAddress()), DeliveryOptionMapper.convertToDto(order.getDeliveryOption()), 
			UserMapper.convertToDto(order.getUser()), SuborderMapper.convertToDto(order.getSuborders()));
	}

	public static List<OrderDto> convertToDto(List<Order> orders) {

		List<OrderDto> list = new ArrayList<OrderDto>();

		for(Order order: orders) {
			list.add(convertToDto(order));
		}

		return list;

	}

}