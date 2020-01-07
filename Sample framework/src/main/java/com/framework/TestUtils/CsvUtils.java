package com.framework.TestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import com.framework.PageObjects.LoginPageObjects;
import com.framework.base.BaseClass;


public class CsvUtils extends BaseClass{
	public int row;
	public int col;
	public String username;
	public String password;
	public LoginPageObjects loginPage;
	

	public CsvUtils () throws IOException {
		super();
		
	}
	public void csv(String sTestCaseName) throws Exception{

			  File file = new File(pro.getProperty("Csv_file"));
		      FileInputStream fis = new FileInputStream(file);
		      byte[] byteArray = new byte[(int)file.length()];
		      fis.read(byteArray);
		      String data = new String(byteArray);
		      String[] stringArray = data.split("\r\n");
		      int rowcnt = stringArray.length;
		      System.out.println("Number of lines in the file are :"+rowcnt);
		      
		      String sCurrentLine;
		      BufferedReader br = new BufferedReader(new FileReader(file));
		      for(int row=1;row<=rowcnt;row++){
		    	  sCurrentLine = br.readLine();
			      String[] val = sCurrentLine.split(",");
			      if(sTestCaseName.equalsIgnoreCase(val[0])){
			    	  System.out.println(sTestCaseName +" is present in row : "+row);
			    	  username = val[2];
			    	  password = val[3];
			    	  System.out.println("Username : "+ username);
			    	  System.out.println("Password : "+ password);
			    	  System.out.println(sTestCaseName);
			    	  loginPage = new LoginPageObjects();
			    	  loginPage.login(username,password,sTestCaseName);
			      }
		      }	  
		      br.close();
		      fis.close();
	}
}
