package com.excercise.filemanager;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleFileManager implements FileManager {

	private NameRepository nameRepository;
	private UIDGenerator uidGenerator;
	
	@Autowired(required=true)
	public SimpleFileManager(NameRepository repository, UIDGenerator uidGenerator) {
		this.nameRepository = repository;
		this.uidGenerator = uidGenerator;
	}
	
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
	public Set<Map.Entry<String, String>> find(String namePart) {
		// TODO Auto-generated method stub
		return null;
	}

	private void write(File file) {
		
	}

}
