package com.imagevideoapp.utils;

import javax.xml.bind.DatatypeConverter;

public class AESEncrypter {
	   
	public static String encrypt(String Data) throws Exception {
		byte[] encValue =Data.getBytes("UTF-8");
		String encryptedValue =DatatypeConverter.printBase64Binary(encValue);
	      return encryptedValue;
	}
	 
	public static String decrypt(String encryptedData) throws Exception {
		byte[] decValue = DatatypeConverter.parseBase64Binary(encryptedData);
    String decryptedValue = new String(decValue,"UTF-8");
    return decryptedValue;
    
	}
	
}
