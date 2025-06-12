package com.app.billingapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "countryid", nullable = false)
	private Long countryId;

	@Column(name = "countrycode", nullable = false)
	private String countryCode;

	@Column(name = "country", nullable = false)
	private String country;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Country() {
	}

	
	public Country(Long countryId, String countryCode, String country) {
		super();
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.country = country;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryCode=" + countryCode + ", country=" + country + "]";
	}

}
