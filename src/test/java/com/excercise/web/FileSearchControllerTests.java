package com.excercise.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

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
public class FileSearchControllerTests {
	private MockMvc mockMvc;
	
	@Autowired
	FileManager fileManagerMock;
	
	@Test
	public void testFileSearchResults() throws Exception {
		String testFileNamePart = "test";

		Map<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("11test11.txt", "1");
		expectedMap.put("22test222.pdf", "22");
		
		
		Mockito.stub(fileManagerMock.find(testFileNamePart)).toReturn(expectedMap);
		
		mockMvc.perform(get("/find").param("fileName", testFileNamePart))
			.andExpect(view().name("searchResults"))
			.andExpect(model().attribute("resultList", expectedMap));
		
		Mockito.verify(fileManagerMock).find(testFileNamePart);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}

}
