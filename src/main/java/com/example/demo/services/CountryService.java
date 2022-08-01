package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Country;
import com.example.demo.controllers.AddResponse;
import com.example.demo.repositories.CountryRepository;

@Component
@Service
public class CountryService {
	
	@Autowired
	CountryRepository countryRepo;
	
	public List<Country> getAllCountries() {
		return countryRepo.findAll();
	}
	
	
	public Country getCountryById(int id) {
		return countryRepo.findById(id).get();
	}
	
	public Country getCountryByName(String name) {
		System.err.println("ddd" + name);
		Country country = null;
		for(Country c :  countryRepo.findAll()) {
			if(c.getCountryName().equalsIgnoreCase(name)) {
			
				country = c;
			}
		}
		return country;
	}
	
	
	public Country addCountry(Country country) {
		country.setId(getMaxId() + 1);
		System.err.println(getMaxId());
		return countryRepo.save(country);
	}
	
	
	public Country updateCountry(int id, Country country) {
		Country c = getCountryById(id);
		if(c != null) {
			c.setCapital(country.getCapital());
			c.setCountryName(country.getCountryName());
			countryRepo.save(c);
		}
		
		return c;
	}
	
	public AddResponse deleteCountry(int id) {
		countryRepo.deleteById(id);
		
		AddResponse res = new AddResponse();
		res.setId(id);
		res.setMsg("Country is deleted..");
		return res ;
	}
	
	// key : 0 -> Country
	// Key: 5 -> Country5
	// 
	private int getMaxId() {
		int max = 0;
		for(Country c : countryRepo.findAll()) {
			if(c.getId() > max) {
				max = c.getId();
			}
		}
		
		return max; 
	}
}
