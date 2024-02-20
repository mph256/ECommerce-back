package com.mph.mappers;

import java.util.List;
import java.util.ArrayList;

import com.mph.entities.Promotion;

import com.mph.dto.PromotionDto;

public class PromotionMapper {

	public static PromotionDto convertToDto(Promotion promotion) {
		return PromotionDto.of(promotion.getId(), promotion.getCode(), promotion.getPercentage(),
			promotion.getExpirationDate());
	}

	public static List<PromotionDto> convertToDto(List<Promotion> promotions) {

		List<PromotionDto> list = new ArrayList<PromotionDto>();

		for(Promotion promotion: promotions) {
			list.add(convertToDto(promotion));
		}

		return list;

	}

}