package etf.project.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

import etf.project.model.container.CustomsContainer;
import etf.project.model.container.PassengerContainer;
import etf.project.model.customsterminal.CustomsTerminal;
import etf.project.model.customsterminal.Gateway;
import etf.project.model.passenger.Passenger;
import etf.project.mylogger.MyLogger;

/**
 * @author NemanjaDavidovic
 * @since 31.12.2022.
 * @version 1.0
 * 
 * <p> CentralRegistryService is a class that provides all the services of the CentralRegistry. </p>
 *
 */


public class CentralRegistryService {
	
	// For some reason, this line of code is now ok.
	//private static final String PATH_OF_TERMINALS = System.getProperty("user.dir") + File.separator + "Terminals" + File.separator;
	
	private static final String PATH_OF_TERMINALS = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/CentralRegistry/Terminals/";
	private static final String PATH_OF_WARRANTS = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/CentralRegistry/Warrants/";
	private static final String PATH_OF_CENTRALREGISTRY = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/CentralRegistry/";
	
	// SOAP method for adding terminals. 
	public boolean addTerminal(CustomsTerminal terminal){
		
		try {
			// For some reason, this line of code create problem during generating WebService
			//File[] directories = new File(System.getProperty("user.dir")).listFiles(File::isDirectory);
			
			if(terminal == null)
				return false;
			
			File[] directories = new File(PATH_OF_CENTRALREGISTRY).listFiles(new FileFilter() {
			    public boolean accept(File file) {
			        return file.isDirectory();
			    }
			});
			
			for(File directory : directories)
				if("Terminals".equals(directory.getName())) {
					
					File[] terminals = new File(PATH_OF_TERMINALS).listFiles();
					if(terminals.length == 0)
						return JAVASerialization(terminal);
					
					String serializationType = terminals[terminals.length-1].getName().split("#")[1];
					
					switch(serializationType) {
					
					case "JAVA":
						return XMLSerialization(terminal);
						
					case "XML":
						return KRYOSerialization(terminal);
						
					case "KRYO":
						return GSONSerialization(terminal);
						
					case "GSON":
						return JAVASerialization(terminal);
						
					default:
						MyLogger.setLogger(CentralRegistryService.class.getName());
						MyLogger.logMessage(Level.INFO, "BAD OPTION.");
						return false;
					}
				}
			
			File terminals = new File(PATH_OF_TERMINALS);
			terminals.mkdir();
			
			return JAVASerialization(terminal);
			
		} catch(NullPointerException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	//SOAP method for updating terminal
	public boolean updateTerminal(long idTerminal, CustomsTerminal terminal){
		
		if(terminal == null)
			return false;
			
		String serializationType = getSerializationType(idTerminal);		
		if(!serializationType.equals("")) {
			
			switch(serializationType) {
				
			case "JAVA":
				return JAVASerialization(terminal);
				
			case "GSON":
				return GSONSerialization(terminal);
					
			case "KRYO":
				return KRYOSerialization(terminal);
					
			case "XML":
				return XMLSerialization(terminal);
					
					
			default:

			}
			return false;
		}
		
		MyLogger.setLogger(CentralRegistryService.class.getName());
		MyLogger.logMessage(Level.INFO, "Problem during updating.");
		
		return false;
	}
	
	//SOAP method for deleting terminals
	public boolean deleteTerminal(long idTerminal) {
		
		File terminal = new File(PATH_OF_TERMINALS + String.valueOf(idTerminal) 
								+ "#" + CentralRegistryService.getSerializationType(idTerminal));
		
		if(terminal.exists())
			return terminal.delete();
		
		return false;
	}
	
	//SOAP method for getting terminals
	public CustomsTerminal getTerminal(long idTerminal){
		
		try {
			File[] terminals = new File(PATH_OF_TERMINALS).listFiles();
			for(File terminal : terminals)
				if(terminal.getName().split("#")[0].equals(String.valueOf(idTerminal))) {
					
					String serializationType = terminal.getName().split("#")[1];
					CustomsTerminal customsTerminal = null;
					
					switch(serializationType) {
					
					case "JAVA":
						customsTerminal = JAVADeserialization(idTerminal);
						break;
					
					case "GSON":
						customsTerminal = GSONDeserialization(idTerminal);
						break;
						
					case "KRYO":
						customsTerminal = KRYODeserialization(idTerminal);
						break;
						
					case "XML":
						customsTerminal = XMLDeserialization(idTerminal);
						break;
						
					default:
						
					}
					return customsTerminal;
				}			
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		} 
		
		return null;
	}
	
	public boolean addCheckedPassenger(Passenger passenger, String terminalName, String direction){
		
		try {
			
			if(passenger == null || "".equals(terminalName))
				return false;
			
			File checkedPassengers = new File(PATH_OF_CENTRALREGISTRY + "CHECKED_PASSENGERS.txt");
			if(!checkedPassengers.exists())
				checkedPassengers.createNewFile();
			
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(checkedPassengers, true));

			String lineToWrite = passenger.getDocumentNumber() + " " + passenger.getLastName() + " " + passenger.getName() + 
					" " + new Date() + " " + terminalName + " " + direction +"\n";
			
			fileWriter.write(lineToWrite);
			fileWriter.close();
			
			return true;
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	//return passengers and date(as string) when they passed through the customs terminal
	public PassengerContainer[] getCheckedPassengers(){
		
		try {
			File checkedPassengersFile = new File(PATH_OF_CENTRALREGISTRY + "CHECKED_PASSENGERS.txt");
			if(!checkedPassengersFile.exists())
				return null;
			
			BufferedReader fileReader = new BufferedReader(new FileReader(checkedPassengersFile));
			String line;
			
			ArrayList<PassengerContainer> checkedPassengers = new ArrayList<>();
			PassengerContainer[] result;
			
			while((line = fileReader.readLine()) != null) {
				
				String[] partsOfLine = line.split(" ");
				Passenger passenger = new Passenger(partsOfLine[1], partsOfLine[2], partsOfLine[0]);
				
				String date = partsOfLine[3] + " " + partsOfLine[4] + " " + partsOfLine[5] 
						+ " " + partsOfLine[6] + " " + partsOfLine[7] + " " + partsOfLine[8];
				
				String terminalName = partsOfLine[9];
				String direction = partsOfLine[10];
				
				checkedPassengers.add(new PassengerContainer(passenger, terminalName, date, direction));
			}
			
			fileReader.close();
			
			if(checkedPassengers.size() == 0)
				return null;
			
			result = new PassengerContainer[checkedPassengers.size()];
			for(int i = 0; i<checkedPassengers.size(); i++)
				result[i] = checkedPassengers.get(i);
			
			return result;
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}
	
	//Close - there somewhere is person on the warrant
	public boolean stopRunningTerminal(CustomsContainer container) {
		
		try {
			String terminalName = container.getTerminalName();
			CustomsTerminal terminal = getByName(terminalName);

			if (terminal == null)
				return false;

			terminal.setTerminalRunning(false);

			return updateTerminal(terminal.getIdCustomsTerminal(), terminal);
						
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		} 
	}
	
	public boolean startRunningTerminal(CustomsContainer container) {
		
		try {
			String terminalName = container.getTerminalName();
			CustomsTerminal terminal = getByName(terminalName);
			
			if (terminal == null)
				return false;
			
			terminal.setTerminalRunning(true);
			
			return updateTerminal(terminal.getIdCustomsTerminal(), terminal);
						
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		} 
	}
		
	public boolean isTerminalAndGatewayAvailable(CustomsContainer container, boolean client){
		
		try {
			String terminalName = container.getTerminalName();
			String occupation = container.getOccupation();
			String typeOfGateway = container.getType();
			int idGateway = container.getId_gateway();
			
			CustomsTerminal terminal = getByName(terminalName);
			
			if (terminal == null)
				return false;
			
			if ("POLICE".equals(occupation)) {
				
				if("ENTRANCE".equals(typeOfGateway)) {
					
					if(terminal.getEntrances()[idGateway] != null) {
						if (terminal.getEntrances()[idGateway].isPolice()) {
							if(client)
								return true;
							return false;
						}
						else {
							if(client)
								return false;
							else
								return true;
						}
						
					}
						
					
				} else if("EXIT".equals(typeOfGateway)) {
					
					if(terminal.getExits()[idGateway] != null) {
						if(terminal.getExits()[idGateway].isPolice()) {
							if(client)
								return true;
							return false;
						}
						else {
							if(client)
								return false;
							else
								return true;
						}
					}
					
				}
				
			} else if("CUSTOMS".equals(occupation)) {
				
				if("ENTRANCE".equals(typeOfGateway)) {
					
					if(terminal.getEntrances()[idGateway] != null) {
						if(terminal.getEntrances()[idGateway].isCustoms()) {
							if(client)
								return true;
							return false;
						}
						else {
							if(client)
								return false;
							else
								return true;
						}
					}
						
					
				} else if("EXIT".equals(typeOfGateway)) {
					
					if(terminal.getExits()[idGateway] != null) {
						if(terminal.getExits()[idGateway].isCustoms()) {
							if(client)
								return true;
							return false;
						}
						else {
							if(client)
								return false;
							else
								return true;
						}
					}	
				} 
				
			} else
				return false;
					
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		} 
		
		return false;
	}
	
	public void logLogin(CustomsContainer container){
		
		try {
			if(container == null)
				return;
			
			CustomsTerminal oldTerminal = getByName(container.getTerminalName());
			if(oldTerminal == null)
				return;
			
			CustomsTerminal newTerminal = oldTerminal;
					
			if("POLICE".equals(container.getOccupation())){
				
				if("ENTRANCE".equals(container.getType()) && (oldTerminal.getEntrances()[container.getId_gateway()]) !=null) {
					
					newTerminal.getEntrances()[container.getId_gateway()].setPolice(true);
					newTerminal.getEntrances()[container.getId_gateway()].setOpen(true);
					
					
				} else if("EXIT".equals(container.getType()) && oldTerminal.getEntrances()[container.getId_gateway()] !=null) {
					
					newTerminal.getExits()[container.getId_gateway()].setPolice(true);
					
				}
				
			} else if("CUSTOMS".equals(container.getOccupation())) {
				
				if("ENTRANCE".equals(container.getType())) {
					
					newTerminal.getEntrances()[container.getId_gateway()].setCustoms(true);
					
				} else if("EXIT".equals(container.getType())) {
					
					newTerminal.getExits()[container.getId_gateway()].setCustoms(true);
				}
			}
			
			newTerminal.setTerminalOpen(true);
			newTerminal.setTerminalRunning(true);
			
			updateTerminal(oldTerminal.getIdCustomsTerminal(), newTerminal);
			
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
		}
	}
	
	public void logCheckOut(CustomsContainer container){
		
		try {
			if(container == null)
				return;
			
			CustomsTerminal oldTerminal = getByName(container.getTerminalName());
			
			if(oldTerminal == null)
				return;
					
			CustomsTerminal newTerminal = oldTerminal;

			if("POLICE".equals(container.getOccupation())){
				
				if("ENTRANCE".equals(container.getType()) && (oldTerminal.getEntrances()[container.getId_gateway()] !=null)) {
					
					newTerminal.getEntrances()[container.getId_gateway()].setPolice(false);
					
					
				} else if("EXIT".equals(container.getType()) && oldTerminal.getEntrances()[container.getId_gateway()] !=null) {
					
					newTerminal.getExits()[container.getId_gateway()].setPolice(false);
					
				}
				
			} else if("CUSTOMS".equals(container.getOccupation())) {
				
				if("ENTRANCE".equals(container.getType())) {
					
					newTerminal.getEntrances()[container.getId_gateway()].setCustoms(false);
					
				} else if("EXIT".equals(container.getType())) {
					
					newTerminal.getExits()[container.getId_gateway()].setCustoms(false);
				}
			}
			
			updateTerminal(oldTerminal.getIdCustomsTerminal(), newTerminal);
			
		}catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
		}
	}
	
	public boolean isTerminalRunning(CustomsContainer container) {
		
		try {
		
			String terminalName = container.getTerminalName();
			CustomsTerminal terminal = getByName(terminalName);
		
			if (terminal == null)
				return false;
			
			return terminal.isTerminalRunning();
					
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		} 
	}
	
	public Passenger[] getPassengersOnWarrants(){
		
		try {
			ArrayList<Passenger> passengersOnWarrant = new ArrayList<>();
			
			File[] persons = new File(PATH_OF_WARRANTS).listFiles();
			if(persons.length == 0)
				return null;
			
			XMLDecoder decoder = null;
			
			for(File person : persons) {
				
				decoder = new XMLDecoder(new FileInputStream(person));
				Passenger passenger = (Passenger) decoder.readObject();
				
				passengersOnWarrant.add(passenger);
			}
			decoder.close();
			
			if(passengersOnWarrant.size() == 0)
				return null;
			
			Passenger[] result = new Passenger[passengersOnWarrant.size()];
			for(int i = 0; i < passengersOnWarrant.size(); i++)
				result[i] = passengersOnWarrant.get(i);
			
			return result;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}
	
	public boolean addPassengerOnWarrant(Passenger passenger){
		
		try {
			// For some reason, this line of code create problem during generating WebService
			//File[] directories = new File(System.getProperty("user.dir")).listFiles(File::isDirectory);
			
			if(passenger == null)
				return false;
			
			File[] directories = new File(PATH_OF_CENTRALREGISTRY).listFiles(new FileFilter() {
			    public boolean accept(File file) {
			        return file.isDirectory();
			    }
			});
			
			for(File directory : directories)
				if("Warrants".equals(directory.getName())) {
					
					XMLEncoder encoder = new XMLEncoder(new FileOutputStream(PATH_OF_WARRANTS + 
							passenger.getDocumentNumber() + "#" + java.time.LocalDate.now().toString()));
					
					encoder.writeObject(passenger);
					encoder.close();
					return true;
					
				}
			
			File warrants = new File(PATH_OF_WARRANTS);
			warrants.mkdir();
			
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(PATH_OF_WARRANTS + 
					passenger.getDocumentNumber()+ "#" + java.time.LocalDate.now().toString()));
			
			encoder.writeObject(passenger);
			encoder.close();
			return true;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	private static CustomsTerminal getByName(String name){
		
		try {
			if("".equals(name))
				return null;
			
			File[] directories = new File(PATH_OF_CENTRALREGISTRY).listFiles(new FileFilter() {
			    public boolean accept(File file) {
			        return file.isDirectory();
			    }
			});

			if(directories.length == 0)
				return null;
			
			int counter = 0;
			for(File directory : directories)
				if("Terminals".equals(directory.getName()))
					counter++;
			
			if(counter != 1)
				return null;

			File[] terminals = new File(PATH_OF_TERMINALS).listFiles();
			if(terminals.length == 0)
				return null;
			
			for(File terminal : terminals) {
				
				String serializationType = terminal.getName().split("#")[1];
				String terminalId = terminal.getName().split("#")[0];
				CustomsTerminal result = null;
				
				switch(serializationType) {
			
				case "JAVA":
					result = JAVADeserialization(Long.parseLong(terminalId));
					break;
					
				case "GSON":
					result = GSONDeserialization(Long.parseLong(terminalId));
					break;
					
				case "KRYO":
					result = KRYODeserialization(Long.parseLong(terminalId));
					break;
					
				case "XML":
					result = XMLDeserialization(Long.parseLong(terminalId));
					break;
					
				default: 
					result = null;
				}
				
				if(result != null && result.getTerminalName().equals(name))
					return result;
			}
		} catch(NumberFormatException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		} 
		
		return null;
	}
	
	private static boolean JAVASerialization(CustomsTerminal terminal) {
		
		try {
			String serializationFilePath = PATH_OF_TERMINALS + String.valueOf(terminal.getIdCustomsTerminal()) + "#JAVA";
			
			FileOutputStream fos = new FileOutputStream(serializationFilePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(terminal);
			
			oos.close();
			fos.close();
			
			return true;
			
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	private static CustomsTerminal JAVADeserialization(long idTerminal) {
		
		try {
			String deserializationFilePath = PATH_OF_TERMINALS + String.valueOf(idTerminal) + "#JAVA";
			
			CustomsTerminal terminal = null;
			FileInputStream fis = new FileInputStream(deserializationFilePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			terminal = (CustomsTerminal) ois.readObject();
			
			ois.close();
			fis.close();
			
			return terminal;
			
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}
	
	private static boolean GSONSerialization(CustomsTerminal terminal){
		
		try {
			System.out.println(1111);
			String serializationFilePath = PATH_OF_TERMINALS + String.valueOf(terminal.getIdCustomsTerminal()) + "#GSON";
			FileWriter writer = new FileWriter(serializationFilePath);
			
			Gson gson = new Gson();
			
			writer.write(gson.toJson(terminal));
			
			writer.close();
			
			return true;
			
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	private static CustomsTerminal GSONDeserialization(long idTerminal) {
		
		try {
			String deserializationFilePath = PATH_OF_TERMINALS + String.valueOf(idTerminal) + "#GSON";
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(deserializationFilePath)));
			Gson gson = new Gson();
			
			CustomsTerminal terminal = gson.fromJson(reader, CustomsTerminal.class);
			
			reader.close();
			
			return terminal;
			
		} catch(IOException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}
	
	private static boolean KRYOSerialization(CustomsTerminal terminal) {
		
		try {
			String serializationFilePath = PATH_OF_TERMINALS + String.valueOf(terminal.getIdCustomsTerminal()) + "#KRYO";
			
			Kryo kryo = new Kryo();
			
			Output output = new Output(new FileOutputStream(serializationFilePath));
			kryo.register(CustomsTerminal.class);
			kryo.register(Gateway[].class);
			kryo.register(Gateway.class);
			kryo.writeObject(output, terminal);
			output.flush();
			
			output.close();
			
			return true;
			
		} catch(Exception e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	private static CustomsTerminal KRYODeserialization(long idTerminal) {
		
		try {
			String deserializationFilePath = PATH_OF_TERMINALS + String.valueOf(idTerminal) + "#KRYO";
			Kryo kryo = new Kryo();
			
			Input input = new Input(new FileInputStream(deserializationFilePath));
			kryo.register(CustomsTerminal.class);
			kryo.register(Gateway[].class);
			kryo.register(Gateway.class);

			CustomsTerminal terminal = (CustomsTerminal) kryo.readObject(input, CustomsTerminal.class);
			
			input.close();
			
			return terminal;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}
	
	private static boolean XMLSerialization(CustomsTerminal terminal) {
		
		try {
			String serializedFilePath = PATH_OF_TERMINALS + String.valueOf(terminal.getIdCustomsTerminal()) + "#XML";
			
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(serializedFilePath)));
			encoder.writeObject(terminal);

			encoder.close();
			
			return true;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return false;
		}
	}
	
	private static CustomsTerminal XMLDeserialization(long idTerminal){
		
		try {
			String deserializedFilePath = PATH_OF_TERMINALS + String.valueOf(idTerminal) + "#XML";
			
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(deserializedFilePath)));
			CustomsTerminal terminal = (CustomsTerminal) decoder.readObject();
			
			decoder.close();
			
			return terminal;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(CentralRegistryService.class.getName());
			MyLogger.logError(Level.INFO, e.toString(), e);
			return null;
		}
	}

	private static String getSerializationType(long idTerminal) {
		
		File[] terminals = new File(PATH_OF_TERMINALS).listFiles();
		if(terminals == null)
			return "";
		
		for(File terminal : terminals)
			if(terminal.getName().startsWith(String.valueOf(idTerminal)))
				return terminal.getName().split("#")[1];
		return "";
	}
}
