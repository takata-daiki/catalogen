package plugins.adufour.blocks.tools.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import icy.plugin.abstract_.Plugin;
import plugins.adufour.blocks.tools.io.WorkbookToFile.MergePolicy;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarFile;
import plugins.adufour.vars.lang.VarWorkbook;
import plugins.adufour.workbooks.Workbooks;

/**
 * IO block that reads a workbook file from disk using the POI library
 * 
 * @author Alexandre Dufour
 */
public class FileToWorkbook extends Plugin implements IOBlock
{
    VarFile     inputFile = new VarFile("input file", null);
    VarWorkbook workbook  = new VarWorkbook("workbook", (Workbook) null);
    
    @Override
    public void run()
    {
        workbook.setValue(readWorkbook(inputFile.getValue(true)));
    }
    
    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("input file", inputFile);
    }
    
    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("workbook", workbook);
    }
    
    /**
     * Reads the specified file into a workbook. This method will read Excel-compatible (.xls)
     * files, but will also read tab-delimited text files. <br/>
     * Note: tab-delimited text files may contain multiple sheets, if specified as in the following
     * example:<br/>
     * <code>== sheet 1 ==<br/>
     * item1 (tab) item2<br/>
     * == sheet 2 ==<br/>
     * item3 (tab) item4</code><br/>
     * (the same convention is followed by
     * {@link WorkbookToFile#saveAsText(Workbook, String, MergePolicy)}
     * 
     * @param file
     * @return
     */
    public static Workbook readWorkbook(File file)
    {
        if (!file.exists() || file.isDirectory()) throw new IllegalArgumentException("Cannot read a workbook from " + file.getPath());
        
        try
        {
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis);
            fis.close();
            return workbook;
        }
        catch (Exception e)
        {
            // try text
            Workbook wb = Workbooks.createEmptyWorkbook();
            Sheet sheet = null;
            
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int rowID = 0;
                boolean newLine = false;
                
                while (reader.ready())
                {
                    String line = reader.readLine();
                    
                    if (line.isEmpty())
                    {
                        // remember there is a new line, deal with it later
                        newLine = true;
                        continue;
                    }
                    
                    // 1) fetch (or create) the sheet
                    
                    // special case: sheet name saved using FileToWorkbook
                    if (line.startsWith("== "))
                    {
                        // don't add the new line if we create a new sheet
                        newLine = false;
                        String sheetName = line.substring(3, line.indexOf(" =="));
                        sheet = wb.createSheet(sheetName);
                        
                        // reset the row
                        rowID = 0;
                        
                        // nothing else interesting in this line...
                        continue;
                    }
                    
                    // otherwise, create a (default) empty sheet
                    if (sheet == null)
                    {
                        sheet = wb.createSheet();
                        // reset the row
                        rowID = 0;
                    }
                    
                    // 2) add the new line (if any)
                    if (newLine) sheet.createRow(rowID++);
                    
                    // 3) Create a new row for the current input
                    Row row = sheet.createRow(rowID++);
                    
                    // skip empty lines
                    if (line.isEmpty()) continue;
                    
                    int colID = 0;
                    
                    String[] words = line.split("\t");
                    for (String word : words)
                    {
                        if (!word.isEmpty())
                        {
                            Cell cell = row.createCell(colID);
                            try
                            {
                                cell.setCellValue(Double.parseDouble(word));
                            }
                            catch (NumberFormatException formatError)
                            {
                                cell.setCellValue(word);
                            }
                        }
                        colID++;
                    }
                }
                
                reader.close();
                
                return wb;
            }
            catch (Exception e2)
            {
                throw new RuntimeException(e2);
            }
        }
    }
}
