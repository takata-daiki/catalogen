package plugins.adufour.vars.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SortOrder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;
import org.math.plot.utils.Array;

import icy.gui.dialog.MessageDialog;
import icy.gui.dialog.OpenDialog;
import icy.gui.frame.IcyFrame;
import icy.math.ArrayMath;
import icy.resource.ResourceUtil;
import icy.system.IcyHandledException;
import icy.system.thread.ThreadUtil;
import plugins.adufour.blocks.tools.io.WorkbookToFile;
import plugins.adufour.blocks.tools.io.WorkbookToFile.MergePolicy;
import plugins.adufour.ezplug.EzButton;
import plugins.adufour.ezplug.EzDialog;
import plugins.adufour.ezplug.EzLabel;
import plugins.adufour.ezplug.EzVarEnum;
import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarBoolean;
import plugins.adufour.vars.lang.VarString;
import plugins.adufour.vars.lang.VarWorkbook;
import plugins.adufour.vars.util.VarListener;
import plugins.adufour.workbooks.IcySpreadSheet;
import plugins.adufour.workbooks.Workbooks;

public class WorkbookEditor extends SwingVarEditor<Workbook>
{
    final VarString                       formula           = new VarString("Formula", "");
    
    final VarBoolean                      useHeader         = new VarBoolean("Header", false);
    
    final VarBoolean                      readOnly          = new VarBoolean("read-only", false);
    
    final VarBoolean                      openButtonVisible = new VarBoolean("show open button", true)
                                                            {
                                                                public void valueChanged(Var<Boolean> source, Boolean oldValue, Boolean newValue)
                                                                {
                                                                    super.valueChanged(source, oldValue, newValue);
                                                                    if (openButton != null) ThreadUtil.invokeLater(new Runnable()
                                                                                                                            {
                                                                                                                                @Override
                                                                                                                                public void run()
                                                                                                                                {
                                                                                                                                    openButton.setVisible(false);
                                                                                                                                }
                                                                                                                            });
                                                                };
                                                            };
    
    private JButton                       openButton, exportButton, plotButton;
    
    private JTabbedPane                   tabs;
    
    private final HashMap<Sheet, JXTable> tables            = new HashMap<Sheet, JXTable>();
    
    private final HashMap<Sheet, JXTable> headers           = new HashMap<Sheet, JXTable>();
    
    class TabChangeListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent arg0)
        {
            Workbook book = variable.getValue();
            
            int sheetIndex = tabs.getSelectedIndex();
            
            if (sheetIndex == book.getNumberOfSheets())
            {
                tabs.removeTabAt(book.getNumberOfSheets());
                createSheet(book);
                updateInterfaceValue();
                tabs.setSelectedIndex(book.getNumberOfSheets() - 1);
                book.setActiveSheet(book.getNumberOfSheets() - 1);
            }
            
            if (sheetIndex >= 0)
            {
                Sheet sheet = book.getSheetAt(sheetIndex);
                book.setActiveSheet(sheetIndex);
                if (tables.containsKey(sheet))
                {
                    JXTable table = tables.get(sheet);
                    SheetModel model = (SheetModel) table.getModel();
                    updateFormulaField(model, table.getSelectedRow(), table.getSelectedColumn());
                }
            }
        }
    }
    
    private final TabChangeListener tabChangeListener = new TabChangeListener();
    
    public WorkbookEditor(VarWorkbook variable)
    {
        super(variable);
    }
    
    @Override
    protected JComponent createEditorComponent()
    {
        return new JXPanel(new BorderLayout());
    }
    
    @Override
    protected void activateListeners()
    {
        useHeader.addListener(new VarListener<Boolean>()
        {
            @Override
            public void valueChanged(Var<Boolean> source, Boolean oldValue, Boolean newValue)
            {
                updateInterfaceValue();
            }
            
            @Override
            public void referenceChanged(Var<Boolean> source, Var<? extends Boolean> oldReference, Var<? extends Boolean> newReference)
            {
            }
        });
    }
    
    @Override
    protected void deactivateListeners()
    {
        useHeader.removeListeners();
    }
    
    @Override
    public void dispose()
    {
        super.dispose();
        
        tables.clear();
        headers.clear();
        
        if (tabs != null)
        {
            tabs.removeChangeListener(tabChangeListener);
            tabs.removeAll();
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(400, 100);
    }
    
    @Override
    public double getComponentVerticalResizeFactor()
    {
        return 1.0;
    }
    
    @Override
    public boolean isComponentResizeable()
    {
        return true;
    }
    
    /**
     * Indicates whether the first table row should be used as a header. If so, the first row will
     * virtually disappear from the table and its values will be used to rename the table columns
     * 
     * @param header
     *            <code>true</code> to use the first row as a header, <code>false</code> otherwise
     */
    public void setFirstRowAsHeader(boolean header)
    {
        useHeader.setValue(header);
    }
    
    /**
     * Sets whether the open button should be visible
     * 
     * @param visible
     */
    public void setOpenButtonVisible(boolean visible)
    {
        openButtonVisible.setValue(visible);
    }
    
    /**
     * Sets whether the table editor is read-only, and therefore rejects user input
     */
    public void setReadOnly(boolean readOnly)
    {
        this.readOnly.setValue(readOnly);
    }
    
    @Override
    protected void updateInterfaceValue()
    {
        if (variable.getReference() != null)
        {
            setReadOnly(true);
            setOpenButtonVisible(false);
        }
        
        final Workbook book = variable.getValue();
        
        if (book == null) return;
        
        JXPanel wbPanel = (JXPanel) getEditorComponent();
        String bookID = "" + book.hashCode();
        
        boolean buildNewEditor = (wbPanel.getName() == null || !wbPanel.getName().equalsIgnoreCase(bookID));
        
        if (buildNewEditor)
        {
            // create a new table
            wbPanel.removeAll();
            
            // Option panel
            JPanel optionBar = new JPanel();
            optionBar.setLayout(new BoxLayout(optionBar, BoxLayout.X_AXIS));
            
            // Open
            openButton = new JButton(ResourceUtil.getImageIcon(ResourceUtil.ICON_OPEN, 18));
            openButton.setVisible(openButtonVisible.getValue());
            openButton.setBorderPainted(false);
            openButton.setFocusable(false);
            openButton.setContentAreaFilled(false);
            openButton.setToolTipText("Open/Create workbook...");
            openButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String path = OpenDialog.chooseFile("Load/Create workbook...", null, "Workbook", ".xls");
                    if (path == null) return;
                    
                    try
                    {
                        variable.setValue(WorkbookFactory.create(new File(path)));
                    }
                    catch (Exception e1)
                    {
                        MessageDialog.showDialog(e1.getMessage(), MessageDialog.ERROR_MESSAGE);
                    }
                }
            });
            optionBar.add(openButton);
            
            // Export
            exportButton = new JButton(ResourceUtil.getImageIcon(ResourceUtil.ICON_SAVE, 18));
            exportButton.setBorderPainted(false);
            exportButton.setFocusable(false);
            exportButton.setContentAreaFilled(false);
            exportButton.setToolTipText("Export workbook to disk...");
            
            final JFileChooser fileChooser = new JFileChooser();
            final FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text files (.txt)", "txt");
            final FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter("Spreadsheets (.xls)", "xls");
            
            final EzDialog confirmationDialog = new EzDialog("Confirmation");
            confirmationDialog.addEzComponent(new EzLabel("This file already exists."));
            final EzVarEnum<MergePolicy> policy = new EzVarEnum<WorkbookToFile.MergePolicy>("Action: ", MergePolicy.values());
            confirmationDialog.addEzComponent(policy);
            final VarBoolean confirmed = new VarBoolean("confirmed", true);
            confirmationDialog.addEzComponent(new EzButton("Ok", new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    confirmed.setValue(true);
                    confirmationDialog.hideDialog();
                }
            }));
            confirmationDialog.addEzComponent(new EzButton("Cancel", new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    confirmed.setValue(false);
                    confirmationDialog.hideDialog();
                }
            }));
            
            final JPopupMenu exportPopupMenu = new JPopupMenu();
            JMenuItem exportXLS = new JMenuItem("Export to an Excel file");
            exportXLS.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    fileChooser.setFileFilter(xlsFilter);
                    if (fileChooser.showSaveDialog(exportPopupMenu) != JFileChooser.APPROVE_OPTION) return;
                    File outputFile = fileChooser.getSelectedFile();
                    if (outputFile.exists())
                    {
                        // ask for confirmation + merging policy
                        confirmationDialog.showDialog(true);
                        if (confirmed.getValue()) WorkbookToFile.saveAsSpreadSheet(book, outputFile.getPath(), policy.getValue());
                    }
                    else WorkbookToFile.saveAsSpreadSheet(book, outputFile.getPath(), MergePolicy.Overwrite);
                }
            });
            JMenuItem exportTXT = new JMenuItem("Export to a text file");
            exportTXT.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    fileChooser.setFileFilter(txtFilter);
                    if (fileChooser.showSaveDialog(exportPopupMenu) != JFileChooser.APPROVE_OPTION) return;
                    File outputFile = fileChooser.getSelectedFile();
                    if (outputFile.exists())
                    {
                        // ask for confirmation + merging policy
                        confirmationDialog.showDialog(true);
                        if (confirmed.getValue()) WorkbookToFile.saveAsText(book, outputFile.getPath(), policy.getValue());
                    }
                    else WorkbookToFile.saveAsText(book, outputFile.getPath(), MergePolicy.Overwrite);
                }
            });
            exportPopupMenu.add(exportTXT);
            exportPopupMenu.add(exportXLS);
            exportButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    exportPopupMenu.show(exportButton, 0, exportButton.getHeight());
                }
            });
            optionBar.add(exportButton);
            
            // Plot (START)
            
            plotButton = new JButton(ResourceUtil.getImageIcon(ResourceUtil.getAlphaIconAsImage("align_left.png"), 18));
            plotButton.setBorderPainted(false);
            plotButton.setFocusable(false);
            plotButton.setContentAreaFilled(false);
            plotButton.setToolTipText("Plot");
            
            final JPopupMenu plotPopupMenu = new JPopupMenu();
            
            final JMenu plotHistogram1D = new JMenu("Histogram (1D)");
            plotHistogram1D.addMenuListener(new MenuListener()
            {
                @Override
                public void menuSelected(MenuEvent e)
                {
                    int sheetIndex = book.getActiveSheetIndex();
                    if (sheetIndex == -1) return;
                    
                    Sheet sheet = book.getSheetAt(sheetIndex);
                    final IcySpreadSheet icySheet = Workbooks.getSheet(book, book.getSheetName(sheetIndex));
                    
                    for (int i = 0; i < icySheet.getNumberOfColumns(); i++)
                    {
                        final double[] histData = icySheet.getColumnValues(i);
                        if (histData == null || histData.length < 2) continue;
                        
                        final String columnName = tables.get(sheet).getColumnName(i);
                        JMenuItem plotColHistogram = new JMenuItem(columnName);
                        plotColHistogram.addActionListener(new ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent)
                            {
                                double min = ArrayMath.min(histData);
                                double max = ArrayMath.max(histData);
                                int bins = Math.max(10, (int) Math.round(Math.sqrt(histData.length)));
                                Plot2DPanel panel = new Plot2DPanel();
                                panel.addHistogramPlot(columnName, histData, min, max, bins);
                                panel.setFixedBounds(0, min, max);
                                
                                // HistogramChart histoChart = new HistogramChart(histData);
                                // Group group = new Group(histoChart);
                                // final Scene scene = new Scene(group);
                                // JFXPanel panel = new JFXPanel();
                                // panel.setLayout(new BorderLayout());
                                // panel.setScene(scene);
                                
                                IcyFrame plot = new IcyFrame("Histogram of " + columnName, true);
                                plot.setPreferredSize(new Dimension(520, 420));
                                plot.addToDesktopPane();
                                plot.add(panel);
                                plot.pack();
                                plot.setVisible(true);
                            }
                        });
                        plotHistogram1D.add(plotColHistogram);
                    }
                }
                
                @Override
                public void menuDeselected(MenuEvent e)
                {
                    plotHistogram1D.removeAll();
                }
                
                @Override
                public void menuCanceled(MenuEvent e)
                {
                    
                }
            });
            
            final JMenu plotHistogram2D = new JMenu("Histogram (2D)");
            plotHistogram2D.addMenuListener(new MenuListener()
            {
                @Override
                public void menuSelected(MenuEvent e)
                {
                    int sheetIndex = book.getActiveSheetIndex();
                    if (sheetIndex == -1) return;
                    
                    Sheet sheet = book.getSheetAt(sheetIndex);
                    final IcySpreadSheet icySheet = Workbooks.getSheet(book, book.getSheetName(sheetIndex));
                    
                    JMenuItem labelFirstColumn = new JMenuItem("Select X axis...");
                    labelFirstColumn.setEnabled(false);
                    plotHistogram2D.add(labelFirstColumn);
                    plotHistogram2D.addSeparator();
                    for (int i = 0; i < icySheet.getNumberOfColumns(); i++)
                    {
                        final double[] xValues = icySheet.getColumnValues(i);
                        if (xValues == null) continue;
                        
                        final double minX = ArrayMath.min(xValues);
                        final double maxX = ArrayMath.max(xValues);
                        final int binsX = Math.max(10, (int) Math.round(Math.sqrt(xValues.length)));
                        
                        final String xColumnName = tables.get(sheet).getColumnName(i);
                        JMenu plotFirstColumn = new JMenu(xColumnName);
                        plotHistogram2D.add(plotFirstColumn);
                        
                        JMenuItem labelSecondColumn = new JMenuItem("Select Y axis...");
                        labelSecondColumn.setEnabled(false);
                        plotFirstColumn.add(labelSecondColumn);
                        plotFirstColumn.addSeparator();
                        for (int j = 0; j < icySheet.getNumberOfColumns(); j++)
                        {
                            if (i == j) continue;
                            
                            final double[] yValues = icySheet.getColumnValues(j);
                            if (yValues == null) continue;
                            
                            if (xValues.length != yValues.length) throw new IcyHandledException("Cannot create scatter plot: datasets have different sizes");
                            
                            final double minY = ArrayMath.min(yValues);
                            final double maxY = ArrayMath.max(yValues);
                            final int binsY = Math.max(10, (int) Math.round(Math.sqrt(yValues.length)));
                            
                            final String yColumnName = tables.get(sheet).getColumnName(j);
                            final String plotTitle = xColumnName + " vs. " + yColumnName;
                            
                            JMenuItem plotSecondColumn = new JMenuItem(yColumnName);
                            plotSecondColumn.addActionListener(new ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent)
                                {
                                    Plot3DPanel panel = new Plot3DPanel();
                                    double[][] xy = Array.mergeColumns(xValues, yValues);
                                    panel.addHistogramPlot(plotTitle, xy, binsX, binsY);
                                    panel.setFixedBounds(new double[] { minX, minY }, new double[] { maxX, maxY });
                                    
                                    IcyFrame plot = new IcyFrame(plotTitle, true);
                                    plot.setPreferredSize(new Dimension(520, 420));
                                    plot.addToDesktopPane();
                                    plot.add(panel);
                                    plot.pack();
                                    plot.setVisible(true);
                                }
                            });
                            plotFirstColumn.add(plotSecondColumn);
                        }
                    }
                }
                
                @Override
                public void menuDeselected(MenuEvent e)
                {
                    plotHistogram2D.removeAll();
                }
                
                @Override
                public void menuCanceled(MenuEvent e)
                {
                    
                }
            });
            
            final JMenu plotScatter2D = new JMenu("Scatter plot (2D)");
            plotScatter2D.addMenuListener(new MenuListener()
            {
                @Override
                public void menuSelected(MenuEvent e)
                {
                    int sheetIndex = book.getActiveSheetIndex();
                    if (sheetIndex == -1) return;
                    
                    Sheet sheet = book.getSheetAt(sheetIndex);
                    final IcySpreadSheet icySheet = Workbooks.getSheet(book, book.getSheetName(sheetIndex));
                    
                    JMenuItem labelFirstColumn = new JMenuItem("Select X axis...");
                    labelFirstColumn.setEnabled(false);
                    plotScatter2D.add(labelFirstColumn);
                    plotScatter2D.addSeparator();
                    for (int i = 0; i < icySheet.getNumberOfColumns(); i++)
                    {
                        final double[] xValues = icySheet.getColumnValues(i);
                        if (xValues == null) continue;
                        
                        final double minX = ArrayMath.min(xValues);
                        final double maxX = ArrayMath.max(xValues);
                        
                        final String xColumnName = tables.get(sheet).getColumnName(i);
                        JMenu plotFirstColumn = new JMenu(xColumnName);
                        plotScatter2D.add(plotFirstColumn);
                        
                        JMenuItem labelSecondColumn = new JMenuItem("Select Y axis...");
                        labelSecondColumn.setEnabled(false);
                        plotFirstColumn.add(labelSecondColumn);
                        plotFirstColumn.addSeparator();
                        for (int j = 0; j < icySheet.getNumberOfColumns(); j++)
                        {
                            if (i == j) continue;
                            
                            final double[] yValues = icySheet.getColumnValues(j);
                            if (yValues == null) continue;
                            
                            if (xValues.length != yValues.length) throw new IcyHandledException("Cannot create scatter plot: datasets have different sizes");
                            
                            final double minY = ArrayMath.min(yValues);
                            final double maxY = ArrayMath.max(yValues);
                            
                            final String yColumnName = tables.get(sheet).getColumnName(j);
                            JMenuItem plotSecondColumn = new JMenuItem(yColumnName);
                            plotSecondColumn.addActionListener(new ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent)
                                {
                                    Plot2DPanel panel = new Plot2DPanel();
                                    panel.addScatterPlot("Scatter plot", xValues, yValues);
                                    panel.setAxisLabels(xColumnName, yColumnName);
                                    panel.setFixedBounds(new double[] { minX, minY }, new double[] { maxX, maxY });
                                    
                                    IcyFrame plot = new IcyFrame(xColumnName + " vs. " + yColumnName, true);
                                    plot.setPreferredSize(new Dimension(520, 420));
                                    plot.addToDesktopPane();
                                    plot.add(panel);
                                    plot.pack();
                                    plot.setVisible(true);
                                }
                            });
                            plotFirstColumn.add(plotSecondColumn);
                        }
                    }
                }
                
                @Override
                public void menuDeselected(MenuEvent e)
                {
                    plotScatter2D.removeAll();
                }
                
                @Override
                public void menuCanceled(MenuEvent e)
                {
                    
                }
            });
            
            final JMenu plotScatter3D = new JMenu("Scatter plot (3D)");
            plotScatter3D.addMenuListener(new MenuListener()
            {
                @Override
                public void menuSelected(MenuEvent e)
                {
                    int sheetIndex = book.getActiveSheetIndex();
                    if (sheetIndex == -1) return;
                    
                    Sheet sheet = book.getSheetAt(sheetIndex);
                    final IcySpreadSheet icySheet = Workbooks.getSheet(book, book.getSheetName(sheetIndex));
                    
                    JMenuItem labelFirstColumn = new JMenuItem("Select X axis...");
                    labelFirstColumn.setEnabled(false);
                    plotScatter3D.add(labelFirstColumn);
                    plotScatter3D.addSeparator();
                    for (int i = 0; i < icySheet.getNumberOfColumns(); i++)
                    {
                        final double[] xValues = icySheet.getColumnValues(i);
                        if (xValues == null) continue;
                        
                        final double minX = ArrayMath.min(xValues);
                        final double maxX = ArrayMath.max(xValues);
                        
                        final String xColumnName = tables.get(sheet).getColumnName(i);
                        JMenu plotFirstColumn = new JMenu(xColumnName);
                        plotScatter3D.add(plotFirstColumn);
                        
                        JMenuItem labelSecondColumn = new JMenuItem("Select Y axis...");
                        labelSecondColumn.setEnabled(false);
                        plotFirstColumn.add(labelSecondColumn);
                        plotFirstColumn.addSeparator();
                        
                        for (int j = 0; j < icySheet.getNumberOfColumns(); j++)
                        {
                            if (i == j) continue;
                            
                            final double[] yValues = icySheet.getColumnValues(j);
                            if (yValues == null) continue;
                            
                            if (xValues.length != yValues.length) throw new IcyHandledException("Cannot create scatter plot: datasets have different sizes");
                            
                            final double minY = ArrayMath.min(yValues);
                            final double maxY = ArrayMath.max(yValues);
                            
                            final String yColumnName = tables.get(sheet).getColumnName(j);
                            JMenu plotSecondColumn = new JMenu(yColumnName);
                            plotFirstColumn.add(plotSecondColumn);
                            
                            JMenuItem labelThirdColumn = new JMenuItem("Select Z axis...");
                            labelThirdColumn.setEnabled(false);
                            plotSecondColumn.add(labelThirdColumn);
                            plotSecondColumn.addSeparator();
                            
                            for (int k = 0; k < icySheet.getNumberOfColumns(); k++)
                            {
                                if (i == k || j == k) continue;
                                
                                final double[] zValues = icySheet.getColumnValues(k);
                                if (zValues == null) continue;
                                
                                if (xValues.length != zValues.length) throw new IcyHandledException("Cannot create scatter plot: datasets have different sizes");
                                
                                final double minZ = ArrayMath.min(zValues);
                                final double maxZ = ArrayMath.max(zValues);
                                
                                final String zColumnName = tables.get(sheet).getColumnName(k);
                                JMenuItem plotThirdColumn = new JMenuItem(zColumnName);
                                plotSecondColumn.add(plotThirdColumn);
                                
                                plotThirdColumn.addActionListener(new ActionListener()
                                {
                                    @Override
                                    public void actionPerformed(ActionEvent actionEvent)
                                    {
                                        Plot3DPanel panel = new Plot3DPanel();
                                        panel.addScatterPlot("Scatter plot", xValues, yValues, zValues);
                                        panel.setAxisLabels(xColumnName, yColumnName, zColumnName);
                                        panel.setFixedBounds(new double[] { minX, minY, minZ }, new double[] { maxX, maxY, maxZ });
                                        
                                        IcyFrame plot = new IcyFrame(xColumnName + " vs. " + yColumnName + " vs. " + zColumnName, true);
                                        plot.setPreferredSize(new Dimension(520, 420));
                                        plot.addToDesktopPane();
                                        plot.add(panel);
                                        plot.pack();
                                        plot.setVisible(true);
                                    }
                                });
                            }
                        }
                    }
                }
                
                @Override
                public void menuDeselected(MenuEvent e)
                {
                    plotScatter3D.removeAll();
                }
                
                @Override
                public void menuCanceled(MenuEvent e)
                {
                    
                }
            });
            
            plotPopupMenu.add(plotHistogram1D);
            plotPopupMenu.add(plotHistogram2D);
            plotPopupMenu.add(plotScatter2D);
            plotPopupMenu.add(plotScatter3D);
            plotButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    plotPopupMenu.show(plotButton, 0, plotButton.getHeight());
                }
            });
            optionBar.add(plotButton);
            
            // Plot (END)
            
            // Formula
            
            VarEditor<String> formulaEditor = formula.createVarViewer();
            formulaEditor.setEnabled(true);
            optionBar.add((Component) formulaEditor.getEditorComponent());
            
            optionBar.add(Box.createHorizontalGlue());
            
            // Use header
            
            JCheckBox useHeaderOption = (JCheckBox) useHeader.createVarEditor(true).getEditorComponent();
            useHeaderOption.setText("Use first row as header");
            useHeaderOption.setFocusable(false);
            optionBar.add(useHeaderOption);
            
            wbPanel.add(optionBar, BorderLayout.NORTH);
            
            if (tabs != null) tabs.removeChangeListener(tabChangeListener);
            tabs = new JTabbedPane(JTabbedPane.BOTTOM);
            tabs.addChangeListener(tabChangeListener);
            
            wbPanel.add(tabs, BorderLayout.CENTER);
            wbPanel.setName("" + book.hashCode());
        }
        
        int nSheets = book.getNumberOfSheets();
        
        // remove unknown tabs (if any)
        tabCheck:
        for (int i = 0; i < tabs.getTabCount(); i++)
        {
            for (int j = 0; j < nSheets; j++)
                if (tabs.getTitleAt(i).equalsIgnoreCase(book.getSheetName(j))) continue tabCheck;
            
            tabs.remove(i--);
        }
        
        for (int i = 0; i < nSheets; i++)
        {
            Sheet sheet = book.getSheetAt(i);
            
            // check if a tab exists for this sheet
            
            int currentSheetIndex = -1;
            
            for (int j = 0; j < tabs.getTabCount(); j++)
                if (tabs.getTitleAt(j).equalsIgnoreCase(sheet.getSheetName()))
                {
                    currentSheetIndex = i;
                    break;
                }
            
            if (currentSheetIndex == -1)
            {
                // create an empty tab to receive the table
                tabs.addTab(sheet.getSheetName(), new JPanel());
                currentSheetIndex = tabs.getTabCount() - 1;
            }
            
            sheet = book.getSheetAt(currentSheetIndex);
            
            if (tables.containsKey(sheet))
            {
                updateTable(sheet);
            }
            else
            {
                createTable(sheet);
            }
        }
        
        // Allow the creation of a new sheet
        if (!readOnly.getValue()) tabs.addTab("+", new JPanel());
    }
    
    private static void createSheet(Workbook book)
    {
        book.createSheet("Sheet " + (book.getNumberOfSheets() + 1));
    }
    
    private void updateTable(Sheet sheet)
    {
        JXTable table = tables.get(sheet), header = headers.get(sheet);
        
        SheetModel model = (SheetModel) table.getModel();
        
        if (model.useFirstDataRowAsHeader != useHeader.getValue())
        {
            model.useFirstDataRowAsHeader = useHeader.getValue();
            model.fireTableDataChanged();
            for (TableColumn column : table.getColumns(true))
                column.setHeaderValue(table.getModel().getColumnName(column.getModelIndex()));
            table.getTableHeader().repaint();
        }
        else if (new IcySpreadSheet(sheet).getNumberOfColumns() != table.getColumnCount(true))
        {
            model.fireTableStructureChanged();
        }
        
        // update the line numbers (on the left-hand side)
        header.tableChanged(new TableModelEvent(header.getModel()));
        table.tableChanged(new TableModelEvent(table.getModel()));
    }
    
    @SuppressWarnings("serial")
    private void createTable(final Sheet sheet)
    {
        final IcySpreadSheet icySheet = new IcySpreadSheet(sheet);
        
        final int sheetIndex = icySheet.getIndex();
        
        final SheetModel tableModel = new SheetModel(icySheet, useHeader.getValue());
        final VarString sheetName = new VarString("sheet name", sheet.getSheetName());
        
        final JXTable table = new JXTable(tableModel)
        {
            @Override
            public TableCellEditor getCellEditor(int row, int column)
            {
                DefaultCellEditor editor = new DefaultCellEditor(new JTextField())
                {
                    @Override
                    public Component getTableCellEditorComponent(JTable theTable, Object value, boolean isSelected, int theRow, int theColumn)
                    {
                        theRow = convertRowIndexToModel(theRow);
                        theColumn = convertColumnIndexToModel(theColumn);
                        
                        // formulas take the priority
                        String contents = icySheet.getFormula(theRow, theColumn);
                        
                        contents = (contents.isEmpty() ? "" : "=") + icySheet.getValue(theRow, theColumn);
                        
                        return super.getTableCellEditorComponent(theTable, contents, true, theRow, theColumn);
                    }
                };
                return editor;
            }
            
            @Override
            public TableCellRenderer getCellRenderer(int row, int column)
            {
                return new DefaultTableCellRenderer()
                {
                    @Override
                    public Component getTableCellRendererComponent(JTable theTable, Object value, boolean isSelected, boolean hasFocus, int theRow, int theColumn)
                    {
                        
                        theRow = convertRowIndexToModel(theRow);
                        theColumn = convertColumnIndexToModel(theColumn);
                        
                        if (useHeader.getValue()) theRow++;
                        
                        Component component = super.getTableCellRendererComponent(theTable, value, isSelected, hasFocus, theRow, theColumn);
                        
                        component.setBackground(icySheet.getFillColor(theRow, theColumn));
                        
                        return component;
                    }
                };
            }
        };
        
        Comparator<Object> comparator = new Comparator<Object>()
        {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public int compare(Object a, Object b)
            {
                if (a == null) a = "";
                if (b == null) b = "";
                
                // compare what is comparable
                if (a instanceof Comparable && b instanceof Comparable && (a.getClass().isAssignableFrom(b.getClass()) || b.getClass().isAssignableFrom(a.getClass())))
                {
                    return ((Comparable) a).compareTo(b);
                }
                
                // otherwise, one of them is perhaps a String
                if (a instanceof String) return -1;
                if (b instanceof String) return 1;
                
                // if none of the above, don't try comparing at all!
                return 0;
            }
        };
        
        int nCol = table.getColumnCount();
        for (int i = 0; i < nCol; i++)
        {
            table.getColumnExt(i).setComparator(comparator);
            table.setSortOrderCycle(SortOrder.DESCENDING, SortOrder.ASCENDING, SortOrder.UNSORTED);
        }
        
        table.setHorizontalScrollEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        table.setCellSelectionEnabled(true);
        table.setColumnControlVisible(true);
        table.setColumnControl(new PersistentColumnControlButton(table));
        
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Create the row header (first column with line numbers)
        // code adapted and largely inspired from:
        // http://stackoverflow.com/questions/6711877/jtable-use-row-numbers
        
        final AbstractTableModel headerModel = new AbstractTableModel()
        {
            @Override
            public int getColumnCount()
            {
                return 1;
            }
            
            @Override
            public Object getValueAt(int row, int column)
            {
                return row + 1;
            }
            
            @Override
            public int getRowCount()
            {
                return tableModel.getRowCount();
            }
        };
        
        JXTable headerTable = new JXTable(headerModel)
        {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column)
            {
                return new TableCellRenderer()
                {
                    private final TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
                    
                    Font                            headerFont     = null;
                    
                    @Override
                    public Component getTableCellRendererComponent(JTable theTable, Object value, boolean isSelected, boolean hasFocus, int theRow, int theColumn)
                    {
                        isSelected = table.getSelectionModel().isSelectedIndex(theRow);
                        
                        Component c = headerRenderer.getTableCellRendererComponent(theTable, value, isSelected, hasFocus, -1, theColumn);
                        
                        if (headerFont == null) headerFont = c.getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN);
                        
                        c.setFont(headerFont);
                        
                        return c;
                    }
                };
            }
        };
        
        headerTable.setShowGrid(true);
        headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        headerTable.setPreferredScrollableViewportSize(new Dimension(40, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        headerTable.setRowHeight(table.getRowHeight());
        
        // changes to the table should affect the header...
        
        table.getRowSorter().addRowSorterListener(new RowSorterListener()
        {
            @Override
            public void sorterChanged(RowSorterEvent e)
            {
                headerModel.fireTableDataChanged();
            }
        });
        
        // ... and clicking on the header should select the table line
        // headerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        // {
        // boolean eraseSelection = true;
        //
        // @Override
        // public void valueChanged(ListSelectionEvent selection)
        // {
        // if (eraseSelection) table.getSelectionModel().clearSelection();
        //
        // eraseSelection = false;
        //
        // int start = selection.getFirstIndex();
        // int end = selection.getLastIndex();
        //
        // // FIXME the end index always becomes the start index on the next click
        // table.setRowSelectionInterval(end, start);
        // table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
        //
        // if (!selection.getValueIsAdjusting()) eraseSelection = true;
        // }
        // });
        
        scrollPane.setRowHeaderView(headerTable);
        
        // end of row header code
        
        tables.put(sheet, table);
        headers.put(sheet, headerTable);
        
        tabs.setComponentAt(sheetIndex, scrollPane);
        
        final JLabel sheetNameLabel = new JLabel(sheetName.getValue());
        
        class Listener implements ListSelectionListener, TableColumnModelListener
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting()) return;
                
                if (table.getSelectedRow() == -1) return;
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                int column = table.convertColumnIndexToModel(table.getSelectedColumn());
                updateFormulaField(tableModel, row, column);
            }
            
            @Override
            public void columnSelectionChanged(ListSelectionEvent e)
            {
                valueChanged(e);
            }
            
            @Override
            public void columnRemoved(TableColumnModelEvent e)
            {
            }
            
            @Override
            public void columnMoved(TableColumnModelEvent e)
            {
            }
            
            @Override
            public void columnMarginChanged(ChangeEvent e)
            {
            }
            
            @Override
            public void columnAdded(TableColumnModelEvent e)
            {
            }
        }
        
        Listener l = new Listener();
        table.getSelectionModel().addListSelectionListener(l);
        table.getColumnModel().addColumnModelListener(l);
        
        if (!readOnly.getValue())
        {
            // handle sheet name changing
            
            sheetNameLabel.setRequestFocusEnabled(false);
            
            sheetNameLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 1)
                    {
                        tabs.setSelectedIndex(sheetIndex);
                    }
                    else
                    {
                        Object answer = JOptionPane.showInputDialog(sheetNameLabel, null, "Rename this sheet to...", JOptionPane.QUESTION_MESSAGE, null, null,
                                sheetName.getValue());
                        
                        if (answer != null) sheetName.setValue(answer.toString());
                    }
                }
            });
            
            sheetName.addListener(new VarListener<String>()
            {
                @Override
                public void valueChanged(Var<String> source, String oldValue, String newValue)
                {
                    variable.getValue().setSheetName(sheetIndex, newValue);
                    sheetNameLabel.setText(newValue);
                }
                
                @Override
                public void referenceChanged(Var<String> source, Var<? extends String> oldReference, Var<? extends String> newReference)
                {
                }
            });
        }
        
        tabs.setTabComponentAt(sheetIndex, sheetNameLabel);
        tabs.setTitleAt(sheetIndex, sheet.getSheetName());
    }
    
    private void updateFormulaField(SheetModel model, int row, int col)
    {
        if (col == -1) 
        {
            formula.setValue("");
            return;
        }
        
        if (model.useFirstDataRowAsHeader) row++;
        
        String value = model.sheet.getFormula(row, col);
        
        if (!value.isEmpty())
        {
            formula.setValue(" Formula: =" + value);
        }
        else
        {
            value = "" + model.sheet.getValue(row, col);
            
            if (!value.isEmpty())
            {
                formula.setValue(" Value: " + value);
            }
            else
            {
                formula.setValue("");
            }
        }
    }
    
    @SuppressWarnings("serial")
    private class SheetModel extends AbstractTableModel
    {
        final int            MAX_NB_ROWS             = 65536;
        
        final int            MAX_NB_COLS             = 256;
        
        private boolean      useFirstDataRowAsHeader = false;
        
        final IcySpreadSheet sheet;
        
        public SheetModel(IcySpreadSheet sheet, boolean useFirstRowAsHeader)
        {
            this.sheet = sheet;
            this.useFirstDataRowAsHeader = useFirstRowAsHeader;
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            if (aValue instanceof String)
            {
                String text = (String) aValue;
                
                if (text.isEmpty())
                {
                    sheet.deleteCell(rowIndex, columnIndex);
                }
                else if (text.startsWith("=")) try
                {
                    // Parse a formula first
                    sheet.setFormula(rowIndex, columnIndex, text);
                }
                catch (FormulaParseException e1)
                {
                    sheet.setValue(rowIndex, columnIndex, text);
                }
                else sheet.setValue(rowIndex, columnIndex, text);
            }
            else sheet.setValue(rowIndex, columnIndex, aValue);
            
            updateFormulaField(this, rowIndex, columnIndex);
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if (useFirstDataRowAsHeader) rowIndex++;
            
            return sheet.getValue(rowIndex, columnIndex);
        }
        
        @Override
        public int getRowCount()
        {
            if (!readOnly.getValue()) return MAX_NB_ROWS;
            
            int nbRows = sheet.getNumberOfRows();
            
            return useFirstDataRowAsHeader ? nbRows - 1 : nbRows;
        }
        
        @Override
        public String getColumnName(int columnIndex)
        {
            if (useFirstDataRowAsHeader) return "" + getValueAt(-1, columnIndex);
            
            return super.getColumnName(columnIndex);
        }
        
        @Override
        public int getColumnCount()
        {
            if (!readOnly.getValue()) return MAX_NB_COLS;
            
            return sheet.getNumberOfColumns();
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return !readOnly.getValue();
        }
    }
}
