package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.beans.Account.AccountType;

public class TransactionDaoDB implements TransactionDao {
	
	private String url;
	private String username;
	private String password;
	
	public TransactionDaoDB() {
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

	public List<Transaction> getAllTransactions() {
		List<Transaction> allTransactions = new ArrayList<Transaction>();
		try {
			Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
			String sql = "SELECT * from transactiontable";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				Transaction t = new Transaction();
				t.setTimestamp(rs.getTimestamp(1).toLocalDateTime());
				
				int sendId = rs.getInt(2);
				String sqlSender = "SELECT * from accounttable WHERE accountid = " + sendId + ";";
				Statement sSender = conn.createStatement();
				ResultSet rsSender = sSender.executeQuery(sqlSender);
				Account sender = new Account();
				while(rsSender.next()){
					sender.setId(rsSender.getInt(1));
					sender.setOwnerId(rsSender.getInt(2));
					sender.setBalance(rsSender.getDouble(3));
					sender.setApproved(rsSender.getBoolean(5));
					String type = "";
					type = rsSender.getString(4);
					if (type.equals("CHECKING")) {
						sender.setType(AccountType.CHECKING);
					} else if (type.equals("SAVINGS")) {
						sender.setType(AccountType.SAVINGS);
					}
				}
				t.setSender(sender);
				
				int recipId;
				if (rs.getInt(3) > 0) {
					recipId = rs.getInt(3);
					String sqlRecip = "SELECT * from accounttable WHERE accountid = " + recipId + ";";
					Statement sRecip = conn.createStatement();
					ResultSet rsRecip = sRecip.executeQuery(sqlRecip);
					Account recip = new Account();
					while(rsRecip.next()){
						recip.setId(rsRecip.getInt(1));
						recip.setOwnerId(rsRecip.getInt(2));
						recip.setBalance(rsRecip.getDouble(3));
						recip.setApproved(rsRecip.getBoolean(5));
						String type = "";
						type = rsRecip.getString(4);
						if (type.equals("CHECKING")) {
							recip.setType(AccountType.CHECKING);
						} else if (type.equals("SAVINGS")) {
							recip.setType(AccountType.SAVINGS);
						}
					}
					t.setRecipient(recip);
				}
				
				t.setAmount(rs.getDouble(4));
				
				String type = rs.getString(5);
				if (type.equals("DEPOSIT")) {
					t.setType(TransactionType.DEPOSIT);
				} else if (type.equals("WITHDRAWAL")) {
					t.setType(TransactionType.WITHDRAWAL);
				}
				
				allTransactions.add(t);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return allTransactions;
	}

}
