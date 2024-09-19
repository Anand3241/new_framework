package com.thtechops.qa.automation.utilities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
 
 public class Logs {
 
// Initialize Log4j logs
 
 //private static Logger Log = Logger.getLogger(Logs.class.getName());
	 
	 public static final Logger logger = LogManager.getLogger(Logs.class);  
	 {
		Configurator.initialize(new DefaultConfiguration());
	}
 
	 // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	 public static void startClassTest(String className)
	 {
		 logger.info("******************     "+className + " CLASS TEST     ********************\n");
	 }
	 
	 //This is to print log for the ending of the test case
	 public static void endTest(){
	 
		 logger.info("*******************************   END CLASS TEST   **********************************\n");
	
	 }
 
	 // When required these methods can be called  
	 public static void info(String message) {
		 logger.info(message);
	 }
	 
	 public static void warn(String message) {
		 logger.warn(message);
	 }
	 
	 public static void error(String message) {
	 
		 logger.error(message);
	 
	 }
	 
	 public static void fatal(String message) {
	 
		 logger.fatal(message);
	 
	 }
	 
	 public static void debug(String message) {
	 
		 logger.debug(message);
	 
	 }
	 
}
