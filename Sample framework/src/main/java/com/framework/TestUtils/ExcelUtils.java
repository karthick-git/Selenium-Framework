package com.framework.TestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

public class ExcelUtils {
	private static HSSFSheet ExcelWSheet;
	private static HSSFWorkbook ExcelWBook;
	private static HSSFCell Cell;
	//private static XSSFRow Row;

//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path,String SheetName) throws Exception 
	{
	   try 
	   {
		   // Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new HSSFWorkbook(ExcelFile);		
			ExcelWSheet = ExcelWBook.getSheet(SheetName);		
		} catch (Exception e)
	   {
				throw (e);
			
	   }
	}

	public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow) throws Exception
	{   
		String[][] tabArray = null;
		try
		{
		   FileInputStream ExcelFile = new FileInputStream(FilePath);
		   // Access the required test data sheet
		   ExcelWBook = new HSSFWorkbook(ExcelFile);
		   ExcelWSheet = ExcelWBook.getSheet(SheetName);
		   int startCol = 1;
		   int ci=0,cj=0;
		   int totalRows = 1;
		   int totalCols = 2;
	//		int rowCount =(ExcelWSheet.getLastRowNum()+1)-(ExcelWSheet.getFirstRowNum());
	//		XSSFRow row = (ExcelWSheet.getRow(iTestCaseRow));
	//		XSSFRow toprow = (ExcelWSheet.getRow(0));
	//		for (int k=0; k < toprow.getLastCellNum(); k++){
	//			String cellvalue = toprow.getCell(k).getStringCellValue();
	//			if(cellvalue.indexOf(value) != -1){
	//				 System.out.println(cellvalue+" in the sheet matches with the provided coulumn value "+value);
	//				 return row.getCell(k).getStringCellValue();
	//				 }
	//		} return null;	   
		   
		   tabArray=new String[totalRows][totalCols];
			   for (int j=startCol;j<=totalCols;j++, cj++)
			   {
				   tabArray[ci][cj]=getCellData(iTestCaseRow,j);
				   System.out.println(tabArray[ci][cj]);
			   }
		}catch (FileNotFoundException e)
		{
			System.out.println("Could not read the Excel sheet, File Not found exception");
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			System.out.println("Could not read the Excel sheet, IO Exception");
			e.printStackTrace();
		}
		return(tabArray);
	}

//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
//To call using Coulumn Number
	public static String getCellData(int RowNum, int ColNum) throws Exception
	{
	   try
	   {
		  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		  String CellData = Cell.getStringCellValue();
		  return CellData;
	   }catch (Exception e)
	   {
			return"";
	   }
	}


	//To call using Column Name
	public static String getCellData(int RowNum, String ColumnName) throws Exception
	{
	   try
	   {
		   for (int k=0; k < ExcelWSheet.getRow(0).getLastCellNum(); k++)
		   {		  
			   String cellvalue = ExcelWSheet.getRow(0).getCell(k).getStringCellValue();
			   if(cellvalue.equalsIgnoreCase(ColumnName))
			   {
				 //System.out.println(cellvalue+" in the sheet matches with the provided coulumn value "+ColumnName);
				 
				 DataFormatter formatter1 = new DataFormatter(); //creating formatter using the default locale
				 Cell = ExcelWSheet.getRow(RowNum).getCell(k);
				 String strcell1 = formatter1.formatCellValue(Cell);			 
				 			
				 return strcell1 ;
			   }
		   } 
		   return null;
		
		}catch (Exception e)
	   	{
			return"";
		}
	}


	public static String getTestCaseName(String sTestCase)throws Exception
	{
		String value = sTestCase;
		try
		{
			System.out.println(sTestCase);
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;			
		}catch (Exception e)
		{
			throw (e);
		}
	}

	public static int getRowContains(String sTestCaseName) throws Exception
	{
		int i;
		try 
		{
			int rowCount = ExcelUtils.getRowUsed();
			for ( i=0 ; i<=rowCount; i++)
			{
				if(ExcelUtils.getCellData(i,0).equalsIgnoreCase(sTestCaseName))
					break;
			}
			return i;
				
		}catch (Exception e)
		{
			throw(e);		
		}
	}

	public static int getRowUsed() throws Exception 
	{
		try
		{
			int RowCount = ExcelWSheet.getLastRowNum();
			return RowCount;
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw (e);
		}

	}
}
