package com.excercise.filemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdByName(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsName(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsId(String fileId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(String fileName, String fileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String fileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Entry<String, String>> find(String namePart) {
		// TODO Auto-generated method stub
		return null;
	}

}
