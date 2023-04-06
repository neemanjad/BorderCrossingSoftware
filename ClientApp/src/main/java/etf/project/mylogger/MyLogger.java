package etf.project.mylogger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

	private static Logger LOGGER;
	private static final String LOGGER_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/ClientApp/LOGGER/";
	private static boolean loggerSetted = false;
	
	public static void logMessage(Level level, String message) {
		LOGGER.log(level, message);
	}
	
	public static void logError(Level level, String message, Exception exc) {
		LOGGER.log(level, message, exc);
	}
	
	public static void setLogger(String name) {
		try {
			if(!loggerSetted) {
				
					LOGGER = Logger.getLogger(name);
								
					File loggerFile = new File(LOGGER_PATH);
					if(!loggerFile.exists())
						loggerFile.mkdir();
					
					FileHandler loggerHandler = new FileHandler(LOGGER_PATH + "loggs.txt");
					loggerHandler.setLevel(Level.ALL);
					
					LOGGER.addHandler(loggerHandler);
					LOGGER.setLevel(Level.ALL);
					
					loggerSetted = true;
			}
		} catch(NullPointerException e) {
			logError(Level.WARNING, e.toString(), e);
		} catch (SecurityException e) {
			logError(Level.WARNING, e.toString(), e);
		} catch (IOException e) {
			logError(Level.WARNING, e.toString(), e);
		}
	}
}
