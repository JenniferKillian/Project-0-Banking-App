package com.revature.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {
	
	private String url;
	private String username;
	private String password;
	
	public AccountDaoDB() {
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

	public Account addAccount(Account a) {
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "INSERT INTO accounttable(ownerid, balance, approved, accounttype, accountid) values(?,?,?,?,?)"; //RETURNING id INTO uId";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(5, a.getId());
			ps.setInt(1, a.getOwnerId());
			ps.setDouble(2, a.getBalance());
			ps.setBoolean(3, a.isApproved());
			if (a.getType() == null) {
				ps.setString(4, "");
			} else {
				ps.setString(4, a.getType().toString());
			}
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return a;
	}

	public Account getAccount(Integer actId) {
		Account u = new Account();
		String type = "";
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from accounttable WHERE accountid = " + actId;
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				u.setId(rs.getInt(1));
				u.setOwnerId(rs.getInt(2));
				u.setBalance(rs.getDouble(3));
				u.setApproved(rs.getBoolean(5));
				type = rs.getString(4);
				if (type.equals("CHECKING")) {
					u.setType(AccountType.CHECKING);
				} else if (type.equals("SAVINGS")) {
					u.setType(AccountType.SAVINGS);
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return u;
	}

	public List<Account> getAccounts() {
		List<Account> allAccounts = new ArrayList<Account>();
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from accounttable";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				Account u = new Account();
				u.setId(rs.getInt(1));
				u.setOwnerId(rs.getInt(2));
				u.setBalance(rs.getDouble(3));
				u.setApproved(rs.getBoolean(5));
				String type = rs.getString(4);
				if (type.equals("CHECKING")) {
					u.setType(AccountType.CHECKING);
				} else if (type.equals("SAVINGS")) {
					u.setType(AccountType.SAVINGS);
				}
				allAccounts.add(u);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return allAccounts;
	}

	public List<Account> getAccountsByUser(User u) {
		int userId = u.getId();
		List<Account> accounts = new ArrayList<Account>();
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from accounttable WHERE ownerid = " + userId + ";";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				//rs.get*()can take the column number or the name of the column in double quotes
				Account a = new Account();
				a.setId(rs.getInt(1));
				a.setOwnerId(rs.getInt(2));
				a.setBalance(rs.getDouble(3));
				a.setApproved(rs.getBoolean(5));
				String type = rs.getString(4);
				if (type.equals("CHECKING")) {
					a.setType(AccountType.CHECKING);
				} else if (type.equals("SAVINGS")) {
					a.setType(AccountType.SAVINGS);
				}
				accounts.add(a);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return accounts;
	}

	public Account updateAccount(Account a) {
		
		if (a.getTransactions() != null) {
			for (Transaction t : a.getTransactions()) {
				
				try {
					Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
					
					String sqlT = "INSERT INTO transactiontable(transactiontime, accountid, recipient, amount, transactiontype) values(?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sqlT);
					ps.setObject(1, Timestamp.valueOf(t.getTimestamp()));
					ps.setInt(2, t.getSender().getOwnerId());
					
					ps.setInt(3, t.getRecipient().getId());
					ps.setDouble(4, t.getAmount());
					if (t.getType() == null) {
						ps.setString(5, "");
					} else {
						ps.setString(5, t.getType().toString());
					}
					ps.execute();
					ps.close();
					
				} catch (SQLException e) {
					System.out.println("error in transaction insert");
					e.printStackTrace();
				}
			}
		}
		
		String type = "";
		if (a.getType() == AccountType.CHECKING) {
			type = "CHECKING";
		} else if (a.getType() == AccountType.SAVINGS) {
			type = "SAVINGS";
		}
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "UPDATE accounttable set" +
			" ownerid = '" + a.getOwnerId() +
			"', balance = '" + a.getBalance() +
			"', approved = '" + a.isApproved() +
			"', accounttype = '" + type +
			"' WHERE accountid = " + a.getId() + ";";
			Statement s = conn.createStatement();
			s.executeQuery(sql);
			
		} catch (SQLException e) {
			System.out.println("error in update account");
			e.printStackTrace();
		}
		
		
		
		return a;
	}

	public boolean removeAccount(Account a) {
		try {
			Connection conn = DriverManager.getConnection(this.url,this.username,this.password);
			String sql = "DELETE FROM accounttable WHERE accountid = " + a.getId() + ";";
			Statement s = conn.createStatement();
			s.executeQuery(sql);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
