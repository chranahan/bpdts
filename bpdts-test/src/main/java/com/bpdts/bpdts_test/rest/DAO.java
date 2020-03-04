package com.bpdts.bpdts_test.rest;

import java.util.List;

import com.bpdts.bpdts_test.entities.Person;

public interface DAO {
	
	public List<Person> getUsers();
	
	public Person getUserDetails(int id);
	
	public List<Person> getUsersByCity(String city);
	
}
