package gnukhata.views;

import gnukhata.globals;
import gnukhata.controllers.reportController;
import gnukhata.controllers.transactionController;
import gnukhata.controllers.reportmodels.AddVoucher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.graphics.Color;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


public class ViewVoucherDetailsComposite extends Composite 
{
	Color Background;
	Color Foreground;
	Color FocusBackground;
	Color FocusForeground;
	Color BtnFocusForeground;
	
	int voucherCode = 0;
	Group ViewVoucherDetails;
	static Display display;
	public static String typeFlag;
	
	double totalDrAmount = 0.00;
	double totalCrAmount = 0.00;
	
	String strOrgName;
	String strFromYear;
	String strToYear;
	
	Label lblLogo;
	Label lblLink ;
	Label lblLine;
	Label lblvoucherName;
	Label lbldate;
	Text txtddate;
	Label dash1;
	Text txtmdate;
	Label dash2;
	Text txtyrdate;
	Label lblvoucherno;
	Text txtvoucherno;
	Table voucherViewTable;
	Label lblaccountName;
	Label lblvouchertype;
	Label lblDrAmount;
	Label lblCrAmount;
	Label lblprojectname;
	Text txtprojectname;
	
	TableItem headerRow;
	TableColumn colAccountName;
	TableColumn colDrAmount;
	TableColumn colCrAmount;
	Label lblnarration;
	Text txtnarration;
	Button btnEdit;
	Button btnClone;
	Button btnDelete;
	Button btnBack;
	
	Button btnBackToLedger;
	int editFlag;
	boolean psdrilldown = false;
	String tbType = "";
	boolean narrationFlag = false;
	String AccountName = "";
	String PN="";
	String endDate;
	String startDate = null;
	boolean ledgerDrilldown = false;
	String selectproject ="" ;
	boolean tbdrilldown = false;
	boolean dualledgerflag=false;
	String oldaccname;
	String oldfromdate;
	String oldenddate;
	String oldselectproject;
	String oldprojectname;
	boolean oldnarration;
	boolean findvoucher;

    
	public ViewVoucherDetailsComposite(Composite parent, int style,String voucherType, int vouchercode,boolean findvoucherflag, boolean tbdrilldown,boolean psdrilldown,String tbType, boolean ledgerDrilldown, String startDate,String oldfromdate1, String endDate,String oldenddate1, String AccountName,String oldaccname1 ,String ProjectName,String oldprojectname1, boolean narrationFlag,boolean narration,String selectproject,String oldselectproject1,boolean dualledgerflag)
	{	
		super(parent,style);
		voucherCode = vouchercode;
		findvoucher=findvoucherflag;
		
		Date today = new Date();
		typeFlag= voucherType;
		this.tbdrilldown= tbdrilldown;
		this.psdrilldown= psdrilldown;
		this.AccountName = AccountName;
		this.PN=ProjectName;
		this.tbType=tbType;
		this.ledgerDrilldown = ledgerDrilldown;
		this.startDate = startDate;
		this.endDate = endDate;
		this.selectproject= selectproject;
		this.narrationFlag = narrationFlag;
		this.dualledgerflag=dualledgerflag;
		
		//old values
		this.oldaccname=oldaccname1;
		this.oldfromdate=oldfromdate1;
		this.oldenddate=oldenddate1;
		this.oldselectproject=oldselectproject1;
		this.oldprojectname=oldprojectname1;
		this.oldnarration=narration;
		
	    
		strOrgName = globals.session[1].toString();
		strFromYear =  globals.session[2].toString();
		strToYear =  globals.session[3].toString();
 
		Background =  new Color(this.getDisplay() ,220 , 224, 227);
		Foreground = new Color(this.getDisplay() ,0, 0,0 );
		FocusBackground  = new Color(this.getDisplay(),78,97,114 );
		FocusForeground = new Color(this.getDisplay(),255,255,255);

		globals.setThemeColor(this, Background, Foreground);

		
		
		
		FormLayout formLayout= new FormLayout();
		this.setLayout(formLayout);
	    FormData layout=new FormData();
	    
	    MainShell.lblLine.setVisible(false);
	    
		Label lblOrgDetails = new Label(this,SWT.NONE);
		lblOrgDetails.setFont( new Font(display,"Times New Roman", 11, SWT.BOLD ) );
		lblOrgDetails.setText(strOrgName+ "\n"+"For Financial Year "+"From "+strFromYear+" To "+strToYear);
		layout.top = new FormAttachment(1);
		layout.left = new FormAttachment(3);
		layout.right = new FormAttachment(62);
		layout.bottom = new FormAttachment(7);
		lblOrgDetails.setLayoutData(layout);
		
		lblLogo = new Label(this, SWT.None);
		layout = new FormData();
		layout.top = new FormAttachment(1);
		layout.left = new FormAttachment(63);
		layout.right = new FormAttachment(87);
		layout.bottom = new FormAttachment(9);
		lblLogo.setLayoutData(layout);
		//Image img = new Image(display, "finallogo1.png");
		lblLogo.setImage(globals.logo);
		
		/*lblLink = new Label(this, SWT.NONE);
		lblLink.setFont( new Font(display,"Times New Roman", 11, SWT.ITALIC ) );
		lblLink.setText("www.gnukhata.org");
		layout = new FormData();
		layout.top = new FormAttachment(lblLogo,1);
		layout.left = new FormAttachment(65);
		lblLink.setLayoutData(layout);*/
		
			
		lblLine = new Label(this, SWT.NONE);
		lblLine.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		lblLine.setFont(new Font(display, "Times New Roman", 18, SWT.ITALIC));
		layout = new FormData();
		layout.top = new FormAttachment(10);
		layout.left = new FormAttachment(3);
		layout.right = new FormAttachment(99);
		
		lblLine.setLayoutData(layout);
		lblLine.setLayoutData(layout);

		
		lblvoucherName = new Label(this, SWT.NONE);
		lblvoucherName.setText(typeFlag +"Voucher");
		lblvoucherName.setFont(new Font(display, "Times New Roman", 13, SWT.ITALIC |SWT.NORMAL));
		 layout = new FormData();
		layout.top = new FormAttachment(15);
		layout.left = new FormAttachment(9);
		layout.right = new FormAttachment(20);
		layout.bottom = new FormAttachment(19);
		lblvoucherName.setLayoutData(layout);
		
		lbldate = new Label(this,SWT.NONE);
		lbldate.setText("&Date :");
		lbldate.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(11);
		//layout.right = new FormAttachment(14);
		//layout.bottom = new FormAttachment(13);
		lbldate.setLayoutData(layout);
		
		txtddate = new Text(this,SWT.BORDER | SWT.READ_ONLY);
		txtddate.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(lbldate,2);
		//layout.right = new FormAttachment(18);
		//layout.bottom = new FormAttachment(12);
		txtddate.setLayoutData(layout);
		txtddate.setEditable(false);
		
		dash1 = new Label(this,SWT.NONE);
		dash1.setText("-");
		dash1.setFont(new Font(display, "Time New Roman",12,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(txtddate,2);
		//layout.right = new FormAttachment(19);
		//layout.bottom = new FormAttachment(12);
		dash1.setLayoutData(layout);
		
		txtmdate = new Text(this,SWT.BORDER | SWT.READ_ONLY);
		txtmdate.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(dash1,2);
		//layout.right = new FormAttachment(22);
		//layout.bottom = new FormAttachment(12);
		txtmdate.setLayoutData(layout);
		txtmdate.setEditable(false);
		
		dash2 = new Label(this,SWT.NONE);
		dash2.setText("-");
		dash2.setFont(new Font(display, "Time New Roman",12,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(txtmdate,2);
		//layout.right = new FormAttachment(23);
		//layout.bottom = new FormAttachment(12);
		dash2.setLayoutData(layout);
		
		txtyrdate = new Text(this,SWT.BORDER | SWT.READ_ONLY);
		txtyrdate.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(dash2,2);
		//layout.right = new FormAttachment(27);
		//layout.bottom = new FormAttachment(12);
		txtyrdate.setLayoutData(layout);
		txtyrdate.setEditable(false);
		
		lblvoucherno = new Label(this,SWT.NONE);
		lblvoucherno.setText("&Voucher No *");
		lblvoucherno.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout =new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(62);
		//layout.right = new FormAttachment(55);
		//layout.bottom = new FormAttachment(13);
		lblvoucherno.setLayoutData(layout);
		
		txtvoucherno = new Text(this,SWT.BORDER |SWT.READ_ONLY);
		txtvoucherno.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblvoucherName,5);
		layout.left = new FormAttachment(lblvoucherno,5);
		//layout.right = new FormAttachment(62);
		//layout.bottom = new FormAttachment(13);
		txtvoucherno.setLayoutData(layout);
		txtvoucherno.setEditable(false);

		voucherViewTable = new Table (this, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		voucherViewTable.setLinesVisible (false);
		voucherViewTable.setHeaderVisible (false);
		voucherViewTable.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lbldate,15);
		layout.left = new FormAttachment(10);
		layout.right = new FormAttachment(80);
		layout.bottom = new FormAttachment(60);
		voucherViewTable.setLayoutData(layout);
		
		lblaccountName = new Label(voucherViewTable,SWT.BORDER);
		lblaccountName.setFont(new Font(display ,"Times New Roman" ,11,SWT.BOLD));
		lblaccountName.setText("        Account Name		    ");

		lblDrAmount = new Label(voucherViewTable,SWT.BORDER);
		lblDrAmount.setFont(new Font(display ,"Times New Roman" ,11,SWT.BOLD));
		lblDrAmount.setText("        Debit Amount           ");
		
		lblCrAmount = new Label(voucherViewTable,SWT.BORDER );
		lblCrAmount.setFont(new Font(display ,"Times New Roman" ,11,SWT.BOLD));
		lblCrAmount.setText("    	 Credit Amount      	 ");
		
		
		headerRow = new TableItem(voucherViewTable, SWT.BORDER);
		
		TableItem[] items = voucherViewTable.getItems();
		colAccountName = new TableColumn(voucherViewTable, SWT.BORDER |SWT.BACKGROUND);
		colDrAmount = new TableColumn(voucherViewTable, SWT.RIGHT);
		colCrAmount = new TableColumn(voucherViewTable, SWT.RIGHT);
		
		TableEditor editorAccName = new TableEditor(voucherViewTable);
		editorAccName.grabHorizontal = true;
		editorAccName.setEditor(lblaccountName,items[0],0);
		
		TableEditor editorDrAmount = new TableEditor(voucherViewTable);
		editorDrAmount.grabHorizontal = true;
		editorDrAmount.setEditor(lblDrAmount,items[0],1);
		
		TableEditor editorCrAmount = new TableEditor(voucherViewTable);
		editorCrAmount.grabHorizontal = true;
		editorCrAmount.setEditor(lblCrAmount,items[0],2);
		
		voucherViewTable.addListener(SWT.MeasureItem, new Listener() 
		{
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated metod stub
				event.width = 230;
				event.height = 35;
			}
		});
		
		lblnarration = new Label(this,SWT.NONE);
		lblnarration.setText("Narrat&ion :");
		lblnarration.setFont(new Font(display, "Time New Roman",10,SWT.RIGHT));
		layout = new FormData();
		layout.top = new FormAttachment(voucherViewTable,35);
		layout.left = new FormAttachment(10);
		//layout.right = new FormAttachment(20);
		//layout.bottom = new FormAttachment(55);
		lblnarration.setLayoutData(layout);
		
		txtnarration = new Text(this,SWT.MULTI | SWT.BORDER | SWT.READ_ONLY);
		txtnarration.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(voucherViewTable,35);
		layout.left = new FormAttachment(lblnarration,10);
		layout.right = new FormAttachment(48);
		layout.bottom = new FormAttachment(72);
		txtnarration.setLayoutData(layout);
		
		
		
		lblprojectname=new Label(this, SWT.NONE);
		lblprojectname.setText("&Project Name :");
		lblprojectname.setFont(new Font(display, "Time New Roman",10,SWT.RIGHT));
		layout = new FormData();
		layout.top = new FormAttachment(txtnarration,25);
		layout.left = new FormAttachment(10);
		//layout.right = new FormAttachment(20);
		//layout.bottom = new FormAttachment(55);
		lblprojectname.setLayoutData(layout);
		
	
		txtprojectname=new Text(this, SWT.NONE);
		txtprojectname.setFont(new Font(display, "Time New Roman",10,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(txtnarration,25);
		layout.left = new FormAttachment(lblprojectname,5);
		txtprojectname.setLayoutData(layout);
		
		
		
		btnBackToLedger=new Button(this, SWT.PUSH);
		if(ledgerDrilldown==true)
		{
			
			btnBackToLedger.setText("&Back to the ledger");
			btnBackToLedger.setFont(new Font(display, "Times New Roman", 10,SWT.BOLD));
			btnBackToLedger.setData("ledgeraccount",AccountName);
			btnBackToLedger.setData("projectname", "No Project");
			btnBackToLedger.setData("narration",narrationFlag);
			btnBackToLedger.setData("drilldown",ledgerDrilldown);
			btnBackToLedger.setData("startDate",startDate);
			btnBackToLedger.setData("endDate",endDate);
			btnBackToLedger.setData("tbDrilldown", tbdrilldown);
			btnBackToLedger.setData("tbType", tbType);
			btnBackToLedger.setData("psDrillDown",psdrilldown);
			btnBackToLedger.setData("sp",selectproject);
		
			
			layout = new FormData();
			layout.top=new FormAttachment(86);
			layout.left = new FormAttachment(12);
			//loyout.right = new FormAttachment(22);
			btnBackToLedger.setLayoutData(layout);
			btnBackToLedger.setFocus();
			
			

		}
		else
		{
			btnBackToLedger.setVisible(false);
		}
		
		
		btnEdit = new Button(this,SWT.PUSH);
		btnEdit.setText("&Edit");
		btnEdit.setFont(new Font(display, "Time New Roman",10,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(86);
		layout.left = new FormAttachment(30);
		btnEdit.setLayoutData(layout);
		btnEdit.setBackground(FocusBackground);
		btnEdit.setForeground(FocusForeground);


		
		btnClone = new Button(this,SWT.PUSH);
		btnClone.setText("&Clone");
		btnClone.setFont(new Font(display, "Time New Roman",10,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(86);
		layout.left = new FormAttachment(btnEdit,20);
		//layout.right = new FormAttachment(29);
		//layout.bottom = new FormAttachment(83);
		btnClone.setLayoutData(layout);
		
		btnDelete = new Button(this,SWT.PUSH);
		btnDelete.setText("De&lete");
		btnDelete.setFont(new Font(display, "Time New Roman",10,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(86);
		layout.left = new FormAttachment(btnClone,20);
		//layout.right = new FormAttachment(38);
		//layout.bottom = new FormAttachment(83);
		btnDelete.setLayoutData(layout);
				
		btnBack= new Button(this,SWT.PUSH);
		btnBack.setText("&Back");
		btnBack.setFont(new Font(display, "Time New Roman",10,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(86);
		layout.left = new FormAttachment(btnDelete,20);
		//layout.right = new FormAttachment(52);
		//layout.bottom = new FormAttachment(83);
		btnBack.setLayoutData(layout);
		if(ledgerDrilldown==true)
		{
			btnBackToLedger.setFocus();
			btnBack.setVisible(false);
			btnEdit.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode == SWT.ARROW_LEFT)
					{
						btnBackToLedger.setFocus();
					
					}
					if(arg0.keyCode == SWT.ARROW_RIGHT)
					{
						btnClone.setFocus();
					}
				}
				
			});
		}
		else
		{
			btnEdit.setFocus();

		}
		
		colAccountName.pack();
		colDrAmount.pack();
		colCrAmount.pack();
		
		
		//now make a call to the voucher details passing voucherCode as the parameter.
		Object[] masterDetails = transactionController.getVoucherMaster(vouchercode);
		Object[] result=transactionController.getVoucherMaster(vouchercode);
		String strprjname=result[4].toString();
		
		Object[] projectlist=transactionController.getAllProjects();
		
		txtvoucherno.setText(masterDetails[0].toString());
		txtddate.setText(masterDetails[1].toString().substring(0,2));
		txtmdate.setText(masterDetails[1].toString().substring(3,5));
		txtyrdate.setText(masterDetails[1].toString().substring(6));
		lblvoucherName.setText(masterDetails[2].toString());
		txtnarration.setText(masterDetails[3].toString());
		
		if(projectlist.length > 0)
		{
			txtprojectname.setText(strprjname);
			
		}
		else 
		{
			lblprojectname.setVisible(false);
			txtprojectname.setVisible(false);
		}
		
	
		ArrayList<AddVoucher> transactionDetails = transactionController.getVoucherDetails(vouchercode );
		for(int r= 0; r <transactionDetails.size(); r ++)
		{
			addRow(transactionDetails.get(r) );
		}
		
		
				
		this.pack();
		this.setEvents();
		BtnFocusForeground=new Color(this.getDisplay(), 0, 0, 255);
		Background =  new Color(this.getDisplay() ,220 , 224, 227);
		Foreground = new Color(this.getDisplay() ,0, 0,0 );
		FocusBackground  = new Color(this.getDisplay(),78,97,114 );
		FocusForeground = new Color(this.getDisplay(),255,255,255);

		globals.setThemeColor(this, Background, Foreground);
        globals.SetButtonColoredFocusEvents(this, FocusBackground, BtnFocusForeground, Background, Foreground);
		globals.SetComboColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground);
        globals.SetTableColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground); 
		globals.SetTextColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground);


	}
	
	
	
	private void setEvents()
	{
		
		btnBackToLedger.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode == SWT.ARROW_RIGHT)
				{
					btnEdit.setFocus();
				}
			}
		});
		
		btnEdit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode == SWT.ARROW_RIGHT)
				{
					btnClone.setFocus();
				}
			}
			
		});
		
		btnClone.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode == SWT.ARROW_RIGHT)
				{
					btnDelete.setFocus();
				}
				if(arg0.keyCode == SWT.ARROW_LEFT)
				{
					btnEdit.setFocus();
				}
			}
		});
		
		btnDelete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_LEFT)
				{
					btnClone.setFocus();
				}
				if(arg0.keyCode == SWT.ARROW_RIGHT)
				{
					btnBack.setFocus();
				}
			}
		});
		
		btnBack.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_LEFT)
				{
					btnDelete.setFocus();
				}
			}
		});
		
		
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				//super.widgetSelected(e);
				
				Composite grandParent=(Composite) btnEdit.getParent().getParent();
				btnEdit.getParent().dispose();
				if(dualledgerflag==false)
				{
					if(findvoucher==true)
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode, findvoucher, 1, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,false);
					}
					else
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode,false, 1, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,false);
					}
					
				}
				if(dualledgerflag==true)
				{
					if(findvoucher==true)
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode, findvoucher, 1, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,dualledgerflag);
					}
					else
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode,false, 1, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,dualledgerflag);
					}
				}
				
				
						
			}
		});
		
		btnClone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				Composite grandParent=(Composite) btnClone.getParent().getParent();
				btnClone.getParent().dispose();
				//transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode, 2, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate, endDate, AccountName, PN, narrationFlag, selectproject,dualledgerflag);
				if(dualledgerflag==false)
				{
					if(findvoucher==true)
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode, findvoucher, 2, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,false);
					}
					else
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode,false, 2, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,false);
					}
					
				}
				if(dualledgerflag==true)
				{
					if(findvoucher==true)
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode, findvoucher, 2, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,dualledgerflag);
					}
					else
					{
						transactionController.showEditCloneVoucher(grandParent, SWT.NONE, typeFlag, voucherCode,false, 2, tbdrilldown, psdrilldown, tbType, ledgerDrilldown, startDate,oldfromdate, endDate,oldenddate, AccountName,oldaccname, PN,oldprojectname, narrationFlag,oldnarration, selectproject,oldselectproject,dualledgerflag);
					}
				}
				

			}
		});
		
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				//make a call to deleteVoucher from transactionController.
				MessageBox msgConfirm = new MessageBox(new Shell(), SWT.YES| SWT.NO| SWT.ICON_QUESTION );
				msgConfirm.setText("Confirm!");
				msgConfirm.setMessage("You are about to delete a voucher, are you sure you wish to do this");
				int answer = msgConfirm.open();
				if(answer == SWT.YES)
				{
					if ( transactionController.deleteVoucher(voucherCode))
					{
						if(ledgerDrilldown==true)
						{
							Composite grandParent=(Composite)btnDelete.getParent().getParent();
							//reportController.showLedger(grandParent, AccountName, startDate, endDate, selectproject, narrationFlag, tbdrilldown, psdrilldown, tbType, selectproject);
							if(dualledgerflag==false)
							{
							reportController.showLedger(grandParent, AccountName, startDate, endDate, PN, narrationFlag, tbdrilldown, psdrilldown, tbType, selectproject);
							}
							if(dualledgerflag==true)
							{
								reportController.showDualLedger(grandParent, AccountName,oldaccname,startDate,oldfromdate, endDate,oldenddate, PN,oldprojectname, narrationFlag,oldnarration, false,false, false,false, "","","", "",true,true);
							}
							//reportController.showLedger(grandParent, AccountName, startDate, endDate, PN, narrationFlag, tbdrilldown, psdrilldown, tbType, selectproject);
							btnDelete.getParent().dispose();
						}
						else
						{
						MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_INFORMATION);
						msg.setText("Information!");
						msg.setMessage("Voucher deleted!");
						msg.open();
						
						Composite grandParent=(Composite) btnDelete.getParent().getParent();
						btnDelete.getParent().dispose();
	
						VoucherTabForm vtb=new VoucherTabForm(grandParent, SWT.NONE);
						vtb.setSize(grandParent.getClientArea().width, grandParent.getClientArea().height);
						if(findvoucher==true)
						{
							FindandEditVoucherComposite fdvoucher = new FindandEditVoucherComposite(vtb.tfTransaction, SWT.NONE,true);
							vtb.tfTransaction.setSelection(1);
							vtb.tifdrecord.setControl(fdvoucher);
							vtb.tinewvoucher.dispose();
							fdvoucher.combosearchRec.setFocus();
						}
						else
						{
							FindandEditVoucherComposite fdvoucher = new FindandEditVoucherComposite(vtb.tfTransaction, SWT.NONE,false);
							vtb.tfTransaction.setSelection(1);
							vtb.tifdrecord.setControl(fdvoucher);
							fdvoucher.combosearchRec.setFocus();
						}
						}
					}
				}
				
				
			}
		});
		
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				
				Composite grandParent=(Composite) btnBack.getParent().getParent();
				btnBack.getParent().dispose();

				VoucherTabForm vtb=new VoucherTabForm(grandParent, SWT.NONE);
				vtb.setSize(grandParent.getClientArea().width, grandParent.getClientArea().height);
			//	vtb.setSize(arg0);
				if(findvoucher==true)
				{
					FindandEditVoucherComposite fdvoucher = new FindandEditVoucherComposite(vtb.tfTransaction, SWT.NONE,findvoucher);
					vtb.tfTransaction.setSelection(1);
					vtb.tifdrecord.setControl(fdvoucher);
					vtb.tinewvoucher.dispose();
				}
				else
				{
					FindandEditVoucherComposite fdvoucher = new FindandEditVoucherComposite(vtb.tfTransaction, SWT.NONE,false);
					vtb.tfTransaction.setSelection(1);
					vtb.tifdrecord.setControl(fdvoucher);
					//fdvoucher.combosearchRec.setFocus();
				}
				
			}
		});
		
		btnBackToLedger.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				//int vouchercode= Integer.valueOf(arg0.widget.getData("vouchercode").toString());
				
				Composite grandParent = (Composite) btnBackToLedger.getParent().getParent();
				btnBackToLedger.getParent().dispose();
				if(dualledgerflag==false)
				{
				reportController.showLedger(grandParent, AccountName, startDate, endDate, PN, narrationFlag, tbdrilldown, psdrilldown, tbType, selectproject);
				}
				if(dualledgerflag==true)
				{
					reportController.showDualLedger(grandParent, AccountName,oldaccname,startDate,oldfromdate, endDate,oldenddate, PN,oldprojectname, narrationFlag,oldnarration, false,false, false,false, "","","", "",true,true);
				}
				}
		});
		
	}

			private void addRow(AddVoucher vRow)
			{
				//we will add a new item (meaning a table row)
				//on 0th position we will have the account name.
				//if the flag is Dr then we will write the amount in the column1 of the item.
				//For cr the amount will go in 2nd position.
				
				TableItem row = new TableItem(voucherViewTable,SWT.BORDER);
				row.setText(0,vRow.getAccountName());
				row.setText(1, Double.toString(vRow.getDrAmount()));
				row.setText(2, Double.toString(vRow.getCrAmount()));
		
				
				//voucherViewTable.pack();
			}
	}