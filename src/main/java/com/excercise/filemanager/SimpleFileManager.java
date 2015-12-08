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
	private FilesystemConnector filesystemConnector;
	
	@Autowired(required=true)
	public SimpleFileManager(NameRepository repository, UIDGenerator uidGenerator, FilesystemConnector connector) {
		this.nameRepository = repository;
		this.uidGenerator = uidGenerator;
		this.filesystemConnector = connector;
	}
	
	@Override
	public void add(String fileName, byte[] fileData) {
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
}
