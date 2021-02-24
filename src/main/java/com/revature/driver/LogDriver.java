package com.revature.driver;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.Log4jEntityResolver;

public class LogDriver extends Log4jEntityResolver {
	
	public final static Logger logger = Logger.getLogger(LogDriver.class);
	
	public static void main(String[] args) {
		
		logger.setLevel(Level.ALL);
		
		logger.warn("This is a warning");
		logger.error("This is an error");
		logger.fatal("This is a fatal error");
		logger.info("-------------------------");
		//myClass mc = new myClass();
		//mc.method1();
		

	}

}