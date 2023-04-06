package etf.project.model.messageuser;

import java.io.Serializable;

/**
 * @author NemanjaDavidovic
 * sender,receiver
 *
 */

public class MessageUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idMessageUser, idGateway;
	private String terminalName, typeOfGateway, occupation;
	
	public MessageUser() {
		super();
	}

	public MessageUser(int idMessageUser, int idGateway, String terminalName, String typeOfGateway, String occupation) {
		super();
		this.idMessageUser = idMessageUser;
		this.idGateway = idGateway;
		this.terminalName = terminalName;
		this.typeOfGateway = typeOfGateway;
		this.occupation = occupation;
	}

	public int getidMessageUser() {
		return idMessageUser;
	}

	public void setidMessageUser(int idMessageUser) {
		this.idMessageUser = idMessageUser;
	}

	public int getIdGateway() {
		return idGateway;
	}

	public void setIdGateway(int idGateway) {
		this.idGateway = idGateway;
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

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGateway;
		result = prime * result + idMessageUser;
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((terminalName == null) ? 0 : terminalName.hashCode());
		result = prime * result + ((typeOfGateway == null) ? 0 : typeOfGateway.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageUser other = (MessageUser) obj;
		if (idGateway != other.idGateway)
			return false;
		if (idMessageUser != other.idMessageUser)
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (terminalName == null) {
			if (other.terminalName != null)
				return false;
		} else if (!terminalName.equals(other.terminalName))
			return false;
		if (typeOfGateway == null) {
			if (other.typeOfGateway != null)
				return false;
		} else if (!typeOfGateway.equals(other.typeOfGateway))
			return false;
		return true;
	}
	
	

}
