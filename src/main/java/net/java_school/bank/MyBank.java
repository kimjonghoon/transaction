package net.java_school.bank;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class MyBank implements Bank {

	private BankDao dao;

	public void setDao(BankDao dao) {
		this.dao = dao;
	}

	public void addAccount(String accountNo, String name, String kind) {
		dao.insertAccount(accountNo, name, kind);
	}

	@Override
	public Account getAccount(String accountNo) {
		return dao.selectOneAccount(accountNo);
	}

	@Override
	public List<Account> findAccountByName(String name) {
		return dao.selectAccountsByName(name);
	}

	@Override
	public List<Account> getAccounts() {
		return dao.selectAllAccounts();
	}

	@Override
	public int deposit(String accountNo, double amount) {
		return dao.deposit(accountNo, amount);
	}

	@Override
	public int withdraw(String accountNo, double amount) {
		return dao.withdraw(accountNo, amount);
	}
	
	@Override
	@Transactional
	public void transfer(String from, String to, double amount) {
		try {
			int check = dao.withdraw(from, amount);
			if (check < 1) throw new RuntimeException("Withdraw fail!");
			check = dao.deposit(to, amount);
			if (check < 1) throw new RuntimeException("Deposit fail!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Transaction> getTransactions(String accountNo) {
		return dao.selectAllTransactions(accountNo);
	}
}