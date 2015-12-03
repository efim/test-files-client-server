package com.excercise.filemanager;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class SimpleFileManager implements FileManager {

	@Override
	public void add(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String fileId) {
		// TODO Auto-generated method stub

	}

	@Override
	public File retrieve(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileEntry find(String namePart) {
		// TODO Auto-generated method stub
		return null;
	}

	private void write(File file) {
		
	}

}
