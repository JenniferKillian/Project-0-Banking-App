package com.revature.driver;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
import org.apache.log4j.*;

/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {

	public static void main(String[] args) {
		
		System.out.println("Press 1 to log in.");
		System.out.println("Press 2 to register new user.");
		System.out.println("Press 3 to quit.");
		
		String input;
		Scanner sc = new Scanner(System.in);
		while (true) {
		  input = sc.nextLine();
		  if (input.equals("1")) {
			  
			  System.out.println("enter username:");
			  String username = sc.nextLine();
			  
			  System.out.println("enter password:");
			  String password = sc.nextLine();
			  
			  User user = new User();
			  UserDaoDB udf = new UserDaoDB();
			  user = udf.getUser(username, password);
			  if (user != null) {
				  System.out.println("Successfully logged in.");
				  SessionCache.setCurrentUser(user);
			  } else {
				  LogDriver.logger.debug("Error in BankApplicationDriver user login failed");
			  }
			  
		  }
		  
		  if (input.equals("2")) {
			  
			  User newUser = new User();
			  
			  System.out.println("enter first name:");
			  String firstName = sc.nextLine();
			  newUser.setFirstName(firstName);
			  
			  System.out.println("enter last name:");
			  String lastName = sc.nextLine();
			  newUser.setLastName(lastName);
			  
			  System.out.println("create user name:");
			  String userName = sc.nextLine();
			  newUser.setUsername(userName);
			  
			  System.out.println("create password:");
			  String password = sc.nextLine();
			  newUser.setPassword(password);
			  
			  UserDaoDB udf = new UserDaoDB();
			  
			  User registered = udf.addUser(newUser);
			  if (registered != null) {
				  System.out.println("Successfully registered.");
				  System.out.println("Please login.");
				  System.out.println("enter username:");
				  String un = sc.nextLine();
				  System.out.println("enter password:");
				  String pw = sc.nextLine();
				  
				  User user = new User();
				  user = udf.getUser(un, pw);
				  if (user != null) {
					  System.out.println("Successfully logged in.");
					  SessionCache.setCurrentUser(user);
				  } else {
					  LogDriver.logger.debug("Error in BankApplicationDriver user login failed");
				  }
			  } else {
				  LogDriver.logger.debug("Error in BankApplicationDriver user registration failed");
			  }
			  
			  
			  
		  }
		  
		  if (input.equals("3")) {
			  System.out.println("Goodbye.");
			  break;
		  } else {
			  System.out.println("invalid input");
			  System.out.println("Press 1 to log in.");
			  System.out.println("Press 2 to register new user.");
			  System.out.println("Press 3 to quit.");
		  }
		}
		
	}
	

}


