package gnukhata.views;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;

import gnukhata.globals;
import gnukhata.controllers.StartupController;
import gnukhata.controllers.transactionController;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.theme.GroupDrawData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class RolloverandClosebooks extends Composite{
	Boolean DecimalFlag = false;
	Composite grandParent;
	Group grpRoll;
	Group grpClose;
	Color Background;
	Color Foreground;
	Color FocusBackground;
	Color FocusForeground;
	Color BtnFocusForeground;

	Label lblCloseBook;
	Label lblcreateroll;
	Label lblcuryr;
	Label lblcuryrd;
	Label lblcuryrm;
	Label lblcuryry;
	Label lblcbd;
	Label lblcbm;
	Label lblcby;
	Label dash3;
	Label dash4;
	Label lblnewyr;
	Label lblnewyrd;
	Label lblnewyrm;
	Label lblnewyry;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static String strOrgName;
	static String strFromYear;
	static String strToYear;
	static Display display;
	String confirmdate;
	String FinalDate;
	String FinalDate1;
	String rollfromdate;
	String rolltodate;
	String type;
	Text txtddate;
	Text txtmdate;
	Text txtyrdate;
	Label cbdash1;
	Label cbdash;
	Text txtcbd;
	Text txtcbm;
	Text txtcby;
	Label dash1;
	Label dash2;
	Button btncreateroll;
	Button btnclose;
	String rolldate;
	String a;
	String b;
	String d;
	String p;
	String q;
	String r;
	ProgressBar selectbar;
	
	public RolloverandClosebooks(Composite parent,int style) 
	{
		super(parent,style);
		sdf.setLenient(false);
		FormLayout formlayout = new FormLayout();
		this.setLayout(formlayout);
		FormData layout = new FormData();
		MainShell.lblLogo.setVisible(false);
		 MainShell.lblLine.setVisible(false);
		 MainShell.lblOrgDetails.setVisible(false);
		    
		 strOrgName =  globals.session[1].toString();
		 strFromYear =  globals.session[2].toString();
		
		strToYear =  globals.session[3].toString();
		type =  globals.session[4].toString();
		
		Label lblLogo = new Label(this, SWT.None);
		layout = new FormData();
		layout.top = new FormAttachment(1);
		layout.left = new FormAttachment(67);
		layout.right = new FormAttachment(95);
		//layout.bottom = new FormAttachment(18);
		//lblLogo.setSize(getClientArea().width, getClientArea().height);
		lblLogo.setLocation(getClientArea().width, getClientArea().height);
		lblLogo.setLayoutData(layout);
		//Image img = new Image(display,"finallogo1.png");
		lblLogo.setImage(globals.logo);
		
		Label lblOrgDetails = new Label(this,SWT.NONE);
		lblOrgDetails.setFont( new Font(display,"Times New Roman", 14, SWT.BOLD ) );
		lblOrgDetails.setText(globals.session[1]+"\n"+"For Financial Year "+"From "+globals.session[2]+" To "+globals.session[3] );
		layout = new FormData();
		layout.top = new FormAttachment(2);
		layout.left = new FormAttachment(2);
		//layout.right = new FormAttachment(53);
		//layout.bottom = new FormAttachment(18);
		lblOrgDetails.setLayoutData(layout);

		Label lblLink = new Label(this,SWT.None);
		lblLink.setText("www.gnukhata.org");
		lblLink.setFont(new Font(display, "Times New Roman", 11, SWT.ITALIC));
		layout = new FormData();
		layout.top = new FormAttachment(lblLogo,0);
		layout.left = new FormAttachment(75);
		//layout.right = new FormAttachment(33);
		//layout.bottom = new FormAttachment(19);
		lblLink.setLayoutData(layout);
		 
		Label lblLine = new Label(this,SWT.NONE);
		lblLine.setText("---------------------------------------------------------------------------------------");
		lblLine.setFont(new Font(display, "Times New Roman", 26, SWT.ITALIC));
		layout = new FormData();
		layout.top = new FormAttachment(lblLogo,2);
		layout.left = new FormAttachment(2);
		layout.right = new FormAttachment(95);
		//layout.bottom = new FormAttachment(22);
		lblLine.setLayoutData(layout);
		
		grpClose = new Group(this, SWT.BORDER);
		grpClose.setText("Close Books");
		grpClose.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblLine,1);
		layout.left = new FormAttachment(5);
		layout.right = new FormAttachment(75);
		layout.bottom = new FormAttachment(45);
		grpClose.setLayoutData(layout);
		
		Label lblcb = new Label(grpClose, SWT.NONE);
		lblcb.setText("Current financial year from: ");
		lblcb.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		lblcb.setLocation(20, 40);
		lblcb.pack();
		
		lblcbd = new Label(grpClose, SWT.NONE);
		lblcbd.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblcbd.setText(strFromYear.substring(0,2));
		layout = new FormData();
		lblcbd.setLocation(260, 41);
		lblcbd.pack();
		
		Label dashcb = new Label(grpClose, SWT.NONE);
		dashcb.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		dashcb.setText("-");
		layout = new FormData();
		dashcb.setLocation(280, 41);
		dashcb.pack();
		
		lblcbm = new Label(grpClose, SWT.NONE);
		lblcbm.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblcbm.setText(strFromYear.substring(3,5));
		layout = new FormData();
		lblcbm.setLocation(285, 41);
		lblcbm.pack();
		
		Label dashcb1 = new Label(grpClose, SWT.NONE);
		dashcb1.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		dashcb1.setText("-");
		layout = new FormData();
		dashcb1.setLocation(305, 41);
		dashcb1.pack();
		
		lblcby = new Label(grpClose, SWT.NONE);
		lblcby.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblcby.setText(strFromYear.substring(6,10));
		layout =new FormData();
		lblcby.setLocation(310, 41);
		lblcby.pack();
		
		Label lblTo = new Label(grpClose, SWT.NONE);
		lblTo.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		lblTo.setText("To:");
		layout =new FormData();
		lblTo.setLocation(360, 40);
		lblTo.pack();
		
		Calendar cl = Calendar.getInstance();
		cl.set(Integer.valueOf(lblcby.getText()),( Integer.valueOf(lblcbm.getText())-1 )  , (Integer.valueOf(lblcbd.getText())-1) );
		cl.add(Calendar.YEAR , 1);
		Date nextYr = cl.getTime();
		SimpleDateFormat sdfcb = new SimpleDateFormat("dd-MM-yyyy");
		String cbdate = sdfcb.format(nextYr);
		
		txtcbd = new Text(grpClose,SWT.BORDER);
		txtcbd.setText(cbdate.substring(0,2));
		txtcbd.setTextLimit(2);
		
		layout = new FormData();
		txtcbd.setLocation(390, 39);
		txtcbd.pack();
				
		cbdash = new Label(grpClose,SWT.NONE);
		cbdash.setText("-");
		cbdash.setFont(new Font(display, "Time New Roman",16,SWT.BOLD));
		layout = new FormData();
		cbdash.setLocation(420, 39);
		cbdash.pack();
		
		txtcbm = new Text(grpClose,SWT.BORDER);
		//txtmdate.setMessage("mm");
		txtcbm.setText(cbdate.substring(3,5));
		txtcbm.setTextLimit(2);
		txtcbm.setLocation(430, 39);
		txtcbm.pack();
		
		cbdash1 = new Label(grpClose,SWT.NONE);
		cbdash1.setText("-");
		cbdash1.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
		layout = new FormData();
		cbdash1.setLocation(460, 39);
		cbdash1.pack();
		
		txtcby = new Text(grpClose,SWT.BORDER);
		txtcby.setMessage("yyyy");
		txtcby.setText(cbdate.substring(6));
		txtcby.setTextLimit(4);
		layout = new FormData();
		txtcby.setLocation(470, 39);
		txtcby.pack();
		
		lblCloseBook = new Label(grpClose, SWT.NONE);
		lblCloseBook.setText("Do you want to Close Books?");
		lblCloseBook.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		lblCloseBook.setLocation(20, 80);
		lblCloseBook.pack();
		
		btnclose = new Button(grpClose, SWT.NONE);
		btnclose.setText("Close &Books");
		btnclose.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		btnclose.setLocation(40, 120);
		btnclose.setFocus();
		btnclose.pack();
		
		grpRoll = new Group(this, SWT.BORDER);
		grpRoll.setText("Roll Over");
		grpRoll.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(grpClose,5);
		layout.left = new FormAttachment(5);
		layout.right = new FormAttachment(75);
		layout.bottom = new FormAttachment(85);
		grpRoll.setLayoutData(layout);
		
		lblcreateroll = new Label(grpRoll, SWT.NONE);
		lblcreateroll.setText("Roll Over for the Period");
		lblcreateroll.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		layout = new FormData();
		lblcreateroll.setLocation(90, 40);
		lblcreateroll.pack();
		
		lblcuryr = new Label(grpRoll, SWT.NONE);
		lblcuryr.setText("Current financial year from:"+globals.session[2]+ " To:");
		lblcuryr.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		lblcuryr.setLocation(40, 100);
		lblcuryr.pack();
		
		lblcuryrd = new Label(grpRoll, SWT.NONE);
		lblcuryrd.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		lblcuryrd.setText(txtcbd.getText());
		layout = new FormData();
		lblcuryrd.setLocation(395, 100);
		lblcuryrd.pack();
		
		dash3 = new Label(grpRoll, SWT.NONE);
		dash3.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		dash3.setText("-");
		layout = new FormData();
		dash3.setLocation(420, 100);
		dash3.pack();
		
		lblcuryrm = new Label(grpRoll, SWT.NONE);
		lblcuryrm.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		lblcuryrm.setText(txtcbm.getText());
		layout = new FormData();
		lblcuryrm.setLocation(425, 100);
		lblcuryrm.pack();
		
		dash4 = new Label(grpRoll, SWT.NONE);
		dash4.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		dash4.setText("-");
		layout = new FormData();
		dash4.setLocation(450, 100);
		dash4.pack();
		
		lblcuryry = new Label(grpRoll, SWT.NONE);
		lblcuryry.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		lblcuryry.setText(txtcby.getText());
		layout = new FormData();
		lblcuryry.setLocation(455, 100);
		lblcuryry.pack();
		
		lblnewyr = new Label(grpRoll, SWT.NONE);
		lblnewyr.setText("New financial year from: ");
		lblnewyr.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		lblnewyr.setLocation(40, 160);
		lblnewyr.pack();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(lblcuryry.getText()),( Integer.valueOf(lblcuryrm.getText())-1 )  , (Integer.valueOf(lblcuryrd.getText())-1) );
		cal.add(Calendar.DATE , 2);

		Date nextYear = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String FinalDate = sdf.format(nextYear);
	
		lblnewyrd = new Label(grpRoll, SWT.NONE);
		lblnewyrd.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblnewyrd.setText(FinalDate.substring(0,2));
		layout = new FormData();
		lblnewyrd.setLocation(252, 162);
		lblnewyrd.pack();
		
		Label dash5 = new Label(grpRoll, SWT.NONE);
		dash5.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		dash5.setText("-");
		layout = new FormData();
		dash5.setLocation(276, 162);
		dash5.pack();
		
		lblnewyrm = new Label(grpRoll, SWT.NONE);
		lblnewyrm.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblnewyrm.setText(FinalDate.substring(3,5));
		layout = new FormData();
		lblnewyrm.setLocation(282, 162);
		lblnewyrm.pack();
		
		Label dash6 = new Label(grpRoll, SWT.NONE);
		dash6.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		dash6.setText("-");
		layout = new FormData();
		dash6.setLocation(306, 162);
		dash6.pack();
		
		lblnewyry = new Label(grpRoll, SWT.NONE);
		lblnewyry.setFont(new Font(display, "Times New Roman", 15, SWT.BOLD));
		lblnewyry.setText(FinalDate.substring(6,10));
		layout =new FormData();
		lblnewyry.setLocation(312, 162);
		lblnewyry.pack();
		
		
		
		Label lblto = new Label(grpRoll, SWT.NONE);
		lblto.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		lblto.setText("To:");
		layout =new FormData();
		lblto.setLocation(360, 162);
		lblto.pack();
		
		Calendar c = Calendar.getInstance();
		c.set(Integer.valueOf(lblnewyry.getText()),( Integer.valueOf(lblnewyrm.getText())-1 )  , (Integer.valueOf(lblnewyrd.getText())-1) );
		c.add(Calendar.YEAR , 1);
		Date nextYear1 = c.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		String FinalDate1 = sdf1.format(nextYear1);
		
		txtddate = new Text(grpRoll,SWT.BORDER);
		txtddate.setText(FinalDate1.substring(0,2));
		txtddate.setTextLimit(2);
		
		layout = new FormData();
		txtddate.setLocation(390, 160);
		txtddate.pack();
				
		dash1 = new Label(grpRoll,SWT.NONE);
		dash1.setText("-");
		dash1.setFont(new Font(display, "Time New Roman",16,SWT.BOLD));
		layout = new FormData();
		dash1.setLocation(425, 160);
		dash1.pack();
		
		txtmdate = new Text(grpRoll,SWT.BORDER);
		//txtmdate.setMessage("mm");
		txtmdate.setText(FinalDate1.substring(3,5));
		txtmdate.setTextLimit(2);
		txtmdate.setLocation(435, 160);
		txtmdate.pack();
		
		dash2 = new Label(grpRoll,SWT.NONE);
		dash2.setText("-");
		dash2.setFont(new Font(display, "Time New Roman",14,SWT.BOLD));
		layout = new FormData();
		dash2.setLocation(470, 160);
		dash2.pack();
		
		txtyrdate = new Text(grpRoll,SWT.BORDER);
		txtyrdate.setMessage("yyyy");
		txtyrdate.setText(FinalDate1.substring(6));
		txtyrdate.setTextLimit(4);
		layout = new FormData();
		txtyrdate.setLocation(480, 160);
		txtyrdate.pack();
		
		btncreateroll = new Button(grpRoll, SWT.NONE);
		
		btncreateroll.setText("Roll &Over");
		btncreateroll.setFont(new Font(display, "Times New Roman", 15, SWT.NONE));
		layout = new FormData();
		btncreateroll.setLocation(50, 190);
		
		btncreateroll.pack();
		
		
		selectbar=new ProgressBar(this, SWT.SMOOTH);
		selectbar.setVisible(false);
		layout=new FormData();
		layout.top=new FormAttachment(88);
		layout.left=new FormAttachment(5);
		layout.right=new FormAttachment(30);
		selectbar.setLayoutData(layout);
		
		
		if(StartupController.getClosedBooksStatus())
		{
			grpClose.setEnabled(false);
			btnclose.setEnabled(false);
			grpRoll.setEnabled(true);
			btncreateroll.setEnabled(true);
			txtddate.setFocus();
		}
		else
		{
			grpRoll.setEnabled(false);
			btncreateroll.setEnabled(false);
			grpClose.setEnabled(true);
		}
		
		
		confirmdate = (FinalDate1.substring(6,10) + "-" + FinalDate1.substring(3,5) + "-" + FinalDate1.substring(0,2));
		
		
	
		this.getAccessible();
		this.setEvents();
		Background =  new Color(this.getDisplay() ,220 , 224, 227);
		Foreground = new Color(this.getDisplay() ,0, 0,0 );
		FocusBackground  = new Color(this.getDisplay(),78,97,114 );
		FocusForeground = new Color(this.getDisplay(),255,255,255);
        BtnFocusForeground=new Color(this.getDisplay(), 0, 0, 255);
		
		globals.setThemeColor(this, Background, Foreground);
		globals.SetButtonColoredFocusEvents(this, FocusBackground, BtnFocusForeground, Background, Foreground);
		globals.SetComboColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground);
        globals.SetTableColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground); 
		globals.SetTextColoredFocusEvents(this, FocusBackground, FocusForeground, Background, Foreground);
        
		this.pack();

		
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
	
	protected void showprogress() {
        
    	selectbar.setVisible(true);
    	selectbar.setMaximum(100000);
		for(int i=0;i<selectbar.getMaximum();i=i+20)
        
        {
			selectbar.setSelection(i);
        }
      }

	
	private void setEvents(){
		Composite g=btnclose.getParent().getParent().getParent();
		
		selectbar.forceFocus();
		
		
		btnclose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				//make a call to the reportController.getLedger()
				try {
					Date closedate = sdf.parse(txtcby.getText() + "-" + txtcbm.getText() + "-" + txtcbd.getText());
					
					} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
					msg.setText("Error!");
					msg.setMessage("Invalid Date");
					txtcbd.setFocus();
					msg.open();
					return;
				}
				
				
				
				if(txtcby.getText().trim().equals("") )
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgDayErr.setMessage("Please enter a Date in dd format.");
					msgDayErr.setText("Validation Date Error!");
					txtcby.setFocus();
					msgDayErr.open();
					return;
				}
				if(txtcbm.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a Month in mm format.");
					msgDayErr.open();
					txtcbm.setFocus();
					return;
				}
				if(txtcby.getText().trim().equals("")&& Integer.valueOf(txtcby.getText())<=0)
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a Year in yyyy format.");
					txtcby.setFocus();
					msgDayErr.open();
					return;
					
				}
				if(txtcby.getText().length( ) < txtcby.getTextLimit())
				{
					MessageBox msgYearErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgYearErr.setText("Validation Date Error!");
					msgYearErr.setMessage("Please enter year in 4 digits format (YYYY)");
					txtcby.setFocus();
					txtcby.setText("");
					msgYearErr.open();
					return;
					
				}
				if (!txtcbm.getText().trim().equals("")&&!txtcby.getText().trim().equals("")&& Integer.valueOf(txtcby.getText())<=0&&!(txtcby.getText().length( ) < txtcby.getTextLimit())) {
					DateValidate dv = new DateValidate(Integer.valueOf(txtcbm
							.getText()), Integer.valueOf(txtcbd.getText()),
							Integer.valueOf(txtcby.getText()));
					String validationResult = dv.toString();
					if (validationResult.substring(2, 3).equals("0")) {
						MessageBox msgErr = new MessageBox(new Shell(), SWT.OK
								| SWT.ERROR | SWT.ICON_ERROR);
						msgErr.setText("Validation Date Error!");
						msgErr.setMessage("You have entered an Invalid Date");
						txtcbd.setFocus();
						msgErr.open();
						return;
					}
				}
				
				
				if(txtcbd.getText().trim().equals("")&&txtcbm.getText().trim().equals("")&&txtcby.getText().trim().equals(""))
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an Invalid Date");
					msgdateErr.open();
					
					
					Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtcbd.setFocus();
							
						}
					});
					return;
				}
				if(txtcbd.getText().trim().equals(""))
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an Invalid Date");
					txtcbd.setFocus();
					msgdateErr.open();
					return;
				}
				if(txtcbm.getText().trim().equals(""))
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an Invalid Date");
					txtcbm.setFocus();
					msgdateErr.open();
					return;
				}
				if(!txtcbd.getText().trim().equals("")&&!txtcbm.getText().trim().equals("")&&!txtcby.getText().trim().equals(""))
				{
					try {
					
					String finstart = (strFromYear.toString().substring(6,10) + "-" + strFromYear.toString().substring(3,5) + "-" + strFromYear.toString().substring(0,2));
					String finend = (strToYear.substring(6,10) + "-" + strToYear.substring(3,5) + "-" + strToYear.substring(0,2));
					String closedate = (txtcby.getText() + "-" + txtcbm.getText() + "-" + txtcbd.getText());
					String contradate=transactionController.getLastDate("Contra");
					String journaldate=transactionController.getLastDate("Journal");
					String paymentdate=transactionController.getLastDate("Payment");
					String receiptdate=transactionController.getLastDate("Receipt");
					String creditnotedate=transactionController.getLastDate("Credit Note");
					String debitnotedate=transactionController.getLastDate("Debit Note");
					String salesdate=transactionController.getLastDate("Sales");
					String salesreturndate=transactionController.getLastDate("Sales Return");
					String purchasedate=transactionController.getLastDate("Purchase");
					String purchasereturndate=transactionController.getLastDate("Purchase Return");
					System.out.println("finstart"+finstart);
					System.out.println("finend"+finend);
					if(closedate.compareTo(finstart)< 0 || closedate.compareTo(finend) > 0 )
					{
						MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK | SWT.ICON_ERROR);
						errMsg.setText("Validation Date Error!");
						errMsg.setMessage("Please enter date within financial year");
						errMsg.open();
						txtcbd.setFocus();
						txtcbd.setSelection(0,2);
						return;
					}
					if(closedate.compareTo(contradate) < 0||closedate.compareTo(journaldate) < 0||closedate.compareTo(paymentdate) < 0||closedate.compareTo(receiptdate) < 0||closedate.compareTo(creditnotedate) < 0||closedate.compareTo(debitnotedate) < 0||closedate.compareTo(salesdate) < 0||closedate.compareTo(salesreturndate) < 0||closedate.compareTo(purchasedate) < 0||closedate.compareTo(purchasereturndate) < 0)
					{
						MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK | SWT.ICON_ERROR );
						errMsg.setText("Error!");
						errMsg.setMessage("There are transactions beyond the closure date");
						errMsg.open();
						txtcbd.setFocus();
						txtcbd.setSelection(0,2);
						return;
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
				MessageBox msg = new MessageBox(new Shell(), SWT.ICON_QUESTION|SWT.YES | SWT.NO);
				msg.setText("Confirm!");
				msg.setMessage("Are you sure you want to Close Books for this Organisation?");
				int answer = msg.open();
				if(answer == SWT.NO)
				{
					return;
				}
				String frmDate = (strFromYear.toString().substring(6,10) + "-" + strFromYear.toString().substring(3,5) + "-" + strFromYear.toString().substring(0,2));
				String lastDate = (strToYear.toString().substring(6,10) + "-" + strToYear.toString().substring(3,5) + "-" + strToYear.toString().substring(0,2));
				String startdate = (lblnewyry.getText()+ "-" + lblnewyrm.getText()+ "-" + lblnewyrd.getText());

				if(answer==SWT.YES)
				{
					showprogress();
					txtcby.setEnabled(false);
					txtcbm.setEnabled(false);
					txtcbd.setEnabled(false);
					grpClose.setEnabled(false);
					btnclose.setEnabled(false);
					grpRoll.setEnabled(true);
					btncreateroll.setEnabled(true);
					
					btnclose.setEnabled(false);
					btncreateroll.setFocus();
					 MainShell.Contra.setEnabled(false);
					 MainShell.Payment.setEnabled(false);
					 MainShell.Receipt.setEnabled(false);
					 MainShell.Sales.setEnabled(false);
					 MainShell.Sales_Return.setEnabled(false);
					 MainShell.Purchase.setEnabled(false);
					 MainShell.Purchase_Return.setEnabled(false);
					 MainShell.Journal.setEnabled(false);
					 MainShell.Debit_Note.setEnabled(false);
					 MainShell.Credit_Note.setEnabled(false);
					 MainShell.create_account.setEnabled(false);
					 MainShell.transactionsmenu.setEnabled(false);
					 MainShell.tiContra.setEnabled(false);
					 MainShell.tiPayment.setEnabled(false);
					 MainShell.tiReceipt.setEnabled(false);
					 MainShell.tiSales.setEnabled(false);
					 MainShell.tiSalesReturn.setEnabled(false);
					 MainShell.tiPurchase.setEnabled(false);
					 MainShell.tiPurchaseReturn.setEnabled(false);
					 MainShell.tiJournal.setEnabled(false);
					 MainShell.tiDebit.setEnabled(false);
					 MainShell.tiCredit.setEnabled(false);
					 MainShell.tiAccount.setEnabled(false);
					 MainShell.edit_org.setEnabled(false);
					 MainShell.tiEditVoucher.setEnabled(false);
					Composite CloseParent = btnclose.getParent().getParent().getParent().getParent();
					if(gnukhata.controllers.StartupController.CloseBooks(frmDate, startdate, lastDate))
					{
					//gnukhata.controllers.StartupController.CloseBooks(frmDate, frmDate, lastDate);
					MessageBox m = new MessageBox(new Shell(), SWT.OK | SWT.ICON_INFORMATION);
					m.setText("Information!");
					m.setMessage("Your Books have been Closed");
					selectbar.setVisible(false);
					m.open();
					 
					}
					else
					{
						MessageBox msgfailure = new MessageBox(new Shell(), SWT.OK| SWT.ERROR |SWT.ICON_ERROR );
						msgfailure.setMessage("Can't close books for this organization for the given financial period");
						msgfailure.setText("Error!");
						msgfailure.open();
					}
						
				}
				 rolldate=(txtcbd.getText()+ "-" + txtcbm.getText()+ "-" + txtcby.getText());
				 lblcuryry.setText(txtcby.getText());
				 lblcuryrm.setText(txtcbm.getText());
				 lblcuryrd.setText(txtcbd.getText());
				 
				 Calendar cal = Calendar.getInstance();
					cal.set(Integer.valueOf(lblcuryry.getText()),( Integer.valueOf(lblcuryrm.getText())-1 )  , (Integer.valueOf(lblcuryrd.getText())-1) );
					cal.add(Calendar.DATE , 2);

					Date nextYear = cal.getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					FinalDate = sdf.format(nextYear);
					
					lblnewyrd.setText(FinalDate.substring(0,2));
					p=lblnewyrd.getText();
					System.out.println("day"+p);
					lblnewyrm.setText(FinalDate.substring(3,5));
					q=lblnewyrm.getText();
					System.out.println("mon"+q);
					lblnewyry.setText(FinalDate.substring(6,10));
					r=lblnewyry.getText();
					System.out.println("year"+r);
					Calendar c = Calendar.getInstance();
					c.set(Integer.valueOf(lblnewyry.getText()),( Integer.valueOf(lblnewyrm.getText())-1 )  , (Integer.valueOf(lblnewyrd.getText())-1) );
					c.add(Calendar.YEAR , 1);
					Date nextYear1 = c.getTime();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					 FinalDate1 = sdf1.format(nextYear1);
					
					txtddate.setText(FinalDate1.substring(0,2));
					a=txtddate.getText();
					txtmdate.setText(FinalDate1.substring(3,5));
					b=txtmdate.getText();
					txtyrdate.setText(FinalDate1.substring(6,10));
					d=txtyrdate.getText();
		
			}
		}
		});
		
		
		btnclose.addKeyListener(new KeyAdapter() {
			@Override
			// TODO Auto-generated method stub
			//super.widgetSelected(arg0);
			public void keyPressed(KeyEvent e)
			{
				if(e.keyCode==SWT.ARROW_DOWN)
				{
					btncreateroll.setFocus();
				}
				if(e.keyCode==SWT.ARROW_UP)
				{
					txtcby.selectAll();
					txtcby.setFocus();
				}
			}
		});
		btncreateroll.addKeyListener(new KeyAdapter() {
			@Override
			// TODO Auto-generated method stub
			//super.widgetSelected(arg0);
			public void keyPressed(KeyEvent e)
			{
				if(e.keyCode==SWT.ARROW_UP)
				{
					txtyrdate.setFocus();
				}
			}
		});
		
		btncreateroll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//super.widgetSelected(arg0);
				try {
					Date rolldate = sdf.parse(txtyrdate.getText() + "-" + txtmdate.getText() + "-" + txtddate.getText());
					
					} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
					msg.setText("Error!");
					msg.setMessage("Invalid Date");
					txtddate.setFocus();
					msg.open();
					return;
				}
				
				try {
				
					String date = (txtyrdate.getText() + "-" + txtmdate.getText() + "-" + txtddate.getText());
					/*String financialstart = (r + "-" + q + "-" + p);
					String abd=(d + "-" + b + "-" + a);*/
					rollfromdate= (FinalDate.substring(6,10) + "-" + FinalDate.substring(3,5) + "-" + FinalDate.substring(0,2));
					System.out.println("fromdate"+rollfromdate);
					
					rolltodate= (FinalDate1.substring(0,2) + "-" + FinalDate1.substring(3,5) + "-" + FinalDate1.substring(6,10));
					System.out.println("todate"+rolltodate);
					String closedate = (txtcby.getText() + "-" + txtcbm.getText() + "-" + txtcbd.getText());
					System.out.println("curentcl"+closedate);
					System.out.println("finaldate"+confirmdate);
				
					if(!txtyrdate.getText().equals("")&&Integer.valueOf(txtyrdate.getText()) < Integer.valueOf(txtcby.getText()) || txtyrdate.getText().equals("0000"))
			
					{
						MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK |SWT.ICON_ERROR);
						errMsg.setText("Validation Date Error!");
						errMsg.setMessage("Please enter Date within financial year");
						errMsg.open();
						txtddate.setFocus();
						txtddate.setSelection(0,2);
						return;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.getMessage();
				}
				
				MessageBox msg = new MessageBox(new Shell(), SWT.ICON_QUESTION|SWT.YES | SWT.NO);
				msg.setText("Confirm!");
				msg.setMessage("Are you sure you want to Roll Over?");
				int answer = msg.open();
				if(answer == SWT.NO)
				{
					return;
				}
				String fromDate = (strFromYear.toString().substring(6,10) + "-" + strFromYear.toString().substring(3,5) + "-" + strFromYear.toString().substring(0,2) + " "+"00"+":"+"00"+":"+"00");
				String toDate = (strToYear.toString().substring(6,10) + "-" + strToYear.toString().substring(3,5) + "-" + strToYear.toString().substring(0,2) + " "+"00"+":"+"00"+":"+"00");
				String endDate = (txtddate.getText()+ "-" + txtmdate.getText()+ "-" + txtyrdate.getText());
				if(answer==SWT.YES)
				{
					showprogress();
					
			    	
						
					txtyrdate.setEnabled(false);
					txtmdate.setEnabled(false);
					txtddate.setEnabled(false);
					if(gnukhata.controllers.StartupController.RollOver(strOrgName, fromDate, toDate, endDate, type))
					{
					MessageBox m = new MessageBox(new Shell(), SWT.OK | SWT.ICON_INFORMATION);
					m.setText("Information!");
					m.setMessage("Roll Over Done");
					
					int ans= m.open();
					
					btncreateroll.setEnabled(false);
					btncreateroll.getDisplay().dispose();
					
					startupForm sf=new startupForm();
					
					}
					else
					{
						MessageBox msgerr = new MessageBox(new Shell(),SWT.OK|SWT.ERROR | SWT.ICON_ERROR);
						msgerr.setText(" Error!");
						msgerr.setMessage("You have not closed your books so can't roll over");
						msgerr.open();
						btnclose.setFocus();
					}
					}
				
				}
		
		});
		
		txtddate.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		txtmdate.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		
txtmdate.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		txtcbd.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		txtcbm.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		txtcby.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
//				/*if(verifyFlag== false)
//				{
//					arg0.doit= true;
//					return;
//				}*/
				switch (arg0.keyCode) {
	            case SWT.BS:           // Backspace
	            case SWT.DEL:          // Delete
	            case SWT.HOME:         // Home
	            case SWT.END:          // End
	            case SWT.ARROW_LEFT:   // Left arrow
	            case SWT.ARROW_RIGHT:  // Right arrow
	            case SWT.TAB:
	            case SWT.CR:
	            case SWT.KEYPAD_CR:
	            case SWT.KEYPAD_DECIMAL:
	                return;
	        }
				if(arg0.keyCode==46)
				{
					return;
				}
	        if (!Character.isDigit(arg0.character)) {
	            arg0.doit = false;  // disallow the action
	        }

			}
		});
		
		txtddate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					txtmdate.setFocus();
					
				}
				
			}
			
		});
		
		txtmdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					txtyrdate.setFocus();
					
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{	
					txtddate.setFocus();
				}
			}
		});
		
		txtyrdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					btncreateroll.setFocus();
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{	
					txtmdate.setFocus();
				}
			}
		});
		
		txtcbd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					txtcbm.setFocus();
					
				}
			
			}
			
		});
		
		txtcbm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					txtcby.setFocus();
					
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{	
					txtcbd.setFocus();
				}
			}
		});
		
		txtcby.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					btnclose.setFocus();
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{	
					txtcbm.setFocus();
				}
			}
		});
		txtcbm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13 || arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
						||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtcby.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
					arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
					arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
					||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtcbd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13 || arg0.keyCode == SWT.KEYPAD_0||
					arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
					arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
					||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		
		
		txtcbd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtcbd.setBackground(Background);
				txtcbd.setForeground(Foreground);
		
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				if(!txtcbd.getText().trim().equals("") && (Integer.valueOf(txtcbd.getText())> 31 || Integer.valueOf(txtcbd.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an Invalid Date");
					msgdateErr.open();
					
					txtcbd.setText("");
					Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtcbd.setFocus();
							
						}
					});
					return;
				}
				if(!txtcbd.getText().equals("") && Integer.valueOf ( txtcbd.getText())<10 && txtcbd.getText().length()< txtcbd.getTextLimit())
				{
					txtcbd.setText("0"+ txtcbd.getText());
					//txtFromDtMonth.setFocus();
					return;
					
					
					
				}
				/*if(txtFromDtDay.getText().length()==2)
				   {
					   txtFromDtMonth.setFocus();
				   }*/
			}@Override
			public void focusGained(FocusEvent arg0) {
				txtcbd.setBackground(FocusBackground);
				txtcbd.setForeground(FocusForeground);
		
				// TODO Auto-generated method stub
			//	super.focusGained(arg0);
			}
		});
		txtcbm.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtcbm.setBackground(Background);
				txtcbm.setForeground(Foreground);
			
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				if(!txtcbm.getText().trim().equals("") && (Integer.valueOf(txtcbm.getText())> 12 || Integer.valueOf(txtcbm.getText()) <= 0))
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an invalid month, please enter it in mm format.");
					msgdateErr.open();
				
					
					Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtcbm.setText("");
							txtcbm.setFocus();
							
						}
					});
					return;
					
				}
				if(! txtcbm.getText().equals("") && Integer.valueOf ( txtcbm.getText())<10 && txtcbm.getText().length()< txtcbm.getTextLimit())
				{
					txtcbm.setText("0"+ txtcbm.getText());
					return;
				}
				
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				txtcbm.setBackground(FocusBackground);
				txtcbm.setForeground(FocusForeground);
		
				// TODO Auto-generated method stub
			//	super.focusGained(arg0);
			}
		});
		
		
		
		txtcby.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				txtcby.setBackground(FocusBackground);
				txtcby.setForeground(FocusForeground);
		
				// TODO Auto-generated method stub
			//	super.focusGained(arg0);
			}
		});
		btnclose.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				btnclose.setBackground(FocusBackground);
				btnclose.setForeground(BtnFocusForeground);
				// TODO Auto-generated method stub
			//	super.focusGained(arg0);
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				btnclose.setBackground(Background);
				btnclose.setForeground(Foreground);
				// TODO Auto-generated method stub
			//	super.focusLost(arg0);
			}
		});
		txtmdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13 || arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
						||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtyrdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
					arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
					arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
					||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtddate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13 || arg0.keyCode == SWT.KEYPAD_0||
					arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
					arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
					||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		
		
		txtddate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
		txtddate.setBackground(Background);
		txtddate.setForeground(Foreground);
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				txtddate.setBackground(FocusBackground);
				txtddate.setForeground(FocusForeground);
				
				// TODO Auto-generated method stub
			//	super.focusGained(arg0);
			}
		});
		
		txtmdate.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				txtmdate.setBackground(FocusBackground);
				txtmdate.setForeground(FocusForeground);
		
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				if(!txtddate.getText().trim().equals("") && (Integer.valueOf(txtddate.getText())> 31 || Integer.valueOf(txtddate.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an Invalid Date");
					txtddate.setFocus();
					msgdateErr.open();
					
					txtddate.setSelection(0,2);
					/*Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtddate.setFocus();
							
						}
					});*/
					return;
				}
				if(!txtddate.getText().equals("") && Integer.valueOf ( txtddate.getText())<10 && txtddate.getText().length()< txtddate.getTextLimit())
				{
					txtddate.setText("0"+ txtddate.getText());
					//txtFromDtMonth.setFocus();
					return;
					
					
					
				}
				
				if(txtddate.getText().trim().equals("")&&!txtmdate.getText().trim().equals("")&&!txtyrdate.getText().trim().equals("")||txtddate.getText().trim().equals("")||txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&txtyrdate.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a valid date.");
					txtddate.setFocus();
					msgDayErr.open();
					/*display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							
						}
					});*/
					return;
				}
							
			
				
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				txtmdate.setBackground(Background);
				txtmdate.setForeground(Foreground);
		
				// TODO Auto-generated method stub
			//	super.focusLost(arg0);
			}
		});
		
		
		
		txtyrdate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				txtyrdate.setBackground(FocusBackground);
				txtyrdate.setForeground(FocusForeground);
		
				if(!txtmdate.getText().trim().equals("") && (Integer.valueOf(txtmdate.getText())> 12 || Integer.valueOf(txtmdate.getText()) <= 0))
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
					msgdateErr.setText("Validation Date Error!");
					msgdateErr.setMessage("You have entered an invalid month, please enter it in mm format.");
					txtmdate.setFocus();
					msgdateErr.open();
					txtmdate.setSelection(0,2);
					
					/*Display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							txtmdate.setFocus();
							
						}
					});*/
					return;
					
				}
					
				if(! txtmdate.getText().equals("") && Integer.valueOf ( txtmdate.getText())<10 && txtmdate.getText().length()< txtmdate.getTextLimit())
				{
					txtmdate.setText("0"+ txtmdate.getText());
					return;
				}
				
				if(!txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&!txtyrdate.getText().trim().equals("")||!txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&txtyrdate.getText().trim().equals("")||txtmdate.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a valid date.");
					txtmdate.setFocus();
					msgDayErr.open();
					/*display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtmdate.setFocus();
							
						}
					});*/
					return;
				}		
					
			
				
			
				

			}
			@Override
			public void focusLost(FocusEvent arg0) {
		txtyrdate.setBackground(Background);
		txtyrdate.setForeground(Foreground);
		
		// TODO Auto-generated method stub
			//	super.focusLost(arg0);
			}		
		});
		
		btncreateroll.addFocusListener(new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent arg0) {
			btncreateroll.setBackground(FocusBackground);
			btncreateroll.setForeground(BtnFocusForeground);
	
			// TODO Auto-generated method stub
			super.focusGained(arg0);
	/*		if(!txtyrdate.getText().equals("")&&Integer.valueOf(txtyrdate.getText()) < Integer.valueOf(txtcby.getText()) )
				
			{
				MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK );
				errMsg.setMessage("Please enter date within financial year");
				errMsg.open();
				display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtddate.setFocus();
						txtddate.setSelection(0,2);
					}
				});
				return;
				
				
			}*/
			if(txtddate.getText().trim().equals("")&&!txtmdate.getText().trim().equals("")&&!txtyrdate.getText().trim().equals("")||txtddate.getText().trim().equals("")||txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&txtyrdate.getText().trim().equals(""))
			{
				MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgDayErr.setText("Validation Date Error!");
				msgDayErr.setMessage("Please enter a valid date.");
				txtddate.setFocus();
				msgDayErr.open();
				/*display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtddate.setFocus();
						
					}
				});*/
				return;
			}
					
			if(!txtddate.getText().trim().equals("")&&!txtmdate.getText().trim().equals("")&&txtyrdate.getText().trim().equals("")||txtyrdate.getText().equals("0000") )
			{
				MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgDayErr.setText("Validation Date Error!");
				msgDayErr.setMessage("Please enter a valid date.");
				txtyrdate.setFocus();
				msgDayErr.open();
				txtyrdate.selectAll();
				/*display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtyrdate.setFocus();
						
					}
				});*/
				return;
			}
			if(!txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&!txtyrdate.getText().trim().equals("")||!txtddate.getText().trim().equals("")&&txtmdate.getText().trim().equals("")&&txtyrdate.getText().trim().equals("")||txtmdate.getText().trim().equals(""))
			{
				MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgDayErr.setText("Validation Date Error!");
				msgDayErr.setMessage("Please enter a valid date.");
				txtmdate.setFocus();
				msgDayErr.open();
				/*display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtmdate.setFocus();
						
					}
					
				});*/
				return;
			}
			
			if(txtyrdate.getText().trim().equals("")&& Integer.valueOf(txtyrdate.getText())<=0)
			{
				MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgDayErr.setText("Validation Date Error!");
				msgDayErr.setMessage("Please enter a Year in yyyy in valid format.");
				txtyrdate.setFocus();
				msgDayErr.open();
				/*display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtyrdate.setFocus();
						
					}
				});*/
				return;
			}
			if(txtyrdate.getText().length( ) < txtyrdate.getTextLimit())
			{
				MessageBox msgYearErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgYearErr.setText("Validation Date Error!");
				msgYearErr.setMessage("Please enter year in 4 digits format (YYYY)");
				txtyrdate.setFocus();
				txtyrdate.setText("");
				msgYearErr.open();
				/*
				Display.getCurrent().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtyrdate.setFocus();
						txtyrdate.setText("");
						
					}
				});*/
				return;
				
			}
			DateValidate dv = new DateValidate(Integer.valueOf(txtmdate.getText()) ,Integer.valueOf(txtddate.getText()) ,Integer.valueOf(txtyrdate.getText()));
			String validationResult = dv.toString();
			if(validationResult.substring(2,3).equals("0"))
			{
				MessageBox msgErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
				msgErr.setText("Validation Date Error!");
				msgErr.setMessage("You have entered an Invalid Date");
				msgErr.open();
			
			Display.getCurrent().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					txtddate.setFocus();
					
				}
			});
			return;
			}

		}
		@Override
			public void focusLost(FocusEvent arg0) {
			btncreateroll.setBackground(Background);
			btncreateroll.setForeground(Foreground);
		
			// TODO Auto-generated method stub
			//	super.focusLost(arg0);
			}
		});
		

	}
	protected void checkSubclass()
	{
	//this is blank method so will disable the check that prevents subclassing of shells.
	}
	
}