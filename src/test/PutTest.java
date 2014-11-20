package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.DB;
import controller.exception.IllegalArgumentException;
import controller.exception.InternalErrorException;

public class PutTest {
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
	public void testPutSuccess() {
		double keyHash = Math.random();
		db.put("key" + keyHash, "value");
	}
	
	@Test
	public void testPutFailSameKey() {
		try {
			double keyHash = Math.random();
			db.put("key" + keyHash, "value");
			db.put("key" + keyHash, "value");
			fail();
		} 
		catch (InternalErrorException e) {}
	}
	
	@Test
	public void testPutFailKeyNull() {
		try {
			db.put(null, "value");
			fail();
		} 
		catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testPutFailKeyEmpty() {
		try {
			db.put("", "value");
			fail();
		} 
		catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testPutFailValueNull() {
		try {
			double keyHash = Math.random();
			db.put("key" + keyHash, null);
			fail();
		} 
		catch (IllegalArgumentException e) {}
	}
	
	@Test
	public void testPutFailValueEmpty() {
		try {
			double keyHash = Math.random();
			db.put("key" + keyHash, "");
			fail();
		} 
		catch (IllegalArgumentException e) {}
	}

}
