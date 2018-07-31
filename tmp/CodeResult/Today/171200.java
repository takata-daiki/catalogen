package userInterface;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import src.Database;
import src.Leaves;

public class Today {
	
	private Color color;
	
	private Label label, label2;
	
	private Table table1, table2;
	
	private Composite group1;
	
	
	private Database data;
	private Leaves leaves;
	
	
	
	public void createComposite(Group group) {
		group1 = new Composite(group, SWT.NONE);
		group1.setLayout(new GridLayout(1, false));
		group1.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		color = new Color(null, 255, 0, 0);
		
		
		label = new Label(group1, SWT.NONE);
		label.setText("These are a tables with some of today news about employees. \n" +
				"The first contains the info about workers that have been instituted on todays date \n" +
				"and the second contains the info about which workers are absent today. \n\n");
		label.setForeground(color);
		
		createTable1();
		
		
		label2 = new Label(group1, SWT.NONE);
		label2.setText("\n\n");
		
		createTable2();
	}
	
	
	
	private void createTable1() {
		table1 = new Table(group1, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		table1.setHeaderVisible(true);
		table1.setLinesVisible(true);
		table1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		String[] ColumnTitles = {"First name", "Second Name", "Last name", "Office/Agency", "Department",
				"Subdepartment", "Position", "Pay", "Holidays", "Working experience", "Appointment date"}; 
		for (int i = 0; i < ColumnTitles.length; i++) {
			TableColumn column = new TableColumn(table1, SWT.NONE);
		    column.setText(ColumnTitles[i]);
		    column.pack();
		}
	    
		
	    data = new Database();
	    List<String> results = null;//data.thisDateAdds();
	    
	    
	    for(int i=0; i<results.size()-10;) {
	    	TableItem newItem = new TableItem(table1, SWT.NONE);
	    	newItem.setText(new String[] { results.get(i++), results.get(i++), results.get(i++),
	    		results.get(i++), results.get(i++), results.get(i++), results.get(i++), results.get(i++),
	    		results.get(i++), results.get(i++)  });
		}
	}
	
	
	
	private void createTable2() {
		table2 = new Table(group1, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		table2.setHeaderVisible(true);
		table2.setLinesVisible(true);
		table2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		String[] ColumnTitles = {"First name", "Second Name", "Last name", "Office/Agency", "Department",
				"Subdepartment", "Position", "Email", "Leave is from", "To", "Leave days",
				"Remaining leave days"  }; 
		for (int i = 0; i < ColumnTitles.length; i++) {
			TableColumn column = new TableColumn(table2, SWT.NONE);
		    column.setText(ColumnTitles[i]);
		    column.pack();
		}
	    
		
	    leaves = new Leaves();
	    List<String> results = null;//leaves.todayAbsentEmployees();
	    
	    
	    for(int i=0; i<results.size()-11;) {
	    	TableItem newItem = new TableItem(table2, SWT.NONE);
	    	newItem.setText(new String[] { results.get(i++), results.get(i++), results.get(i++),
	    		results.get(i++), results.get(i++), results.get(i++), results.get(i++), results.get(i++),
	    		results.get(i++), results.get(i++), results.get(i++)  });
		}
	}
	
	
	
	public void dispose() {
		group1.dispose();
	}
}
