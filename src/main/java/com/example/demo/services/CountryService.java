package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.beans.Country;
import com.example.demo.controllers.AddResponse;

@Component
public class CountryService {

	static HashMap<Integer, Country> countryMap;
	
	public CountryService() {
		
		countryMap = new HashMap<Integer, Country>();
		
		Country c1 = new Country(1, "Sri Lanka", "Colombo");
		Country c2 = new Country(2, "India", "Delhi");
		Country c3 = new Country(3, "Italy", "Rome");
		
		countryMap.put(c1.getId(), c1);
		countryMap.put(c2.getId(), c2);
		countryMap.put(c3.getId(), c3);
		
	}
	
	
	public List<Country> getAllCountries() {
		List<Country> countryList = new ArrayList<Country>();
		
		countryList.addAll(countryMap.values());
		
		return countryList;
	}
	
	
	public Country getCountryById(int id) {
		return countryMap.get(id);
	}
	
	public Country getCountryByName(String name) {
		Country country = null;
		for(int i : countryMap.keySet()) {
			if(countryMap.get(i).getCountryName().equals(name)) {
				country = countryMap.get(i);
			}
		}
		return country;
	}
	
	
	public Country addCountry(Country country) {
		Country _country = new Country(getMaxId() + 1, country.getCountryName(), country.getCapital());
		countryMap.put(getMaxId() + 1, country);
		return getCountryById(_country.getId());
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
