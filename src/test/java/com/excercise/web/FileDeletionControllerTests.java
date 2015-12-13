package com.excercise.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.excercise.TestFilesClientServerApplication;
import com.excercise.filemanager.FileManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestFilesClientServerApplication.class)
public class FileDeletionControllerTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	FileManager fileManagerMock;
	
	@Test
	public void testDeleteFileById() throws Exception {
		String testFileId = "1";
		//how do I set up mock fileManager???
		
		mockMvc.perform(post("/delete").param("fileId", testFileId))
			.andExpect(model().attribute("deletionSussesfull", is(true)))
			.andExpect(view().name("homepage"));
		
		Mockito.verify(fileManagerMock).remove(testFileId);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}

}
