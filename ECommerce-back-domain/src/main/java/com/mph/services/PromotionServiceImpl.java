package com.mph.services;

import java.util.List;

import java.util.Date;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mph.services.interfaces.PromotionService;

import com.mph.services.exceptions.InvalidExpirationDateException;
import com.mph.services.exceptions.PromotionNotFoundException;

import com.mph.repositories.interfaces.PromotionRepository;

import com.mph.entities.Promotion;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	@Override
	public Promotion addPromotion(String code, int percentage, Date expirationDate) {

		checkExpirationDate(expirationDate);

		Promotion promotion = Promotion.of(code, percentage, expirationDate);

		promotion = promotionRepository.save(promotion);

		return promotion;

	}

	@Override
	public Promotion updatePromotion(long promotionId, String code, int percentage, Date expirationDate) {

		Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);

		if(optionalPromotion.isEmpty())
			throw new PromotionNotFoundException();

		Promotion promotion = optionalPromotion.get();

		if(!promotion.getCode().equals(code))
			promotion.setCode(code);

		if(promotion.getPercentage() != percentage) {

			promotion.setPercentage(percentage);

		}

		if(!promotion.getExpirationDate().equals(expirationDate)) {

			checkExpirationDate(expirationDate);

			promotion.setExpirationDate(expirationDate);

		}

		promotion = promotionRepository.save(promotion);

		return promotion;

	}

	@Override
	public Promotion deletePromotion(long promotionId) {

		Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);

		if(optionalPromotion.isEmpty())
			throw new PromotionNotFoundException();

		Promotion promotion = optionalPromotion.get();

		promotionRepository.deleteById(promotionId);

		return promotion;

	}

	@Override
	public Optional<Promotion> getPromotionById(long promotionId) {
		return promotionRepository.findById(promotionId);
	}

	@Override
	public Optional<Promotion> getPromotionByCode(String promotionCode) {
		return promotionRepository.findByCode(promotionCode);
	}

	@Override
	public List<Promotion> getPromotions() {
		return promotionRepository.findAll();
	}

	private void checkExpirationDate(Date expirationDate) {

		if(expirationDate.compareTo(new Date()) < 0)
			throw new InvalidExpirationDateException();

	}

}