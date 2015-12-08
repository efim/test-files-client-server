package com.excercise.filemanager;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
class MD5ChecksumUIDGenerator implements UIDGenerator {

	@Override
	public String getUID(byte[] fileData) {		
        String checksum = null;
        try {
        checksum = DigestUtils.md5Hex(new ByteArrayInputStream(fileData));
        } catch (IOException e) {
        	//do nothing for now
        	//TODO: add logging
        }

        return checksum;
	}
	
}
