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
	private PredicateFactory<String> nameSearchPredicateFactory;
	
	@Autowired
	@Override
	public void setSaveFileName(@Value("${NameRepository.saveFileName}") String saveFileName) {
		this.saveFileName = saveFileName;
	}
	
	@Autowired
	public SimpleNameRepository(PredicateFactory<String> nameSearchPredicateFactory) {
		this.nameToIdMap = new HashMap<String, String>();
		this.nameSearchPredicateFactory = nameSearchPredicateFactory;

		
	}
	
	public SimpleNameRepository(Map<String, String> nameToIdMap, PredicateFactory<String> nameSearchPredicateFactory) {
		this.nameToIdMap = nameToIdMap;
		this.nameSearchPredicateFactory = nameSearchPredicateFactory;
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
		Map<String, String> result = Maps.filterKeys(nameToIdMap, nameSearchPredicateFactory.getPredicate(namePart)); 
		return result;
	}

	@Override
	public void saveToDisk() {
		Properties properties = new Properties();
		properties.putAll(nameToIdMap);
		try (FileOutputStream out = new FileOutputStream(saveFileName)){
			properties.store(out, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void loadFromDisk() {
		Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(saveFileName)){
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String key : properties.stringPropertyNames()) {
			nameToIdMap.put(key, properties.get(key).toString());
		}		
	}

}
