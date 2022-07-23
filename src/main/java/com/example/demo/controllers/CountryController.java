package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.Country;
import com.example.demo.services.CountryService;

@RestController
@RequestMapping("/countries")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	@GetMapping(path= "")
	public List<Country> getCountries() {
		return countryService.getAllCountries();
	}
	
	
	@GetMapping(path= "/{id}")
	public Country getCountryById(@PathVariable(value = "id") int id) {
		return countryService.getCountryById(id);
	}
	
	@GetMapping(path= "/byname")
	public Country getCountryByName(@RequestParam(value = "name") String name) {
		return countryService.getCountryByName(name);
	}
	
	@PostMapping(path="")
	public Country addCountry(@RequestBody Country country) {
		return countryService.addCountry(country);
	}
	
	@PutMapping(path="/{id}")
	public Country updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country) {
		return countryService.updateCountry(id, country);
	}
	
	@DeleteMapping(path="/{id}")
	public AddResponse deleteCountry(@PathVariable(value = "id") int id) {
		return countryService.deleteCountry(id);
	}
	
}
