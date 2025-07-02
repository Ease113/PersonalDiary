package com.Java.practiseWebV3.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;

public class utility {
	
	/*
	 * Replace replaceable chars from string by using arrayList
	 */
	public String replace (String baseSQL, ArrayList<String> param) {
		
		String targetSQL = baseSQL;
		
		for (int i=0; i < param.size(); i++) {
			
			String replaceChar = String.format("'{%d}'", i);
			
			String replaceCharForInteger = String.format("{%d}", i);
			
			if (param.get(i).chars().allMatch(Character::isDigit)) {
				targetSQL = targetSQL.replace(replaceCharForInteger, param.get(i));
			} else {
				targetSQL = targetSQL.replace(replaceChar, '"' + param.get(i) + '"');
			}
		}
		
		return targetSQL; 
	}
	
	/*
	 * Replace the one replaceable char from string by using single word
	 * 
	 * if your str has letter "'" in the middle of the str, then please use replace method instead
	 */
	public String replaceOne (String baseSQL, String replaceText) {
		
		String targetSQL = baseSQL;
		
		targetSQL = targetSQL.replace("{0}", replaceText);
		
		return targetSQL; 
	}
	
	public String encoder (byte[] args) {
		
		byte[] byteString = args;
		String encodedByteString = Base64.getEncoder().encodeToString(byteString);
		
		return encodedByteString;
	}
	
	public String decoder (String args) throws UnsupportedEncodingException {
		
		String beforeDecode = args;
		
		byte[] afterDecode = Base64.getDecoder().decode(beforeDecode);
		
		String decodedString = new String(afterDecode, "UTF-8");
		
		return decodedString;
		
	}
}
