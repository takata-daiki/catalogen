package plugins.adufour.workbooks;

import java.awt.Color;
import java.io.File;

import javax.swing.BoxLayout;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import icy.plugin.abstract_.PluginActionable;
import icy.plugin.interface_.PluginThreaded;
import icy.system.thread.ThreadUtil;
import plugins.adufour.blocks.tools.io.FileToWorkbook;
import plugins.adufour.blocks.tools.io.WorkbookToFile;
import plugins.adufour.blocks.tools.io.WorkbookToFile.MergePolicy;
import plugins.adufour.ezplug.EzDialog;
import plugins.adufour.vars.gui.swing.WorkbookEditor;
import plugins.adufour.vars.lang.VarWorkbook;

/**
 * Main class loaded by Icy to provide workbook manipulation and editing facilities
 * 
 * @author Alexandre Dufour
 */
public class Workbooks extends PluginActionable implements PluginThreaded
{
    /**
     * List of supported workbook formats
     * 
     * @author Alexandre Dufour
     */
    public enum WorkbookFormat
    {
        /** Legacy format (compatible with Excel <= 2004). Limited to 256 columns and 65536 rows */
        XLS,
        
        /** Recommended format (compatible with Excel >= 2007) */
        XLSX;
        
        Workbook createEmptyWorkbook()
        {
            switch (this)
            {
            case XLS:
                return new HSSFWorkbook();
            case XLSX:
                return new XSSFWorkbook();
            default:
                throw new UnsupportedOperationException("Unknown format: " + toString());
            }
        }
        
        public String getExtension()
        {
            return '.' + name().toLowerCase();
        }
        
        public static WorkbookFormat getFormat(Workbook workbook)
        {
            if (workbook instanceof HSSFWorkbook) return XLS;
            if (workbook instanceof XSSFWorkbook) return XLSX;
            
            throw new IllegalArgumentException("Unknown format for workbook " + workbook);
        }
    }
    
    private final String defaultTitle = "Icy Workbooks v." + getDescriptor().getVersion().getMajor() + "." + getDescriptor().getVersion().getMinor();
    
    @Override
    public void run()
    {
        show(createEmptyWorkbook(), defaultTitle, true);
    }
    
    /**
     * @return A new empty workbook using the old compatibility format (XLS).
     */
    public static Workbook createEmptyWorkbook()
    {
        return createEmptyWorkbook(WorkbookFormat.XLSX);
    }
    
    /**
     * Creates a new (empty) workbook with the specified format
     * 
     * @param format
     *            the format of the workbook to create (see {@link WorkbookFormat})
     * @return a new (empty) workbook
     * @see WorkbookFormat
     * @see WorkbookFormat#XLS
     */
    public static Workbook createEmptyWorkbook(WorkbookFormat format)
    {
        Workbook workbook = format.createEmptyWorkbook();
        workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
        return workbook;
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
     * @param filePath
     *            the path of the file to open
     * @return
     */
    public static Workbook openWorkbook(String filePath)
    {
        return openWorkbook(new File(filePath));
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
     *            the file to open
     * @return
     */
    public static Workbook openWorkbook(File file)
    {
        return FileToWorkbook.readWorkbook(file);
    }
    
    /**
     * @param wb
     * @return The format of this workbook (see the {@link WorkbookFormat} enumeration)
     */
    public static WorkbookFormat getFormat(Workbook wb)
    {
        return WorkbookFormat.getFormat(wb);
    }
    
    /**
     * Fetches the specified sheet from the specified workbook. The sheet is created if necessary.
     * <br/>
     * NB: If the provided sheet name contains invalid characters, they are automatically replaced
     * in order to comply with the workbook format (similarly to the
     * {@link #containsSheet(Workbook, String)} method)
     * 
     * @param workbook
     *            the workbook where the sheet should be fetched (or created)
     * @param sheetName
     *            the name of the sheet to fetch or create. Note that the final sheet name may be
     *            different from the provided name, for instance if the name contains special
     *            characters
     * @return the sheet with the specified name, wrapped into a {@link IcySpreadSheet} object for
     *         simplified manipulation
     */
    public static IcySpreadSheet getSheet(Workbook workbook, String sheetName)
    {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet != null) return new IcySpreadSheet(sheet);
        
        sheetName = WorkbookUtil.createSafeSheetName(sheetName);
        sheet = workbook.getSheet(sheetName);
        
        return new IcySpreadSheet(sheet != null ? sheet : workbook.createSheet(sheetName));
    }
    
    /**
     * Checks whether the specified workbook contains a sheet with the specified name.<br/>
     * NB: If the provided sheet name contains invalid characters, they are automatically replaced
     * in order to comply with the workbook format (similarly to the
     * {@link #getSheet(Workbook, String)} method)
     * 
     * @param workbook
     *            the workbook where the sheet should be searched for
     * @param sheetName
     *            the name of the sheet to search for
     * @return <code>true</code> if the sheet exists, <code>false</code> otherwise
     */
    public static boolean containsSheet(Workbook workbook, String sheetName)
    {
        sheetName = WorkbookUtil.createSafeSheetName(sheetName);
        return workbook.getSheet(sheetName) != null;
    }
    
    /**
     * Shows the specified workbook on screen in an editor window
     * 
     * @param workbook
     *            the workbook to show
     * @param editable
     *            <code>true</code> if the user can edit the workbook, <code>false</code> otherwise
     */
    public static void show(Workbook workbook, String windowTitle)
    {
        show(workbook, windowTitle, false);
    }
    
    /**
     * Shows the specified workbook on screen in an editor window
     * 
     * @param workbook
     *            the workbook to show
     * @param editable
     *            <code>true</code> if the user can edit the workbook, <code>false</code> otherwise
     */
    public static void show(final Workbook workbook, final String windowTitle, final boolean editable)
    {
        final VarWorkbook wb = new VarWorkbook(windowTitle, workbook);
        
        ThreadUtil.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                final EzDialog dialog = new EzDialog(windowTitle);
                
                dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
                
                WorkbookEditor editor = new WorkbookEditor(wb);
                editor.setReadOnly(!editable);
                editor.setEnabled(true);
                dialog.add(editor.getEditorComponent());
                
                dialog.addToDesktopPane();
                dialog.setVisible(true);
            }
        });
    }
    
    /**
     * A test to make sure the plug-in works as intended. Also useful as a sample code
     */
    public static void test()
    {
        // Create an empty workbook
        Workbook wb = Workbooks.createEmptyWorkbook();
        
        // Get a (possibly new) sheet
        IcySpreadSheet sheet = Workbooks.getSheet(wb, "Test");
        
        // Set the header row (all at once, easier to write!)
        sheet.setRow(0, "Col 0", "Col 1", "Some other column"); // etc.
        
        // Assign a few cell values
        // NB: give any object (unknown types are converted to text)
        sheet.setValue(0, 0, "Name");
        sheet.setValue(1, 0, 3);
        
        // Need to insert a formula?
        // NB: this is the standard formula syntax. The first (corner) cell is called "A1"
        sheet.setFormula(1, 1, "A2 * A2");
        
        // How about changing the background color?
        sheet.setFillColor(1, 0, Color.cyan);
        
        // Finally, show the workbook on screen
        // with a nice window title and whether the table should be editable
        Workbooks.show(wb, "Workbook test", false);
    }
}
