package com.mph.repositories.interfaces;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mph.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT CASE "
		+ "WHEN COUNT(*) > 0 THEN false "
		+ "ELSE true "
		+ "END "
		+ "FROM "
		+ "("
		+ "SELECT product.id AS id FROM Product product JOIN product.categories category "
		+ "WHERE category.id = ?1"
		+ ")")
	public boolean isDeletable(long categoryId);

	public Optional<Category> findFirstByIdAndIsActive(long categoryId, boolean isActive);

	public List<Category> findByIdInAndIsActiveOrderByName(List<Long> categories, boolean isActive);

	public List<Category> findByIsActiveOrderByName(boolean isActive);

}