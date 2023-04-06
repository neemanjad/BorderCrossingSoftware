package etf.project.logic;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import etf.project.model.passenger.Passenger;
import etf.project.service.CentralRegistryService;

/**
 * @author NemanjaDavidovic
 * @since 24.01.2023.
 * 
 * <p> Implementation of the service for the only REST functionality for the administrator application. Use path 'wanted'. </p>
 * 
 */


@Path("/wanted")
public class CentralRegistryRest {
	
	CentralRegistryService service;
	
	public CentralRegistryRest() {
		service = new CentralRegistryService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Passenger> getPassengersOnWarrant(){
		
		Passenger[] utilityResult = service.getPassengersOnWarrants();
		ArrayList<Passenger> restResult = new ArrayList<>();
		
		for(int i = 0; i < utilityResult.length; i++)
			restResult.add(utilityResult[i]);
		
		return restResult;
	}

}
