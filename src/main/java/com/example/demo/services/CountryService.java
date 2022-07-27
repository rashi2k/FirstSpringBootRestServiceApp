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
		return countryRepo.save(country);
	}
	
	
	public Country updateCountry(int id, Country country) {
		//Country c1 = countryMap.get(country.getId());
		if(country.getId() > 0) { // this is fake implementation
			countryMap.put(country.getId(), country);
		}
		
		return country;
	}
	
	public AddResponse deleteCountry(int id) {
		countryMap.remove(id);
		
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
		for(int i : countryMap.keySet()) {
			if(i > max) {
				max = i;
			}
		}
		
		return max; 
	}
}
