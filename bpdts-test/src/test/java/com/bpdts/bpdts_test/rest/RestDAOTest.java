package com.bpdts.bpdts_test.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.bpdts.bpdts_test.entities.Person;

public class RestDAOTest {
	
	RestDAO dao = new RestDAO();
	

	@Test
	public void testGetUsers() {
		List<Person> personList = dao.getUsers();
		System.out.println("Testing getUsers ");
		personList.forEach(person -> {
			System.out.println("person = " + person);
		});
		
		assertFalse(personList.isEmpty());
		
	}

	@Test
	public void testGetUserDetails() {
		
		System.out.println("Testing getUserDetails ");
		Person person = dao.getUserDetails(1);
		System.out.println("person = " + person);
		assertTrue(person.getId() == 1);
	}

	@Test
	public void testGetUsersByCity() {
		
		List<Person> personList = dao.getUsersByCity("Kax");
		System.out.println("Testing getUsersByCity ");
		personList.forEach(person -> {
			System.out.println("person = " + person);
		});
		
		assertFalse(personList.isEmpty());
	}

}
