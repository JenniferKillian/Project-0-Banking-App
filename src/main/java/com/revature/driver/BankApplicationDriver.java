package com.revature.driver;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.dao.UserDaoFile;

/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {

	public static void main(String[] args) {
		
		/*
		 * Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter username");

    String userName = myObj.nextLine();  // Read user input
    System.out.println("Username is: " + userName);
		 */
		//PrintMenu
		System.out.println("Press 1 to log in.");
		System.out.println("Press 2 to register new user.");
		System.out.println("Press 3 to quit.");
		
		String input;
		Scanner sc = new Scanner(System.in);
		while (true) {
		  input = sc.nextLine();
		  if (input == "1") {
			  
		  } else if (input == "2") {
			  
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
			  
			  newUser.setId(null);
			  newUser.setUserType(null);
			  //UserDaoFile.addUser(newUser);
			  
			  
		  } else if (input == "3") {
			  
		  } else {
			  System.out.println("invalid input");
			  System.out.println("Press 1 to log in.");
			  System.out.println("Press 2 to register new user.");
			  System.out.println("Press 3 to quit.");
		  }
		}
		
	}
	
	

}
