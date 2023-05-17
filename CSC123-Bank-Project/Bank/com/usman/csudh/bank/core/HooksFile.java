package com.usman.csudh.bank.core;

import java.io.InputStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.util.*;
public class HooksFile extends ConfigTemplate{
	@Override
	protected InputStream getInputStream() throws Exception {
		
		return new FileInputStream(new File("exchange-rate.csv"));

	}

}
