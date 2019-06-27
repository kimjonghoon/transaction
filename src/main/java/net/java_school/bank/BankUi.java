package net.java_school.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BankUi {

	private Bank bank;

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	private PrintStream stream;

	public void setStream(PrintStream stream) {
		this.stream = stream;
	}

	private String readCommandLine() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String input = br.readLine();
		return input;
	}

	public void startWork() {

		String menu = null;

		do {
			stream.println(" 1 ** New Account registration    ");
			stream.println(" 2 ** All Accounts    ");
			stream.println(" 3 ** Deposit    ");
			stream.println(" 4 ** Withdraw    ");
			stream.println(" 5 ** Transfer    ");
			stream.println(" 6 ** Transaction history    ");
			stream.println(" q ** Quit    ");
			stream.println(" ********************** ");
			stream.println(">>");

			try {
				menu = readCommandLine();

				String accountNo = null;
				String name = null;
				String kind = null;
				long amount = 0;

				if (menu.equals("1")) {
					//TODO New Account registration
					stream.println("Enter the account number of the account you want to create: ");
					accountNo = this.readCommandLine();
					stream.println("Enter the owner name of the account you want to create: ");
					name = this.readCommandLine();
					stream.println("Please select an account kind. noraml (n), minus (m): normal (n) : : ");
					kind = this.readCommandLine();
					if (kind.equals("") || kind.equals("n") || kind.equals("m")) {
						if (kind.equals("")) {
							kind = Account.NORMAL;
						} else if (kind.equals("n")) {
							kind = Account.NORMAL;
						} else {
							kind = Account.MINUS;
						}
						bank.addAccount(accountNo, name, kind);
					}

				} else if (menu.equals("2")) {
					//All accounts
					List<Account> accounts = bank.getAccounts();
					for (Account account : accounts) {
						stream.println(account);
					}
				} else if (menu.equals("3")) {
					//Deposit
					stream.println("Please enter your account number: ");
					accountNo = this.readCommandLine();
					stream.println("Please enter deposit amount: ");
					amount = Integer.parseInt(this.readCommandLine());
					bank.deposit(accountNo, amount);
				} else if (menu.equals("4")) {
					//Withdraw
					stream.println("Please enter your account number: ");
					accountNo = this.readCommandLine();
					stream.println("Please enter withdraw amount: ");
					amount = Integer.parseInt(this.readCommandLine());
					bank.withdraw(accountNo, amount);
				} else if (menu.equals("5")) {
					//Transfer
					stream.println("Please enter your withdrawal account number: ");
					String from = this.readCommandLine();
					stream.println("Please enter your deposit account number: ");
					String to = this.readCommandLine();
					stream.println("Enter transfer amount: ");
					amount = Integer.parseInt(this.readCommandLine());
					bank.transfer(from, to, amount);
				} else if (menu.equals("6")) {
					//Transaction history
					stream.println("Please enter your account number: ");
					accountNo = this.readCommandLine();
					List<Transaction> transactions = bank.getTransactions(accountNo);
					for (Transaction transaction : transactions) {
						stream.println(transaction);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			stream.println();
		} while (!menu.equals("q"));

	}

	public static void main(String[] args) throws Exception {
		//ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); //XML
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BankConfig.class); //JavaConfig
		BankUi ui = ctx.getBean(BankUi.class);
		ui.startWork();
		ctx.close();
	}

}