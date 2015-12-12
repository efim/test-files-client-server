package com.excercise.filemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
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
public class SimpleFileManagerTests {

	@Test
	public void testAddNewFile() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();

		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");

		String expectedFileId = uidGenerator.getUID(data);		
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);
		fileManager.add("file1.txt", data);
		
		Mockito.verify(mockNameRepository).add("file1.txt", expectedFileId);
		Mockito.verify(mockFilesystemConnector).write(expectedFileId, data);		
	};
	
	@Test
	public void testAddDuplicate() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();

		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");

		String expectedFileId = uidGenerator.getUID(data);		
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);
		fileManager.add("file1.txt", data);
		
		Mockito.when(mockNameRepository.containsId(expectedFileId)).thenReturn(true);
		
		fileManager.add("file2-duplicate.txt", data);
		fileManager.add("another duplicate.txt", data);
		
		Mockito.verify(mockNameRepository).add("file1.txt", expectedFileId);
		Mockito.verify(mockNameRepository).add("file2-duplicate.txt", expectedFileId);
		Mockito.verify(mockNameRepository).add("another duplicate.txt", expectedFileId);
		Mockito.verify(mockFilesystemConnector, Mockito.atMost(1)).write(expectedFileId, data);
		
	}
	
	@Test
	public void testRemove() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();
		
		Mockito.when(mockNameRepository.containsId("1")).thenReturn(true);
		Mockito.when(mockNameRepository.containsId("2")).thenReturn(false);
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);

		boolean result = fileManager.remove("1");
		assertTrue(result);
		Mockito.verify(mockNameRepository).remove("1");
		Mockito.verify(mockFilesystemConnector).delete("1");
		
		result = fileManager.remove("2");
		assertFalse(result);
	
	};
	
	@Test
	public void testRetrieve() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();
		byte[] expectedData = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");
		
		Mockito.when(mockNameRepository.containsId("1")).thenReturn(true);
		Mockito.when(mockFilesystemConnector.read("1")).thenReturn(expectedData);		
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);
		
		byte[] result = fileManager.retrieve("1");
		
		assertEquals(expectedData, result);
	};
	
	@Test
	public void testFind() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);

		Set<Map.Entry<String, String>> expectedSearchResult = new HashSet<Map.Entry<String, String>>();
		for (int i = 1; i < 30; i++) {
			String index = Integer.toString(i);
			expectedSearchResult.add(new AbstractMap.SimpleEntry<String, String>("file" + index, index));
		}
		
		assertTrue(expectedSearchResult.size() > 25);
		
		Mockito.when(mockNameRepository.find("file1")).thenReturn(expectedSearchResult);
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, null, null);
		
		Set<Map.Entry<String, String>> result = fileManager.find("file1");
		
		Mockito.verify(mockNameRepository).find("file1");

		assertTrue(result.size() <= 25);
	};
	
	
}
