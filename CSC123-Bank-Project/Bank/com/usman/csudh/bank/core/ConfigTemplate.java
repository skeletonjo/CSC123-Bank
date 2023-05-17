package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;
public abstract class ConfigTemplate {
	public static String exchangeinfo;
	public static ConfigTemplate getInstance(String configinput) throws Exception
	{
		
		if (configinput.equalsIgnoreCase("file"))
		{
			exchangeinfo = new HooksFile().toString();
			return new HooksFile();
		}
		else 
		{
			exchangeinfo = new HooksHTTP().toString();
			return new HooksFile();
		}
		//return null;
	}
protected abstract InputStream getInputStream() throws Exception; 
	
	public ArrayList<String> readExchange() throws Exception{
		
		InputStream in=getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		ArrayList<String> array=new ArrayList<String>();
		String line=null; 
		while((line=br.readLine())!=null) {
			
			array.add(line);
		}
		return array;
		
	}
}
