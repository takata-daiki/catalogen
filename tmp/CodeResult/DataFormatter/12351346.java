package org.grooveclipse.xls.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsAnalysis {

	private static final String SHEET_ARG = "-n";
	private static final String INPUT_ARG = "-i";
	private static final String SETTINGS_ARG = "-s";
	private static final String HELP_ARG = "-h";
	private boolean trim = true;
	String defaultEmpty = "";

	public List<List<String>> convert(File file) throws IOException,
			InvalidFormatException {

		if (!file.exists()) {
			throw new FileNotFoundException("Can't access $file!");
		}

		Workbook workBook = WorkbookFactory.create(file);
		int numberOfSheets = workBook.getNumberOfSheets();
		String firstSheetName = null;
		if (numberOfSheets > 0) {
			Sheet sheet = workBook.getSheetAt(0);
			firstSheetName = sheet.getSheetName();
		}

		return convert(file, firstSheetName);
	}

	public List<List<String>> convert(File file, String sheetName)
			throws IOException, InvalidFormatException {

		List<List<String>> resultLines = new ArrayList<List<String>>();

		Workbook workBook = WorkbookFactory.create(file);
		FormulaEvaluator evaluator = workBook.getCreationHelper()
				.createFormulaEvaluator();
		DataFormatter formatter = new DataFormatter(true);
		Sheet sheet = workBook.getSheet(sheetName);

		if (sheet.getPhysicalNumberOfRows() > 0) {

			int lastRowNum = sheet.getLastRowNum();
			for (int j = 0; j <= lastRowNum; j++) {
				Row row = sheet.getRow(j);

				List<String> contents = new ArrayList<String>();

				// Check to ensure that a row was recovered from the sheet as it
				// is
				// possible that one or more rows between other populated rows
				// could
				// be
				// missing - blank. If the row does contain cells then...
				if (row != null) {

					String content = null;

					Cell cell = null;
					int lastCellNum = 0;

					// Get the index for the right most cell on the row and then
					// step along the row from left to right recovering the
					// contents
					// of each cell, converting that into a formatted String

					lastCellNum = row.getLastCellNum();
					for (int cellIdx = 0; cellIdx <= lastCellNum; cellIdx++) {
						cell = row.getCell(cellIdx);
						content = xlsUtil.toString(evaluator, formatter, cell);
						contents.add(content);
					}
				}
				resultLines.add(contents);
			}
		}

		return resultLines;
	}

	private static XlsUtil xlsUtil = XlsUtil.getInstance();

}