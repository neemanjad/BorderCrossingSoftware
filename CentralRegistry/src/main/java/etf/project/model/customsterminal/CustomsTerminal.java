package etf.project.model.customsterminal;

import java.io.Serializable;

/**
 * @author NemanjaDavidovic
 * @since 22.12.2022.
 * @version 1.0
 * 
 * <p> The CustomsTerminal represent in real life border crossing with entrances and exits. </p>
 * 
 * @param isTerminalOpen indicates whether the terminal is open
 * @param isTerminalRunning indicates whether the terminal is open, but it is not running because there is some problem, or checking persons
 * 
 */

public class CustomsTerminal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idCustomsTerminal;
	private int numberOfEntrances, numberOfExits;
	private String terminalName;
	private boolean isTerminalOpen, isTerminalRunning;
	
	private Gateway[] entrances;
	private Gateway[] exits;
	
	
	public CustomsTerminal() {
		super();
	}
	
	public CustomsTerminal(long id, int entrNmb, int exitNmb, String name, boolean open, boolean run, Gateway[] entr, Gateway[] exit) {
		super();
		this.idCustomsTerminal = id;
		this.numberOfEntrances = entrNmb;
		this.numberOfExits = exitNmb;
		this.terminalName = name;
		this.isTerminalOpen = open;
		this.isTerminalRunning = run;
		this.entrances = entr;
		this.exits = exit;
	}
	
	public CustomsTerminal(long id, int entrNmb, int exitNmb, String name, boolean open, boolean run) {
		super();
		this.idCustomsTerminal = id;
		this.numberOfEntrances = entrNmb;
		this.numberOfExits = exitNmb;
		this.terminalName = name;
		this.isTerminalOpen = open;
		this.isTerminalRunning = run;
		
		this.entrances = new Gateway[entrNmb];
		for(int i = 0; i < this.numberOfEntrances; i++)
			this.entrances[i] = new Gateway(i+1, false, false, false);
		
		this.exits = new Gateway[exitNmb];
		for(int i = 0; i < this.numberOfExits; i++)
			this.exits[i] = new Gateway(i+1, false, false, false);
	}
	
	public boolean isTerminalRunning() {
		return isTerminalRunning;
	}

	public void setTerminalRunning(boolean isTerminalRunning) {
		this.isTerminalRunning = isTerminalRunning;
	}

	public long getIdCustomsTerminal() {
		return idCustomsTerminal;
	}

	public void setIdCustomsTerminal(long idCustomsTerminal) {
		this.idCustomsTerminal = idCustomsTerminal;
	}

	public int getNumberOfEntrances() {
		return numberOfEntrances;
	}

	public void setNumberOfEntrances(int numberOfEntrances) {
		this.numberOfEntrances = numberOfEntrances;
	}

	public int getNumberOfExits() {
		return numberOfExits;
	}

	public void setNumberOfExits(int numberOfExits) {
		this.numberOfExits = numberOfExits;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public boolean isTerminalOpen() {
		return isTerminalOpen;
	}

	public void setTerminalOpen(boolean isTerminalOpen) {
		this.isTerminalOpen = isTerminalOpen;
	}
	
	public Gateway[] getEntrances() {
		return entrances;
	}

	public void setEntrances(Gateway[] entrances) {
		this.entrances = entrances;
	}

	public Gateway[] getExits() {
		return exits;
	}

	public void setExits(Gateway[] exits) {
		this.exits = exits;
	}

	@Override
	public String toString() {
		return "CustomsTerminal [idCustomsTerminal=" + idCustomsTerminal + ", numberOfEntrances=" + numberOfEntrances
				+ ", numberOfExits=" + numberOfExits + ", terminalName=" + terminalName + ", isTerminalOpen="
				+ isTerminalOpen + ", entrances=" + entrances + ", exits=" + exits + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrances == null) ? 0 : entrances.hashCode());
		result = prime * result + ((exits == null) ? 0 : exits.hashCode());
		result = prime * result + (int) (idCustomsTerminal ^ (idCustomsTerminal >>> 32));
		result = prime * result + (isTerminalOpen ? 1231 : 1237);
		result = prime * result + numberOfEntrances;
		result = prime * result + numberOfExits;
		result = prime * result + ((terminalName == null) ? 0 : terminalName.hashCode());
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
		CustomsTerminal other = (CustomsTerminal) obj;
		if (entrances == null) {
			if (other.entrances != null)
				return false;
		} else if (!entrances.equals(other.entrances))
			return false;
		if (exits == null) {
			if (other.exits != null)
				return false;
		} else if (!exits.equals(other.exits))
			return false;
		if (idCustomsTerminal != other.idCustomsTerminal)
			return false;
		if (isTerminalOpen != other.isTerminalOpen)
			return false;
		if (numberOfEntrances != other.numberOfEntrances)
			return false;
		if (numberOfExits != other.numberOfExits)
			return false;
		if (terminalName == null) {
			if (other.terminalName != null)
				return false;
		} else if (!terminalName.equals(other.terminalName))
			return false;
		return true;
	}
}
