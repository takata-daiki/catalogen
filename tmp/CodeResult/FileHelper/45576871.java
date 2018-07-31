package de.htwaalen.selenium.ws.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;

public class FileHelper {
	/**
	 * Stores the given string into a file.
	 * @param fileContent Content of the stored file
	 * @param fileType Filetype without "." e.g. html, xml
	 * @param folderPath Path to the folder, to store the file at.
	 * @return Referenced File object
	 * @throws IOException
	 */
	public static File createTmpFile(String fileContent, String fileType, String folderPath) throws IOException{
		if(fileContent =="" || fileType =="" || folderPath == ""){
			return null;
		}
		String fileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), fileType);
		File file = new File(folderPath+fileName);
		BufferedWriter bw;

		if (!file.exists()) {
			file.createNewFile();
		}

		bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));		
		bw.write(fileContent);
		
		bw.close();
		return file;
	}
	public static File createTmpFile(String fileContent, String fileType) throws IOException{
		return FileHelper.createTmpFile(fileContent, fileType, "tmp/");		
	}
	/** Copy file content into a string.
	 *  @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
	 */ 
	 public static String readFileAsString(String filePath) throws java.io.IOException{
	     StringBuffer fileData = new StringBuffer(1000);
	     BufferedReader reader = new BufferedReader(
	             new FileReader(filePath));
	     char[] buf = new char[1024];
	     int numRead=0;
	     while((numRead=reader.read(buf)) != -1){
	         String readData = String.valueOf(buf, 0, numRead);
	         fileData.append(readData);
	         buf = new char[1024];
	     }
	     reader.close();
	     return fileData.toString();
	 }
}
