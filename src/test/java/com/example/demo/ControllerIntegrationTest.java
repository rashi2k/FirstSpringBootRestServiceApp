package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTest {

	@Test
	@Order(1)
	void getAllCountriesIntegrationTest() throws JSONException {

		String expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" + "        \"countryName\": \"India\",\r\n"
				+ "        \"capital\": \"Dehli\"\r\n" + "    },\r\n" + "    {\r\n" + "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"Sri Lanka\",\r\n" + "        \"capital\": \"Colombo\"\r\n" + "    }\r\n"
				+ "]";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/countries", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(2)
	void getCountryByIdIntegrationTest() throws JSONException {

		String expected = "{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"countryName\": \"India\",\r\n" + 
				"    \"capital\": \"Dehli\"\r\n" + 
				"}";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/countries/1", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(3)
	void getCountryByNameIntegrationTest() throws JSONException {

		String expected = "{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"countryName\": \"India\",\r\n" + 
				"    \"capital\": \"Dehli\"\r\n" + 
				"}";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/countries/byname?name=India",
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	@Order(4)
	void addCountryIntegrationTest() throws JSONException {

//		String expected = "{\r\n" + 
//				"    \"id\": 4,\r\n" + 
//				"    \"countryName\": \"test\",\r\n" + 
//				"    \"capital\": \"t\"\r\n" + 
//				"}";

		Country country = new Country(4, "England", "London");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/countries", request,
				String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Order(5)
	void updateCountryIntegrationTest() throws JSONException {

		String expected = "{\r\n" + "    \"id\": 2,\r\n" + "    \"countryName\": \"Itally\",\r\n"
				+ "    \"capital\": \"Rome\"\r\n" + "}";

		Country country = new Country(2, "Itally", "Rome");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/countries/2", HttpMethod.PUT,
				request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());	
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(5)
	void deleteCountryIntegrationTest() throws JSONException {

		RestTemplate restTemplate = new RestTemplate();
		
		Country country = new Country(2, "Itally", "Rome");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/countries/2", HttpMethod.DELETE,
				request, String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());	
		
	}
}