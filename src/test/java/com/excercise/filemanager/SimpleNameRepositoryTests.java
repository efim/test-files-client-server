package com.excercise.filemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);
    	
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
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);
    	
    	assertEquals("1", nameRepository.getIdByName("file1.txt"));
    	assertEquals("1", nameRepository.getIdByName("file1.pdf"));
    	assertEquals("2", nameRepository.getIdByName("file2.txt"));
	};
	
	@Test
	public void testContainsName() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);		
		
    	assertTrue(nameRepository.containsName("file1.txt"));
    	assertTrue(nameRepository.containsName("file2.txt"));
    	assertFalse(nameRepository.containsName("file3.jpg"));
	};
	
	@Test
	public void testContainsId() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);	

    	assertTrue(nameRepository.containsId("1"));
    	assertTrue(nameRepository.containsId("2"));
    	assertFalse(nameRepository.containsId("8"));
	};
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAdd() {
		nameToIdMap = Mockito.mock(Map.class);
		
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);	
    	
    	nameRepository.add("file1.txt", "1");
    	
    	Mockito.verify(nameToIdMap).put("file1.txt", "1");
	};
	
	@Test
	public void testRemove() {
    	nameToIdMap = new HashMap<String, String>();
    	nameToIdMap.put("file1.txt", "1");
    	nameToIdMap.put("file2.txt", "2");
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, null);	
	
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
    	
    	NameSearchPredicateFactory nameSearchPredicateFactory = Mockito.mock(NameSearchPredicateFactory.class);
    	Mockito.stub(nameSearchPredicateFactory.getPredicate("file1")).toReturn((String e) -> e.contains("file1"));
    	
    	
    	NameRepository nameRepository = new SimpleNameRepository(nameToIdMap, nameSearchPredicateFactory);	
    	
    	Map<String, String> result = nameRepository.find("file1");
	
    	assertEquals(expectedSearchResult, result);
	};
	
	@Test
	public void testSaveAndLoadDisk() {
    	NameRepository nameRepository = new SimpleNameRepository(null);	
    	nameRepository.add("file1", "1");
    	nameRepository.add("file2", "3254");
    	nameRepository.add("file3", "5532wedfg3");
    	nameRepository.add("file4!", "4");
    	
    	String saveFileName = "testMap";
    	
    	nameRepository.setSaveFileName(saveFileName);
    	
    	nameRepository.saveToDisk();
    	
    	nameRepository.remove("4");
    	
    	nameRepository.saveToDisk();
    	
    	NameRepository loadedRepository = new SimpleNameRepository(null);
    	loadedRepository.setSaveFileName(saveFileName);
    	loadedRepository.loadFromDisk();
    	
    	assertTrue(loadedRepository.containsId("1"));
    	assertTrue(loadedRepository.containsId("3254"));
    	assertTrue(loadedRepository.containsId("5532wedfg3"));
    	
    	assertFalse(loadedRepository.containsId("4"));
    	
    	assertFalse(loadedRepository.containsId("2"));
    	assertFalse(loadedRepository.containsId("54tert"));
    	
    	assertEquals(loadedRepository.getIdByName("file1"), nameRepository.getIdByName("file1"));
    	assertEquals(loadedRepository.getIdByName("file2"), nameRepository.getIdByName("file2"));
    	assertEquals(loadedRepository.getIdByName("file3"), nameRepository.getIdByName("file3"));
    	
    	try {
			Files.delete(FileSystems.getDefault().getPath("", saveFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
}
