package br.com.optcode.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import br.com.optcode.domain.Car;
import br.com.optcode.domain.CarFactory;
import br.com.optcode.domain.CarFactoryRepository;
import br.com.optcode.domain.Color;

public class ExcelCarFactoryRepository implements CarFactoryRepository {
	private String excelFileName;
	
	public ExcelCarFactoryRepository(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	private List<CarFactory> allCarFactories() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFileName));
			Sheet sheet = workbook.getSheetAt(0);
			
			List<CarFactory> carFactories = new ArrayList<CarFactory>();
			CarFactory previousCarFactory = null;
			Car previousCar = null;
			
			for (Row row : sheet) {
				String colorCode = null;
				
				for (Cell cell : row) {
					Integer currentColumn = cell.getColumnIndex();
					String cellValue = cell.getStringCellValue();
					
					switch (currentColumn) {
						case 0:
							CarFactory currentCarFactory = new CarFactory(cellValue);
							
							if (!currentCarFactory.equals(previousCarFactory)) {
								previousCarFactory = currentCarFactory;
								carFactories.add(currentCarFactory);
							}							
							
							break;
						case 1:
							Car currentCar = new Car(cellValue);
							
							if (!currentCar.equals(previousCar)) {
								previousCar = currentCar;
								previousCarFactory.addCar(currentCar);
							}
							
							break;
						case 2:
							colorCode = cellValue;
							break;
						case 3:
							previousCar.addAvailableColor(new Color(colorCode, cellValue));
							break;
					}
				}
			}
			
			return carFactories;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private XmlObject allCarFactoriesToXml() {
		XmlObject xml = XmlObject.Factory.newInstance();
		XmlCursor cursor = xml.newCursor();
		cursor.toNextToken();
		cursor.beginElement("fabricas");
		
		for (CarFactory carFactory : allCarFactories()) {
			carFactory.toXml().newCursor().copyXmlContents(cursor);
		}
		
		cursor.dispose();
		return xml;
	}

	public void allCarFactoriesToXmlFile(String filename) {
		try {
			allCarFactoriesToXml().save(
				new File("resources/car-factories.xml"), 
				new XmlOptions().setSavePrettyPrint().setSavePrettyPrintIndent(4)
			);
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
