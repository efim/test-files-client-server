package com.excercise.filemanager;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Component;

@Component
public class SimpleFilesystemConnector implements FilesystemConnector {

	@Override
	public void write(String fileId, byte[] data) {

		Path file = FileSystems.getDefault().getPath("", fileId);

		try {
			Files.write(file, data, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			// TODO add logging
		}
	}

	@Override
	public byte[] read(String fileId) {
		Path file = FileSystems.getDefault().getPath("", fileId);

		try {
			return Files.readAllBytes(file);
		} catch (IOException e) {
			// TODO add logging
		}

		return null;
	}

	@Override
	public void delete(String fileId) {

		Path file = FileSystems.getDefault().getPath("", fileId);
		try {
			Files.delete(file);
		} catch (IOException e) {
			// TODO add logging
		}
	}

}
