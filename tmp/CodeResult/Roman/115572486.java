package gnukhata.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import gnukhata.globals;
import gnukhata.controllers.accountController;
import gnukhata.controllers.transactionController;
import gnukhata.controllers.reportController;

import java.util.ArrayList;
import java.util.Vector;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ViewDualLedgr extends Composite {

	/**
	 * @param args
	 * 
	 * 
	 * 
	 */	String strOrgName;
		String strFromYear;
		String strToYear;
		String accname1;
		String oldaccName;
		int counter=0;
		Table ledgerReport2;
		Table ledgerReport1;
		TableColumn lr1_Date;
		TableColumn lr1_Particulars;
		TableColumn lr1_VoucherNumber;
		TableColumn lr1_Dr;
		TableColumn lr1_Cr;
		TableColumn lr1_Narration;
		TableColumn lr2_Date;
		TableColumn lr2_Particulars;
		TableColumn lr2_VoucherNumber;
		TableColumn lr2_Dr;
		TableColumn lr2_Cr;
		TableColumn lr2_Narration;
		
		TableItem headerRow1;
		TableItem headerRow2;
		Label lblOrgDetails;
		Label lblheadline;
		Label lblorgname;
		Label lr1_lblDate;
		Label lr1_lblParticulars;
		Label lr1_lblVoucherNumber;
		Label lr1_lblNarration;
		Label lr1_lblDr;
		Label lr1_lblCr;
		
		Label lr2_lblDate;
		Label lr2_lblParticulars;
		Label lr2_lblVoucherNumber;
		Label lr2_lblNarration;
		Label lr2_lblDr;
		Label lr2_lblCr;
		
		
		Label lblLogo;
		Label lblLink ;
		Label lblLine;
		Label lblPageName;
		Label lblPageName2;
		Label lblPeriod;
		
		Label lblPrjName;
		Label lblPrjName1;
		
		TableEditor lr1_DateEditor;
		TableEditor lr1_ParticularEditor;
		TableEditor lr1_VoucherNumberEditor;
		TableEditor lr1_DrEditotr;
		TableEditor lr1_CrEditor;
		TableEditor lr1_NarrationEditor;
		
		TableEditor lr2_DateEditor;
		TableEditor lr2_ParticularEditor;
		TableEditor lr2_VoucherNumberEditor;
		TableEditor lr2_DrEditotr;
		TableEditor lr2_CrEditor;
		TableEditor lr2_NarrationEditor;
		static Display display;
		
		Button btnViewLedgerForPeriod;
		Button btnViewLedgerForAccount;
		Button btnPrint;
		
		String strFromDate1;
		String strToDate1;
		String strFromDate;
		String strToDate;
		ArrayList<Button> voucherCodes = new ArrayList<Button>();
		String tb1;
		String accountName1;
		String startDate="";
		String endDate="";
		String projectName;
		String ledgerProject;
		boolean narrationflag;
		boolean narration;
		boolean tbDrilldown;
		boolean psDrilldown;
		boolean dualflag1;
		boolean tbflag;
		boolean projectflag;
		boolean ledgerflag;
		String oldaccName1;
		String oldprojectName1;
		String oldfromdate1;
		String oldenddate1;
		String oldselectproject1;
		boolean narration1;
		
	
		Vector<Object> printLedgerData = new Vector<Object>();
		// ViewDualLedgr(grandParent, SWT.None, result_t2,oldprojectName, narration, oldaccName,oldfromdate,oldenddate,tbflag,projectflag,tb,oldselectproject,dualflag);
			
		public ViewDualLedgr(Composite parent,int style,Object[] result_t1,Object[] result_t2,String ProjectName,String oldprojectName,boolean narrationFlag,boolean narration,String accountName,String oldaccName,String fromDate,String oldfromdate,String toDate,String oldenddate,boolean tbDrillDown,boolean tbflag,boolean psdrilldown,boolean projectflag,String tbType,String tb,String selectproject,String oldselectproject,boolean dualledgerflag,boolean dualflag)
		{
			super(parent, style);
			// TODO Auto-generated constructor stub
			strOrgName = globals.session[1].toString();
			strFromYear =  globals.session[2].toString();
			strToYear =  globals.session[3].toString();
			//txtaccname.setText(result[0].toString());
			
			/*MessageBox msg=w MessageBox(new Shell(),SWT.OK);
			msg.setMessage("Project name"+selectproject);
			msg.open();*/
			
			accountName1=accountName;
			projectName=ProjectName;
			narrationflag=narrationFlag;
			startDate=fromDate;
			endDate=toDate;
			tbDrilldown=tbDrillDown;
			psDrilldown=psdrilldown;
			tb1 = tbType;
			projectName=selectproject;
			ledgerProject=ProjectName;
			dualflag1=dualledgerflag;
			
			//oldvalues
			oldaccName1=oldaccName;
			oldprojectName1=oldprojectName;
			oldfromdate1=oldfromdate;
			oldenddate1=oldenddate;
			oldselectproject1=oldselectproject;
			narration1=narration;
			
			
			
			
			FormLayout formLayout= new FormLayout();
			this.setLayout(formLayout);
		    FormData layout =new FormData();
		    strFromDate=oldfromdate.substring(8)+"-"+oldfromdate.substring(5,7)+"-"+oldfromdate.substring(0,4);
			strToDate=oldenddate.substring(8)+"-"+oldenddate.substring(5,7)+"-"+oldenddate.substring(0,4);
			strFromDate1=fromDate.substring(8)+"-"+fromDate.substring(5,7)+"-"+fromDate.substring(0,4);
			strToDate1=toDate.substring(8)+"-"+toDate.substring(5,7)+"-"+toDate.substring(0,4);
			
		  
			
			 lblLogo = new Label(this, SWT.None);
			layout = new FormData();
			layout.top = new FormAttachment(1);
			layout.left = new FormAttachment(70);
			//layout.right = new FormAttachment(95);
			//layout.bottom = new FormAttachment(18);
			lblLogo.setLayoutData(layout);
			//Image img = new Image(display,"finallogo1.png");
			lblLogo.setImage(globals.logo);
			
			lblOrgDetails = new Label(this,SWT.NONE);
			lblOrgDetails.setFont( new Font(display,"Times New Roman", 14, SWT.BOLD ) );
			lblOrgDetails.setText(globals.session[1]+"\n"+"For Financial Year "+"From "+globals.session[2]+" To "+globals.session[3] );
			layout = new FormData();
			layout.top = new FormAttachment(1);
			layout.left = new FormAttachment(2);
			//layout.right = new FormAttachment(53);
			//layout.bottom = new FormAttachment(18);
			lblOrgDetails.setLayoutData(layout);

			
			lblLine = new Label(this,SWT.NONE);
			lblLine.setText("-----------------------------------------------------------------------------------------------------");
			lblLine.setFont(new Font(display, "Times New Roman", 26, SWT.ITALIC));
			layout = new FormData();
			layout.top = new FormAttachment(lblLogo,1);
			layout.left = new FormAttachment(2);
			layout.right = new FormAttachment(98);
			lblLine.setLayoutData(layout);
	
			
			 lblheadline=new Label(this, SWT.NONE);
			lblheadline.setText(""+globals.session[1]);
			lblheadline.setFont(new Font(display, "Times New Roman", 18, SWT.ITALIC| SWT.BOLD));
			layout = new FormData();
			layout.top = new FormAttachment(lblLine,1);
			layout.left = new FormAttachment(40);
			lblheadline.setLayoutData(layout);
			lblPrjName=new Label(this, SWT.NONE);
			lblPrjName.setFont( new Font(display,"Times New Roman", 12, SWT.NORMAL | SWT.BOLD) );
			//lblPrjName.setText("Project: "+ProjectName);
			layout = new FormData();
			layout.top = new FormAttachment(lblheadline,1);
			layout.left = new FormAttachment(5);
			lblPrjName.setLayoutData(layout);
			if(ProjectName=="No Project" || ProjectName=="")
			{
				lblPrjName.setVisible(false);
			}
			else
			{
				lblPrjName.setText("Project: "+ProjectName);
			}
			//System.out.print("Project name is dual :"+ProjectName);
			
			
			lblPrjName1=new Label(this, SWT.NONE);
			lblPrjName1.setFont( new Font(display,"Times New Roman", 12, SWT.NORMAL | SWT.BOLD) );
			//lblPrjName.setText("Project: "+ProjectName);
			layout = new FormData();
			layout.top = new FormAttachment(lblheadline,1);
			layout.left = new FormAttachment(50);
			lblPrjName1.setLayoutData(layout);
			if(oldprojectName=="No Project" || oldprojectName=="")
			{
				lblPrjName1.setVisible(false);
			}
			else
			{
				lblPrjName1.setText("Project: "+oldprojectName);
			}
			
			Label lblAccName=new Label(this, SWT.NONE);
			lblAccName.setFont( new Font(display,"Times New Roman", 12, SWT.NORMAL ) );
			lblAccName.setText("Account Name: "+accountName1);
			layout = new FormData();
			layout.top = new FormAttachment(lblPrjName,1);
			layout.left = new FormAttachment(1);
			lblAccName.setLayoutData(layout);
			
			Label lblAccName2=new Label(this, SWT.NONE);
			lblAccName2.setFont( new Font(display,"Times New Roman", 12, SWT.NORMAL ) );
			lblAccName2.setText("Account Name: "+oldaccName1);
			layout = new FormData();
			layout.top = new FormAttachment(lblPrjName1,1);
			layout.left = new FormAttachment(44);
			lblAccName2.setLayoutData(layout);
			
			lblPageName = new Label(this, SWT.NONE);
			//lblPageName.setText("Ledger for account : "+ accountName );
			lblPageName.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL ));
			lblPageName.setText("Period From "+strFromDate1+" To "+strToDate1);
			layout = new FormData();
			layout.top = new FormAttachment(lblAccName,1);
			layout.left = new FormAttachment(1);
			lblPageName.setLayoutData(layout);

			lblPageName2 = new Label(this, SWT.NONE);
			//lblPageName.setText("Ledger for account : "+ accountName );
			lblPageName2.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL ));
			lblPageName2.setText("Period From "+strFromDate+" To "+strToDate);
			layout = new FormData();
			layout.top = new FormAttachment(lblAccName2,1);
			layout.left = new FormAttachment(44);
			lblPageName2.setLayoutData(layout);
			
		
		
		    ledgerReport1= new Table(this, SWT.MULTI|SWT.BORDER|SWT.FULL_SELECTION|SWT.LINE_SOLID);
		    ledgerReport1.setLinesVisible (true);
			ledgerReport1.setHeaderVisible (false);
			layout = new FormData();
			layout.top = new FormAttachment(lblPageName,10);

			if(narrationflag)
			{
				layout.left = new FormAttachment(1);
				layout.right = new FormAttachment(44);
				
			}
			else
			{
				layout.left = new FormAttachment(1);
				layout.right = new FormAttachment(44);
				
			}
			
			layout.bottom = new FormAttachment(86);
			ledgerReport1.setLayoutData(layout);
			
		    ledgerReport2= new Table(this, SWT.MULTI|SWT.BORDER|SWT.FULL_SELECTION|SWT.LINE_SOLID);
		    ledgerReport2.setLinesVisible (true);
			ledgerReport2.setHeaderVisible (false);
			layout = new FormData();
			layout.top = new FormAttachment(lblPageName,10);

			if(narration1)
			{
				layout.left = new FormAttachment(44);
				layout.right = new FormAttachment(94);
				
			}
			else
			{
				layout.left = new FormAttachment(44);
				layout.right = new FormAttachment(94);
				
			}
			
			layout.bottom = new FormAttachment(86);
			ledgerReport2.setLayoutData(layout);

			
			btnViewLedgerForAccount=new Button(this,SWT.PUSH);
			if(psdrilldown == false)
			{
				btnViewLedgerForAccount.setText("&View Another Ledger");
				 btnViewLedgerForAccount.setData("psdrilldown",psdrilldown );
			if(tbDrillDown == false)
			{
				btnViewLedgerForAccount.setText("&View Another Ledger");
				btnViewLedgerForAccount.setData("tbdrilldown",tbDrilldown );
			}
			else
			{
				btnViewLedgerForAccount.setData("tbdrilldown",tbDrilldown );
				btnViewLedgerForAccount.setData("enddate",toDate  );
				btnViewLedgerForAccount.setData("tbtype", tbType  );
				btnViewLedgerForAccount.setText("&Back to Trial Balance ");
			}
			
			
			}
			else
			{
				btnViewLedgerForAccount.setData("psdrilldown",psdrilldown );
				btnViewLedgerForAccount.setData("enddate",toDate  );
				btnViewLedgerForAccount.setData("selectproject", selectproject  );
				btnViewLedgerForAccount.setText("&Back to Project Statement ");
			}
			
			btnViewLedgerForAccount.setFont(new Font(display, "Times New Roman", 14,SWT.BOLD));
			layout = new FormData();
			layout.top=new FormAttachment(ledgerReport1,8);
			//layout.left=new FormAttachment(30);
			layout.left = new FormAttachment(25);
			btnViewLedgerForAccount.setLayoutData(layout);
			//btnViewLedgerForAccount.setFocus();
			
		  
		    this.makeaccessible(ledgerReport1);
		    this.getAccessible();
		    this.setBounds(this.getDisplay().getPrimaryMonitor().getBounds());
		    this.setReport(result_t1, result_t2);
		    this.setEvents();
			   
		 

		}
		
		private void setReport(Object[] result_t1,Object[] result_t2)
		{
			String[] columns;
			if(narrationflag)
			{
				columns = new String[]{"Date","Particulars","Voucher No.","Dr","Cr","Narration"};
			}
			else
			{
				columns = new String[]{"Date","Particulars","Voucher No.","Dr","Cr"};
			}
			lr1_lblDate = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblDate.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblDate.setText("    Date   ");
			
			lr1_lblParticulars = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblParticulars.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblParticulars.setText("    Particulars		     ");
			
			lr1_lblVoucherNumber = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblVoucherNumber.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblVoucherNumber.setText(" V.No");
			
			lr1_lblDr = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblDr.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblDr.setText("    	Dr		     ");
			
			lr1_lblCr = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblCr.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblCr.setText("    	Cr		     ");
			
			lr1_lblNarration = new Label(ledgerReport1,SWT.BORDER|SWT.CENTER);
			lr1_lblNarration.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr1_lblNarration.setText("  Narration   ");
			
			
			//ledger2
			
			lr2_lblDate = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblDate.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblDate.setText("    Date   ");
			
			lr2_lblParticulars = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblParticulars.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblParticulars.setText("    Particulars		     ");
			
			lr2_lblVoucherNumber = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblVoucherNumber.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblVoucherNumber.setText(" V.No");
			
			lr2_lblDr = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblDr.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblDr.setText("    	Dr		     ");
			
			lr2_lblCr = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblCr.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblCr.setText("    	Cr		     ");
			
			lr2_lblNarration = new Label(ledgerReport2,SWT.BORDER|SWT.CENTER);
			lr2_lblNarration.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
			lr2_lblNarration.setText(" Narration   ");
			
			
			
			
			
			  final int ledg1width = ledgerReport1.getClientArea().width;
			 
			  
				
				ledgerReport1.addListener(SWT.MeasureItem, new Listener() 
				{
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub

						if(narrationflag==true)
						{
							lr1_Date.setWidth(12 * ledg1width / 100);
							lr1_Particulars.setWidth(24 * ledg1width / 100);
							lr1_VoucherNumber.setWidth(10 * ledg1width / 100);
							lr1_Dr.setWidth(15 * ledg1width / 100);
							lr1_Cr.setWidth(15 * ledg1width / 100);
							lr1_Narration.setWidth(24 * ledg1width / 100);
							event.height = 11;
						}
						else
						{
							lr1_Date.setWidth(13 * ledg1width / 100);
							lr1_Particulars.setWidth(33 * ledg1width / 100);
							lr1_VoucherNumber.setWidth(10 * ledg1width / 100);
							lr1_Dr.setWidth(20 * ledg1width / 100);
							lr1_Cr.setWidth(20 * ledg1width / 100);
							event.height = 11;
						}
						
					}
				});

				 final int ledg2width = ledgerReport2.getClientArea().width;
				  
				ledgerReport2.addListener(SWT.MeasureItem, new Listener() 
				{
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub

						if(narration1==true)
						{
							lr2_Date.setWidth(12 * ledg2width / 100);
							lr2_Particulars.setWidth(22 * ledg2width / 100);
							lr2_VoucherNumber.setWidth(10 * ledg2width / 100);
							lr2_Dr.setWidth(15 * ledg2width / 100);
							lr2_Cr.setWidth(15 * ledg2width / 100);
							lr2_Narration.setWidth(25 * ledg2width / 100);
							event.height = 11;
						}
						else
						{
							lr2_Date.setWidth(12 * ledg2width / 100);
							lr2_Particulars.setWidth(33 * ledg2width / 100);
							lr2_VoucherNumber.setWidth(10 * ledg2width / 100);
							lr2_Dr.setWidth(20 * ledg2width / 100);
							lr2_Cr.setWidth(20 * ledg2width / 100);
							event.height = 11;
						}
						
					}
				});

			    headerRow1 = new TableItem(ledgerReport1, SWT.NONE);
				TableItem[] items1 = ledgerReport1.getItems();		
				lr1_Date = new TableColumn(ledgerReport1, SWT.BORDER|SWT.BACKGROUND| SWT.CENTER );
				lr1_Particulars = new TableColumn(ledgerReport1, SWT.BORDER);
				lr1_VoucherNumber = new TableColumn(ledgerReport1, SWT.RIGHT);
				lr1_Dr= new TableColumn(ledgerReport1, SWT.RIGHT);
				lr1_Cr= new TableColumn(ledgerReport1, SWT.RIGHT);
				if(narrationflag)
				{
					lr1_Narration=new TableColumn(ledgerReport1, SWT.BORDER);
				}
				
				   headerRow2 = new TableItem(ledgerReport2, SWT.NONE);
					TableItem[] items2 = ledgerReport2.getItems();
					lr2_Date = new TableColumn(ledgerReport2, SWT.BORDER|SWT.BACKGROUND| SWT.CENTER );
					lr2_Particulars = new TableColumn(ledgerReport2, SWT.BORDER);
					lr2_VoucherNumber = new TableColumn(ledgerReport2, SWT.RIGHT);
					lr2_Dr= new TableColumn(ledgerReport2, SWT.RIGHT);
					lr2_Cr= new TableColumn(ledgerReport2, SWT.RIGHT);
					if(narration1)
					{
						lr2_Narration=new TableColumn(ledgerReport2, SWT.BORDER);
					}
					
					lr1_DateEditor = new TableEditor(ledgerReport1);
					lr1_DateEditor.grabHorizontal = true;
					lr1_DateEditor.setEditor(lr1_lblDate,items1[0],0);
					
					lr1_ParticularEditor = new TableEditor(ledgerReport1);
					lr1_ParticularEditor.grabHorizontal = true;
					lr1_ParticularEditor.setEditor(lr1_lblParticulars,items1[0],1);
					
					lr1_VoucherNumberEditor = new TableEditor(ledgerReport1);
					lr1_VoucherNumberEditor.grabHorizontal = true;
					lr1_VoucherNumberEditor.setEditor(lr1_lblVoucherNumber,items1[0],2);
					
					lr1_DrEditotr = new TableEditor(ledgerReport1);
					lr1_DrEditotr.grabHorizontal = true;
					lr1_DrEditotr.setEditor(lr1_lblDr,items1[0],3);
					
					lr1_CrEditor = new TableEditor(ledgerReport1);
					lr1_CrEditor.grabHorizontal = true;
					lr1_CrEditor.setEditor(lr1_lblCr,items1[0],4);
					

					lr2_DateEditor = new TableEditor(ledgerReport2);
					lr2_DateEditor.grabHorizontal = true;
					lr2_DateEditor.setEditor(lr2_lblDate,items2[0],0);
					
					lr2_ParticularEditor = new TableEditor(ledgerReport2);
					lr2_ParticularEditor.grabHorizontal = true;
					lr2_ParticularEditor.setEditor(lr2_lblParticulars,items2[0],1);
					
					lr2_VoucherNumberEditor = new TableEditor(ledgerReport2);
					lr2_VoucherNumberEditor.grabHorizontal = true;
					lr2_VoucherNumberEditor.setEditor(lr2_lblVoucherNumber,items2[0],2);
					
					lr2_DrEditotr = new TableEditor(ledgerReport2);
					lr2_DrEditotr.grabHorizontal = true;
					lr2_DrEditotr.setEditor(lr2_lblDr,items2[0],3);
					
					lr2_CrEditor = new TableEditor(ledgerReport2);
					lr2_CrEditor.grabHorizontal = true;
					lr2_CrEditor.setEditor(lr2_lblCr,items2[0],4);

					lr1_Date.pack();
					lr1_Particulars.pack();
					lr1_VoucherNumber.pack();
					lr1_Dr.pack();
					lr1_Cr.pack();
					lr2_Date.pack();
					lr2_Particulars.pack();
					lr2_VoucherNumber.pack();
					lr2_Dr.pack();
					lr2_Cr.pack();
					
					if(narrationflag==true)
					{
						lr1_NarrationEditor = new TableEditor(ledgerReport1);
						lr1_NarrationEditor.grabHorizontal = true;
						lr1_NarrationEditor.setEditor(lr1_lblNarration,items1[0],5);
						lr1_Narration.pack();
						
					}
					
					if(narration1==true)
					{
						lr2_NarrationEditor = new TableEditor(ledgerReport2);
						lr2_NarrationEditor.grabHorizontal = true;
						lr2_NarrationEditor.setEditor(lr2_lblNarration,items2[0],5);
						lr2_Narration.pack();
						
					}
					
					for(int rowcounter =0; rowcounter < result_t1.length; rowcounter ++)
					{
						

						Object[] ledgerRecord = (Object[]) result_t1[rowcounter];
						Object[] particulars = (Object[])  ledgerRecord[1];
						if(! ledgerProject.equals("No Project")&& particulars[0].toString().equals("Opening Balance b/f") )
						{
							continue;
						}
						TableItem ledgerRow = new TableItem(ledgerReport1, SWT.NONE);
						 ledgerRow.setFont(new Font(display, "Times New Roman",11,SWT.BOLD|SWT.CENTER));
						 
						ledgerRow.setText(0,ledgerRecord[0].toString() );

						
						for(int particularcounter = 0; particularcounter < particulars.length; particularcounter ++)
						{
							ledgerRow.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
							
							ledgerRow.setText(1, ledgerRow.getText(1) +"\n"+particulars[particularcounter].toString() + "\n" );
						}
						if(!ledgerRecord[2].toString().equals(""))
						{
							TableEditor codeEditor = new TableEditor(ledgerReport1);
							Button btnVoucherCode = new Button(ledgerReport1, SWT.FLAT);
							btnVoucherCode.setText(ledgerRecord[2].toString());
							codeEditor.grabHorizontal = true;
							codeEditor.setEditor(btnVoucherCode, ledgerRow,2);
							btnVoucherCode.setData("vouchercode", ledgerRecord[ledgerRecord.length -1] );
							voucherCodes.add(btnVoucherCode);
							
						}
						else
						{
							ledgerRow.setText(2, ledgerRecord[2].toString() );
							ledgerRow.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
						}
						ledgerRow.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
						
						ledgerRow.setText(3, ledgerRecord[3].toString() );
						ledgerRow.setText(4, ledgerRecord[4].toString() );
						if(narrationflag==true)
						{
							
							ledgerRow.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
							
							ledgerRow.setText(5, ledgerRecord[5].toString());
						}
					}
		
					
					

					for(int rowcounter =0; rowcounter < result_t2.length; rowcounter ++)
					{

						


						Object[] ledgerRecord2 = (Object[]) result_t2[rowcounter];
						
						Object[] particulars = (Object[])  ledgerRecord2[1];
						if(! ledgerProject.equals("No Project")&& particulars[0].toString().equals("Opening Balance b/f") )
						{
							continue;
						}
						TableItem ledgerRow1 = new TableItem(ledgerReport2, SWT.NONE);
						 ledgerRow1.setFont(new Font(display, "Times New Roman",11,SWT.BOLD|SWT.CENTER));
							
						ledgerRow1.setText(0,ledgerRecord2[0].toString() );

						
						for(int particularcounter = 0; particularcounter < particulars.length; particularcounter ++)
				
						{
							ledgerRow1.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
							
							ledgerRow1.setText(1, ledgerRow1.getText(1) +"\n"+particulars[particularcounter].toString() + "\n" );
						}
						if(!ledgerRecord2[2].toString().equals(""))
						{
							TableEditor codeEditor = new TableEditor(ledgerReport2);
							Button btnVoucherCode = new Button(ledgerReport2, SWT.FLAT);
							btnVoucherCode.setText(ledgerRecord2[2].toString());
							codeEditor.grabHorizontal = true;
							codeEditor.setEditor(btnVoucherCode, ledgerRow1,2);
							btnVoucherCode.setData("vouchercode", ledgerRecord2[ledgerRecord2.length -1] );
							voucherCodes.add(btnVoucherCode);
							
						}
						else
						{
							ledgerRow1.setText(2, ledgerRecord2[2].toString() );
							ledgerRow1.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
						}
						ledgerRow1.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
						
						ledgerRow1.setText(3, ledgerRecord2[3].toString() );
						ledgerRow1.setText(4, ledgerRecord2[4].toString() );
						if(narration1==true)
						{
							
							ledgerRow1.setFont(new Font(display,"Times New Roman",11,SWT.BOLD));
							
							ledgerRow1.setText(5, ledgerRecord2[5].toString());
						}
						
						
					}

		
		
		}
				
 public void setEvents()
 {
	
		btnViewLedgerForAccount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				
				
				Composite grandParent = (Composite) btnViewLedgerForAccount.getParent().getParent();
				if( (Boolean) btnViewLedgerForAccount.getData("psdrilldown") == false )
				{
				if( (Boolean) btnViewLedgerForAccount.getData("tbdrilldown") == false )
				{
					btnViewLedgerForAccount.getParent().dispose();
					
					ViewLedger vl=new ViewLedger(grandParent,SWT.NONE,"","","","",false,false,false,"","",false);
						vl.setSize(grandParent.getClientArea().width,grandParent.getClientArea().height);
				}
				else
				{
					String enddate = btnViewLedgerForAccount.getData("enddate").toString();
					String tbtype = btnViewLedgerForAccount.getData("tbtype").toString();
					btnViewLedgerForAccount.getParent().dispose();
					reportController.showTrialBalance(grandParent, enddate, tbtype);
				}
				}

				else
				{
					String enddate = btnViewLedgerForAccount.getData("enddate").toString();
					String sp = btnViewLedgerForAccount.getData("selectproject").toString();
					btnViewLedgerForAccount.getParent().dispose();
					reportController.showProjectStatement(grandParent, enddate, sp);
				}
		}
		
		});
		

	 
	 
	 
	 for(int voucherCodeCounter=0; voucherCodeCounter < voucherCodes.size(); voucherCodeCounter++)
		{
			voucherCodes.get(voucherCodeCounter).addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					//super.widgetSelected(arg0);
					Button btncurrent=(Button) arg0.widget;
					int vouchercode= Integer.valueOf(arg0.widget.getData("vouchercode").toString());
					
					Composite grandParent=(Composite) btncurrent.getParent().getParent().getParent();
					btncurrent.getParent().getParent().dispose();
					transactionController.showVoucherDetail(grandParent, "", vouchercode, tbflag, projectflag, tb1, true, startDate,oldfromdate1, endDate,oldenddate1, accountName1,oldaccName1, ledgerProject,oldprojectName1, narrationflag,narration1, projectName,oldselectproject1,true);
				}
			});
			
					
			voucherCodes.get(voucherCodeCounter).addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode==SWT.ARROW_DOWN && counter < voucherCodes.size()-1 )
					{
						counter++;
						if(counter >= 0 && counter < voucherCodes.size())
						{
							
							voucherCodes.get(counter).setFocus();
						}
					}
					if(arg0.keyCode==SWT.ARROW_UP && counter > 0)
					{
						counter--;
						if(counter >= 0&& counter < voucherCodes.size())
						{
							
							voucherCodes.get(counter).setFocus();
						}
					}
					if(arg0.keyCode==SWT.ARROW_RIGHT)
					{
						ledgerReport2.setFocus();
					}
					if(arg0.keyCode==SWT.ARROW_RIGHT)
					{
						ledgerReport1.setFocus();
					}
					
				}
			});
			
			
			
			ledgerReport1.setFocus();
			ledgerReport1.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusGained(arg0);
					if(voucherCodes.isEmpty())
					{
						btnViewLedgerForAccount.setFocus();
					
					}
					else
					{
						voucherCodes.get(0).setFocus();
					}
				}
			});
		
		
			
			ledgerReport2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusGained(arg0);
					if(voucherCodes.isEmpty())
					{
						btnViewLedgerForAccount.setFocus();
					
					}
					else
					{
						voucherCodes.get(0).setFocus();
					}
				}
			});
			
		}
 }
		 
		
		/*private void getAccessible() {
			// TODO Auto-generated method stub
			
		}*/

		public void makeaccessible(Control c) {
			/*
			 * getAccessible() method is the method of class Control which is the
			 * parent class of all the UI components of SWT including Shell.so when
			 * the shell is made accessible all the controls which are contained by
			 * that shell are made accessible automatically.
			 */
			c.getAccessible();
		}
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
