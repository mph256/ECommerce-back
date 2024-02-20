package com.mph.repositories.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mph.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public List<Country> findAllByOrderByName();

}