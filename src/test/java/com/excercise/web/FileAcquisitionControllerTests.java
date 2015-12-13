package com.excercise.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.excercise.TestFilesClientServerApplication;
import com.excercise.filemanager.FileManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class FileAcquisitionControllerTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	FileManager fileManagerMock;
	
	@Test
	public void testDeleteFileById() throws Exception {
		String testFileId = "1";
		//i don't need to stub it, just need to verify calls
		//Create MultipartFile to pass as a parameter!!!
		byte[] content = "Hello World!".getBytes();
		String fileName = "filename.txt";
		
		MultipartFile uploadedFile = new MockMultipartFile(fileName, content);
		
		
		mockMvc.perform(post("/upload").param("fileId", testFileId))
			.andExpect(model().attribute("uploadedFile", uploadedFile))
			.andExpect(view().name("homepage"));
		
		Mockito.verify(fileManagerMock).add(fileName, content);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
		
	}

}
