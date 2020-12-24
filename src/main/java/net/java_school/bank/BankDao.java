package net.java_school.bank;

import java.util.List;

public interface BankDao {

	public void insertAccount(String accountNo, String name, String kind);

	public Account selectOneAccount(String accountNo);

	public List<Account> selectAccountsByName(String name);

	public List<Account> selectAllAccounts();

	public int deposit(String accountNo, double amount);

	public int withdraw(String accountNo, double amount);

	public List<Transaction> selectAllTransactions(String accountNo);

}