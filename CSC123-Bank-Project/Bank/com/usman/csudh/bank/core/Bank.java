package com.usman.csudh.bank.core;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
//Joseph Lewis jlewis157@toromail.csudh.edu
public class Bank {
	
	private static Map<Integer,Account> accounts=new TreeMap<Integer,Account>();
	private static boolean fileexistence;
	public static Account openCheckingAccount(String firstName, String lastName, String ssn, String currency, double overdraftLimit) {
			Customer c=new Customer(firstName,lastName, ssn, currency);
			Account a=new CheckingAccount(c,overdraftLimit);
			accounts.put(a.getAccountNumber(), a);
			return a;
		
		
		
	}
	
	public static Account openSavingAccount(String firstName, String lastName, String ssn, String Currency) {
		Customer c=new Customer(firstName,lastName, ssn, Currency);
		Account a=new SavingAccount(c);
		accounts.put(a.getAccountNumber(), a);
		return a;
		
	}

	
	
	public static Account lookup(int accountNumber) throws NoSuchAccountException{
		if(!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: "+accountNumber+" nout found!\n\n");
		}
		Customer c = new Customer();
		Account information = accounts.get(accountNumber);
		String[] split = information.toString().split(":");
		System.out.println(split);
		return accounts.get(accountNumber);
	}
	public static Account information(int accountNumber) throws NoSuchAccountException{
		if(!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: "+accountNumber+" nout found!\n\n");
		}
		
		return accounts.get(accountNumber);
	}
	
	public static void makeDeposit(int accountNumber, double amount) throws AccountClosedException, NoSuchAccountException{
		
		lookup(accountNumber).deposit(amount);
		
	}
	
	public static void makeWithdrawal(int accountNumber, double amount) throws InsufficientBalanceException, NoSuchAccountException {
		lookup(accountNumber).withdraw(amount);
	}
	
	public static void closeAccount(int accountNumber) throws NoSuchAccountException {
		lookup(accountNumber).close();
	}

	
	public static double getBalance(int accountNumber) throws NoSuchAccountException {
		
		return lookup(accountNumber).getBalance();
	}

	public static void listAccounts(OutputStream out) throws IOException{
		
		out.write((byte)10);
		Collection<Account> col=accounts.values();
		
		for (Account a:col) {
			out.write(a.toString().getBytes());
			out.write((byte)10);
		}
		
		out.write((byte)10);
		out.flush();
	}
	
	public static void printAccountTransactions(int accountNumber, OutputStream out) throws IOException,NoSuchAccountException{
		
		lookup(accountNumber).printTransactions(out);
		
	}
	public static void printAccountInformation(int accountNumber, OutputStream out) throws IOException, NoSuchAccountException
	{
		//Customer c=new Customer("","", "", "");
		Customer cu = new Customer();
		out.write((byte)10);
		Collection<Account> col=accounts.values();
		
		for (Account a:col) {
			
			out.write(cu.getAllInformation().getBytes());
			//out.write((byte)10);
		}
		
		out.write((byte)10);
		out.flush();
		//System.out.println(c.getAllInformation());
		
	}
	
	public static boolean getExchangeFile() throws IOException, NumberFormatException, Exception
		
		{try 
		{
			/*File exchangeRateFile=new File("exchange-rate.csv");  //Paths for exchange Files
			Scanner reader = new Scanner(exchangeRateFile);
			FileReader read = new FileReader("exchange-rate.csv"); //Paths for exchange File*/
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"))
			.build();
			HttpResponse<String> response = client.send(request,  HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			String fileContents = response.body();
			//System.out.println(fileContents);
			//return true;
			int count = 0;
			String fileContentsSplit = "";
			String[] lines= fileContents.split(System.lineSeparator());
			double exchangeRat = 0.0;
			for (String l : lines)
			{
				String s = l;
				String[] parts = s.split(",", 3);
				String Currency = "", countryname = "", exchangeString = "";
				Currency = parts[0];
				countryname = parts[1];
				exchangeString = parts[2];
				exchangeRat = Double.parseDouble(exchangeString);
				String format = String.format("%.3f", exchangeRat);
				exchangeRat = Double.parseDouble(format);
				Currency c = new Currency(Currency, countryname, exchangeRat);

			}
			
			/*BufferedReader buffreader = new BufferedReader(read);
			
			while ((Line1 = buffreader.readLine()) !=null) // This is to print entire file
			{
				System.out.println(Line1);
			}	
			if (!exchangeRateFile.exists())
			{
				System.out.println("Fileexists");
			}
			double exchangeRat = 0.0;
			while (reader.hasNextLine() && (Line1 = buffreader.readLine()) !=null )
			{
				
				String s = reader.nextLine();
				String[] parts = s.split(",", 3);
				String Currency = "", countryname = "", exchangeString = "";
				Currency = parts[0];
				countryname = parts[1];
				exchangeString = parts[2];
				exchangeRat = Double.parseDouble(exchangeString);
				String format = String.format("%.3f", exchangeRat);
				exchangeRat = Double.parseDouble(format);
				Currency c = new Currency(Currency, countryname, exchangeRat);
			}
			if (exchangeRateFile.exists())
			{
				return true;
			}
			else
			{
				return false;
			}*/
			return true;
		
		
		}catch (FileNotFoundException w )
		{
			System.out.println("Error finding the exchange file");
			fileexistence = false;
			return false;
		}
		finally
		{
			
		}
		
	}
	public boolean getFileExistence()
	{
		return fileexistence;
	}
	public static boolean CurrencyExists(String Currenc)
	{
		Currency c = new Currency("", "", 0);
		if (c.getExchangeExists(Currenc) == true) return true;
		else return false;
	}
	public static void inputExchange(String sellCountry, String buyCountry, double amount )
	{
		try {
			Currency c = new Currency("","", 0);
			if (c.getExchangeCalc(sellCountry, buyCountry, amount).equals(null))
			{
				System.out.println("One of the currencies has to be USD.  Try again");
			}
		
			else System.out.println(c.getExchangeCalc(sellCountry, buyCountry, amount));
		} catch(NullPointerException n)
		{
			System.out.println("The currency does not exist");
		}
		
		
		
		
	}
	public static Object ReturnAccounts()
	{
		
		return accounts;
	}
	
	
	
}
