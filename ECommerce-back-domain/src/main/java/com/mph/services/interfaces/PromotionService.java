package com.mph.services.interfaces;

import java.util.List;

import java.util.Date;

import java.util.Optional;

import com.mph.services.exceptions.InvalidExpirationDateException;
import com.mph.services.exceptions.PromotionNotFoundException;

import com.mph.entities.Promotion;

public interface PromotionService {

	/**
	 * Creates a new promotion.
	 * 
	 * @param code the code
	 * @param percentage the percentage
	 * @param expirationDate the expiration date
	 * 
	 * @return the new promotion created
	 * 
	 * @throws InvalidExpirationDateException if the expiration date is a past date
	 */
	public Promotion addPromotion(String code, int percentage, Date expirationDate) throws InvalidExpirationDateException;

	/**
	 * Updates a promotion.
	 * 
	 * @param promotionId the identifier of the promotion to update
	 * @param code the new code
	 * @param percentage the new percentage
	 * @param expirationDate the new expiration date
	 * 
	 * @return the updated promotion
	 * 
	 * @throws InvalidExpirationDateException if the expiration date is a past date
 	 * @throws PromotionNotFoundException if no promotion with this id is found
	 */
	public Promotion updatePromotion(long promotionId, String code, int percentage, Date expirationDate) throws InvalidExpirationDateException, 
		PromotionNotFoundException;

	/**
	 * Deletes a promotion.
	 * 
	 * @param promotionId the identifier of the promotion to delete
	 * 
	 * @return the deleted promotion
	 * 
  	 * @throws PromotionNotFoundException if no promotion with this id is found
	 */
	public Promotion deletePromotion(long promotionId) throws PromotionNotFoundException;

	public Optional<Promotion> getPromotionById(long promotionId);

	public Optional<Promotion> getPromotionByCode(String promotionCode);

	public List<Promotion> getPromotions();

}