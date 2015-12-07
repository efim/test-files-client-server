package com.excercise.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
class MD5ChecksumUIDGenerator implements UIDGenerator {

	@Override
	public String getUID(File file) {		
        String checksum = null;
        try {  
            checksum = DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException ex) {
        	//do nothing for now
        	//TODO: insert logging
        }
        return checksum;
	}

}
