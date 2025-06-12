package com.app.billingapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Country;

public interface CountryRepository extends JpaRepository<Country,String>{
	 boolean existsByCountry(String country);

	Optional<Country> findByCountryCode(String countryCode);
}
