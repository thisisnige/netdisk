package com.iamnige.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.iamnige.logger.Logger;

public class Md5Digest {
	public static String md5(String message) throws NoSuchAlgorithmException{
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(message.getBytes()); 
			byte b[] = md.digest(); 
			return byteArrayToHexString(b);
			
		} catch (NoSuchAlgorithmException e) { 
			Logger.logError(e.getMessage());
			throw e;
		} 
	}
	
	public static String fileMD5(File f) throws IOException, NoSuchAlgorithmException {
		
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		try {
			fileInputStream = new FileInputStream(f);
			digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
		} catch (FileNotFoundException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		
		byte[] buffer =new byte[bufferSize];
		try {
			while (digestInputStream.read(buffer) > 0);
		} catch (IOException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		messageDigest= digestInputStream.getMessageDigest();
		byte[] resultByteArray = messageDigest.digest();
		return byteArrayToHexString(resultByteArray);
	}
	
	private static String byteArrayToHexString(byte b[]){
		int i; 
		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) i+= 256; 
			if(i<16) 
				buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
		} 
		return buf.toString();
	}
}
