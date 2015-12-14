package com.excercise.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import com.excercise.filemanager.FileManager;


public class FileSearchControllerTests {
		
	@Test
	public void testFileSearchResults() throws Exception {
		String testFileNamePart = "test";
		FileManager fileManagerMock = Mockito.mock(FileManager.class);

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("11test11.txt", "1");
		expectedMap.put("22test222.pdf", "22");
		
		Mockito.stub(fileManagerMock.find(testFileNamePart)).toReturn(expectedMap);
		
		FileSearchController controller = new FileSearchController(fileManagerMock);
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/search").param("fileName", testFileNamePart))
			.andExpect(view().name("searchResults"))
			.andExpect(model().attribute("resultList", expectedMap));
		
		Mockito.verify(fileManagerMock).find(testFileNamePart);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}

}
