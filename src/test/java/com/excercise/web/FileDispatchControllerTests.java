package com.excercise.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class FileDispatchControllerTests {
	private MockMvc mockMvc;
	
	@Autowired
	FileManager fileManagerMock;
	
	@Test
	public void testAcquireFileById() throws Exception {
		String testFileId = "1";
		byte[] data = javax.xml.bind.DatatypeConverter.parseHexBinary("000100100100");

		Mockito.stub(fileManagerMock.retrieve(testFileId)).toReturn(data);
		
		mockMvc.perform(get("/download").param("fileId", testFileId))
			.andExpect(view().name("homepage"))
			.andExpect(content().bytes(data));
		
		Mockito.verify(fileManagerMock).retrieve(testFileId);
		Mockito.verifyNoMoreInteractions(fileManagerMock);
	}

	

}
