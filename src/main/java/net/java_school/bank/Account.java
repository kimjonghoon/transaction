package net.java_school.bank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Account {
	private String accountNo;
	private String name;
	private double balance;
	private String kind;
	private List<Transaction> transactions = new ArrayList<Transaction>();

	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	static final String DEPOSIT = "DEPOSIT";
	static final String WITHDRAW = "WITHDRAW";
	static final String NORMAL = "NORMAL";
	static final String MINUS = "MINUS";

	public Account() {}

	public Account(String accountNo, String name, String kind) {
		this.accountNo = accountNo;
		this.name = name;
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(accountNo);
		sb.append("|");
		sb.append(name);
		sb.append("|");
		sb.append(balance);
		sb.append("|");
		sb.append(kind);

		return sb.toString();
	}
}
