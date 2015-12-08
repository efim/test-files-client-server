package com.excercise.filemanager;

import static org.junit.Assert.assertEquals;

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

		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("hello world");

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

		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("hello world");

		String expectedFileId = uidGenerator.getUID(data);		
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);
		fileManager.add("file1.txt", data);
		fileManager.add("file2-duplicate.txt", data);
		fileManager.add("another duplicate.txt", data);
		
		Mockito.verify(mockNameRepository).add("file2-duplicate.txt", expectedFileId);
		Mockito.verify(mockNameRepository).add("another duplicate.txt", expectedFileId);
		Mockito.verify(mockFilesystemConnector, Mockito.atMost(1)).write(expectedFileId, data);
		
	}
	
	@Test
	public void testRemove() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);

		fileManager.remove("1");
		Mockito.verify(mockNameRepository).remove("1");
		Mockito.verify(mockFilesystemConnector).delete("1");
	
	};
	
	@Test
	public void testRetrieve() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);
		FilesystemConnector mockFilesystemConnector = Mockito.mock(FilesystemConnector.class);
		UIDGenerator uidGenerator = new MD5ChecksumUIDGenerator();
		byte[] expectedData = javax.xml.bind.DatatypeConverter.parseHexBinary("hello world");
		
		Mockito.when(mockFilesystemConnector.read("1")).thenReturn(expectedData);		
		
		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, uidGenerator, mockFilesystemConnector);
		
		assertEquals(expectedData, fileManager.retrieve("1"));
	};
	
	@Test
	public void testFind() {
		NameRepository mockNameRepository = Mockito.mock(NameRepository.class);

		SimpleFileManager fileManager = new SimpleFileManager(mockNameRepository, null, null);
		
		fileManager.find("file1");
		
		Mockito.verify(mockNameRepository).find("file1");
	};
}
