package com.excercise.filemanager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class FileManagerTest {
	@Autowired
	FileManager fileManager;

	@Test
	public void testAdd() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testRemove() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testRetrieve() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testFind() {
		throw new RuntimeException("this test is not implemented yet");
	};
}
