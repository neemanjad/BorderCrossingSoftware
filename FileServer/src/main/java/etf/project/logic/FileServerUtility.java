package etf.project.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;

import com.google.gson.Gson;

import etf.project.model.document.Document;

public class FileServerUtility{
	
	private static final String DOCUMENTS_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/FileServer/DOCUMENTS";
	private static final Logger LOGGER = Logger.getLogger(FileServerUtility.class.getName());	
	
	public ArrayList<Document> getAllDocuments(){

		try {
			ArrayList<Document> documents = new ArrayList<>();
			File[] documentFiles = new File(DOCUMENTS_PATH).listFiles();
			
			if(documentFiles.length == 0)
				return null;
			
			for(File documentFile : documentFiles) {
				
				Document document = readDocument(documentFile);
				if(document != null)
					documents.add(document);
			}
			
			return documents;
			
		} catch(NullPointerException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			return null;
		}
	}
	
	private static Document readDocument(File file) {
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			String filePath = DOCUMENTS_PATH + file.getName() + "DECOMPRESSED";
			FileOutputStream outputStream = new FileOutputStream(filePath);
			
			InflaterInputStream decompresser = new InflaterInputStream(inputStream);
			int contents;
			
			while((contents = decompresser.read()) != -1)
				outputStream.write(contents);
			
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			Gson gson = new Gson();
			
			Document document = gson.fromJson(reader, Document.class);
			outputStream.close();
			
			inputStream.close();
			decompresser.close();
			
			File tempFile = new File(filePath);
			if(tempFile.exists())
				tempFile.delete();
			
			return document;
			
		} catch(FileNotFoundException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			return null;
		} catch(IOException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			return null;
		}
	}

}
