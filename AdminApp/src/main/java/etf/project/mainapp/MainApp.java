package etf.project.mainapp;

import java.util.Date;
import java.util.Scanner;

import etf.project.logic.AdminAppRestUtility;
import etf.project.logic.AdminAppSoapUtility;

public class MainApp {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("WELCOME ADMIN");
		
		String OPTION = "";
		Scanner scanner = new Scanner(System.in);
		
		AdminAppRestUtility restService = new AdminAppRestUtility();
		AdminAppSoapUtility soapService = new AdminAppSoapUtility();
		
		do {
			
			System.out.println("Time: " + new Date());
			showOptions();
			OPTION = scanner.nextLine();
			
			switch(OPTION) {
			
			case "1":
				if(soapService.addCustomsTerminal())
					System.out.println("Terminal is successfully addedd.");
				else
					System.out.println("Terminal is not added. Try again later.");
				break;
				
			case "2":
				if(soapService.updateTerminal())
					System.out.println("Terminal is successfully updated.");
				else
					System.out.println("Terminal is not updated. Sorry.");
				break;
				
			case "3":
				soapService.showTerminal();
				break;
				
			case "4":
				soapService.deleteTerminal();
				break;
				
			case "5":
				restService.getPassengersOnWarrant();
				break;
				
			case "6":
				restService.getDocuments();
				break;
				
			case "7":
				restService.addUser();
				break;
				
			case "8":
				restService.updateUser();
				break;
				
			case "9":
				restService.getUser();
				break;
				
			case "10": 
				restService.getUsers();
				break;
				
			case "11":
				restService.deleteUser();
				break;
				
			case "12":
				restService.changePassword();
				break;
				
			case "END":
				System.out.println("GOODBYE ADMIN.");
				break;
				
			default:
				System.out.println("You enter a wrong option. Try again.");
			}
			
			Thread.sleep(2000);
		} while(!OPTION.equals("END"));
		
		scanner.close();
	}
	
	
	
	private static void showOptions() {
		
		System.out.println("=========================================");
		System.out.println("add new terminal - 1");
		System.out.println("update terminal - 2");
		System.out.println("show terminal - 3");
		System.out.println("delete terminal - 4");
		System.out.println("get passengers on warrant - 5");
		
		System.out.println("get customs documents - 6");
		
		System.out.println("add new user - 7");
		System.out.println("update user - 8");
		System.out.println("get user - 9");
		System.out.println("get all users - 10");
		System.out.println("delete user - 11");
		System.out.println("change the password for user - 12");
		
		System.out.println("THE END - END");
		System.out.println("=========================================");
		
		System.out.println("Please, enter your option: ");
	}
}
