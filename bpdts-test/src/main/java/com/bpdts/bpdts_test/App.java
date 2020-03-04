package com.bpdts.bpdts_test;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import com.bpdts.bpdts_test.entities.Person;
import com.bpdts.bpdts_test.rest.DAO;
import com.bpdts.bpdts_test.rest.RestDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * BPDTS test api
 *
 */
public class App {

	public static final double LONDON_LATITUDE = 51.509865;
	public static final double LONDON_LONGITUDE = -0.118092;

	public static final double LONDON_LATITUDE_RAD = Math.toRadians(LONDON_LATITUDE);
	public static final double LONDON_LONGITUDE_RAD = Math.toRadians(LONDON_LONGITUDE);
	
	private DAO restDAO;
	
	
	/**
	 * Constructor requires an implementation of the DAO interface
	 * @param restDAO
	 */
	public App(DAO restDAO) {
		this.restDAO = restDAO;
	}
	
	

	/**
	 * Main entry point to api
	 * 
	 * Returns a JSON list of users within 50 miles of London, or a JSON error
	 * containing exception details from REST call
	 * 
	 * @param args None
	 */
	public static void main(String[] args) {
		
		App app = new App(new RestDAO());

		System.out.println(app.bpdtsProcessor());

	}

	/**
	 * Gets list of users who are within 50 miles of London
	 * 
	 * @return
	 */
	public String bpdtsProcessor() {

		try {
			List<Person> usersWithin50MilesList = this.getUsersWithin50milesLondon();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			return gson.toJson(usersWithin50MilesList);
		} catch (WebApplicationException ex) {

			return "{\"error\":\"" + ex.getMessage() + "\"}";
		}
	}
	
	
	/**
	 * Gets list of users who are within 50 miles of London
	 * 
	 * @return
	 */
	public List<Person> getUsersWithin50milesLondon() throws WebApplicationException, NotFoundException {

		// Get list of all users

		List<Person> allUsers = restDAO.getUsers();

		// create filtered list to all users within 50 miles of London

		List<Person> usersWithin50Miles = allUsers.stream().filter(p -> {
			return this.isWithin50MilesOfLondon(p);
		}).collect(Collectors.toList());

		// enrich each person object with the city attribute which is not present on the
		// original list

		usersWithin50Miles.stream().forEach(person -> {
			Person fullPersonDets = restDAO.getUserDetails(person.getId());
			person.setCity(fullPersonDets.getCity());
		});

		return usersWithin50Miles;
	}


	/**
	 * Checks whether the input person object is with 50 miles of London using the
	 * Haversine formula
	 * 
	 * @param person
	 * @return true if within 50 miles of London
	 */
	public boolean isWithin50MilesOfLondon(Person person) {

		if (person.getLatitude() == 0.0 && person.getLongitude() == 0.0) {
			return false;
		}

		// Check distance from London using code modified from
		// https://www.geeksforgeeks.org/program-distance-two-points-earth/

		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.

		double personLonRad = Math.toRadians(person.getLongitude());
		double personLatRad = Math.toRadians(person.getLatitude());

		// Haversine formula
		double dlon = personLonRad - LONDON_LONGITUDE_RAD;
		double dlat = personLatRad - LONDON_LATITUDE_RAD;
		double a = Math.pow(Math.sin(dlat / 2), 2)
				+ Math.cos(LONDON_LATITUDE_RAD) * Math.cos(personLatRad) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in miles
		double r = 3956;

		// calculate the result
		double distance = (c * r);

		if (distance > 50.0) {
			return false;
		} else {
			return true;
		}

	}
}
