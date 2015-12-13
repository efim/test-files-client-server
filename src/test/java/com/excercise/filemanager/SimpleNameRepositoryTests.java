package com.excercise.filemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class SimpleNameRepositoryTests {
	
	private Map<String, String> nameToIdMap;
	
	@Test
	public void testGetNamesById() {
		
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file1.pdf", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	Set<String> expected = new HashSet<String>();
    	expected.add("file1.txt");
    	expected.add("file1.pdf");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);
    	
    	assertTrue(expected.equals(nameRepository.getNamesById("1")));
    	
    	expected = new HashSet<String>();
    	expected.add("file2.txt");
    	
    	assertTrue(expected.equals(nameRepository.getNamesById("2")));
	};
	
	@Test
	public void testGetIdByName() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file1.pdf", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);
    	
    	assertEquals("1", nameRepository.getIdByName("file1.txt"));
    	assertEquals("1", nameRepository.getIdByName("file1.pdf"));
    	assertEquals("2", nameRepository.getIdByName("file2.txt"));
	};
	
	@Test
	public void testContainsName() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);		
		
    	assertTrue(nameRepository.containsName("file1.txt"));
    	assertTrue(nameRepository.containsName("file2.txt"));
    	assertFalse(nameRepository.containsName("file3.jpg"));
	};
	
	@Test
	public void testContainsId() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);	

    	assertTrue(nameRepository.containsId("1"));
    	assertTrue(nameRepository.containsId("2"));
    	assertFalse(nameRepository.containsId("8"));
	};
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAdd() {
		nameToIdMap = Mockito.mock(Map.class);
		
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);	
    	
    	nameRepository.add("file1.txt", "1");
    	
    	Mockito.verify(nameToIdMap).put("file1.txt", "1");
	};
	
	@Test
	public void testRemove() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);	
	
    	nameRepository.remove("1");
    	    	
    	assertFalse(nameToIdMap.containsKey("file1.txt"));
    	assertTrue(nameToIdMap.containsKey("file2.txt"));
	};
	
	@Test
	public void testFind() {
		Map<String, String> expectedSearchResult = new HashMap<String, String>();
		expectedSearchResult.put("file1.txt", "1");
		expectedSearchResult.put("file1-picture.pdf", "4432");
		
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file1-picture.pdf", "4432");
    	nameToIdMap.put("otherFile", "3345");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap);	
    	
    	Map<String, String> result = nameRepository.find("file1");
	
    	assertEquals(expectedSearchResult, result);
	};
	
}
