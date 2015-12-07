package com.excercise.filemanager;

import java.util.Set;

interface NameRepository {
	public Set<String> getNamesById(String fileId);
	public String getIdByName(String fileName);
	public boolean containsName(String fileName);
	public boolean containsId(String fileId);
	public void add(String fileName, String fileId);
	public void remove(String fileId);
}
