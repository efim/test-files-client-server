package com.excercise.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.excercise.filemanager.FileManager;

public class FileAcquisitionControllerTests {
	
	@Test
	public void testUploadFile() throws Exception {
		FileManager fileManagerMock = Mockito.mock(FileManager.class);

		byte[] content = "Hello World!".getBytes();
		String fileName = "filename.txt";
		
		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName, "multipart/form-data", content);
	
		FileAcquisitionController controller = new FileAcquisitionController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
				.file(uploadedFile))
				.andExpect(model().attribute("uploadSuccessfull", is(true)))
				.andExpect(view().name("homepage"));
		
		Mockito.verify(fileManagerMock).add(fileName, content);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
		
	}

}
