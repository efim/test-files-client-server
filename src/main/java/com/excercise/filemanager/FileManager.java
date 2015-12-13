package com.excercise.filemanager;

import java.util.Map;
import java.util.Set;

public interface FileManager {
	public void add(String fileName, byte[] fileData);
	public boolean remove(String fileId);
	public byte[] retrieve(String fileId);
	public Map<String, String> find(String namePart);
	public String getNameById(String fileId);
}
