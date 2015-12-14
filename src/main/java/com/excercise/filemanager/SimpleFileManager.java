package com.excercise.filemanager;

import java.util.Map;
import java.util.Optional;
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
	public boolean remove(String fileId) {
		if (!nameRepository.containsId(fileId)) {
			return false;
		}
		
		nameRepository.remove(fileId);
		filesystemConnector.delete(fileId);
		return true;
	}

	@Override
	public byte[] retrieve(String fileId) {
		if (nameRepository.containsId(fileId)) {
			return filesystemConnector.read(fileId);
		}
		return null;
	}

	@Override
	public Map<String, String> find(String namePart) {
		Map<String, String> result = nameRepository.find(namePart);
		
		result.keySet().retainAll(result.keySet().stream().limit(maxSearchLenght).collect(Collectors.toSet()));
		
		return result;
	}

	@Override
	public String getNameById(String fileId) {
		
		Optional<String> someName = nameRepository.getNamesById(fileId).stream().findAny();
		if (!someName.isPresent()) {
			return null;
		} else {
			return someName.get();
		}		
	}
}
