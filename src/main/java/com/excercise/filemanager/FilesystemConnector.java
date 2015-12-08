package com.excercise.filemanager;

interface FilesystemConnector {
	
	public void write(String filename, byte[] data);
	
	public byte[] read(String fileId);
	
}
