package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@GetMapping(path = "")
	
	public ResponseEntity<List<Country>> getCountries() {
		try {
			List<Country> countryList = countryService.getAllCountries();
			return new ResponseEntity<List<Country>>(countryList, HttpStatus.FOUND);
		}catch(Exception e){
			return new ResponseEntity<List<Country>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id) {
		try {
			Country country = countryService.getCountryById(id);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/byname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String name) {
		try {
			Country country = countryService.getCountryByName(name);
			System.out.println("****" + country);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "")
	public Country addCountry(@RequestBody Country country) {
		return countryService.addCountry(country);
	} 

	@PutMapping(path = "/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country) {
		try {
			Country c= countryService.updateCountry(id, country);
			return new ResponseEntity<Country>(c, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		} 
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable(value = "id") int id) {
		Country country = null;
		try {
			country = countryService.deleteCountry(id);	
			return new ResponseEntity<>(country, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
