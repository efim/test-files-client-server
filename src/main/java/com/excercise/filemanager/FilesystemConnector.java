package com.excercise.filemanager;

interface FilesystemConnector {
	
	public void write(String fileId, byte[] data);
	
	public byte[] read(String fileId);
	
	public void delete(String fileId);
	
}
