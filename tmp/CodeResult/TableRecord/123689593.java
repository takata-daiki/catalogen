package com.bhpb.xmleditor.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import java.util.Vector;
import java.util.logging.Logger;

import com.bhpb.xmleditor.XmlEditorGUI;
import com.bhpb.xmleditor.swing.model.FileListModel;
import com.bhpb.xmleditor.swing.model.TableRecord;
import com.bhpb.xmleditor.swing.model.EntryTableModel;
import javax.swing.table.DefaultTableModel;
import com.bhpb.qiworkbench.compAPI.model.*;

/**
 * there is no array kept for the list data, rather it is generated
 * on the fly as only those elements are needed.
 *
 * @version 1.0
 */
public class PluginManager implements ChangeListener {
	private static Logger logger = Logger.getLogger(PluginManager.class.getName());
	private static final long serialVersionUID = 1L;
	int     schemaId;
	XmlEditorGUI gui;
    FileListModel      listModel = new FileListModel();
    JTable             table      = null;
    DefaultTableModel  tableModel = null;
    
    JTextArea     textArea;
    PackagePanel p2Panel = null;
    JTabbedPane tab;
    ImageIcon images[] = new ImageIcon[7];
    
    int activeIndex = 0;
    
    int listNum = 0;
    int tableNum = 0;
    int listSelections[] = {0, 0, 0, 0};
    int listIndex = 0;
    int tableIndex = 0;
    
    
    public PluginManager(XmlEditorGUI gui) {
       loadImages();
	   this.gui = gui;
	   tab = new JTabbedPane();
	   tab.getModel().addChangeListener(this);
	   
	   
	   tab.add("Packages", p2Panel.getBasePanel());
	  // tab.add("Schemas",  pPanel.getBasePanel());
	  // tab.add("Manage",   mPanel.getBasePanel());
	   tab.add("Relations", createTextPane());
	   this.gui.setInternalFrame(tab);
	   //schemaPanel.clickRadioButton(gui.getAgent().getSchemaIndex());
    }

    
    public void stateChanged(ChangeEvent e) {
 	   SingleSelectionModel model = (SingleSelectionModel) e.getSource();
 	   if(model.getSelectedIndex() == 0) {
 		  activeIndex = 0;
 	   } else if(model.getSelectedIndex() == 1) {
 		  activeIndex = 1;
 	   } else if(model.getSelectedIndex() == 2) {
 		  activeIndex = 2;
 	   } else if(model.getSelectedIndex() == 3) {
 		   activeIndex = 3;
 		   textArea.setText(gui.getAgent().getStrPkg());
 	   }
 	   
     }
    
    public void keepThis(int listNum1) {
    	//pPanel.keepThis(listNum1);
    }
     public void setListType() {
    	 listSelections[listNum] = listIndex;
    	 //pPanel.setListType(listNum, listIndex);
     }
     
    /*
     public void setSchemaFile(int  index) {
    	schemaId = index;
    	StringBuffer sb = new StringBuffer();
		sb.append("<font face=\"Arial\" color=\"blue\" size=3>Build-In Schemas and related files !!</font>");
		sb.append("<font face=\"Arial\" size=3>");
		sb.append("<br>Schema: "   + getFileLink().getSchemas().get(index).getFileName());
		sb.append("<br>XML File: Not Specified");
		sb.append("<br>");
		sb.append("</font>");
		schemaPanel.getEditorPane().setText(sb.toString());
		schemaPanel.getLoadButton().setEnabled(false);
		schemaPanel.getDelFileButton().setEnabled(false);
     }
     
     public void setFileDir(InfoFile node) {
     	currFile = node;
    	StringBuffer sb = new StringBuffer();
    	sb.append("<font face=\"Arial\" color=\"blue\" size=3>Build-In Schemas and related files !!</font>");
		sb.append("<font face=\"Arial\" size=3>");
		sb.append("<br>Schema: "     + getFileLink().getSchemas().get(schemaId).getFileName());
		sb.append("<br>XML: "   + currFile.getFullPath());
		sb.append("<br>");
		sb.append("</font>");
		schemaPanel.getEditorPane().setText(sb.toString());
		schemaPanel.getLoadButton().setEnabled(false);
		schemaPanel.getDelFileButton().setEnabled(false);
      }*/

     public XmlEditorGUI getUI() {
    	 return gui;
     }
     
     public int getActiveIndex() {
    	 return activeIndex;
     }
     
     public int getSchemaId() {
    	 return schemaId;
     }
     
    public PluginModel getPluginMdoel() {
    	return this.gui.getAgent().getPkgConfig();
    }
    
    public PackageLoad getPackageLoad(String packName) {
    	return null;
    	//return this.gui.getAgent().getGlobalConfig().getPackageLoad(packName);
    }
    
    public ImageIcon createImageIcon(String filename, String description) {
 	   if(gui != null) {
 	       return gui.createImageIcon(filename, description);
 	   } else {
 	       String path = "/res/" + filename;
 	       return new ImageIcon(getClass().getResource(path), description); 
 	   }
     }
    
    public ImageIcon getImageIcon(int index) {
    	return images[index];
    }
    
    public void removeFile() {
    	/*
    	gui.getAgent().getGlobalConfig().removeXmlFile(schemaId, currFile);
    	currentPanel.clickRadioButton(schemaId);*/
    }
    
    public void removeSchema(int index) {
    	/*
    	currentPanel.removeRadioButton(index);
    	currentPanel.clickRadioButton(0);*/
    }
    
    void loadImages() {
	    images[0] = createImageIcon("images/yellow.gif", "");
	    images[1] = createImageIcon("images/green.gif",  "");
	    images[2] = createImageIcon("images/magenta.gif","");
	    images[3] = createImageIcon("images/gray.gif",   "");
	    images[4] = createImageIcon("images/blue.gif",   "");
	    images[5] = createImageIcon("images/rbh.gif",    "");
	    images[6] = createImageIcon("images/rbsh.gif",   "");   
    }
    
    private JScrollPane createTextPane() {
    	textArea = new JTextArea();
    	textArea.setEditable(false);
    	JScrollPane scrollPane = new JScrollPane(textArea);  
 	   return scrollPane;
    }


	public FileListModel getListModel() {
		return listModel;
	}


	public void setListModel(FileListModel listModel) {
		this.listModel = listModel;
	}


	public int getTableIndex() {
		return tableIndex;
	}


	public DefaultTableModel getTableModel() {
		return tableModel;
	}


	public void setTableModel(EntryTableModel tableModel) {
		this.tableModel = tableModel;
	}


	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
		if (tableNum > 1)  {
			//tableModel = pPanel.getModel();
			//pPanel.keepNone();
			Vector record = (Vector)tableModel.getDataVector().get(tableIndex);
			TableRecord line = getTableRecor(record);
		    //pPanel.setValues(line);
		} else { 
		  //tableModel = mPanel.getTableModel(tableNum);
		  //mPanel.clearSelection(tableNum);
		  Vector record = (Vector)tableModel.getDataVector().get(tableIndex);
		  TableRecord line = getTableRecor(record);
		  //mPanel.setValues(line);
		}
		
	}

	
	public void addPackage(int index) {
		//String[] strs = mPanel.getParams(index);
		//tableModel = mPanel.getTableModel(index);
		//tableModel.addRow(strs);
	}
	
	public void addPackage() {
		String[] strs = p2Panel.getParams();
		tableModel = p2Panel.getModel();
		tableModel.addRow(strs);
	}
	
	public void deletePackage(int index) {
		//tableModel = mPanel.getTableModel(index);
		tableModel.removeRow(tableIndex);
	}
	
	public void updatePackage(int index) {
		//String[] strs = mPanel.getParams(index);
		//tableModel = mPanel.getTableModel(index);
		tableModel.removeRow(tableIndex);
		//tableModel.addRow(strs);
	}
	
	public void updatePackage() {
		//String[] strs = mPanel.getParams(1);
		//tableModel = mPanel.getTableModel(1);
		tableModel.removeRow(tableIndex);
		//tableModel.addRow(strs);
	}
	
	private TableRecord getTableRecor(Vector record) {
		TableRecord line = new TableRecord();
			line.setDispName((String)record.get(0));
			line.setSchema((String)record.get(1));
			line.setPlugin((String)record.get(2));
			line.setConfig((String)record.get(3));
			line.setType("Build-in");
		return line;
	}

	public int getTableNum() {
		return tableNum;
	}


	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}


	public int getListIndex() {
		return listIndex;
	}


	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}


	public int getListNum() {
		return listNum;
	}


	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	
	public void setSelection(int listNum, int selected) {
		this.listNum = listNum;
		this.listIndex = selected;
		listSelections[listNum] = selected;
	}

}
