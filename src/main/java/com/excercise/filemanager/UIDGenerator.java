package com.excercise.filemanager;

import java.io.File;

interface UIDGenerator {
	public String getUID(byte[] fileData);
}
