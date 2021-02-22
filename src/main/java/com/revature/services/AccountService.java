package com.revature.services;

import java.util.ArrayList;
import java.util.List;


import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.exceptions.OverdraftException;

/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 0.01;
	
	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}
	
	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		if (!a.isApproved()) {
			throw new UnsupportedOperationException();
		}
		
		if (amount < 0) {
			throw new UnsupportedOperationException();
		} else {
			Double balance = a.getBalance();
			if (balance > amount) {
				a.setBalance(balance - amount);
			} else {
				throw new OverdraftException();
			}
		}
	}
	
	/**
	 * Deposit funds to an account
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		if (!a.isApproved()) {
			throw new UnsupportedOperationException();
		}
		
		if (amount < 0) {
			throw new UnsupportedOperationException();
		} else {
			Double balance = a.getBalance();
			a.setBalance(balance + amount);
		}
	}
	
	/**
	 * Transfers funds between accounts
	 * @throws UnsupportedOperationException if amount is negative or 
	 * the transaction would result in a negative balance for either account
	 * or if either account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct the account to deposit to
	 * @param amount the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		if (!fromAct.isApproved()) {
			throw new UnsupportedOperationException();
		}
		if (!toAct.isApproved()) {
			throw new UnsupportedOperationException();
		}
		if (amount < 0) {
			throw new UnsupportedOperationException();
		}
		
		Double fromActBal = fromAct.getBalance();
		if (fromActBal < amount) {
			throw new UnsupportedOperationException();
		} else {
			fromAct.setBalance(fromActBal - amount);
			actDao.updateAccount(fromAct);
			toAct.setBalance(toAct.getBalance() + amount);
			actDao.updateAccount(toAct);
		}
	}
	
	/**
	 * Creates a new account for a given User
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User u) {
		Account account = new Account();
		account.setId(u.getId());
		account.setBalance(0.01);
		List<Account> acts = new ArrayList<Account>();
		acts.add(account);
		u.setAccounts(acts);
		actDao.addAccount(account);
		//System.out.println(account);
		//System.out.println(acts);
		return account;
	}
	
	/**
	 * Approve or reject an account.
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {
		a.setApproved(true);
		return false;
	}
}
