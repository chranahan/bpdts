package com.bpdts.bpdts_test.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.bpdts.bpdts_test.entities.Person;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RestDAO implements DAO {

	private static final String baseUrl = "https://bpdts-test-app.herokuapp.com";
	private Gson gson = new Gson();


	/**
	 * Returns list of all users
	 *
	 */
	public List<Person> getUsers() {
		
		WebClient client = WebClient.create(baseUrl).path("users").accept(MediaType.APPLICATION_JSON);
		
		String jsonResult = client.get(String.class);
		
		List<Person> personList = gson.fromJson(jsonResult, new TypeToken<List<Person>>(){}.getType());
		
		return personList;
	}

	/**
	 * Returns person object for given input id
	 *
	 */
	public Person getUserDetails(int id) {
		
		WebClient client = WebClient.create(baseUrl).path("user/{id}", id).accept(MediaType.APPLICATION_JSON);
		
		String jsonResult = client.get(String.class);
		
		Person person = gson.fromJson(jsonResult, Person.class);
		
		return person;
	}

	/**
	 * Returns list of all persons for given city
	 * 
	 */
	public List<Person> getUsersByCity(String city) {
		
		WebClient client = WebClient.create(baseUrl).path("city/{city}/users", city).accept(MediaType.APPLICATION_JSON);
		
		String jsonResult = client.get(String.class);
		
		List<Person> personList = gson.fromJson(jsonResult, new TypeToken<List<Person>>(){}.getType());
		
		return personList;
	}

}
