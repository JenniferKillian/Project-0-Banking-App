package com.revature.dao;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.revature.beans.User;
import com.revature.beans.User.UserType;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */
public class UserDaoDB implements UserDao {
	
	private String url;
	private String username;
	private String password;
	
	public UserDaoDB() {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream("database.properties");
		Properties p = new Properties();
		try {
		p.load(is);
		this.url = (String) p.getProperty("url");
		this.username = (String) p.getProperty("username");
		this.password = (String) p.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public User addUser(User user) {
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "INSERT INTO usertable(username, pword, firstname, lastname, usertype, id) values(?,?,?,?,?,?)"; //RETURNING id INTO uId";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			if (user.getUserType() == null) {
				ps.setString(5, "");
			} else {
				ps.setString(5, user.getUserType().toString());
			}
			if (user.getId() != null) {
				ps.setInt(6, user.getId());
			} else {
				ps.setInt(6, 0);
			}
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public User getUser(Integer userId) {
		User u = new User();
		String uType = "";
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from usertable WHERE id = " + userId;
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				uType = rs.getString(6);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if (uType.equals("CUSTOMER")) {
			u.setUserType(UserType.CUSTOMER);
		} else if (uType.equals("EMPLOYEE")) {
			u.setUserType(UserType.EMPLOYEE);
		}
		return u;
	}

	public User getUser(String uname, String pass) {
		User u = new User();
		String uType = "";
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from usertable WHERE username = " + uname + " AND pword = " + pass;
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				uType = rs.getString(6);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if (uType.equals("CUSTOMER")) {
			u.setUserType(UserType.CUSTOMER);
		} else if (uType.equals("EMPLOYEE")) {
			u.setUserType(UserType.EMPLOYEE);
		}
		return u;
	}

	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList<User>();
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from usertable";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				User u = new User();
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				String uType = rs.getString(6);
				if (uType.equals("CUSTOMER")) {
					u.setUserType(UserType.CUSTOMER);
				} else if (uType.equals("EMPLOYEE")) {
					u.setUserType(UserType.EMPLOYEE);
				}
				allUsers.add(u);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return allUsers;
	}

	public User updateUser(User user) {
		String uType = "";
		if (user.getUserType() == UserType.CUSTOMER) {
			uType = "CUSTOMER";
		} else if (user.getUserType() == UserType.EMPLOYEE) {
			uType = "EMPLOYEE";
		}
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "UPDATE usertable set" +
			" username = '" + user.getUsername() +
			"', pword = '" + user.getPassword() +
			"', firstname = '" + user.getFirstName() +
			"', lastname = '" + user.getLastName() +
			"', usertype = '" + uType +
			"' WHERE id = " + user.getId() + ";";
			Statement s = conn.createStatement();
			s.executeQuery(sql);
			
			/*
			String sql = "UPDATE usertable set username = ?, password = ?, firstname = ?, lastname = ?, usertype = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getUserType().toString());
			ps.setInt(6, user.getId());
			ps.execute();
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean removeUser(User u) {
		try {
			Connection conn = DriverManager.getConnection(this.url,this.username,this.password);
			String sql = "DELETE FROM usertable WHERE id = " + u.getId() + ";";
			Statement s = conn.createStatement();
			s.executeQuery(sql);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
