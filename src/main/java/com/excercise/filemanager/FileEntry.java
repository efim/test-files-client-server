package com.excercise.filemanager;

class FileEntry {
	private String filename;
	private String fileId;
	
	public FileEntry(String filename, String fileId) {
		this.filename = filename;
		this.fileId = fileId;
	}

	public String getFilename() {
		return filename;
	}

	public String getFileId() {
		return fileId;
	}
	
	
}
