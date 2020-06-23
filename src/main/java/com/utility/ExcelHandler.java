package com.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHandler extends BaseTest {
	CSVPrinter csvPrinter;

	public String getExcelData(String sheetName, int rowNum, int cellNum) {
		String retval = null;
		try {
			FileInputStream fis = new FileInputStream(
					"C:\\Users\\costrategix\\Downloads\\don_dumma_export - don_dumma_export.csv");
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.getCell(cellNum);
			retval = c.getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retval;

	}

	public int getRowCount(String sheetName) {
		int rowCount = 0;
		try {
			FileInputStream fis = new FileInputStream("C:\\Users\\costrategix\\Desktop\\input.xls");
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			rowCount = s.getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public void setExcelData(String sheetName, int rowNum, int cellNum, String data) {
		try {
			FileInputStream fis = new FileInputStream("/home/local/DOMAIN/j.adarsh/workspace/java_project/input.xls");
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.createCell(cellNum);
			c.setCellValue(data);
			FileOutputStream fos = new FileOutputStream("/home/local/DOMAIN/j.adarsh/workspace/java_project/input.xls");
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CSVParser readCSV(String file_name) {

		try {
			Reader reader = Files.newBufferedReader(Paths.get("./src/main/java/resources/" + file_name + ".csv"));
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			/*
			 * for (CSVRecord csvRecord : csvParser) {
			 * System.out.println(csvRecord.get("fdc_store_id"));
			 * System.out.println(csvRecord.get("store_city")); }
			 */
			return csvParser;

		} catch (IOException e) {
		}
		return null;

	}

	public void createCSV(String file_name, Map headers) {

		BufferedWriter writer;
		new File("./src/main/java/resources/" + file_name + ".csv");

		try {
			writer = Files.newBufferedWriter(Paths.get("./src/main/java/resources/" + file_name + ".csv"));
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withFirstRecordAsHeader());
			csvPrinter.printRecord(headers.keySet());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setRows(Map values) {
		try {
			csvPrinter.printRecord(values.values());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void flushData() {
		try {
			csvPrinter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
