package com.example.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Country")
public class Country {

	@Id
	@Column(name="id")
	int id;
	
	@Column(name="country_name")
	String countryName;
	
	@Column(name="capital")
	String capital;
	

	public Country(int id, String countryName, String capital) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.capital = capital;
	}

	public Country() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

}
