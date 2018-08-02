package plugins.adufour.vars.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.processing.FilerException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.gui.swing.WorkbookEditor;
import plugins.adufour.vars.util.VarException;
import plugins.adufour.workbooks.Workbooks;
import plugins.adufour.workbooks.Workbooks.WorkbookFormat;

public class VarWorkbook extends Var<Workbook>
{
    /**
     * Creates a new variable with the workbook read from the specified file
     * 
     * @param name
     *            the name of the workbook
     * @param workbook
     *            the workbook to work with
     */
    public VarWorkbook(String name, Workbook workbook)
    {
        super(name, Workbook.class, workbook, null);
    }
    
    /**
     * Creates a new variable with the workbook read from the specified file
     * 
     * @param name
     *            the name of the workbook
     * @param file
     *            the file to read the workbook from
     */
    public VarWorkbook(String name, File file) throws InvalidFormatException, IOException
    {
        this(name, WorkbookFactory.create(file));
    }
    
    /**
     * Creates a new variable with a new empty workbook
     * 
     * @param name
     *            the name of the workbook
     * @param firstSheetName
     *            the name of the first sheet
     */
    public VarWorkbook(String name, String firstSheetName)
    {
        this(name, Workbooks.createEmptyWorkbook(WorkbookFormat.XLS));
        
        getValue().createSheet(firstSheetName);
    }
    
    @Override
    public VarEditor<Workbook> createVarEditor()
    {
        WorkbookEditor editor = new WorkbookEditor(this);
        return editor;
    }
    
    @Override
    public VarEditor<Workbook> createVarViewer()
    {
        WorkbookEditor editor = new WorkbookEditor(this);
        editor.setReadOnly(true);
        editor.setOpenButtonVisible(false);
        return editor;
    }
    
    /**
     * Save the workbook to disk
     * 
     * @param folder
     *            the folder on disk where the workbook should be saved (must exist)
     * @param workbookName
     *            the name of the workbook (without extension)
     * @throws VarException
     *             if the workbook is <code>null</code>
     * @throws IOException
     *             if the file cannot be accessed
     */
    public void saveToDisk(File folder, String workbookName) throws VarException, IOException
    {
        if (!folder.isDirectory()) throw new FilerException(folder + "is not a valid folder");
        
        Workbook wb = getValue(true);
        
        String filename = folder.getPath() + File.separator + workbookName + ".xls";
        
        if (wb instanceof XSSFWorkbook) filename = filename + "x";
        
        FileOutputStream out = new FileOutputStream(filename);
        wb.write(out);
        out.close();
    }
}
