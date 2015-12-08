package com.excercise.filemanager;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class NameRepositoryTests {
	
    @Before
    public void setUp() {
        //set up name repository mock
    	//myServiceStub = new MyServiceStub();
        
    	//create my file manager
    	//myLauncher = new MyLauncher(myServiceStub);
    }

	
	@Test
	public void testGetNamesById() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testGetIdByName() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testContainsName() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testContainsId() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testAdd() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
	@Test
	public void testRemove() {
		throw new RuntimeException("this test is not implemented yet");
	};
	
}
