package be.artesis.timelog.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import be.artesis.timelog.gui.UserInterface;
import be.artesis.timelog.view.DataInputException;
import be.artesis.timelog.view.Project;

public class Excel {

	private Project project;
	private String fileSavePath;
	
	public final File fileLocation = new File("factuur\\sjabloon.chrono"); //Excel .xls sjabloon file
	
	public Excel(Project project, String fileSavePath) {
		this.project = project;
		this.fileSavePath = fileSavePath;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws DataInputException 
	 */
	public void makeFile() throws IOException, DataInputException {
		
		UserInterface.getUser().getOpdrachtgever(project.getOpdrachtgeverId());
		
		FileInputStream file = new FileInputStream(fileLocation);
		//Get the workbook instance for XLS file 
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		//Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);
		int aantal = project.getTaken().size();
		int cellnum1;
		int cellnum2;
		Row row;
		Cell cell;
		
		/* 
		 * Vul gebruiker gegevens in
		 */
		
		//Firma naam
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getVolledigeNaam());
		
		//Firma straat
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getStraat()); // No entry in database
				
		//Firma plaats
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getPlaats()); // No entry in database
		
		//Firma telefoon
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getTelefoonnummer()); // Geen get methode in userinterface
		
		//Firma IBAN
		row = sheet.createRow(5);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getIBAN());
		
		//Firma BIC
		row = sheet.createRow(6);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getBIC());
		
		//Firma BTW
		row = sheet.createRow(7);
		cell = row.createCell(0);
		cell.setCellValue(UserInterface.getUser().getVAT());
				
		// Gegevens opdrachtgever
		
		//opdrachtgever firmanaam
		row = sheet.createRow(4);
		cell = row.createCell(5);
		cell.setCellValue(UserInterface.getUser().getOpdrachtgever(project.getOpdrachtgeverId()).getBedrijfsnaam());
		
		//opdrachtgever naam
		row = sheet.createRow(5);
		cell = row.createCell(5);
		cell.setCellValue(UserInterface.getUser().getOpdrachtgever(project.getOpdrachtgeverId()).getNaam());
		
		//opdrachtgever adres
		row = sheet.createRow(6);
		cell = row.createCell(5);
		cell.setCellValue("Bouwmeestersstraat 2");
		
		//opdrachtgever plaats
		row = sheet.createRow(6);
		cell = row.createCell(5);
		cell.setCellValue("2000 Antwerpen");
		
		//// Gegevens op FACTUUR lijn
		Date date = new Date();
		//datum
		row = sheet.createRow(12);
		cell = row.createCell(0);
		cell.setCellValue(date.getDate());
				
		//nummer
		row = sheet.createRow(12);
		cell = row.createCell(1);
		cell.setCellValue("1002");
		
		//vervalsdag
		long addDays = date.getTime() / 1000 + 604800;
		Date newDate = new Date(addDays);
		row = sheet.createRow(12);
		cell = row.createCell(2);
		cell.setCellValue(newDate);
		
		// opdrachtgever BTW
		row = sheet.createRow(12);
		cell = row.createCell(3);
		cell.setCellValue("BE 123.654.789");
		
		/*
		 *  Vul alle taken in
		 */
		
		// begin rij
		row = sheet.getRow(14);
		Cell basisCell = row.getCell(0);
		// CellStyle style = basisCell.getCellStyle();
		basisCell = row.getCell(5);
		CellStyle styleeuro = basisCell.getCellStyle();
		
		HSSFCellStyle styleUren = workbook.createCellStyle();
		styleUren.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		HSSFCellStyle styleNaam = workbook.createCellStyle();
		styleNaam.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		HSSFCellStyle styleEndOfTasks = workbook.createCellStyle();
		styleEndOfTasks.setBorderTop(HSSFCellStyle.BORDER_THIN);

		for (int i = 0; i < aantal; i++) {
			row = sheet.createRow(i+14);
			
			//Taak naam cell
			cell = row.createCell(0);
			cell.setCellValue(project.getTaken().get(i).getNaam());
			cell.setCellStyle(styleNaam);
			
			// 2 blanke cellen met borders
			/*cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("");
			cell.setCellStyle(style);*/
			//Uren cell
			double uren = project.getTaken().get(i).getTotaleWerktijd()/3600;
			cell = row.createCell(3);
			cell.setCellValue(uren);
			cell.setCellStyle(styleUren);
			
			// Bedrag EP cell
			cell = row.createCell(4);
			cell.setCellValue(50);
			cell.setCellStyle(styleeuro);
			
			//Totaal
			cellnum1 = 15+i;
			cell = row.createCell(5);
			cell.setCellFormula("D"+ cellnum1 +"*E"+cellnum1);
			cell.setCellStyle(styleeuro);
		}
		
		// Create blank line with Top border on end of tasks
		row = sheet.createRow(aantal+14);
		cell = row.createCell(0);
		cell.setCellStyle(styleEndOfTasks);
		
		cell = row.createCell(1);
		cell.setCellStyle(styleEndOfTasks);
		
		cell = row.createCell(2);
		cell.setCellStyle(styleEndOfTasks);
		
		cell = row.createCell(3);
		cell.setCellStyle(styleEndOfTasks);
		
		
		// Netto
		row = sheet.createRow(14+aantal+1);
		cell = row.createCell(4);
		cell.setCellValue("NETTO");
		
		cell = row.createCell(5);
		cellnum1 = 14+aantal;
		cell.setCellFormula("SUM(F15:F"+ cellnum1 +")");
		cell.setCellStyle(styleeuro);
		//
		
		// BTW
		row = sheet.createRow(14+aantal+1+1);
		cell = row.createCell(4);
		cell.setCellValue("BTW");
				
		cell = row.createCell(5);
		cellnum1 = 14+aantal+1+1;
		cell.setCellFormula("F14*F"+ cellnum1);
		cell.setCellStyle(styleeuro);
		//
		
		// Bruto
		row = sheet.createRow(14+aantal+1+1+1);
		cell = row.createCell(4);
		cell.setCellValue("BRUTO");
		
		cell = row.createCell(5);
		cellnum1 = 14+aantal+1+1;
		cellnum2 = 14+aantal+1+1+1;
		cell.setCellFormula("SUM(F"+ cellnum1 +":F"+ cellnum2 +")");
		cell.setCellStyle(styleeuro);
		//
		
		file.close();
		
		//
	    FileOutputStream outFile = new FileOutputStream(new File(fileSavePath));
	    workbook.write(outFile);
	    outFile.close();
	}
}