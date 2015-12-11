package com.excercise.filemanager;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleFileManager implements FileManager {

	private static int maxSearchLenght = 25;
	
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
		
		String fileId = uidGenerator.getUID(fileData);
		if (nameRepository.containsId(fileId) && nameRepository.containsName(fileName)) {
			return;
		} else if (nameRepository.containsId(fileId)) {
			nameRepository.add(fileName, fileId);			
		} else {
			nameRepository.add(fileName, fileId);
			filesystemConnector.write(fileId, fileData);
		}
		
	}

	@Override
	public void remove(String fileId) {
		nameRepository.remove(fileId);
		filesystemConnector.delete(fileId);
	}

	@Override
	public byte[] retrieve(String fileId) {
		if (nameRepository.containsId(fileId)) {
			return filesystemConnector.read(fileId);
		}
		return null;
	}

	@Override
	public Set<Map.Entry<String, String>> find(String namePart) {
		Set<Map.Entry<String, String>> result = nameRepository.find(namePart);
		
		return result.stream().limit(maxSearchLenght).collect(Collectors.toSet());
	}
}
