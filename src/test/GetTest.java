package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.DB;
import controller.exception.IllegalArgumentException;
import controller.exception.NoSuchEntryException;

public class GetTest {
	private DB db;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.db = new DB();
		this.db.connect("jdbc:mysql://localhost/mip", "root", "root");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDorw() throws Exception {
		try
		{ this.db.close(); } 
		catch (Exception e) {}
	}

	@Test
	public void testGetSuccess() {
		try {
			double keyHash = Math.random();
			db.put("key" + keyHash, "value");
			
			String value = db.get("key" + keyHash);
			assertTrue( value.equals("value") );
		} 
		catch (Exception e)
		{ fail(); }
	}
	
	@Test
	public void testGetFailKeyNull() {
		try {
			db.get(null);
			fail();
		} 
		catch (IllegalArgumentException e) {};
	}
	
	@Test
	public void testGetFailKeyEmpty() {
		try {
			db.get("");
			fail();
		} 
		catch (IllegalArgumentException e) {};
	}
	
	@Test
	public void testGetFailNoSuchEntry() {
		try {
			db.get("NoSuchEntry");
			fail();
		} 
		catch (NoSuchEntryException e) {};
	}

}
