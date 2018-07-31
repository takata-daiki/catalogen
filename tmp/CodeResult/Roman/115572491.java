package gnukhata.views;

import gnukhata.globals;
import gnukhata.controllers.reportController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class viewProfitAndLossReport extends Composite {
	
	int counter = 0;
	static Display display;
	Table tblProfitAndLoss;
	Table tblProfitAndLoss1;
	//Table tblProfitAndLoss;
	Table tblProfitAndLoss3;
	TableItem headerRow;
	TableColumn by;
	TableColumn accname;
	TableColumn amtincome;
	
	TableItem headerRow1;
	TableColumn by1;
	TableColumn accname1;
	TableColumn amtincome1;
	
	TableItem headerRow2;
	TableColumn by2;
	TableColumn accname2;
	TableColumn amtincome2;
	
	TableItem headerRow3;
	TableColumn by3;
	TableColumn accname3;
	TableColumn amtincome3;


	Label lblby;
	Label lblaccname;
	Label lblamtincome;
	Label lblby1;
	Label lblaccname1;
	Label lblamtincome1;
	Label lblby2;
	Label lblaccname2;
	Label lblamtincome2;
	Label lblby3;
	Label lblaccname3;
	Label lblamtincome3;


	Button btnViewplForAccount;
	Button btnPrint;
	NumberFormat nf;
	ODPackage  sheetStream;
	ODPackage  iesheetStream;
	 
	Vector<Object> printPl = new Vector<Object>();
	//ArrayList<Button> accounts = new ArrayList<Button>();
	String toDateParam = ""; 
	public viewProfitAndLossReport(Composite parent, int style, String toDate, Object[]result)
	{
		super(parent,style);
		//endDateParam = endDate;
		FormLayout formlayout = new FormLayout();
		FormData layout=new FormData();
		this.setLayout(formlayout);
		
		Label lblLogo = new Label(this, SWT.None);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(67);
		layout.right = new FormAttachment(95);
		//layout.bottom = new FormAttachment(18);
		lblLogo.setLayoutData(layout);
		//Image img = new Image(display,"finallogo1.png");
		lblLogo.setImage(globals.logo);
		
		Label lblOrgDetails = new Label(this,SWT.NONE);
		lblOrgDetails.setFont( new Font(display,"Times New Roman", 11, SWT.BOLD ) );
		String strdate;
		//strdate=endDate.substring(8)+"-"+endDate.substring(5,7)+"-"+endDate.substring(0, 4);
		lblOrgDetails.setText(globals.session[1]+"\n"+"For Financial Year "+"From "+globals.session[2]+" To "+globals.session[3]);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(2);
		//layout.right = new FormAttachment(53);
		//layout.bottom = new FormAttachment(18);
		lblOrgDetails.setLayoutData(layout); 
		
		Label lblLine = new Label(this,SWT.NONE);
		lblLine.setText("----------------------------------------------------------------------------------------------------------------------------------");
		lblLine.setFont(new Font(display, "Times New Roman", 18, SWT.ITALIC));
		layout = new FormData();
		layout.top = new FormAttachment(12);
		layout.left = new FormAttachment();
		layout.right = new FormAttachment(100);
		layout.bottom = new FormAttachment(15);
		lblLine.setLayoutData(layout);
		
		

		
		Label lblheadline=new Label(this, SWT.NONE);
		//String strdate;
		strdate=toDate.substring(8)+"-"+toDate.substring(5,7)+"-"+toDate.substring(0, 4);
		toDateParam = strdate;
		if(globals.session[4].equals("profit making"))
		{
			lblheadline.setText("Profit and Loss Account For The Period "+"From "+globals.session[2]+" To "+strdate);
		}
		if(globals.session[4].equals("ngo"))
		{
			lblheadline.setText("Income and Expenditure Account For The Period "+"From "+globals.session[2]+" To "+strdate);
		}
		
		lblheadline.setFont(new Font(display, "Times New Roman", 13, SWT.ITALIC| SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblOrgDetails,2);
		layout.left = new FormAttachment(22);
		lblheadline.setLayoutData(layout);
		
		Label exp=new Label(this,SWT.NONE);
		exp.setFont(new Font(display, "Times New Roman", 12, SWT.ITALIC| SWT.BOLD));
		exp.setText("EXPENDITURE");
		layout = new FormData();
		layout.top = new FormAttachment(18);
		layout.left = new FormAttachment(20);
		exp.setLayoutData(layout);
		
		Label inc=new Label(this,SWT.NONE);
		inc.setFont(new Font(display, "Times New Roman", 12, SWT.ITALIC| SWT.BOLD));
		inc.setText("INCOME");
		layout = new FormData();
		layout.top = new FormAttachment(18);
		layout.left = new FormAttachment(65);
		inc.setLayoutData(layout);
		

			

		//table1
		tblProfitAndLoss = new Table (this, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION|SWT.LINE_SOLID);
		tblProfitAndLoss.setLinesVisible (true);
		tblProfitAndLoss.setHeaderVisible (false);
		layout = new FormData();
		layout.top = new FormAttachment(22);
		layout.left = new FormAttachment(1);
		layout.right = new FormAttachment(99);
		layout.bottom = new FormAttachment(91);
		tblProfitAndLoss.setLayoutData(layout);
		
		    btnViewplForAccount =new Button(this,SWT.PUSH);
		   if(globals.session[4].equals("profit making"))
			{
				btnViewplForAccount.setText("&Back to Profit and Loss");
			}
			if(globals.session[4].equals("ngo"))
			{
				btnViewplForAccount.setText("&Back to Income and Expenditure ");
			}
			 btnViewplForAccount.setFont(new Font(display,"Times New Roman",14,SWT.BOLD));
			layout = new FormData();
			layout.top=new FormAttachment(tblProfitAndLoss,20);
			layout.left=new FormAttachment(25);
			btnViewplForAccount.setLayoutData(layout);

		
		   btnPrint =new Button(this,SWT.PUSH);
			btnPrint.setText(" &Print ");
			btnPrint.setFont(new Font(display,"Times New Roman",14,SWT.BOLD));
			layout = new FormData();
			layout.top=new FormAttachment(tblProfitAndLoss,20);
			layout.left=new FormAttachment(60);
			btnPrint.setLayoutData(layout);

		
			this.makeaccessible(tblProfitAndLoss);
			this.getAccessible();
			this.setEvents();
			//this.pack();
			this.setBounds(this.getDisplay().getPrimaryMonitor().getBounds());
			this.setReport(result);
			//setEvents();
			try {
				sheetStream = ODPackage.createFromStream(this.getClass().getResourceAsStream("/templates/ProfitAndLoss.ots"),"ProfitAndLoss");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				iesheetStream = ODPackage.createFromStream(this.getClass().getResourceAsStream("/templates/IncomeExpenditure.ots"),"IncomeExpenditure");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	private void setReport(Object[]result)
	{
		String[] columns;
		columns=new String[]{" ","Account Name","Amount"," ","Account Name","Amount"};
		
		
		lblby = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblby.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblby.setText("");
	
		lblaccname = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblaccname.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblaccname.setText("    Account Name    ");
		
		lblamtincome = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblamtincome.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblamtincome.setText(" Amount  ");
		
		lblby1 = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblby1.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblby1.setText("");
	
		lblaccname1 = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblaccname1.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblaccname1.setText("    Account Name    ");
		
		lblamtincome1 = new Label(tblProfitAndLoss,SWT.BORDER|SWT.CENTER);
		lblamtincome1.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
		lblamtincome1.setText(" Amount  ");


        headerRow = new TableItem(tblProfitAndLoss, SWT.BORDER|SWT.COLOR_DARK_RED);
		
		TableItem[] items = tblProfitAndLoss.getItems();
		by = new TableColumn(tblProfitAndLoss,SWT.CENTER);
		accname = new TableColumn(tblProfitAndLoss,SWT.LEFT);
		amtincome = new TableColumn(tblProfitAndLoss,SWT.RIGHT);
		by1 = new TableColumn(tblProfitAndLoss,SWT.CENTER);
		accname1 = new TableColumn(tblProfitAndLoss,SWT.LEFT);
		amtincome1 = new TableColumn(tblProfitAndLoss,SWT.RIGHT);
		
		TableEditor editorby = new TableEditor(tblProfitAndLoss);
		editorby.grabHorizontal = true;
		editorby.setEditor(lblby,items[0],0);
		
		TableEditor editorAccName = new TableEditor(tblProfitAndLoss);
		editorAccName.grabHorizontal = true;
		editorAccName.setEditor(lblaccname,items[0],1);
		
		TableEditor editoramtincome = new TableEditor(tblProfitAndLoss);
		editoramtincome.grabHorizontal = true;
		editoramtincome.setEditor(lblamtincome,items[0],2);
		
		TableEditor editorby1 = new TableEditor(tblProfitAndLoss);
		editorby1.grabHorizontal = true;
		editorby1.setEditor(lblby1,items[0],3);
		
		TableEditor editorAccName1 = new TableEditor(tblProfitAndLoss);
		editorAccName1.grabHorizontal = true;
		editorAccName1.setEditor(lblaccname1,items[0],4);
		
		TableEditor editoramtincome1 = new TableEditor(tblProfitAndLoss);
		editoramtincome1.grabHorizontal = true;
		editoramtincome1.setEditor(lblamtincome1,items[0],5);
    
    

		final int tblwidth = tblProfitAndLoss.getClientArea().width;
      tblProfitAndLoss.addListener(SWT.MeasureItem, new Listener() 
		{
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				by.setWidth(4 * tblwidth / 100);
				accname.setWidth(28 * tblwidth / 100);
				amtincome.setWidth(15* tblwidth / 100);
				by1.setWidth(4 * tblwidth / 100);
				accname1.setWidth(28 * tblwidth / 100);
				amtincome1.setWidth(15 * tblwidth / 100);

				event.height = 16;
			}
		});
		
        by.pack();
		accname.pack();
		amtincome.pack();
		by1.pack();
		accname1.pack();
		amtincome1.pack();

			
		     Integer trialdata = result.length;
			  Integer balllength= result.length -10;

			 Integer grandTotal =result.length -1; 
			 Integer netTotal = result.length -2; 
			 Integer dirincm = result.length -10; 
			 Integer direxp = result.length -9; 
			 Integer indirincm = result.length -8; 
			 Integer indirexp = result.length -7; 
			 Integer grossFlag =result.length -6; 
			 Integer grossProfitloss = result.length -5; 
			 Integer netFlag = result.length -4; 
			 Integer netProfitloss = result.length -3; 
			 

			 int grpcode4=0;
				int grpcode5=0;
				int grpcode7=0;
				int grpcode8=0;
						
				for(int cnt =0; cnt < balllength; cnt++)
				{
					Object[] len = (Object[]) result[cnt];
					if(len[1].equals(4))
					{
						 grpcode4++;
					}
					if(len[1].equals(5))
					{
						 grpcode5++;
					}
					if(len[1].equals(7))
					{
						 grpcode7++;
					}
					if(len[1].equals(8))
					{
						 grpcode8++;
					}
								}
				

				grpcode5 = grpcode4 + grpcode5;
				//grpcode6= grpcode5+ grpcode6;
				grpcode7 = grpcode5 + grpcode7;
				grpcode8 = grpcode7 + grpcode8;
				
		
		 
		 TableItem tbdirexp = new TableItem(tblProfitAndLoss , SWT.NONE);
		 tbdirexp.setFont(new Font(display, "Times New Roman",14,SWT.BOLD|SWT.CENTER));
		 tbdirexp.setText(1,"DIRECT EXPENDITURE");
		 tbdirexp.setText(4,"DIRECT INCOME");
		 Object[] printableRow1 = new Object[]{"","DIRECT EXPENDITURE","","","DIRECT INCOME",""};
	     printPl.add(printableRow1);
   
		 //TableItem tbrow = new TableItem(tblProfitAndLoss , SWT.NONE); 
			
		 btnPrint.setData("printcolumns",columns);

		 for(int tbcounter = 0; tbcounter< grpcode5; tbcounter ++) 
		 { 		 
		   TableItem tbrow = new TableItem(tblProfitAndLoss , SWT.NONE); 
		   Object[] tbrecord = (Object[]) result[tbcounter]; 

		   

			if(tbrecord[1].equals(4)) 
			{ 
				if(tbrecord[4].equals("Dr"))
				{
						tbrow.setText(0,"To,"); 
						tbrow.setText(1,tbrecord[2].toString()); 
						tbrow.setText(2,tbrecord[3].toString()); 
						Object[] printableRow = new Object[]{"To,",tbrecord[2].toString(),tbrecord[3].toString(),"","",""};
					    printPl.add(printableRow);
				   
					}
				
			
			
			 
				if(tbrecord[4].equals("Cr"))
				{
						tbrow.setText(3,"By,"); 
						tbrow.setText(4,tbrecord[2].toString()); 
						tbrow.setText(5,tbrecord[3].toString()); 
						Object[] printableRow = new Object[]{"","","","By,",tbrecord[2].toString(),tbrecord[3].toString()};
					    printPl.add(printableRow);
				   
					}
				
				 if(tbrecord[3].equals("0.00"))
				 {
					/* tbrow.setText(0,""); 
					 tbrow.setText(1,""); 
					 tbrow.setText(2,"");
					 tbrow.setText(3,""); 
					 tbrow.setText(4,""); 
					 tbrow.setText(5,""); 

*/
					 tbrow.dispose();
				 }

		 
						}
	
		 
		
		   

			if(tbrecord[1].equals(5)) 
			{ 
				if(tbrecord[4].equals("Dr"))
				{
					tbrow.setText(0,"To,"); 
					tbrow.setText(1,tbrecord[2].toString()); 
					tbrow.setText(2,tbrecord[3].toString()); 
					Object[] printableRow = new Object[]{"To,",tbrecord[2].toString(),tbrecord[3].toString(),"","",""};
				    printPl.add(printableRow);
			   
					}
				
			
			
			 
				if(tbrecord[4].equals("Cr"))
				{
					tbrow.setText(3,"By,"); 
					tbrow.setText(4,tbrecord[2].toString()); 
					tbrow.setText(5,tbrecord[3].toString()); 
					Object[] printableRow = new Object[]{"","","","By,",tbrecord[2].toString(),tbrecord[3].toString()};
				    printPl.add(printableRow);
			   
					}
				
		 
				
			if(tbrecord[3].equals("0.00"))
			 {
				/* tbrow.setText(0,""); 
				 tbrow.setText(1,""); 
				 tbrow.setText(2,"");
				 tbrow.setText(3,""); 
				 tbrow.setText(4,""); 
				 tbrow.setText(5,""); 
             */
				tbrow.dispose();

			 }
		}
	}
 	

 	
	if(result[grossFlag].toString().equals("grossProfit")) 
 	{ 
 		if(globals.session[4].equals("profit making")) 
 		{ 
 			TableItem grossprofit_row = new TableItem(tblProfitAndLoss, SWT.NONE); 
 			grossprofit_row.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
 			grossprofit_row.setText(0,"To,");
 			grossprofit_row.setText(1,"Gross Profit C/F"); 
 			grossprofit_row.setText(2,result[grossProfitloss].toString()); 
 			Object[] printableRow = new Object[]{"To,","Gross Profit C/F",result[grossProfitloss].toString(),"","",""};
		    printPl.add(printableRow);
	   
 		}
					 
 		if(globals.session[4].equals("ngo")) 
 		{ 
 			TableItem grossprofit_row = new TableItem(tblProfitAndLoss, SWT.NONE);
 			grossprofit_row.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
 			grossprofit_row.setText(0,"To,");
 			grossprofit_row.setText(1,"Gross Surplus C/F"); 
 			grossprofit_row.setText(2,result[grossProfitloss].toString()); 
 			Object[] printableRow = new Object[]{"To,","Gross Surplus C/F",result[grossProfitloss].toString(),"","",""};
		    printPl.add(printableRow);
	   
 		} 
 
 	}
	
	 if(result[grossFlag].toString().equals("grossLoss")) 
	  { 
	   if(globals.session[4].equals("profit making")) 
	  { 
	  TableItem grossloss_row1 = new TableItem(tblProfitAndLoss, SWT.NONE); 
	  grossloss_row1.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
	  grossloss_row1.setText(3,"By,");
	  grossloss_row1.setText(4,"Gross Loss C/F"); 
	  grossloss_row1.setText(5,result[grossProfitloss].toString()); 
	  Object[] printableRow = new Object[]{"","","","By,","Gross Loss C/F",result[grossProfitloss].toString()};
	  printPl.add(printableRow);
 
	  }
	  					 
	   if(globals.session[4].equals("ngo")) 
	  { 
	  TableItem grossloss_row1 = new TableItem(tblProfitAndLoss, SWT.NONE); 
	  grossloss_row1.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
	  grossloss_row1.setText(3,"By,"); 
	  grossloss_row1.setText(4,"Gross Deficit C/F"); 
	  grossloss_row1.setText(5,result[grossProfitloss].toString());
	  Object[] printableRow = new Object[]{"","","","By,","Gross Deficit C/F",result[grossProfitloss].toString()};
	  printPl.add(printableRow);
 
	  } 
	   
	  }
	 
					 
						    
	if(result[grossFlag].toString().equals("grossProfit")) 
	{ 
		TableItem totalrow = new TableItem(tblProfitAndLoss, SWT.NONE); 
		totalrow.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		totalrow.setText(0,""); 
		totalrow.setText(1,"Total Of Amounts"); 
		totalrow.setText(2,result[dirincm].toString()); 
		totalrow.setText(3,""); 
		totalrow.setText(4,"Total Of Amounts"); 
		totalrow.setText(5,result[dirincm].toString()); 
		 Object[] printableRow = new Object[]{"","Total Of Amounts",result[dirincm].toString(),"","Total Of Amounts",result[dirincm].toString()};
		 printPl.add(printableRow);
	 
					        			 
	} 
	if(result[grossFlag].toString().equals("grossLoss")) 
	{ 
		TableItem totalrow = new TableItem(tblProfitAndLoss, SWT.NONE);
		totalrow.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		totalrow.setText(0,""); 
		totalrow.setText(1,"Total Of Amounts"); 
		totalrow.setText(2,result[direxp].toString()); 
		totalrow.setText(3,""); 
		totalrow.setText(4,"Total Of Amounts"); 
		totalrow.setText(5,result[direxp].toString()); 
		 Object[] printableRow = new Object[]{"","Total Of Amounts",result[direxp].toString(),"","Total Of Amounts",result[direxp].toString()};
		 printPl.add(printableRow);
	 
					        			 
	} 
	
	
	TableItem blank = new TableItem(tblProfitAndLoss , SWT.NONE);
	//blank.setFont(new Font(display, "Times New Roman",15,SWT.BOLD|SWT.CENTER));
	
		 TableItem tbindirexp = new TableItem(tblProfitAndLoss , SWT.NONE);
		  tbindirexp.setFont(new Font(display, "Times New Roman",14,SWT.BOLD|SWT.CENTER));
		  tbindirexp.setText(1,"INDIRECT EXPENDITURE");
			
		  tbindirexp.setText(4,"INDIRECT INCOME");
		  Object[] printableRow2= new Object[]{"","INDIRECT EXPENDITURE","","","INDIRECT INCOME",""};
		     printPl.add(printableRow2);
	   
		  if(result[grossFlag].toString().equals("grossLoss")) 
		  { 
		   if(globals.session[4].equals("profit making")) 
		  { 
		  TableItem grossloss_row2 = new TableItem(tblProfitAndLoss, SWT.NONE); 
		  grossloss_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossloss_row2.setText(0,"To,");
		  grossloss_row2.setText(1,"Gross Loss B/F"); 
		  grossloss_row2.setText(2,result[grossProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"To,","Gross Loss B/F",result[grossProfitloss].toString(),"","",""};
		     printPl.add(printableRow);
	   
		  }
		  					 
		   if(globals.session[4].equals("ngo")) 
		  { 
		  TableItem grossloss_row2 = new TableItem(tblProfitAndLoss, SWT.NONE); 
		  grossloss_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossloss_row2.setText(0,"To,"); 
		  grossloss_row2.setText(1,"Gross Deficit B/F"); 
		  grossloss_row2.setText(2,result[grossProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"To,","Gross Deficit B/F",result[grossProfitloss].toString(),"","",""};
		     printPl.add(printableRow);
	   
		  } 
		   
		  }
		  
		  if(result[grossFlag].toString().equals("grossProfit")) 
		  { 
		   if(globals.session[4].equals("profit making")) 
		  { 
		  TableItem grossprofit_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
		  grossprofit_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossprofit_row3.setText(3,"By,");
		  grossprofit_row3.setText(4,"Gross Profit B/F"); 
		  grossprofit_row3.setText(5,result[grossProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"","","","By,","Gross Profit B/F",result[grossProfitloss].toString()};
		     printPl.add(printableRow);
	   
		  }
		  					 
		   if(globals.session[4].equals("ngo")) 
		  { 
		  TableItem grossprofit_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
		  grossprofit_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossprofit_row3.setText(3,"By,"); 
		  grossprofit_row3.setText(4,"Gross Surplus B/F"); 
		  grossprofit_row3.setText(5,result[grossProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"","","","By,","Gross Surplus B/F",result[grossProfitloss].toString()};
		     printPl.add(printableRow);
	   
		  } 
		   
		  }

		     
		 for(int tbcounter = grpcode5; tbcounter< grpcode8; tbcounter ++) 
		 { 		 
		   TableItem tbrow = new TableItem(tblProfitAndLoss , SWT.NONE); 
		   Object[] tbrecord = (Object[]) result[tbcounter]; 

		   

			if(tbrecord[1].equals(7)) 
			{ 
				if(tbrecord[4].equals("Dr"))
				{
						tbrow.setText(0,"To,"); 
						tbrow.setText(1,tbrecord[2].toString()); 
						tbrow.setText(2,tbrecord[3].toString()); 
						Object[] printableRow= new Object[]{"To,",tbrecord[2].toString(),tbrecord[3].toString(),"","",""};
					     printPl.add(printableRow);
				   	
						
					}
				
			
			
			 
				if(tbrecord[4].equals("Cr"))
				{
					    
						tbrow.setText(3,"By,"); 
						tbrow.setText(4,tbrecord[2].toString()); 
						tbrow.setText(5,tbrecord[3].toString()); 
						Object[] printableRow= new Object[]{"","","","By,",tbrecord[2].toString(),tbrecord[3].toString()};
					     printPl.add(printableRow);
				   	
					}
				
		 
				
			if(tbrecord[3].equals("0.00"))
			 {
				 /*tbrow.setText(0,""); 
					tbrow.setText(1,""); 
					tbrow.setText(2,"");
					tbrow.setText(3,""); 
					tbrow.setText(4,""); 
					tbrow.setText(5,""); 
*/
				tbrow.dispose();

			 }
		}
	
		 
			if(tbrecord[1].equals(8)) 
			{ 
				if(tbrecord[4].equals("Dr"))
				{       
						tbrow.setText(0,"To,"); 
						tbrow.setText(1,tbrecord[2].toString()); 
						tbrow.setText(2,tbrecord[3].toString()); 
						Object[] printableRow= new Object[]{"To,",tbrecord[2].toString(),tbrecord[3].toString(),"","",""};
					     printPl.add(printableRow);
				   	
					}
				
			
			
			 
				if(tbrecord[4].equals("Cr"))
				{
					
						tbrow.setText(3,"By,"); 
						tbrow.setText(4,tbrecord[2].toString()); 
						tbrow.setText(5,tbrecord[3].toString()); 
						Object[] printableRow= new Object[]{"","","","By,",tbrecord[2].toString(),tbrecord[3].toString()};
					     printPl.add(printableRow);
				   	
					}
				
		 
				
			if(tbrecord[3].equals("0.00"))
			 {
				/* tbrow.setText(0,""); 
					tbrow.setText(1,""); 
					tbrow.setText(2,"");
					tbrow.setText(3,""); 
					tbrow.setText(4,""); 
					tbrow.setText(5,""); 

				 

*/	tbrow.dispose();
				}
			}
	}
		 if((result[grossFlag].toString().equals("grossProfit")) && (result[netFlag].toString().equals("netProfit"))) 
		  { 
		   if(globals.session[4].equals("profit making")) 
		  { 
		  TableItem grossprofit_row2 = new TableItem(tblProfitAndLoss, SWT.NONE);
		  grossprofit_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossprofit_row2.setText(0,"To,");
		  grossprofit_row2.setText(1,"Net Profit"); 
		  grossprofit_row2.setText(2,result[netProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"To,","Net Profit ",result[netProfitloss].toString(),"","",""};
		     printPl.add(printableRow);
	   
		  }
		  					 
		   if(globals.session[4].equals("ngo")) 
		  { 
		  TableItem grossprofit_row2 = new TableItem(tblProfitAndLoss, SWT.NONE); 
		  grossprofit_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
		  grossprofit_row2.setText(0,"To,"); 
		  grossprofit_row2.setText(1,"Net Surplus"); 
		  grossprofit_row2.setText(2,result[netProfitloss].toString()); 
		  Object[] printableRow= new Object[]{"To,","Net Surplus ",result[netProfitloss].toString(),"","",""};
		     printPl.add(printableRow);
	   
		  } 
		   
		  }
		  
		 
		   if((result[grossFlag].toString().equals("grossLoss")) && (result[netFlag].toString().equals("netProfit"))) 
			  { 
			   if(globals.session[4].equals("profit making")) 
			  { 
			  TableItem grossprofit_row2 = new TableItem(tblProfitAndLoss, SWT.NONE); 
			  grossprofit_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
			  grossprofit_row2.setText(0,"To,");
			  grossprofit_row2.setText(1,"Net Profit"); 
			  grossprofit_row2.setText(2,result[netProfitloss].toString());
			  Object[] printableRow= new Object[]{"To,","Net Profit ",result[netProfitloss].toString(),"","",""};
			     printPl.add(printableRow);
		   
			  }
			  					 
			   if(globals.session[4].equals("ngo")) 
			  { 
			  TableItem grossprofit_row2 = new TableItem(tblProfitAndLoss, SWT.NONE); 
			  grossprofit_row2.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
			  grossprofit_row2.setText(0,"To,"); 
			  grossprofit_row2.setText(1,"Net Surplus"); 
			  grossprofit_row2.setText(2,result[netProfitloss].toString()); 
			  Object[] printableRow= new Object[]{"To,","Net Surplus ",result[netProfitloss].toString(),"","",""};
			     printPl.add(printableRow);
		   
			  } 
			  
			  } 
				  if((result[grossFlag].toString().equals("grossProfit")) && (result[netFlag].toString().equals("netLoss"))) 
				  { 
				   if(globals.session[4].equals("profit making")) 
				  { 
				  TableItem grossloss_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
				  grossloss_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
				  grossloss_row3.setText(3,"By,");
				  grossloss_row3.setText(4,"Net Loss"); 
				  grossloss_row3.setText(5,result[netProfitloss].toString()); 
				  Object[] printableRow= new Object[]{"","","","By,","Net Loss",result[netProfitloss].toString()};
				     printPl.add(printableRow);
			   
				  }
				  					 
				   if(globals.session[4].equals("ngo")) 
				  { 
				  TableItem grossloss_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
				  grossloss_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
				  grossloss_row3.setText(3,"By,"); 
				  grossloss_row3.setText(4,"Net Deficit"); 
				  grossloss_row3.setText(5,result[netProfitloss].toString()); 
				  Object[] printableRow= new Object[]{"","","","By,","Net Deficit",result[netProfitloss].toString()};
				     printPl.add(printableRow);
			   
				  } 
				   
				  }
				  					 
				 				  
				  if((result[grossFlag].toString().equals("grossLoss")) && (result[netFlag].toString().equals("netLoss"))) 
				  { 
				   if(globals.session[4].equals("profit making")) 
				  { 
				  TableItem grossloss_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
				  grossloss_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
				  grossloss_row3.setText(3,"By,");
				  grossloss_row3.setText(4,"Net Loss"); 
				  grossloss_row3.setText(5,result[netProfitloss].toString()); 
				  Object[] printableRow= new Object[]{"","","","By,","Net Loss",result[netProfitloss].toString()};
				     printPl.add(printableRow);
			   
				  }
				  					 
				   if(globals.session[4].equals("ngo")) 
				  { 
				  TableItem grossloss_row3 = new TableItem(tblProfitAndLoss, SWT.NONE); 
				  grossloss_row3.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
				  grossloss_row3.setText(3,"By,"); 
				  grossloss_row3.setText(4,"Net Deficit"); 
				  grossloss_row3.setText(5,result[netProfitloss].toString()); 
				  Object[] printableRow= new Object[]{"","","","By,","Net Deficit",result[netProfitloss].toString()};
				     printPl.add(printableRow);
			   
				  
				  } 
				   
				  }
				  					 

		  					 
			   if(result[netFlag].toString().equals("netLoss")) 
				{ 
					TableItem totalrow = new TableItem(tblProfitAndLoss, SWT.NONE); 
					totalrow.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
					totalrow.setText(0,""); 
					totalrow.setText(1,"Total Of Amounts"); 
					totalrow.setText(2,result[netTotal].toString()); 
					totalrow.setText(3,""); 
					totalrow.setText(4,"Total Of Amounts"); 
					totalrow.setText(5,result[netTotal].toString()); 
					Object[] printableRow = new Object[]{"","Total Of Amounts",result[netTotal].toString(),"","Total Of Amounts",result[netTotal].toString()};
					 printPl.add(printableRow);
				 	
								        			 
				} 
				if(result[netFlag].toString().equals("netProfit")) 
				{ 
					TableItem totalrow = new TableItem(tblProfitAndLoss, SWT.NONE);
					totalrow.setFont(new Font(display, "Times New Roman",12,SWT.BOLD|SWT.CENTER));
					totalrow.setText(0,""); 
					totalrow.setText(1,"Total Of Amounts"); 
					totalrow.setText(2,result[grandTotal].toString()); 
					totalrow.setText(3,""); 
					totalrow.setText(4,"Total Of Amounts"); 
					totalrow.setText(5,result[grandTotal].toString()); 
					Object[] printableRow = new Object[]{"","Total Of Amounts",result[grandTotal].toString(),"","Total Of Amounts",result[grandTotal].toString()};
					 printPl.add(printableRow);
				 			        			 
				}
				tblProfitAndLoss.pack();
			}		  
			
	private void setEvents()
	{	
		tblProfitAndLoss.setFocus();
		btnViewplForAccount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				
				
				Composite grandParent = (Composite) btnViewplForAccount.getParent().getParent();
				btnViewplForAccount.getParent().dispose();
					
					viewProfitAndLoss vp=new viewProfitAndLoss(grandParent,SWT.NONE);
					vp.setSize(grandParent.getClientArea().width,grandParent.getClientArea().height);
				}
		
		});

		
			
			
		btnViewplForAccount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_RIGHT)
				{
					btnPrint.setFocus();
				}
			}
		});
		
		btnPrint.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_LEFT)
				{
					btnViewplForAccount.setFocus();
				}
			}
		});
			
			btnPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				String[] columns = (String[]) btnPrint.getData("printcolumns");
				Object[][] finalData =new Object[printPl.size()][columns.length ];
				for(int counter = 0; counter < printPl.size(); counter ++ )
				{
					Object[] printRow = (Object[]) printPl.get(counter);												
					finalData[counter] = printRow;
				}
					
				//printLedgerData.copyInto(finalData);
				
					TableModel model = new DefaultTableModel(finalData,columns);
					try {
						final File pl = new File("/tmp/gnukhata/Report_Output/ProfitandLoss" );
						final File ie = new File("/tmp/gnukhata/Report_Output/IncomeExpenditure");
						/*final File pltemplate = new File("Report_Templates/ProfitAndLoss.ots");
						final File ietemplate = new File("Report_Templates/IncomeExpenditure.ots");*/						
						//SpreadSheet.createEmpty(model).saveAs(pl);
						final Sheet plsheet =  sheetStream.getSpreadSheet().getFirstSheet();
						final Sheet iesheet =  iesheetStream.getSpreadSheet().getFirstSheet();
						plsheet.ensureRowCount(1000000);
						iesheet.ensureRowCount(1000000);
						/*plsheet.getColumn(0).setWidth(new Integer(30));
						plsheet.getColumn(1).setWidth(new Integer(50));
						plsheet.getColumn(2).setWidth(new Integer(50));
						plsheet.getColumn(3).setWidth(new Integer(35));
						plsheet.getColumn(4).setWidth(new Integer(45));
						plsheet.getColumn(5).setWidth(new Integer(45));*/
						if(globals.session[4].equals("ngo")) 
						{
							iesheet.getCellAt(0,0).setValue(globals.session[1].toString());
							iesheet.getCellAt(0,1).setValue("Income And Expenditure Account For The Period From "+globals.session[2]+" To "+globals.session[3]);
							for(int rowcounter=0; rowcounter<printPl.size(); rowcounter++)
							{
								Object[] printrow = (Object[]) printPl.get(rowcounter);
								iesheet.getCellAt(0,rowcounter+4).setValue(printrow[0].toString());
								iesheet.getCellAt(1,rowcounter+4).setValue(printrow[1].toString());
								iesheet.getCellAt(2,rowcounter+4).setValue(printrow[2].toString());
								iesheet.getCellAt(3,rowcounter+4).setValue(printrow[3].toString());
								iesheet.getCellAt(4,rowcounter+4).setValue(printrow[4].toString());
								iesheet.getCellAt(5,rowcounter+4).setValue(printrow[5].toString());
							}
							OOUtils.open(iesheet.getSpreadSheet().saveAs(ie));
						}
						else
						{
							plsheet.getCellAt(0,0).setValue(globals.session[1].toString());
							plsheet.getCellAt(0,1).setValue("Profit And Loss Account For The Period From "+globals.session[2]+" To "+globals.session[3]);
						
							for(int rowcounter=0; rowcounter<printPl.size(); rowcounter++)
							{
							Object[] printrow = (Object[]) printPl.get(rowcounter);
							plsheet.getCellAt(0,rowcounter+4).setValue(printrow[0].toString());
							plsheet.getCellAt(1,rowcounter+4).setValue(printrow[1].toString());
							plsheet.getCellAt(2,rowcounter+4).setValue(printrow[2].toString());
							plsheet.getCellAt(3,rowcounter+4).setValue(printrow[3].toString());
							plsheet.getCellAt(4,rowcounter+4).setValue(printrow[4].toString());
							plsheet.getCellAt(5,rowcounter+4).setValue(printrow[5].toString());
							}
							OOUtils.open(plsheet.getSpreadSheet().saveAs(pl));
						}
						//OOUtils.open(pl);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}

	
		public void makeaccessible(Control c)
	{
	/*
	 * getAccessible() method is the method of class Controlwhich is the
	 * parent class of all the UI components of SWT including Shell.so when
	 * the shell is made accessible all the controls which are contained by
	 * that shell are made accessible automatically.
	 */
		c.getAccessible();
	}
	protected void checkSubclass()
	{
	//this is blank method so will disable the check that prevents subclassing of shells.
	}


		




}