package etf.project.model.container;

import java.io.Serializable;

import etf.project.model.document.Document;

/**
 * @author NemanjaDavidovic
 * @since 27.01.2023. 
 * 
 * <p> It serves to transfer data from the TestApp to the ClientApp, apropos from the person who is trying to pass through customs terminal. </p>
 * 
 * @param document represent the document from the person who is trying to pass through the customs terminal
 * @param terminalName represent the name of terminal where going across is happening
 * @param typeOfGateway represent is person going in or out of the country
 * @param idGateway represent the number of entrance/exit
 * 
 * 
 *
 */

public class AccessContainer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Document document;
	private String terminalName, typeOfGateway;
	private int idGateway;
	
	public AccessContainer() {
		super();
	}
	
	public AccessContainer(Document document, String terminalName, String typeOfGateway, int id) {
		super();
		this.document = document;
		this.idGateway = id;
		this.terminalName = terminalName;
		this.typeOfGateway = typeOfGateway;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTypeOfGateway() {
		return typeOfGateway;
	}

	public void setTypeOfGateway(String typeOfGateway) {
		this.typeOfGateway = typeOfGateway;
	}

	public int getIdGateway() {
		return idGateway;
	}

	public void setIdGateway(int idGateway) {
		this.idGateway = idGateway;
	}

	@Override
	public String toString() {
		return "AccessContainer "
				+ "[document=" + document + ", terminalName=" + terminalName + ", typeOfGateway=" + typeOfGateway + ", idGateway=" + idGateway + "]";
	}
}
