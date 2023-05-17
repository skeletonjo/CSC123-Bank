package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;
public abstract class ConfigTemplate {
	public static String result;
	public static ConfigTemplate getInstance(String configinput) throws Exception
	{
		
		if (configinput.equalsIgnoreCase("file"))
		{
			result = new HooksFile().toString();
			return new HooksFile();
		}
		else // (configinput.equalsIgnoreCase("webservice"))
		{
			result = new HooksHTTP().toString();
			return new HooksFile();
		}
		//return null;
	}
	//protected abstract InputStream getInputStream() throws Exception;
	
protected abstract InputStream getInputStream() throws Exception; 
	
	public ArrayList<String> readExchange() throws Exception{
		//get an input steam
		InputStream in=getInputStream();
		//Create stream readers / buffered reader
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		
		ArrayList<String> list=new ArrayList<String>();
		
		String line=null;
		//read lines 
		while((line=br.readLine())!=null) {
			//add lines to arraylist
			list.add(line);
		}
		
		//return array list 
		
		return list;
		
	}
}
