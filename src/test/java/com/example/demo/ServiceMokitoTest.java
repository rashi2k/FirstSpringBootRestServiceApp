package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.beans.Country;
import com.example.demo.repositories.CountryRepository;
import com.example.demo.services.CountryService;


@SpringBootTest(classes= {ServiceMokitoTest.class})
public class ServiceMokitoTest {
	
	@Mock
	CountryRepository countryRepository;
	
	@InjectMocks
	CountryService countryService;
	
	public List<Country> myCountries; 
	
	@Test
	@Order(1)
	public void test_getAllCountries() {
		myCountries = new ArrayList<>();
		myCountries.add(new Country(1, "Sri Lanka", "Colombo"));
		myCountries.add(new Country(2, "India", "Delhi"));
		
		when(countryRepository.findAll()).thenReturn(myCountries);
		
		assertEquals(2, countryService.getAllCountries().size());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() {
		
		Country sriLanka = new Country(1, "Sri Lanka", "Colombo");
		
		int id = 1;
		
		when(countryRepository.findById(id)).thenReturn(Optional.of(sriLanka));
		
		assertEquals(sriLanka, countryService.getCountryById(id));
		
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		myCountries = new ArrayList<>();
		myCountries.add(new Country(1, "Sri Lanka", "Colombo"));
		myCountries.add(new Country(2, "India", "Delhi"));
		
		String name = "Sri Lanka";
		
		when(countryRepository.findAll()).thenReturn(myCountries);
		
		assertEquals("Sri Lanka", countryService.getCountryByName(name).getCountryName());
		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		Country india = new Country(2, "India", "Delhi");
		
		when(countryRepository.save(india)).thenReturn(india);
		
		assertEquals(india, countryService.addCountry(india));
		
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		int id = 2; 
		Country currentCountry = new Country(2, "India", "Delhi");
		Country updateToCountry = new Country(2, "Germany", "Berlin");
		 
		when(countryRepository.findById(id)).thenReturn(Optional.of(currentCountry));
		when(countryRepository.save(updateToCountry)).thenReturn(updateToCountry);
		
		assertEquals("Germany", countryService.updateCountry(id, updateToCountry).getCountryName());
		
	}
}
