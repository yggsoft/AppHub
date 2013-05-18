package com.app.hub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMD5 {

	private File file;
	private static final int BUFFER_SIZE = 1024;

	public FileMD5(File file) {
		this.file = file;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.file == obj) {
			return true;
		}

		if (!(obj instanceof FileMD5)) {
			throw new RuntimeException("only compare file.");
		}

		return getFileMD5().equals(((FileMD5) obj).getFileMD5());
	}

	
	public String getFileMD5() {
		MessageDigest digest = null;
		InputStream is = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			is = new FileInputStream(this.file);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DigestInputStream dis = new DigestInputStream(is, digest);
		byte[] bytes = new byte[BUFFER_SIZE];

		try {
			while (dis.read(bytes) != -1)
				;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest = dis.getMessageDigest();
		byte[] result = digest.digest();

		// return bytesToString(result);
		return new String(result);
	}

	private String bytesToString(byte[] result) {

		// TODO Auto-generated method stub
		return null;
	}

}
