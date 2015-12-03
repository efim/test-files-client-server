package com.excercise.filemanager;

import java.io.File;

public interface FileManager {
	public void add(File file);
	public void remove(String fileId);
	public File retrieve(String fileId);
	public FileEntry find(String namePart);
}
