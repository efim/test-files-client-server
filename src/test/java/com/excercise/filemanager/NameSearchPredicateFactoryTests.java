package com.excercise.filemanager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class NameSearchPredicateFactoryTests {
	
	@Test
	public void testGetPredicate() {
		NameSearchPredicateFactory factory = new NameSearchPredicateFactory();
		
		assertTrue(factory.getPredicate("file").apply("file"));
		assertTrue(factory.getPredicate("file*").apply("file111"));
		assertTrue(factory.getPredicate("*file*").apply("hello_files.txt"));
		assertTrue(factory.getPredicate("*").apply("any string"));
		assertTrue(factory.getPredicate("*.txt").apply("file1.txt"));
		assertTrue(factory.getPredicate("*.txt").apply("file2_edit.txt"));

		
		assertFalse(factory.getPredicate("file").apply("mismatch"));
		assertFalse(factory.getPredicate("file*").apply("no wildcard in beginning files"));
		
	}

}
