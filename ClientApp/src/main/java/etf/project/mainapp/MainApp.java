package etf.project.mainapp;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import etf.project.logic.ClientAppChatService;
import etf.project.logic.ClientAppRestUtility;
import etf.project.model.container.CustomsContainer;
import etf.project.model.container.PassengerContainer;
import etf.project.mylogger.MyLogger;
import etf.project.service.CentralRegistryService;
import etf.project.service.CentralRegistryServiceServiceLocator;

/**
 * @author NemanjaDavidovic
 * @since 25.01.2023.
 * 
 * <p> Main application for police or customs officers at the customs terminal. </p>
 *
 */

public class MainApp {
	
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		try {
			System.setProperty("java.security.policy", "resources/client_policyfile.txt");
			
			System.out.println("HELLO, the time when you started were " + new Date());
			System.out.println("Enter id of the entrance/exit gateway you want to login: ");
			int idGateway = Integer.parseInt(scanner.nextLine());
			
			System.out.println("ENTRANCE or EXIT?");
			String type = scanner.nextLine();
			
			System.out.println("If you are police officer, enter POLICE");
			System.out.println("If you are customs officer, enter CUSTOMS");
			System.out.println("Please, enter data: ");
			String occupation = scanner.nextLine();
			
			System.out.println("Enter the name of customs terminal: ");
			String terminalName = scanner.nextLine();
			
			CustomsContainer container = new CustomsContainer(idGateway, occupation, terminalName, type);
			
			CentralRegistryServiceServiceLocator locator = new CentralRegistryServiceServiceLocator();
			CentralRegistryService soapService = locator.getCentralRegistryService();
			
			boolean dataOK = soapService.isTerminalAndGatewayAvailable(container, false);
			if(!dataOK) {
				System.out.println("Some of data: ");
				System.out.println("Id entrance/exit: " + idGateway);
				System.out.println("Entrance/exit: " + type);
				System.out.println("Occupation: " + occupation);
				System.out.println("Terminal name: " + terminalName);
				System.out.println("WAS WRONG OR YOUR COLLEGE IS ALREADY WORKING ON IT. TRY AGAIN LATER. GOODBUY. " + new Date());
				
				return;
				
			} else {
				System.out.println("Now, you should enter your personal data to sign up to system. \n");
				
				System.out.println("Enter your username: ");
				String userName = scanner.nextLine();
				
				System.out.println("Enter your password: ");
				String password = scanner.nextLine();
				
				ClientAppRestUtility restService = new ClientAppRestUtility();
				
				boolean credentialsOK = restService.validateCredentials(userName, password);
				if(!credentialsOK) {
					System.out.println("Some of data: ");
					System.out.println("Username: " + userName);
					System.out.println("Password: " + password);
					System.out.println("WAS WRONG. TRY AGAIN LATER. GOODBUY. " + new Date());
					
					return;
					
				} else {
					
					System.out.println("WELCOME.");
					
					soapService.logLogin(container);
					String OPTION = "";
					
					ClientAppChatService chatService = new ClientAppChatService();
					if(!chatService.signUpForChat(container)) {
						System.out.println("Error at signing to chat. Try again later.");
						return;
					}
						
					do {
						showOptions();
						OPTION = scanner.nextLine();
						
						switch(OPTION) {
						
						case "1":
							if(chatService.sendMulticastMessage(container))
								System.out.println("Message successfully sended to all entrances/exits on the termnial " + terminalName);
							else
								System.out.println("Message is not sended. ERROR.");
							break;
							
						case "2":
							if(chatService.sendBroadcastMessage(container))
								System.out.println("Message successfully sended to all entrances/exits on all terminals.");
							else
								System.out.println("Message is not sended. ERROR.");
							break;
							
						case "3":
							if(chatService.sendUnicastMessage(container))
								System.out.println("Message successfully sended to the marked receiver.");
							else
								System.out.println("Message is not sended. ERROR.");
							break;
							
						case "4":
							soapService.logCheckOut(container);
							System.out.println("Goodbye " + userName + ". See you. ");
							OPTION = "END";
							return;
							
						case "5":
							boolean changePasswordOK = restService.changePassword(userName);
							if(changePasswordOK)
								System.out.println("The passwod " + password + " is changed. Remember it for new logging. Good luck.");
							else
								System.out.println("The password " + password + "is not changed.");
							break;
							
						case "6":
							PassengerContainer[] checkedPassengers = soapService.getCheckedPassengers();
							if(checkedPassengers == null)
								System.out.println("There is no checked passengers. Try again later.");
							else {
								
								int counter = 1;
								System.out.println("Passengers checked to today are: ");
								
								for(PassengerContainer item : checkedPassengers) {
									
									System.out.println("\nSerial number: " + counter++);
									System.out.println("Date of border crossing: " + item.getDateAsString());
									System.out.println("Terminal name: " + item.getTerminalName());
									System.out.println("Name: " + item.getPassenger().getName());
									System.out.println("Last name: " + item.getPassenger().getLastName());
									System.out.println("Document number: " + item.getPassenger().getDocumentNumber());
									System.out.println("Direction: " + item.getDirection());
								}
							}
							break;
						
						case "7":
							chatService.showMessages(container);
					
							break;
							
						case "END":
							System.out.println("THE WORK IS OVER." + new Date());
							soapService.logCheckOut(container);
							break;
							
						default:
							System.out.println("You entered a unavailable option. Try again.");
						}
						Thread.sleep(1000);
					} while(!"END".equals(OPTION));
				}
			}
		} catch(NumberFormatException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} catch (ServiceException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} catch (RemoteException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} catch (InterruptedException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} finally {
			scanner.close();
		}
	}
	
	private static void showOptions() {
		System.out.println("============================================================");
		System.out.println("SEND MESSAGE TO ALL ENTRANCES/EXITS ON TERMINAL - 1");//multicast
		System.out.println("SEND MESSAGE TO ALL ENTRANCES/EXITS ON ALL TERMINALS - 2");//broadcast
		System.out.println("SEND MESSAGE TO EXACTLY ONE ENTRANCE/EXIT - 3");//unicast
		System.out.println("LOG OUT - 4");
		System.out.println("CHANGE PASSWORD - 5");
		System.out.println("SHOW CHECKED PASSENGERS - 6");
		System.out.println("SHOW MESSAGES FROM INBOX - 7");
		System.out.println("QUIT - END");
		System.out.println("============================================================");
		System.out.println("Enter your option: ");
	}
}
