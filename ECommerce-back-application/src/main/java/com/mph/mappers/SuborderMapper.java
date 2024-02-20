package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Suborder;

import com.mph.dto.SuborderDto;

public class SuborderMapper {

	public static SuborderDto convertToDto(Suborder suborder) {
		return SuborderDto.of(suborder.getId(), suborder.getStatus().getName().toString(), suborder.getOrder().getId(),
			ShoppingItemMapper.convertToDto(suborder.getItems()));
	}

	public static List<SuborderDto> convertToDto(List<Suborder> suborders) {

		List<SuborderDto> list = new ArrayList<SuborderDto>();

		for(Suborder suborder: suborders) {
			list.add(convertToDto(suborder));
		}

		return list;

	}

}