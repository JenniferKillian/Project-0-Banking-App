package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {
	
	public static String fileLocation = "Users.txt";

	public User addUser(User user) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
				oos.writeObject(user);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return user;
	}

	public User getUser(Integer userId) {
		User u = new User();
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));

			u = (User)ois.readObject();
			
			System.out.println(u);
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	public User getUser(String username, String pass) {
		User u = new User();
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));

			u = (User)ois.readObject();
			
			System.out.println(u);
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	public List<User> getAllUsers() {
		
		List<User> allUsers = new ArrayList<User>();
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));

			allUsers = (ArrayList<User>)ois.readObject();
			for(User u: allUsers) {
			System.out.println(u);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allUsers;
	}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

}
