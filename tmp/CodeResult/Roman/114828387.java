package gnukhata.views;

/*@ Author
Vinay khedekar < vinay.itengg@gmail.com>
*/
import java.awt.TextArea;
import java.util.Arrays;
import gnukhata.globals;
import java.util.Vector;

import javax.swing.border.Border;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class VoucherForm extends Composite
{
	static String strOrgName;
	static String strFromYear;
	static String strToYear;
	static Display display;
	TabFolder tfTransaction;
	TabItem tinewvoucher;
	TabItem fdrecord ;
	Label lbldate;
	Text txtddate;
	Label dash1;
	Text txtmdate;
	Label dash2;
	Text txtyrdate;
	Label lblvoucherno;
	Text txtvoucherno;
	Table table;
	TableItem tableitem0;
	TableItem tableitem1;
	TableItem tableitem2;
	TableItem tableitem3;
	TableColumn column0;
	TableColumn column1;
	TableColumn column2;
	TableColumn column3;
	Label lblDR_CR;
	Label lblAccName;
	Label lblDrAmount;
	Label lblcrAmount;
	TableColumn column;
	TableEditor dr_crEditor;
	TableEditor accNameEditor;
	TableEditor debAmtEditor;
	TableEditor creAmtEditor;
	TableEditor editor;
	TableEditor editor1;
	TableEditor editor2;
	TableEditor editor3;
	TableEditor editor4;
	TableEditor editor5;
	TableEditor editor6;
	TableEditor editor7;
	TableEditor editor8;
	TableEditor editor9;
	TableEditor editor10;
	Text txtDr_DebAmt;
	Text txtCr_DebAmt;
	Text txtDr_CrdAmt;
	Text txtCr_CrdAmt;
	Text txttotalDebAmt;
	Text txttotalCrAmt;
	Label lblTotal;
	Label lblnarration;
	Text txtnarration;
	Label lblselprj ;
	Combo comboselprj;
	Button save;
	Label lblsearchRec;
	Combo combosearchRec;
	Label lbleditvoucherno;
	Text txteditvoucherno;
	Text txtFromDt;
	Label lblFromDtDash1;
	Combo comboDr_cr;
	Combo comboCr_Dr;
	Combo comboDr_accName;
	Combo comboCr_accName;
	Label lblvFromDt;
	Text txtFromDt1;
	Label lblFromDtDash2;
	Text txtFromDt2;
	Label lblToDt;
	Text txtToDt;
	Label lblToDtDash1;
	Text txtToDt1;
	Label lblToDtDash2;
	Text txtToDt2;
	Label lblentamount;
	Text txtentamount;
	Button btndelete;
	Button btnsearch;
	Table vdtable;
	

	Vector<Object> params;
	
	public VoucherForm(Composite parent,int style)
	{
		super(parent,style);
		FormLayout formlayout = new FormLayout();
		this.setLayout(formlayout);
		FormData layout = new FormData();
		/*Label lblHeadline = new Label(this,SWT.None);
		lblHeadline.setFont(new Font(display, "Times New Roman", 18, SWT.BOLD));
		lblHeadline.setText("GNUKhata a Free and Open Source Accounting Software");
		layout = new FormData();
		layout.top = new FormAttachment(10);
		layout.left = new FormAttachment(5);
		layout.right = new FormAttachment(51);
		layout.bottom = new FormAttachment(15);
		lblHeadline.setLayoutData(layout);
		*/
		Label lblLogo = new Label(this, SWT.None);
		//Image img = new Image(display,"finallogo.png");
		lblLogo.setImage(globals.logo);
		layout = new FormData();
		layout.top = new FormAttachment(1);
		layout.left = new FormAttachment(63);
		layout.right = new FormAttachment(87);
		layout.bottom = new FormAttachment(9);
		lblLogo.setLayoutData(layout);
		
		Label lblOrgDetails = new Label(this,SWT.NONE);
		lblOrgDetails.setFont( new Font(display,"Times New Roman", 11, SWT.BOLD ) );
		lblOrgDetails.setText(strOrgName+ "\n"+ " For Financial Year "+"From "+strFromYear+" To "+strToYear);
		layout.top = new FormAttachment(1);
		layout.left = new FormAttachment(3);
		layout.right = new FormAttachment(62);
		layout.bottom = new FormAttachment(7);
		lblOrgDetails.setLayoutData(layout);
	    
		Label lblLine = new Label(this,SWT.NONE);
		lblLine.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		lblLine.setFont(new Font(display, "Times New Roman", 16, SWT.ITALIC));
		layout = new FormData();
		layout.top = new FormAttachment(lblLogo,1);
		layout.left = new FormAttachment(3);
		layout.right = new FormAttachment(99);
		lblLine.setLayoutData(layout);
		
		tfTransaction =new TabFolder(this, SWT.NONE);
		layout =new FormData();
		layout.top = new FormAttachment(lblLine,0);
		layout.left = new FormAttachment(6);
		layout.right = new FormAttachment(95);
		layout.bottom = new FormAttachment(97);
		tfTransaction.setLayoutData(layout);
		
		tinewvoucher = new TabItem(tfTransaction, SWT.NONE);
		tinewvoucher.setText("                        New Voucher                            ");
		Composite cmpnewvoucher = new Composite(tfTransaction,SWT.BORDER);
		cmpnewvoucher.setLayout(formlayout);
		tinewvoucher.setControl(cmpnewvoucher);
		
		fdrecord = new TabItem(tfTransaction, SWT.NONE);
		fdrecord.setText("                Find/Edit/Delete Records                   ");
		Composite cmpfdrecord = new Composite(tfTransaction, SWT.BORDER | SWT.V_SCROLL);
		cmpfdrecord.setLayout(formlayout);
		fdrecord.setControl(cmpfdrecord);
		//Group//
	/*	Group addcontravoucher = new Group(cmpnewvoucher,SWT.BORDER);
		addcontravoucher.setText("    Add Contra Voucher    ");
		lblLine.setFont(new Font(display, "Times New Roman", 30, SWT.ITALIC ));
		addcontravoucher.setLayout(formlayout);
		new Label (addcontravoucher,SWT.NONE).setText(" Date : (dd/mm/yy)");
		*/
		lbldate = new Label(cmpnewvoucher,SWT.NONE);
		lbldate.setText("&Date : (dd/mm/yyyy)");
		lbldate.setFont(new Font(display, "Time New Roman",12,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(2);
		layout.right = new FormAttachment(17);
		layout.bottom = new FormAttachment(9);
		lbldate.setLayoutData(layout);
		
		txtddate = new Text(cmpnewvoucher,SWT.BORDER);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(17);
		layout.right = new FormAttachment(21);
		layout.bottom = new FormAttachment(9);
		txtddate.setLayoutData(layout);
		dash1 = new Label(cmpnewvoucher,SWT.NONE);
		dash1.setText("-");
		dash1.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(21);
		layout.right = new FormAttachment(22);
		layout.bottom = new FormAttachment(9);
		dash1.setLayoutData(layout);
		txtmdate = new Text(cmpnewvoucher,SWT.BORDER);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(22);
		layout.right = new FormAttachment(26);
		layout.bottom = new FormAttachment(9);
		txtmdate.setLayoutData(layout);
		dash2 = new Label(cmpnewvoucher,SWT.NONE);
		dash2.setText("-");
		dash2.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(26);
		layout.right = new FormAttachment(27);
		layout.bottom = new FormAttachment(9);
		dash2.setLayoutData(layout);
		txtyrdate = new Text(cmpnewvoucher,SWT.BORDER);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(27);
		layout.right = new FormAttachment(34);
		layout.bottom = new FormAttachment(9);
		txtyrdate.setLayoutData(layout);
		
		lblvoucherno = new Label(cmpnewvoucher,SWT.NONE);
		lblvoucherno.setText("&Voucher No *");
		lblvoucherno.setFont(new Font(display, "Time New Roman",12,SWT.NORMAL));
		layout =new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(65);
		layout.right = new FormAttachment(76);
		layout.bottom = new FormAttachment(9);
		lblvoucherno.setLayoutData(layout);
		txtvoucherno = new Text(cmpnewvoucher,SWT.BORDER);
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(76);
		layout.right = new FormAttachment(96);
		layout.bottom = new FormAttachment(9);
		txtvoucherno.setLayoutData(layout);
		//Table
		table = new Table (cmpnewvoucher, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (false);
		layout = new FormData();
		layout.top = new FormAttachment(12);
		layout.left = new FormAttachment(10);
		layout.right = new FormAttachment(85);
		layout.bottom = new FormAttachment(44);
		table.setLayoutData(layout);
		
		lblDR_CR = new Label(table,SWT.BORDER);
		lblDR_CR.setText("    				 DR/CR		     	");
	
		lblAccName = new Label(table,SWT.BORDER);
		lblAccName.setText("        	  Account Name 	          ");
		
		lblDrAmount = new Label(table,SWT.BORDER);
		lblDrAmount.setText("    			 Debit Amount     			 ");
		
		lblcrAmount = new Label(table,SWT.BORDER);
		lblcrAmount.setText("    			 Credit Amount   		  ");
		
		tableitem0 = new TableItem(table, SWT.NONE);
		tableitem1 = new TableItem(table, SWT.NONE);
		tableitem2 = new TableItem(table, SWT.NONE);
		tableitem3 = new TableItem(table, SWT.NONE);
		
		table.addListener(SWT.MeasureItem, new Listener() 
		{
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				event.width = 200;
				event.height = 30;
				
			}
		});
		   
		      TableItem[] items = table.getItems();
		      column0 = new TableColumn(table, SWT.BORDER);
		      column1 = new TableColumn(table, SWT.BORDER);
		      column2 = new TableColumn(table, SWT.RIGHT);
		      column3 = new TableColumn(table, SWT.RIGHT);
		      
		      dr_crEditor = new TableEditor(table);
		      dr_crEditor.grabHorizontal = true;
		      dr_crEditor.setEditor(lblDR_CR,items[0],0);
		      
		      accNameEditor = new TableEditor(table);
		      accNameEditor.grabHorizontal = true;
		      accNameEditor.setEditor(lblAccName,items[0],1);
		      
		      debAmtEditor = new TableEditor(table);
		      debAmtEditor.grabHorizontal = true;
		      debAmtEditor.setEditor(lblDrAmount,items[0],2);
		      
		      creAmtEditor = new TableEditor(table);
		      creAmtEditor.grabHorizontal = true;
		      creAmtEditor.setEditor(lblcrAmount,items[0],3);
		     
		      editor = new TableEditor(table);
		      comboDr_cr = new Combo(table, SWT.READ_ONLY);
		      comboDr_cr.add("Dr");
		      comboDr_cr.add("Cr");
		      comboDr_cr.select(0);
		      editor.grabHorizontal = true;
		      editor.setEditor( comboDr_cr, items[1], 0);
		      
		      editor2 = new TableEditor(table);
		      comboDr_accName = new Combo(table, SWT.READ_ONLY);
		      editor2.grabHorizontal = true;
		      editor2.setEditor(comboDr_accName, items[1],1);
		      
		      editor4 = new TableEditor(table);
		      txtDr_DebAmt= new Text(table,SWT.BORDER);
		      txtDr_DebAmt.setText("0.00");
		      editor4.grabHorizontal=true;
		      editor4.setEditor( txtDr_DebAmt,items[1],2);
		      
		      editor5 = new TableEditor(table);
		      txtDr_CrdAmt = new Text(table,SWT.BORDER);
		      txtDr_CrdAmt.setText("0.00");
		      editor5.grabHorizontal=true;
		      editor5.setEditor(txtDr_CrdAmt,items[1],3);
		   
		      editor1 = new TableEditor(table);
		      comboCr_Dr = new Combo(table, SWT.READ_ONLY);
		      comboCr_Dr.add("Dr");
		      comboCr_Dr.add("Cr");
		      comboCr_Dr.select(1);
		      editor1.grabHorizontal = true;
		      editor1.setEditor(comboCr_Dr, items[2],0);
		   
		      editor3 = new TableEditor(table);
		      comboCr_accName = new Combo(table, SWT.READ_ONLY);
		      editor3.grabHorizontal = true;
		      editor3.setEditor(comboCr_accName, items[2],1);
		     
		      
		      editor6 = new TableEditor(table);
		      txtCr_DebAmt= new Text(table,SWT.RIGHT);
		      txtCr_DebAmt.setText("0.00");
		      editor6.grabHorizontal=true;
		      editor6.setEditor( txtCr_DebAmt,items[2],2);
		      
		      editor7 = new TableEditor(table);
		      txtCr_CrdAmt= new Text(table, SWT.RIGHT);
		      txtCr_CrdAmt.setText("0.00");
		      editor7.grabHorizontal=true;
		      editor7.setEditor( txtCr_CrdAmt,items[2],3);
		      
		      editor8 = new TableEditor(table);
		      lblTotal = new Label(table,SWT.BORDER);
		      lblTotal = new Label(table,SWT.RIGHT);
		      lblTotal.setText("Total");
		      lblTotal.setFont(new Font(display, "Times New Roman", 16, SWT.NORMAL));
		      editor8.grabHorizontal = true;
		      editor8.setEditor(lblTotal, items[3], 1);
		      
		      editor9 = new TableEditor(table);
		      txttotalDebAmt= new Text(table,SWT.RIGHT);
		      txttotalDebAmt.setText("0.00");
		      editor9.grabHorizontal=true;
		      editor9.setEditor(txttotalDebAmt,items[3],2);
		      
		      editor10 = new TableEditor(table);
		      txttotalCrAmt= new Text(table,SWT.RIGHT);
		      txttotalCrAmt.setText("0.00");
		      editor10.grabHorizontal=true;
		      editor10.setEditor(txttotalCrAmt,items[3],3);
		      
		      this.makeaccessible(table);
				column0.pack();
				column1.pack();
				column2.pack();
				column3.pack();
		//Narration 
		        lblnarration = new Label(cmpnewvoucher,SWT.NONE);
				lblnarration.setText("Narrat&ion :");
				lblnarration.setFont(new Font(display, "Time New Roman",14,SWT.RIGHT));
				layout = new FormData();
				layout.top = new FormAttachment(table,15);
				layout.left = new FormAttachment(23);
				layout.right = new FormAttachment(32);
				layout.bottom = new FormAttachment(57);
				lblnarration.setLayoutData(layout);
				txtnarration = new Text(cmpnewvoucher,SWT.MULTI | SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(table,15);
				layout.left = new FormAttachment(32);
				layout.right = new FormAttachment(70);
				layout.bottom = new FormAttachment(70);
				txtnarration.setLayoutData(layout);
		      
				lblselprj = new Label(cmpnewvoucher,SWT.NONE);
				lblselprj.setText("Select &Project :");
				lblselprj.setFont(new Font(display, "Time New Roman",14,SWT.RIGHT));
				layout = new FormData();
				layout.top = new FormAttachment(txtnarration,8);
				layout.left = new FormAttachment(23);
				layout.right = new FormAttachment(35);
				layout.bottom = new FormAttachment(77);
				lblselprj.setLayoutData(layout);
				
				comboselprj = new Combo(cmpnewvoucher,SWT.READ_ONLY);
				comboselprj.setToolTipText("Select your Orgnization");
				comboselprj.add("No project");
				comboselprj.select(0);
				comboselprj.setVisible(true);
				layout = new FormData();
				layout.top = new FormAttachment(txtnarration,8);
				layout.left = new FormAttachment(35);
				layout.right = new FormAttachment(65);
				layout.bottom = new FormAttachment(77);
				comboselprj.setLayoutData(layout);
				
				save = new Button(cmpnewvoucher,SWT.PUSH);
				save.setText("&Save");
				save.setFont(new Font(display, "Time New Roman",14,SWT.RIGHT));
				layout = new FormData();
				layout.top = new FormAttachment(comboselprj,23);
				layout.left = new FormAttachment(45);
				layout.right = new FormAttachment(52);
				layout.bottom = new FormAttachment(90);
				save.setLayoutData(layout);
				
		// Find/Edit/delete Records
				lblsearchRec = new Label(cmpfdrecord,SWT.NONE);
				lblsearchRec.setText("S&earch Record By : ");
				lblsearchRec.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(10);
				layout.left = new FormAttachment(25);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(15);
				lblsearchRec.setLayoutData(layout);
				
				combosearchRec = new Combo(cmpfdrecord,SWT.READ_ONLY );
				combosearchRec.add("           ---- Please Select -----        ");
				combosearchRec.add("   Voucher No");
				combosearchRec.add("   Time Interval (From-To)");
				combosearchRec.add("   Amount");
				combosearchRec.add("   Narration");
				combosearchRec.select(0);
				layout = new FormData();
				layout.top = new FormAttachment(10);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(60);
				layout.bottom = new FormAttachment(15);
				combosearchRec.setLayoutData(layout);
		//search voucher by voucher number		
				lbleditvoucherno = new Label(cmpfdrecord,SWT.NONE);
				lbleditvoucherno.setText("Enter Voucher No : ");
				lbleditvoucherno.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(lblsearchRec,17);
				layout.left = new FormAttachment(25);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(25);
				lbleditvoucherno.setLayoutData(layout);
				lbleditvoucherno.setVisible(false);
				
				txteditvoucherno = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,17);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(60);
				layout.bottom = new FormAttachment(26);
				txteditvoucherno.setLayoutData(layout);
				txteditvoucherno.setVisible(false);
				
				//date fields when search voucher by time interval(From-to)
				lblvFromDt = new Label(cmpfdrecord,SWT.NONE);
				lblvFromDt.setText("From Date (dd/mm/yyyy) : ");
				lblvFromDt.setFont(new Font(display, "Times New Roman", 14, SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(lblsearchRec,19);
				layout.left = new FormAttachment(22);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(25);
				lblvFromDt.setLayoutData(layout);
				lblvFromDt.setVisible(false);
				txtFromDt = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,20);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(44);
				layout.bottom = new FormAttachment(27);
				txtFromDt.setLayoutData(layout);
				txtFromDt.setVisible(false);
				lblFromDtDash1 = new Label(cmpfdrecord, SWT.NONE);
				lblFromDtDash1.setText("-");
				lblFromDtDash1.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,22);
				layout.left = new FormAttachment(44);
				layout.right = new FormAttachment(45);
				layout.bottom = new FormAttachment(24);
				lblFromDtDash1.setLayoutData(layout);
				lblFromDtDash1.setVisible(false);
				txtFromDt1 = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,20);
				layout.left = new FormAttachment(45);
				layout.right = new FormAttachment(48);
				layout.bottom = new FormAttachment(27);
				txtFromDt1.setLayoutData(layout);
				txtFromDt1.setVisible(false);
				lblFromDtDash2 = new Label(cmpfdrecord, SWT.NONE);
				lblFromDtDash2.setText("-");
				lblFromDtDash2.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,22);
				layout.left = new FormAttachment(48);
				layout.right = new FormAttachment(49);
				layout.bottom = new FormAttachment(24);
				lblFromDtDash2.setLayoutData(layout);
				lblFromDtDash2.setVisible(false);
				txtFromDt2 = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,20);
				layout.left = new FormAttachment(49);
				layout.right = new FormAttachment(54);
				layout.bottom = new FormAttachment(27);
				txtFromDt2.setLayoutData(layout);
				txtFromDt2.setVisible(false);
				
				lblToDt = new Label(cmpfdrecord,SWT.NONE);
				lblToDt.setText("To Date :");
				lblToDt.setFont(new Font(display, "Times New Roman", 14, SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(lblvFromDt,17);
				layout.left = new FormAttachment(34);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(34);
				lblToDt.setLayoutData(layout);
				lblToDt.setVisible(false);
				txtToDt = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(txtFromDt,10);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(44);
				layout.bottom = new FormAttachment(36);
				txtToDt.setLayoutData(layout);
				txtToDt.setVisible(false);
				lblToDtDash1 = new Label(cmpfdrecord, SWT.NONE);
				lblToDtDash1.setText("-");
				lblToDtDash1.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
				layout = new FormData();
				layout.top = new FormAttachment(txtFromDt,13);
				layout.left = new FormAttachment(44);
				layout.right = new FormAttachment(45);
				layout.bottom = new FormAttachment(38);
				lblToDtDash1.setLayoutData(layout);
				lblToDtDash1.setVisible(false);
				txtToDt1 = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(txtFromDt,10);
				layout.left = new FormAttachment(45);
				layout.right = new FormAttachment(48);
				layout.bottom = new FormAttachment(36);
				txtToDt1.setLayoutData(layout);
				txtToDt1.setVisible(false);
				lblToDtDash2 = new Label(cmpfdrecord, SWT.NONE);
				lblToDtDash2.setText("-");
				lblToDtDash2.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
				layout = new FormData();
				layout.top = new FormAttachment(txtFromDt,13);
				layout.left = new FormAttachment(48);
				layout.right = new FormAttachment(49);
				layout.bottom = new FormAttachment(35);
				lblToDtDash2.setLayoutData(layout);
				lblToDtDash2.setVisible(false);
				txtToDt2 = new Text(cmpfdrecord,SWT.BORDER);
				layout = new FormData();
				layout.top = new FormAttachment(txtFromDt,10);
				layout.left = new FormAttachment(49);
				layout.right = new FormAttachment(54);
				layout.bottom = new FormAttachment(36);
				txtToDt2.setLayoutData(layout);
				txtToDt2.setVisible(false);
		// searching records by entering  Amount 
				lblentamount = new Label(cmpfdrecord, SWT.NONE);
				lblentamount.setText("Enter Amount :");
				lblentamount.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(lblsearchRec,27);
				layout.left = new FormAttachment(28);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(28);
				lblentamount.setLayoutData(layout);
				lblentamount.setVisible(false);
				txtentamount = new Text(cmpfdrecord,SWT.RIGHT);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,25);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(60);
				layout.bottom = new FormAttachment(28);
				txtentamount.setLayoutData(layout);
				txtentamount.setVisible(false);
		// searching records by narration
				lblnarration = new Label(cmpfdrecord, SWT.NONE);
				lblnarration.setText("Enter Narration containing :");
				lblnarration.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(lblsearchRec,33);
				layout.left = new FormAttachment(19);
				layout.right = new FormAttachment(41);
				layout.bottom = new FormAttachment(28);
				lblnarration.setLayoutData(layout);
				lblnarration.setVisible(true);
				
				txtnarration = new Text(cmpfdrecord,SWT.BORDER | SWT.MULTI);
				layout = new FormData();
				layout.top = new FormAttachment(combosearchRec,33);
				layout.left = new FormAttachment(41);
				layout.right = new FormAttachment(65);
				layout.bottom = new FormAttachment(38);
				txtnarration.setLayoutData(layout);
				txtnarration.setVisible(true);
			
				btnsearch = new Button(cmpfdrecord,SWT.PUSH);
				btnsearch.setText("Search");
				btnsearch.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout = new FormData();
				layout.top = new FormAttachment(txteditvoucherno,90);
				layout.left = new FormAttachment(39);
				layout.right = new FormAttachment(48);
				layout.bottom = new FormAttachment(56);
				btnsearch.setLayoutData(layout);
				
				btndelete = new Button(cmpfdrecord,SWT.PUSH);
				btndelete.setText("Delete");
				btndelete.setFont(new Font(display, "Time New Roman",13,SWT.NORMAL));
				layout= new FormData();
				layout.top = new FormAttachment(table,385);
				layout.left = new FormAttachment(46);
				layout.right = new FormAttachment(54);
				layout.bottom = new FormAttachment(99);
				btndelete.setLayoutData(layout);
				btndelete.setVisible(false);
				
				vdtable = new Table(cmpfdrecord,SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
				vdtable.setLinesVisible(true);
				vdtable.setHeaderVisible(true);
				layout = new FormData();
				layout.top = new FormAttachment(btnsearch,28);
				layout.left = new FormAttachment(6);
				layout.right = new FormAttachment(97);
				layout.bottom = new FormAttachment(90);
				vdtable.setLayoutData(layout);
				String[] vheader = {"      Voucher \n    Number      ","       Voucher \n      Type        ","        Date of \n Transaction     ","       Dr Amount       ","      Cr Amount      ","       Amount        ","                   Narration                 ","        Action        ","     Mark for \n    Delation  "};
				for (int i=0; i<vheader.length; i++) 
				{
					TableColumn vcolumn = new TableColumn (vdtable, SWT.BORDER);
					vcolumn.setText (vheader [i]);
				}
				for (int i=0; i<vheader.length; i++) 
				{
				vdtable.getColumn (i).pack ();
				}	
				for (int i = 0; i < 9; i++) 
			 	{
			      new TableItem(vdtable, SWT.NONE);
			    }
				//write event for dynamic table Item//
		
				
				
				
			
		this.pack();
		this.getAccessible();
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