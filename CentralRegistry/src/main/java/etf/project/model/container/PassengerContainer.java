package etf.project.model.container;

import java.io.Serializable;

import etf.project.model.passenger.Passenger;

/**
 * @author NemanjaDavidovic
 * @since 25.01.2023. 
 * 
 * <p> Serves for getting checked passengers. </p>
 *
 */

public class PassengerContainer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Passenger passenger;
	private String terminalName, dateAsString, direction;
	
	public PassengerContainer() {
		super();
	}
	
	public PassengerContainer(Passenger passenger, String terminalName, String dateAsString, String direction) {
		super();
		this.passenger = passenger;
		this.terminalName = terminalName;
		this.dateAsString = dateAsString;
		this.direction = direction;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getDateAsString() {
		return dateAsString;
	}

	public void setDateAsString(String dateAsString) {
		this.dateAsString = dateAsString;
	}

	@Override
	public String toString() {
		return "PassengerContainer [passenger=" + passenger + ", terminalName=" + terminalName + ", dateAsString=" + dateAsString + "]";
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
