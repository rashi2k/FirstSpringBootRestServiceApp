package com.example.demo;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.beans.Country;
import com.example.demo.controllers.CountryController;
import com.example.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages="com.example.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes=ControllerMockMvcTest.class)
public class ControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CountryService countryService;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> countryList;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}
	
	@Test
	@Order(1)
	public void test_mvc_getCountries() throws Exception {
		countryList = new ArrayList<>();
		countryList.add(new Country(1, "Sri Lanka", "Colombo"));
		countryList.add(new Country(2, "India", "Delhi"));
		countryList.add(new Country(3, "France", "Paris"));
		
		when(countryService.getAllCountries()).thenReturn(countryList);
		
		this.mockMvc.perform(get("/countries"))
		.andExpect(status().isFound())
		.andDo(print());
		
	}
	
	@Test
	@Order(2)
	public void test_mvc_getContryById() throws Exception {
		int id = 1;
		Country c = new Country(id, "USA", "washington");
		when(countryService.getCountryById(id)).thenReturn(c);
		
		this.mockMvc.perform(get("/countries/{id}", id))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
		.andExpect(MockMvcResultMatchers.jsonPath(".capital").value("washington"))
		.andDo(print());
		
	}
	
	@Test
	@Order(3)
	public void test_mvc_getContryByName() throws Exception {
		String name = "China";
		Country c = new Country(3, name, "Colombo");
		when(countryService.getCountryByName(name)).thenReturn(c);
		
		this.mockMvc.perform(get("/countries/byname").param("name", "China"))
		.andExpect(status().isOk())
		.andDo(print());
		
	}
	
	@Test
	@Order(4)
	public void test_mvc_addCountry() throws Exception {
		Country country = new Country(3, "India", "Delhi");
		when(countryService.addCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);
		
		 this.mockMvc.perform(post("/countries")
		.content(jsonBody)
		.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
		 .andDo(print());
	}
	
	@Test
	@Order(5)
	public void test_mvc_updateCountry() throws Exception {
		int id = 8;
		Country country = new Country(id, "Italy", "Rome");
		when(countryService.updateCountry(id, country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);
		
		this.mockMvc.perform(put("/countries/{id}", id)
				.content(jsonBody)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(
		        MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
		    ).andExpect(
		        MockMvcResultMatchers.jsonPath("$.id").exists()
		    ).andDo((result)->{
		        System.out.println(result.getResolvedException());
		        System.out.println(result.getResponse());
		    })
		.andDo(print());
		
	}
	
	@Test
	@Order(5)
	public void test_mvc_deleteCountry() throws Exception {
		int id = 8;
		Country country = new Country(id, "Italy", "Rome");
		when(countryService.updateCountry(id, country)).thenReturn(country);
		
		this.mockMvc.perform(delete("/countries/{id}", id))
		.andExpect(status().isOk())
		.andDo(print());
		
	}
	
	// There are some issues with addContry and updateCountry method. It is not returning the response body.
}
