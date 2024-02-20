package com.mph.comparators;

import java.util.Date;

import java.util.Comparator;

import com.mph.entities.Order;
import com.mph.entities.SuborderStatus;

public class OrderComparator implements Comparator<Order> {

	@Override
	public int compare(Order order1, Order order2) {

		SuborderStatus status1 = order1.getSuborders().get(0).getStatus();
		SuborderStatus status2 = order2.getSuborders().get(0).getStatus();

		if(status1.equals(status2)) {

			Date deliveryDate1 = order1.getDeliveryDate();
			Date deliveryDate2 = order2.getDeliveryDate();

			if(deliveryDate1.equals(deliveryDate2))
				return Long.compare(order1.getId(), order2.getId());
			else
				return deliveryDate1.compareTo(deliveryDate2);

		} else {

			SuborderStatus.SuborderStatusEnum name1 = status1.getName();

			if(name1.equals(SuborderStatus.SuborderStatusEnum.IN_PROGRESS) || status2.getName().equals(SuborderStatus.SuborderStatusEnum.IN_PROGRESS))
				return name1.equals(SuborderStatus.SuborderStatusEnum.IN_PROGRESS)?-1:1;
			else
				return Long.compare(status1.getId(), status2.getId());

		}

	}

}