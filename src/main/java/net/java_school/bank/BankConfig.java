package net.java_school.bank;

import javax.sql.DataSource;

import net.java_school.commons.TestLogger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class BankConfig {

	@Bean
	public TestLogger testLogger() {
		return new TestLogger();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");
		dataSource.setUsername("scott");
		dataSource.setPassword("tiger");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public BankDao shinhanBankDao() {
		ShinhanBankDao bankDao = new ShinhanBankDao();
		bankDao.setDataSource(dataSource());
		return bankDao;
	}

	@Bean
	public Bank shinhanBank() {
		Bank bank = new ShinhanBank();
		bank.setDao(shinhanBankDao());
		return bank;
	}

	@Bean
	public BankUi bankUi() {
		BankUi ui = new BankUi();
		ui.setBank(shinhanBank());
		ui.setStream(System.out);
		return ui;
	}

}