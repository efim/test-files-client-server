package com.excercise.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import com.excercise.filemanager.FileManager;

public class FileDeletionControllerTests {
	
	
	@Test
	public void testDeleteFileById() throws Exception {
		String testFileId = "1";
		FileManager fileManagerMock = Mockito.mock(FileManager.class);
		Mockito.stub(fileManagerMock.remove("1")).toReturn(true);
		
		FileDeletionController controller = new FileDeletionController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(post("/delete").param("fileId", testFileId))
			.andExpect(model().attribute("deletionSussesfull", is(true)))
			.andExpect(view().name("redirect:/"));
		
		Mockito.verify(fileManagerMock).remove(testFileId);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}
	
	@Test
	public void testDeleteFileByBadId() throws Exception {
		String testFileId = "1";
		FileManager fileManagerMock = Mockito.mock(FileManager.class);
		Mockito.stub(fileManagerMock.remove("1")).toReturn(false);
		
		FileDeletionController controller = new FileDeletionController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(post("/delete").param("fileId", testFileId))
			.andExpect(model().attribute("deletionSussesfull", is(false)))
			.andExpect(view().name("redirect:/"));
		
		Mockito.verify(fileManagerMock).remove(testFileId);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}

}
