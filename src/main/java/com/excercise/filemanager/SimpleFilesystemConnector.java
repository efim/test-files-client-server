package com.excercise.filemanager;

import org.springframework.stereotype.Component;

@Component
public class SimpleFilesystemConnector implements FilesystemConnector {

	@Override
	public void write(String filename, byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] read(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

}
