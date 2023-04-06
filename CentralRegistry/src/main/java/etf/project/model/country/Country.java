package etf.project.model.country;

import java.util.ArrayList;

import etf.project.model.customsterminal.CustomsTerminal;

/**
 * @author NemanjaDavidovic
 * @since 01.01.2023.
 * @version 1.0
 * 
 * <p> As the name suggest, the class Country is represent of any Country in the World. </p>
 *
 */

public class Country {
	
	private ArrayList<CustomsTerminal> terminals;
	private String nameOfCountry;
	
	public Country() {
		super();
		this.nameOfCountry = "";
		this.terminals = new ArrayList<>();
	}
	
	public Country(String name, ArrayList<CustomsTerminal> terminals) {
		this.nameOfCountry = name;
		this.terminals = terminals;
	}

	public ArrayList<CustomsTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(ArrayList<CustomsTerminal> terminals) {
		this.terminals = terminals;
	}

	public String getNameOfCountry() {
		return nameOfCountry;
	}

	public void setNameOfCountry(String nameOfCountry) {
		this.nameOfCountry = nameOfCountry;
	}
}
