package net.java_school.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class ShinhanBankDao extends NamedParameterJdbcDaoSupport implements BankDao {

	private static final String INSERT_ACCOUNT = 
			"INSERT INTO " +
					"bankaccount (accountno, owner, kind) " +
					"VALUES (:accountNo, :name, :kind)";

	@Override
	public void insertAccount(String accountNo, String name, String kind) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		params.put("name", name);
		params.put("kind", kind);

		getNamedParameterJdbcTemplate().update(INSERT_ACCOUNT, params);        
	}

	private static final String SELECT_ONE_ACCOUNT = 
			"SELECT accountno,owner,balance,kind " +
					"FROM bankaccount " +
					"WHERE accountno = :accountNo";

	@Override
	public Account selectOneAccount(String accountNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);

		return getNamedParameterJdbcTemplate().queryForObject(
				SELECT_ONE_ACCOUNT,
				params,
				new RowMapper<Account>() {
					public Account mapRow(ResultSet rs,int rowNum) throws SQLException {
						Account account = new Account();
						account.setAccountNo(rs.getString("accountNo"));
						account.setName(rs.getString("owner"));
						account.setBalance(rs.getLong("balance"));
						account.setKind(rs.getString("kind"));

						return account;
					}
				}
				);
	}

	private static final String SELECT_ACCOUNTS_BY_NAME = 
			"SELECT accountno,owner,balance,kind " +
					"FROM bankaccount " +
					"WHERE owner = :name";

	@Override
	public List<Account> selectAccountsByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		RowMapper<Account> rowMapper = new AccountRowMapper();

		return getNamedParameterJdbcTemplate().query(SELECT_ACCOUNTS_BY_NAME,params,rowMapper);
	}

	protected class AccountRowMapper implements RowMapper<Account> {

		public Account mapRow(ResultSet rs,int rowNum) throws SQLException {

			String accountNo = rs.getString("accountNo");
			String name = rs.getString("owner");
			long balance = rs.getLong("balance");
			String kind = rs.getString("kind");

			Account account = new Account();
			account.setAccountNo(accountNo);
			account.setName(name);
			account.setBalance(balance);
			account.setKind(kind);

			return account;
		}
	}

	private static final String SELECT_ALL_ACCOUNTS = 
			"SELECT accountNo,owner,balance,kind " +
					"FROM bankaccount " +
					"ORDER BY accountNo DESC";

	@Override
	public List<Account> selectAllAccounts() {
		RowMapper<Account> rowMapper = new AccountRowMapper();
		return getJdbcTemplate().query(SELECT_ALL_ACCOUNTS,rowMapper);
	}

	private static final String DEPOSIT = 
			"UPDATE bankaccount " +
					"SET balance = balance + :amount " +
					"WHERE accountno = :accountNo";

	@Override
	public int deposit(String accountNo, long amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("accountNo", accountNo);

		return getNamedParameterJdbcTemplate().update(DEPOSIT, params);
	}

	private static final String WITHDRAW = 
			"UPDATE bankaccount " +
					"SET balance = balance - :amount " +
					"WHERE accountno = :accountNo";

	@Override
	public int withdraw(String accountNo, long amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("accountNo", accountNo);

		return getNamedParameterJdbcTemplate().update(WITHDRAW, params);        
	}

	private static final String SELECT_ALL_TRANSACTIONS = 
			"SELECT transactionDate,kind,amount,balance " +
					"FROM transaction " +
					"WHERE accountno = :accountNo " +
					"ORDER By transactionDate ASC";

	@Override
	public List<Transaction> selectAllTransactions(String accountNo) {
		RowMapper<Transaction> rowMapper = new TransactionRowMapper();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);

		return getNamedParameterJdbcTemplate().query(SELECT_ALL_TRANSACTIONS,params,rowMapper);
	}

	protected class TransactionRowMapper implements RowMapper<Transaction> {

		public Transaction mapRow(ResultSet rs,int rowNum) throws SQLException {

			String date = Account.DATE_FORMAT.format(rs.getTimestamp("transactionDate"));
			String time = Account.TIME_FORMAT.format(rs.getTimestamp("transactionDate"));
			String kind = rs.getString("kind");
			long amount = rs.getLong("amount");
			long balance = rs.getLong("balance");

			Transaction transaction = new Transaction();
			transaction.setTransactionDate(date);
			transaction.setTransactionTime(time);
			transaction.setKind(kind);
			transaction.setAmount(amount);
			transaction.setBalance(balance);

			return transaction;
		}
	}

}