package etf.project.logic;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import etf.project.model.document.Document;

@Path("/documents")
public class FileServerRest {

	FileServerUtility service;
	
	public FileServerRest() {
		service = new FileServerUtility();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Document> getAllDocuments(){
		return service.getAllDocuments();
	}
	
}
