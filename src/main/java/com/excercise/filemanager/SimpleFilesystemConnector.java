package com.excercise.filemanager;

import org.springframework.stereotype.Component;

@Component
public class SimpleFilesystemConnector implements FilesystemConnector {

	@Override
	public void write(String fileId, byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] read(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String fileId) {
		// TODO Auto-generated method stub
		
	}

}
