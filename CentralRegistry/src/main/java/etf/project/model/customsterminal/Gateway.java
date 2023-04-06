package etf.project.model.customsterminal;

import java.io.Serializable;

/**
 * @author NemanjaDavidovic
 * @since 25.12.2022.
 * @version 1.0
 * 
 * <p> In real life, the class Gateway is a facility where workers of border police or customs administration are housed.
 * If necessary It would be a BaseClass for classes Entrance and Exit. </p>
 * 
 * @param isPolice indicates that there is or not exact one police officer, depend on the values of parameter, represent police control
 * @param isCustoms indicates that there is or not exact one customs officer, depend on the value of parameter, represent customs control
 * 
 * <p> In the part of the project task related to the CentralRegister, 
 * there is no further elaboration of police and customs control, therefore these terms are modeled as simple attributes. </p>
 * 
 * <p> It is necessary that in one object instance of the Gateway type @param isPolice and @param @isCustoms set
 * on true for successful operation and working. After that, @param isOpen should be set on true. </p>
 *  
 */


public class Gateway implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idGateway;
	private boolean isOpen, isPolice, isCustoms;
	
	public Gateway() {
		super();
	}
	
	public Gateway(int id, boolean op, boolean pol, boolean cust) {
		this.idGateway = id;
		this.isOpen = op;
		this.isPolice = pol;
		this.isCustoms = cust;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isPolice() {
		return isPolice;
	}

	public void setPolice(boolean isPolice) {
		this.isPolice = isPolice;
	}

	public boolean isCustoms() {
		return isCustoms;
	}

	public void setCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	public int getIdGateway() {
		return idGateway;
	}

	public void setIdGateway(int idGateway) {
		this.idGateway = idGateway;
	}

	@Override
	public String toString() {
		return "Gateway [idGateway=" + idGateway + ", isOpen=" + isOpen + ", isPolice=" + isPolice + ", isCustoms=" + isCustoms + "]";
	}
}
