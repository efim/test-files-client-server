package com.excercise.filemanager;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class SimpleFilesystemConnectorTests {
	
	
	@Test
	public void testAll() {
		SimpleFilesystemConnector connector = new SimpleFilesystemConnector();
		byte[] testData = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");
		String testFileId = "1";
		
		connector.write(testFileId, testData);
		
		byte[] readData = connector.read(testFileId);
		
		assertTrue(Arrays.equals(testData, readData));
		
		connector.delete(testFileId);
	};

}
