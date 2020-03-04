package com.bpdts.bpdts_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bpdts.bpdts_test.entities.Person;

public class AppTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testBpdtsProcessor() {
		App app = new App();
		
		List<Person> personWithin50MileList =  app.bpdtsProcessor();
		
		assertTrue(personWithin50MileList.size() > 0);
				
	}

	@Test
	public void testIsWithin50MilesOfLondon() {
		
		
		App app = new App();
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
