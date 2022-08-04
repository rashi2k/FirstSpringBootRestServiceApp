package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.beans.Country;
import com.example.demo.controllers.CountryController;
import com.example.demo.services.CountryService;

@SpringBootTest(classes = {ControllerMokitoTest.class})
public class ControllerMokitoTest {
	
	@Mock
	CountryService countryService; 
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> countryList;
	
	Country country;
	
	
	@Test
	@Order(1)
	public void test_getCountries() {
		countryList = new ArrayList<>();
		countryList.add(new Country(1, "Sri Lanka", "Colombo"));
		countryList.add(new Country(2, "India", "Delhi"));
		countryList.add(new Country(3, "France", "Paris"));
		
		when(countryService.getAllCountries()).thenReturn(countryList);
		ResponseEntity<List<Country>> res = countryController.getCountries();
		
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals("Sri Lanka", res.getBody().get(0).getCountryName());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() {
		int id = 3; 
		country = new Country(id, "Germany", "Berlin");
		when(countryService.getCountryById(id)).thenReturn(country);
		
		ResponseEntity<Country> response = countryController.getCountryById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Germany", response.getBody().getCountryName());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		String name = "Germany"; 
		country = new Country(1, name, "Berlin");
		when(countryService.getCountryByName(name)).thenReturn(country);
		
		ResponseEntity<Country> response = countryController.getCountryByName(name);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Germany", response.getBody().getCountryName());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		country = new Country(2, "America", "Berlin");
		when(countryService.addCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> response = countryController.addCountry(country);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("America", response.getBody().getCountryName());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		int id = 5;
		country = new Country(id, "Italy", "Rome");
		when(countryService.updateCountry(id, country)).thenReturn(country);
		
		ResponseEntity<Country> response = countryController.updateCountry(id, country);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Italy", response.getBody().getCountryName());
		assertEquals(5, response.getBody().getId());
		assertEquals("Rome", response.getBody().getCapital());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		int id = 6;
		country = new Country(id, "China", "Beijing");
		when(countryService.deleteCountry(id)).thenReturn(country);
		
		ResponseEntity<Country> response = countryController.deleteCountry(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("China", response.getBody().getCountryName());
	}
}
