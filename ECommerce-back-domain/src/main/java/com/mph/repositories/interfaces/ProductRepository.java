package com.mph.repositories.interfaces;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mph.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT CASE "
		+ "WHEN COUNT(*) > 0 THEN false "
		+ "ELSE true "
		+ "END "
		+ "FROM "
		+ "("
		+ "SELECT item.id id FROM ShoppingItem item "
		+ "WHERE item.product.id = ?1 AND item.suborder IS NULL AND item.cart IS NULL"
		+ ")")
	public boolean isDeletable(long creditCardId);

	public Optional<Product> findFirstByIsActiveAndReviewsId(boolean isActive, long reviewId);

	public List<Product> findByIsActiveAndCategoriesNameIgnoreCaseOrderByName(boolean isActive, String categoryName);

	public List<Product> findByNameContainingIgnoreCaseAndIsActiveAndCategoriesNameIgnoreCaseOrderByName(String productName, boolean isActive, String categoryName);

	public List<Product> findByIsActiveAndSellerUsernameOrderByName(boolean isActive, String username);

	@Query("SELECT product FROM Product product "
		+ "WHERE product.id IN "
		+ "("
		+ "SELECT item.product.id FROM ShoppingItem item "
		+ "GROUP BY item.product.id "
		+ "ORDER BY SUM(item.quantity) DESC "
		+ "LIMIT 10"
		+ ") "
		+ "AND product.isActive")
	public List<Product> findBestSellers();

	public List<Product> findFirst10ByIsActiveOrderByLastUpdateDesc(boolean isActive);

}