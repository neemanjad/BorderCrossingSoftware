package etf.project.logic;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import etf.project.model.customsterminal.CustomsTerminal;
import etf.project.model.customsterminal.Gateway;
import etf.project.mylogger.MyLogger;
import etf.project.service.CentralRegistryService;
import etf.project.service.CentralRegistryServiceServiceLocator;

/**
 * @author NemanjaDavidovic
 * @since 15.01.2023.
 * 
 * <p> Serves to implement all necessary SOAP methods from other applications. </p>
 *
 */

public class AdminAppSoapUtility {

	private static final Scanner scanner = new Scanner(System.in);
	
	private CentralRegistryServiceServiceLocator locator;
	private CentralRegistryService service;
	
	public AdminAppSoapUtility(){
		super();
		
		try {
			locator = new CentralRegistryServiceServiceLocator();
			service = locator.getCentralRegistryService();
		} catch(ServiceException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
	
	//CentralRegistry - adding new customs terminal to a state customs terminals settings
	public boolean addCustomsTerminal() {
			
		try {
			
			CustomsTerminal terminal = new CustomsTerminal();
			System.out.println("Enter terminal name:");
			String terminalName = scanner.nextLine();
			
			if("".equals(terminalName))
				return false;
			terminal.setTerminalName(terminalName);
			
			System.out.println("Enter the number of terminal entrances: ");
			int entrNmb = Integer.parseInt(scanner.nextLine());
			if(entrNmb < 0)
				return false;
			terminal.setNumberOfEntrances(entrNmb);
			
			System.out.println("Enter the number of terminal exits: ");
			int exitNmb = Integer.parseInt(scanner.nextLine());
			if(exitNmb < 0)
				return false;
			terminal.setNumberOfExits(exitNmb);
			
			terminal.setTerminalRunning(false);
			terminal.setTerminalOpen(true);
			
			Gateway[] entrances = new Gateway[entrNmb];
			Gateway[] exits = new Gateway[exitNmb];
			
			for(int i = 0; i < entrNmb; i++)
				entrances[i] = new Gateway(false, i, true, false);
			
			for (int i = 0; i < exitNmb; i++)
				exits[i] = new Gateway(false, i, true, false);
			
			terminal.setEntrances(entrances);
			terminal.setExits(exits);
			
			terminal.setIdCustomsTerminal(System.currentTimeMillis());

			return service.addTerminal(terminal);
			
		} catch (RemoteException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch(NumberFormatException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
	//CentralRegistry - delete an existing customs terminal from state customs terminals
	public boolean deleteTerminal() {
		
		try {
			
			System.out.println("Enter the id of terminal you want to delete: ");
			long idTerminal = Long.parseLong(scanner.nextLine());
			
			return service.deleteTerminal(idTerminal);
						
		} catch(RemoteException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
	//CentralRegistry - updating an existing terminal to new values
	public boolean updateTerminal() {
		
		try {
			
			System.out.println("Enter the id of terminal you want to update: ");
			long idTerminal = Long.parseLong(scanner.nextLine());
			
			CustomsTerminal terminal = service.getTerminal(idTerminal);
			if(terminal == null)
				return false;
			
			String OPTION = "";
			do {
				
				System.out.println("Update name - 1");
				System.out.println("Update is terminal open - 2");
				System.out.println("Update is terminal running - 3");
				System.out.println("Update number of entrances - 4");
				System.out.println("Update number of exits - 5");
				System.out.println("Update special entrance or exit - 6");
				System.out.println("QUIT - END");
				
				System.out.println("Enter your chooice: ");
				OPTION = scanner.nextLine();
				
				switch(OPTION){
					
				case "1": 
					System.out.println("Old name is " + terminal.getTerminalName() + ". Enter the new name of terminal:");
					String newName = scanner.nextLine();
					terminal.setTerminalName(newName);
					break;
					
				case "2":
					System.out.println("Current state of TERMINAL-OPEN is " + terminal.isTerminalOpen() 
												+ ". If you want to change that, enter CHANGE");
					String openStatus = scanner.nextLine();
					if("CHANGE".equals(openStatus))
						terminal.setTerminalOpen(!terminal.isTerminalOpen());
					break;
					
				case "3":
					System.out.println("Current state of /'terminal running/' is " + terminal.isTerminalRunning() 
												+ ". If you want to change that, enter CHANGE");
					String runningStatus = scanner.nextLine();
					if("CHANGE".equals(runningStatus))
						terminal.setTerminalRunning(!terminal.isTerminalRunning());
					break;
					
				case "4":
					System.out.println("Old number of entrances is " + terminal.getNumberOfEntrances() + ". Enter the new number: ");
					int nmbEnt = Integer.parseInt(scanner.nextLine());
					
					if(nmbEnt < 0 || nmbEnt > 15)
						System.out.println("The number of entrances can not be under 0 or above 15. ERROR.");
					else {
						if(nmbEnt > terminal.getNumberOfEntrances()) {
						
							Gateway[] entrances = new Gateway[nmbEnt];
							for(int i = 0; i < terminal.getNumberOfEntrances(); i++)
								entrances[i] = terminal.getEntrances()[i];
							
							for(int i = terminal.getNumberOfEntrances(); i < nmbEnt; i++)
								entrances[i] = new Gateway(false, i, false, false);	
							
							terminal.setEntrances(entrances);
							
						} else if(nmbEnt < terminal.getNumberOfEntrances())
							for(int i = nmbEnt; i < terminal.getNumberOfEntrances(); i++)
								terminal.getEntrances()[i] = null;
					}
					break;
					
				case "5": 
					System.out.println("Old number of exits is " + terminal.getNumberOfExits() + ". Enter the new number: ");
					int nmbExits = Integer.parseInt(scanner.nextLine());
					
					if(nmbExits < 0 || nmbExits > 15)
						System.out.println("The number of exits can not be under 0 or above 15. ERROR.");
					else {
						if(nmbExits > terminal.getNumberOfExits()) {
						
							Gateway[] exits = new Gateway[nmbExits];
							for(int i = 0; i < terminal.getNumberOfExits(); i++)
								exits[i] = terminal.getExits()[i];
							
							for(int i = terminal.getNumberOfExits(); i < nmbExits; i++)
								exits[i] = new Gateway(false, i, false, false);	
							
							terminal.setEntrances(exits);
							
						} else if(nmbExits < terminal.getNumberOfExits())
							for(int i = nmbExits; i < terminal.getNumberOfExits(); i++)
								terminal.getExits()[i] = null;
					}
					break;
					
				case "6":
					System.out.println("For update entrance, enter ENTRANCE, for exit - EXIT");
					String type = scanner.nextLine();
					
					System.out.println("Enter the id of entrance/exit you want to change: ");
					int idGateway = Integer.parseInt(scanner.nextLine());
					
					System.out.println("Change customs state - 1");
					System.out.println("Change police state - 2");
					System.out.println("Change open state - 3");
					System.out.println("Enter your chooice: ");
					String tempChooice = scanner.nextLine();

					String tempOption = "";
					switch(type) {
					
					case "ENTRANCE":
						Gateway entrance = terminal.getEntrances()[idGateway];
						if(entrance != null)
							switch(tempChooice) {
							
							case "1": 
								System.out.println("Current customs state is " + entrance.isCustoms() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
								
								if("CHANGE".equals(tempOption))
									entrance.setCustoms(!entrance.isCustoms());
								break;
								
							case "2":
								System.out.println("Current police state is " + entrance.isPolice() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
								
								if("CHANGE".equals(tempOption))
									entrance.setPolice(!entrance.isPolice());
								break;
								
							case "3":
								System.out.println("Current open state is " + entrance.isOpen() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
								
								if("CHANGE".equals(tempOption))
									entrance.setOpen(!entrance.isOpen());
								break;
								
							default:
								System.out.println("Wrong option during choosing entrance");
							}
						terminal.getEntrances()[idGateway] = entrance;
						break;
						
					case "EXIT":
						Gateway exit = terminal.getExits()[idGateway];
						if(exit != null)
							switch(tempChooice) {
						
							case "1": 
								System.out.println("Current customs state is " + exit.isCustoms() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
							
								if("CHANGE".equals(tempOption))
									exit.setCustoms(!exit.isCustoms());
								break;
							
							case "2":
								System.out.println("Current police state is " + exit.isPolice() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
								
								if("CHANGE".equals(tempOption))
									exit.setPolice(!exit.isPolice());
								break;
							
							case "3":
								System.out.println("Current open state is " + exit.isOpen() + ". If you want change, enter CHANGE" );
								tempOption = scanner.nextLine();
								
								if("CHANGE".equals(tempOption))
									exit.setOpen(!exit.isOpen());
								break;
							
							default:
								System.out.println("Wrong option during choosing exit");
							}
						terminal.getExits()[idGateway] = exit;
						break;
						
					default:
					System.out.println("WRONG OPTION DURING CHOOSING ENTRANCE OR EXIT.");
					}
					break;
					
				case "END":
					System.out.println("Over changing entrance/exit." );
					break;
					
				default:
					System.out.println("You choosed unavailable option. Try again.");
				}
				
			} while("END".equals(OPTION));
			
			return service.updateTerminal(idTerminal, terminal);
			
		} catch(RemoteException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
	//CentralRegistry - getTerminal, show an existing terminal to administrator
	public void showTerminal() {

		try {
			
			System.out.println("Enter the id of terminal you want to show: ");
			long idTerminal = Long.parseLong(scanner.nextLine());
			
			CustomsTerminal terminal = service.getTerminal(idTerminal);
			if(terminal == null)
				System.out.println("There is no terminal with ID " + idTerminal);
			else {
				
				System.out.println("Terminal name: " + terminal.getTerminalName());
				System.out.println("Number of entrances: " + terminal.getNumberOfEntrances());
				System.out.println("Number of exits: " + terminal.getNumberOfExits());
				System.out.println("Terminal state: " + terminal.isTerminalRunning());
				
				for(int i = 0; i < terminal.getNumberOfEntrances(); i++) {
					
					System.out.println("\nEntrance: " + (i+1));
					System.out.println("Id entrance: " + terminal.getEntrances()[i].getIdGateway());
					System.out.println("Customs state: " + terminal.getEntrances()[i].isCustoms());
					System.out.println("Police state: " + terminal.getEntrances()[i].isPolice());
				}
				
				for (int i = 0; i < terminal.getNumberOfExits(); i++) {
					
					System.out.println("\nExit: " + (i+1));
					System.out.println("Id exit: " + terminal.getExits()[i].getIdGateway());
					System.out.println("Customs state: " + terminal.getExits()[i].isCustoms());
					System.out.println("Police state: " + terminal.getExits()[i].isPolice());
				}
			}
			
		} catch(RemoteException e) {
			MyLogger.setLogger(AdminAppSoapUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
}
