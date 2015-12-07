package com.excercise.filemanager;

import java.io.File;
import java.util.Map;
import java.util.Set;

public interface FileManager {
	public void add(File file);
	public void remove(String fileId);
	public File retrieve(String fileId);
	public Set<Map.Entry<String, String>> find(String namePart);
}
