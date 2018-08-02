package server.web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author
 * Mikael
 */
public class convertXlsxToCsv {

    /**
     *
     * @param fileName
     * @throws IOException
     */
    public void convertXlsxToCsv(String fileName) throws IOException {
        OutputStream os = null;
        FileInputStream myInput = null;

        try {
            //File to store data in form of CSV
            String newFileName = null;
            //String fileName = file.getName();
            if (fileName.contains("xlsx")) {
                newFileName = fileName.replace("xlsx", "csv");
            } else if (fileName.contains("xls")) {
                newFileName = fileName.replace("xls", "csv");
            }
            File f = new File(newFileName);

            os = (OutputStream) new FileOutputStream(f);
            String encoding = "ISO8859-1";
            OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
            try (BufferedWriter bw = new BufferedWriter(osw)) {
                myInput = new FileInputStream(fileName);
                Workbook wb = WorkbookFactory.create(myInput);

                // Gets the sheets from workbook
                Sheet sheet = wb.getSheetAt(0);
                bw.write(sheet.getSheetName());
                bw.newLine();


                //Gets the cells from sheet
                Iterator rowIter = sheet.rowIterator();
                while (rowIter.hasNext()) {
                    Row row = (Row) rowIter.next();
                    Iterator cellIter = row.cellIterator();

                    Cell cell = (Cell) cellIter.next();
                    if (cell.getCellType() == 1) {
                        bw.write(cell.getStringCellValue());
                    } else {
                        Double doub = cell.getNumericCellValue();
                        bw.write(doub.toString());
                    }
                    System.out.println(cell.getCellType());
                    while (cellIter.hasNext()) {
                        cell = (Cell) cellIter.next();
                        bw.write(',');
                        if (cell.getCellType() == 1) {
                            bw.write(cell.getStringCellValue());
                        } else {
                            Double doub = cell.getNumericCellValue();
                            bw.write(doub.toString());
                        }

                    }
                }
                bw.newLine();

                bw.flush();
            }

        } catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            if (os != null) {
                os.close();
            }

            if (myInput != null) {
                myInput.close();
            }


        }
    }
}
