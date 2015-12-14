package com.excercise.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import com.excercise.filemanager.FileManager;


public class FileDispatchControllerTests {	
	@Test
	public void testAcquireFileById() throws Exception {
		String testFileId = "1";
		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");

		FileManager fileManagerMock = Mockito.mock(FileManager.class);
		Mockito.stub(fileManagerMock.retrieve(testFileId)).toReturn(data);
		
		FileDispatchController controller = new FileDispatchController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/download").param("fileId", testFileId))
			.andExpect(view().name("downloadResults"))
			.andExpect(model().attribute("downloadSuccess", is(true)))
			.andExpect(content().bytes(data));
		
		Mockito.verify(fileManagerMock).retrieve(testFileId);
	}
	
	@Test
	public void testAcquireFileNonexistent() throws Exception {
		String testFileId = "1";

		FileManager fileManagerMock = Mockito.mock(FileManager.class);
		Mockito.stub(fileManagerMock.retrieve(testFileId)).toReturn(null);
		
		FileDispatchController controller = new FileDispatchController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/download").param("fileId", testFileId))
			.andExpect(view().name("downloadResults"))
			.andExpect(model().attribute("error", "FileNotFound"))
			.andExpect(model().attribute("downloadSuccess", is(false)));
		
		Mockito.verify(fileManagerMock).retrieve(testFileId);
	}

}
