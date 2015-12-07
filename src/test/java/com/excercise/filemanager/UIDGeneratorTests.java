package com.excercise.filemanager;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excercise.TestFilesClientServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class UIDGeneratorTests {
	
	@Autowired
	UIDGenerator uidGenerator;
	
	@Test
	public void testDoesntDepentOnName() throws IOException {
		//Should return same result for same file under different names
		File file1 = new File("test1.txt");
		File file2 = new File("test2.txt");
		
		String fileContent = "This is the content of file";
		FileUtils.writeStringToFile(file1, fileContent);
		FileUtils.writeStringToFile(file2, fileContent);
		
		//IOUtils.toByteArray(InputStream input).
		byte[] input1 = IOUtils.toByteArray(new FileInputStream(file1));
		byte[] input2 = IOUtils.toByteArray(new FileInputStream(file2));
		
		String firstUid = uidGenerator.getUID(input1);
		String secondUid = uidGenerator.getUID(input2);
		
		assertEquals(firstUid, secondUid);
		
		file1.delete();
		file2.delete();
	};

}
