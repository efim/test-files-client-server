package com.excercise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excercise.TestFilesClientServerApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestFilesClientServerApplication.class)
@WebAppConfiguration
public class TestFilesClientServerApplicationTests {

	@Test
	public void contextLoads() {
		throw new RuntimeException("Example test is not implemented");
	}

}
