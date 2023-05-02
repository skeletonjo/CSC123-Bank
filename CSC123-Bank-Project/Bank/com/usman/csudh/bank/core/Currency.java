package com.usman.csudh.bank.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.io.File;
import java.io.FileNotFoundException;



public class Currency extends Bank{
	private static Map<String,Double> exchange = new TreeMap<String, Double>();
	private String threelettercurrency;
	private String countryname;
	private double exchanger;
	
	public Currency(String currencyname, String country, double exchangeRat)
	{
		
			setthreelettercurrency(currencyname);
			setcountryname(country);
			setexchanger(exchangeRat);
			exchange.put(currencyname, exchangeRat);
	}

	private void setexchanger(double exchangeRat) {
		exchanger = exchangeRat;
		
	}

	private void setcountryname(String country) {
		countryname = country;
		
	}

	private void setthreelettercurrency(String currencyname) {
		threelettercurrency = currencyname;
		
	}
	public String getCurrencyName() {
		return threelettercurrency;
	}
	
	public String getCountryName() {
		return countryname;
	}
	

	public double getExchangeRate() {
		return exchanger;
	}
	
	public double getExchangeRatefromCurrency(String key)
	{
		return exchanger ;
	}
	public boolean getExchangeExists(String checkcurrency)
	{
		if (exchange.containsKey(checkcurrency))
		{
			return true;
		}
		else return false;
	}
	public Double getExchangeCalc(String firstCoun, String secondCoun, double amounts)		
	{
		//try {
			if (firstCoun.equals("USD") || secondCoun.equals("USD"))
			{
				if (firstCoun.equals("USD") && !secondCoun.equals("USD")) { //Convert if buying country is not USD
					return amounts * exchange.get(secondCoun);
				}
			
				else if (!firstCoun.equals("USD") || secondCoun.equals("USD")) //Convert if selling country is not USD
				{
					return amounts / exchange.get(firstCoun);
				}
				else if (!firstCoun.equals("USD") || secondCoun.equals("USD")) //BOTH are USD
				{
					return amounts * exchange.get(firstCoun);
				}
			}
			else return null; //None are USD
			return 0.0;
		/*} catch (NullPointerException n)
		{
			System.out.println("The currency does not exist");
			return null;
		}*/
	}
	public Double getExchangeCurrency (String key)
	{
		if (exchange.get(key) == null)
		{
			System.out.println("Currency is wrong");
			return null;
		}
		else return exchange.get(key);
	}

}
