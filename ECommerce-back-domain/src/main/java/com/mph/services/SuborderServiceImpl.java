package com.mph.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.SuborderService;
import com.mph.services.interfaces.SuborderStatusService;

import com.mph.services.exceptions.SuborderNotFoundException;
import com.mph.services.exceptions.NonShippableSuborderException;

import com.mph.repositories.interfaces.SuborderRepository;

import com.mph.entities.Suborder;
import com.mph.entities.SuborderStatus;
import com.mph.entities.Order;
import com.mph.entities.User;
import com.mph.entities.ShoppingItem;

@Service
public class SuborderServiceImpl implements SuborderService {

	@Autowired
	private SuborderStatusService suborderStatusService;

	@Autowired
	private SuborderRepository suborderRepository;

	@Override
	public Suborder addSuborder(Order order, User seller, List<ShoppingItem> items) {

		Suborder suborder = Suborder.of(suborderStatusService.getSuborderStatusById(1), order, seller, items);

		suborder = suborderRepository.save(suborder);

		return suborder;

	}

	@Override
	public Suborder shipSuborder(long suborderId) {

		Suborder suborder = getSuborderById(suborderId);

		if(!isShippable(suborder))
			throw new NonShippableSuborderException();

		suborder.setStatus(suborderStatusService.getSuborderStatusById(3));

		suborder = suborderRepository.save(suborder);

		return suborder;

	}

	public Suborder getSuborderById(long suborderId) {

		Optional<Suborder> optionalSuborder = suborderRepository.findById(suborderId);

		if(optionalSuborder.isEmpty())
			throw new SuborderNotFoundException();

		Suborder suborder = optionalSuborder.get();

		return suborder;

	}

	private boolean isShippable(Suborder suborder) {
		return suborder.getStatus().getName().equals(SuborderStatus.SuborderStatusEnum.IN_PROGRESS);
	}

}