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
			stream.print(">>");

			try {
				menu = readCommandLine();

				String accountNo = null;
				String name = null;
				String kind = null;
				double amount = 0;

				if (menu.equals("1")) {
					//New Account registration
					stream.print("Enter the account number of the account you want to create: ");
					accountNo = readCommandLine();
					stream.print("Enter the owner name of the account you want to create: ");
					name = readCommandLine();
					stream.print("Please select an account kind. noraml (just Enter), minus (2): ");
					kind = readCommandLine();
					if (kind != null && kind.equals("2")) {
						bank.addAccount(accountNo, name, Account.MINUS);
					} else {
						bank.addAccount(accountNo, name, Account.NORMAL);
					}
				} else if (menu.equals("2")) {
					//All accounts
					List<Account> accounts = bank.getAccounts();
					for (Account account : accounts) {
						stream.println(account);
					}
				} else if (menu.equals("3")) {
					//Deposit
					stream.print("Please enter your account number: ");
					accountNo = readCommandLine();
					stream.print("Please enter deposit amount: ");
					amount = Double.parseDouble(this.readCommandLine());
					bank.deposit(accountNo, amount);
				} else if (menu.equals("4")) {
					//Withdraw
					stream.print("Please enter your account number: ");
					accountNo = readCommandLine();
					stream.print("Please enter withdraw amount: ");
					amount = Double.parseDouble(this.readCommandLine());
					bank.withdraw(accountNo, amount);
				} else if (menu.equals("5")) {
					//Transfer
					stream.print("Please enter your withdrawal account number: ");
					String from = readCommandLine();
					stream.print("Please enter your deposit account number: ");
					String to = readCommandLine();
					stream.print("Enter transfer amount: ");
					amount = Double.parseDouble(this.readCommandLine());
					bank.transfer(from, to, amount);
				} else if (menu.equals("6")) {
					//Transaction history
					stream.print("Please enter your account number: ");
					accountNo = readCommandLine();
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