package org.ml.util;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

public class LogHelper {
	
	static Logger logger = Logger.getLogger(LogHelper.class);
	static{
		PropertyConfigurator.configure(Global.LOG4J_LOCATION);
	}
	   
	public static void  debug(Object source, String message){
		logger.setLevel(Level.DEBUG);   
		String msg = message;
		if(source!=null)
			msg = source.getClass().getName() +" "+ msg;
		logger.debug(msg);	
	}
	
	public static void  debug(String message){
		logger.setLevel(Level.DEBUG);   
		logger.debug(message);	
	}
	
	public static void  error(Object source, String message){
		logger.setLevel(Level.DEBUG);   
		String msg = message;
		if(source!=null)
			msg = "SILENT ERROR"+source.getClass().getName() +" "+ msg;
		logger.debug(msg);	
	}
	public static void  error(String message){
		logger.setLevel(Level.DEBUG);   
		logger.debug("SILENT ERROR"+message);	
	}

}
