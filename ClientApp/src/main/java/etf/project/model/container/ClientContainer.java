package etf.project.model.container;

import java.io.Serializable;

/**
 * @author NemanjaDavidovic
 * @since 24.01.2023.
 * 
 * <p> Serves for the transmission of the necessary application data by
 * 	police/customs officers to the CentralRegisty. </p>
 * 
 * @param occupation indicates is a new logging as police or customs officer
 * @param type indicates is entrance or exit at customs terminal
 * @param id_gateway indicates which gateway will be used
 * @param terminalName indicates the name of the terminal
 * 
 */

public class ClientContainer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String occupation, type, terminalName;
	private int id_gateway;
	
	public ClientContainer() {
		super();
	}

	public ClientContainer(String occupation, String type, String terminalName, int id_gateway) {
		super();
		this.occupation = occupation;
		this.type = type;
		this.terminalName = terminalName;
		this.id_gateway = id_gateway;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public int getId_gateway() {
		return id_gateway;
	}

	public void setId_gateway(int id_gateway) {
		this.id_gateway = id_gateway;
	}
}
