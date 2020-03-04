package com.bpdts.bpdts_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bpdts.bpdts_test.entities.Person;
import com.bpdts.bpdts_test.rest.RestDAO;

public class AppTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetUsersWithin50milesLondon() {
		App app = new App(new RestDAO());
		
		List<Person> personWithin50MileList =  app.getUsersWithin50milesLondon();
		
		assertTrue(personWithin50MileList.size() > 0);
				
	}
	
	@Test
	public void testRestError() {
				
		RestDAO mockedRestDAO = mock(RestDAO.class);
		
		when(mockedRestDAO.getUsers()).thenThrow(new WebApplicationException("TEST"));
		
		App app = new App(mockedRestDAO);
		
		String result = app.bpdtsProcessor();
		
		System.out.println("Result is " + result);
		
		assertTrue(result.contains("TEST"));
		
		
	}
	

	@Test
	public void testIsWithin50MilesOfLondon() {
		
		
		App app = new App(new RestDAO());
		// Test within 50 miles of London
		
		Person person = new Person();
		
		person.setLatitude(51.8);
		person.setLongitude(-0.113);
		
		assertTrue(app.isWithin50MilesOfLondon(person));
		
		// test outside 50 miles from London
		
		person.setLatitude(52.509865);
		person.setLongitude(-0.218092);
		
		assertFalse(app.isWithin50MilesOfLondon(person));
		
		
	}

}
