package com.excercise.filemanager;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

//google.common.collect.interner[s]
@Component
public class SimpleFileManager implements FileManager {
	Interner<String> interner;

	private static int maxSearchLenght = 25;

	private NameRepository nameRepository;
	private UIDGenerator uidGenerator;
	private FilesystemConnector filesystemConnector;

	@Autowired(required = true)
	public SimpleFileManager(NameRepository repository, UIDGenerator uidGenerator, FilesystemConnector connector) {
		this.nameRepository = repository;
		this.uidGenerator = uidGenerator;
		this.filesystemConnector = connector;

		this.interner = Interners.newWeakInterner();

		repository.loadFromDisk();
	}

	@Override
	public void add(String fileName, byte[] fileData) {

		String fileId = uidGenerator.getUID(fileData);

		synchronized (interner.intern(fileId)) {
			if (nameRepository.containsId(fileId) && nameRepository.containsName(fileName)) {
				return;
			} else if (nameRepository.containsId(fileId)) {
				nameRepository.add(fileName, fileId);
			} else {
				nameRepository.add(fileName, fileId);
				filesystemConnector.write(fileId, fileData);
			}
			nameRepository.saveToDisk();
		}
	}

	@Override
	public boolean remove(String fileId) {

		synchronized (interner.intern(fileId)) {
			if (!nameRepository.containsId(fileId)) {
				return false;
			}

			nameRepository.remove(fileId);
			filesystemConnector.delete(fileId);
			nameRepository.saveToDisk();
		}
		return true;
	}

	@Override
	public byte[] retrieve(String fileId) {
		synchronized (interner.intern(fileId)) {

			if (nameRepository.containsId(fileId)) {
				return filesystemConnector.read(fileId);
			}
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

	@PreDestroy
	public void cleanUp() throws Exception {
		nameRepository.saveToDisk();
	}
}
