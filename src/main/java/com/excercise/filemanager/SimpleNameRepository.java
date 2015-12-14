package com.excercise.filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
class SimpleNameRepository implements NameRepository {
	
	private String saveFileName;
	private Map<String, String> nameToIdMap;
	
	@Autowired
	@Override
	public void setSaveFileName(@Value("${NameRepository.saveFileName}") String saveFileName) {
		this.saveFileName = saveFileName;
	}
	
	public SimpleNameRepository() {
		nameToIdMap = new HashMap<String, String>();
	}
	
	public SimpleNameRepository(Map<String, String> nameToIdMap) {
		this.nameToIdMap = nameToIdMap;
	}

	@Override
	public Set<String> getNamesById(String fileId) {
		return nameToIdMap.keySet().stream()
				.filter(key -> nameToIdMap.get(key).equals(fileId))
				.collect(Collectors.toSet());
	}

	@Override
	public String getIdByName(String fileName) {
		return nameToIdMap.get(fileName);
	}

	@Override
	public boolean containsName(String fileName) {
		return nameToIdMap.containsKey(fileName);
	}

	@Override
	public boolean containsId(String fileId) {
		return nameToIdMap.containsValue(fileId);
	}

	@Override
	public void add(String fileName, String fileId) {
		nameToIdMap.put(fileName, fileId);
	}

	@Override
	public void remove(String fileId) {
		while (nameToIdMap.values().remove(fileId));
	}

	@Override
	public Map<String, String> find(String namePart) {		
		Map<String, String> result = Maps.filterKeys(nameToIdMap, e -> e.contains(namePart)); 
		return result;
	}

	@Override
	public void saveToDisk() {
		Properties properties = new Properties();
		properties.putAll(nameToIdMap);
		try {
			properties.store(new FileOutputStream(saveFileName), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void loadFromDisk() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(saveFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String key : properties.stringPropertyNames()) {
			nameToIdMap.put(key, properties.get(key).toString());
		}		
	}

}
