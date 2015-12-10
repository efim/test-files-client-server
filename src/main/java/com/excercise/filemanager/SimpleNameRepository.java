package com.excercise.filemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
class SimpleNameRepository implements NameRepository {
	
	private Map<String, String> nameToIdMap;
	
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
	public Set<Entry<String, String>> find(String namePart) {
		
		Predicate<Map.Entry<String,String>> searchPredicate = e -> e.getKey().contains(namePart);
		
		return nameToIdMap.entrySet().stream()
				.filter(searchPredicate)
				.collect(Collectors.toSet());
	}

}
