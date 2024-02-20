package com.mph.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.SuborderStatusService;

import com.mph.services.exceptions.SuborderStatusNotFoundException;

import com.mph.repositories.interfaces.SuborderStatusRepository;

import com.mph.entities.SuborderStatus;

@Service
public class SuborderStatusServiceImpl implements SuborderStatusService {

	@Autowired
	private SuborderStatusRepository suborderStatusRepository;

	@Override
	public SuborderStatus getSuborderStatusById(long orderStatusId) {

		Optional<SuborderStatus> optionalSuborderStatus = suborderStatusRepository.findById(orderStatusId);

		if(optionalSuborderStatus.isEmpty())
			throw new SuborderStatusNotFoundException();

		SuborderStatus suborderStatus = optionalSuborderStatus.get();

		return suborderStatus;

	}

}