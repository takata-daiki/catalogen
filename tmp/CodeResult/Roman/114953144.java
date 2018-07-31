package gnukhata.views;
			
/*@ Authors



 Amit Chougule <acamit333@gmail.com>,
 Vinay khedekar < vinay.itengg@gmail.com>
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import gnukhata.globals;
import gnukhata.controllers.StartupController;
import gnukhata.views.DateValidate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sun.org.apache.bcel.internal.generic.INEG;

public class startupForm extends Shell {
    public void addControlListener(org.eclipse.swt.events.ControlListener arg0) {};
	Color Background;
	Color Foreground;
	Color FocusBackground;
	Color FocusForeground;
	Color BtnFocusForeground;

    String orgName;
	String orgType;
	String fromDate, toDate;
	String[] financialYears;	
	String searchText = "";
	String[] orgNameList1;
	long searchTexttimeout = 0;
	long wait=0;
	boolean msgflag=false;
	boolean verifyFlag=false;
	static Display display;
	Button btnExistingOrg;	
	Button btnCreateOrg;
	Label lblOrgName;
	CCombo dropdownOrgName;
	Label lblFinancialYear;
    CCombo dropdownFinancialYear;
	Button btnProceed;
	Button btnPreferences;
	Label lblNewOrgName;
	Text txtNewOrgName;
	Label lblOrgType;
	CCombo dropdownOrgType;
	Label lblNewFinancialYear;
	Label lblFromDt;
	Label lblToDt;
	Text txtFromdateDay;
	Text txtTodateDay;
	Button btnNext;

	Label lblFromDtDash1;
	Text txtFromdateMonth;
	Label lblFromDtDash2;
	Text txtFromdateYear;
	Text txtTodateYear;
	Label lblToDtDash1;
	Text txtTodateMonth;
	Label lblToDtDash2;
   
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	Vector<Object> params;
	protected int[] orgNameList;
	public startupForm() {
		super(Display.getDefault());
		FormLayout formlayout = new FormLayout();
		this.setLayout(formlayout);
		//this.setCursor(new Cursor(display,SWT.CURSOR_HAND ));
		StartupController.getOrganisationNames();
		this.setText("GNUKhata Startup");
		Label lblWelcome = new Label(this, SWT.None);
		lblWelcome.setText("Welcome");
		lblWelcome.setFont(new Font(display, "Times New Roman", 20, SWT.BOLD));
		FormData layout = new FormData();
		layout.top = new FormAttachment(3, 5);
		layout.left = new FormAttachment(2, 0);
		
		layout.right = new FormAttachment(18, 0);
		layout.bottom = new FormAttachment(8,0);
		lblWelcome.setLayoutData(layout);
		
		Label lblHeadline = new Label(this, SWT.None);
		lblHeadline.setFont(new Font(display, "Times New Roman", 13, SWT.BOLD));
		lblHeadline.setText("GNUKhata: A Free and Open Source Accounting Software");
		layout = new FormData();
		
		layout.top = new FormAttachment(lblWelcome , 15);
		layout.left = new FormAttachment(2, 0);
		layout.right = new FormAttachment(48, 0);
		layout.bottom = new FormAttachment(13, 7);
		lblHeadline.setLayoutData(layout);
		
		Label lblLogo = new Label(this, SWT.None);
		layout = new FormData();
		layout.top = new FormAttachment(6);
		layout.left = new FormAttachment(63);
		layout.right = new FormAttachment(99);
		layout.bottom = new FormAttachment(15);
		lblLogo.setLayoutData(layout);
		//Image img = new Image(display, "finallogo.png");
		lblLogo.setImage(globals.logo);

		Label lblLink = new Label(this, SWT.None);
		lblLink.setText("www.gnukhata.org");
		lblLink.setFont(new Font(display, "Times New Roman", 17, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblHeadline,0);
		layout.left = new FormAttachment(11);
		layout.right = new FormAttachment(30);
		layout.bottom = new FormAttachment(20);
		lblLink.setLayoutData(layout);
		
		Label lblLine = new Label(this, SWT.NONE);
		lblLine.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		lblLine.setFont(new Font(display, "Times New Roman", 18, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(25);
		layout.left = new FormAttachment(2);
		layout.right = new FormAttachment(99);
		layout.bottom = new FormAttachment(31);
		lblLine.setLayoutData(layout);
		
		Label lblFeatures = new Label(this, SWT.NONE);
		lblFeatures.setText("Features Of GNUKhata:");
		lblFeatures.setFont(new Font(display, "Times New Roman", 13, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(36);
		layout.left = new FormAttachment(2);
		layout.right = new FormAttachment(22);
		layout.bottom = new FormAttachment(41);
		lblFeatures.setLayoutData(layout);
		
		Label lblPoint = new Label(this, SWT.NONE);
		lblPoint.setText("* It is lightweight");
		lblPoint.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblFeatures, 5);
		layout.left = new FormAttachment(4);
		layout.right = new FormAttachment(25);
		layout.bottom = new FormAttachment(45);
		lblPoint.setLayoutData(layout);
		
		Label lblPoint1 = new Label(this, SWT.NONE);
		lblPoint1.setText("* It is scalable");
		lblPoint1.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblPoint, 5);
		layout.left = new FormAttachment(4);
		layout.right = new FormAttachment(20);
		layout.bottom = new FormAttachment(49);
		lblPoint1.setLayoutData(layout);
		
		Label lblPoint2 = new Label(this, SWT.NONE);
		lblPoint2.setText("* It is fast and robust");
		lblPoint2.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblPoint1, 5);
		layout.left = new FormAttachment(4);
		layout.right = new FormAttachment(25);
		layout.bottom = new FormAttachment(53);
		lblPoint2.setLayoutData(layout);
		
		Label lblPoint3 = new Label(this, SWT.NONE);
		lblPoint3.setText("* It can be deployed for \n   profit and non-profit organisations");
		lblPoint3.setFont(new Font(display, "Times New Roman", 12, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblPoint2, 5);
		layout.left = new FormAttachment(4);
		layout.right = new FormAttachment(30);
		layout.bottom = new FormAttachment(66);
		lblPoint3.setLayoutData(layout);
		
		Label lblnote = new Label(this, SWT.NONE);
		lblnote.setText("Press and Hold Alt for screen specific shortcuts.");
		lblnote.setFont(new Font(display, "Times New Roman", 18, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(84);
		layout.left = new FormAttachment(3);
		//layout.right = new FormAttachment(12);
		//layout.bottom = new FormAttachment(66);
		lblnote.setLayoutData(layout);

		
		// decoration over now the real usable widgets start.
		btnExistingOrg = new Button(this, SWT.PUSH);
		btnExistingOrg.setText(" Select E&xisting Organisation");
		btnExistingOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
		btnExistingOrg.setToolTipText("Click here to select an existing organization from the list");
		layout = new FormData();
		layout.top = new FormAttachment(38);
		layout.left = new FormAttachment(lblHeadline);
		layout.right = new FormAttachment(70);
		layout.bottom = new FormAttachment(44);
		btnExistingOrg.setLayoutData(layout);
		
		btnCreateOrg = new Button(this, SWT.PUSH);
		btnCreateOrg.setText(" Create New &Organisation");
		btnCreateOrg.setToolTipText("Click here to create a new organization");
		btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(38);
		layout.left = new FormAttachment(btnExistingOrg, 40);
		layout.right = new FormAttachment(95);
		layout.bottom = new FormAttachment(44);
		btnCreateOrg.setLayoutData(layout);

		// Existing Organisation
		lblOrgName = new Label(this, SWT.None);
		lblOrgName.setText("Organisation N&ame :");
		lblOrgName.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(btnExistingOrg, 30);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(52);
		lblOrgName.setLayoutData(layout);
		lblOrgName.setVisible(false);
		
		dropdownOrgName = new CCombo(this, SWT.BORDER| SWT.READ_ONLY);
		dropdownOrgName.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(btnExistingOrg, 28);
		if(globals.session[8].toString().equals("gnome"))
		{
			layout.left = new FormAttachment(lblOrgName,5 );
			layout.right = new FormAttachment(97);
		}
		if(globals.session[8].toString().equals("ubuntu"))
		{
			layout.left = new FormAttachment(lblOrgName, 11);
			layout.right = new FormAttachment(93);	
		}
		layout.bottom = new FormAttachment(51);
		dropdownOrgName.setLayoutData(layout);
		dropdownOrgName.setVisible(false);
		
		lblFinancialYear = new Label(this, SWT.NONE);
		lblFinancialYear.setText("Financial Year  :");
		lblFinancialYear.setFont(new Font(display, "Times New Roman", 11,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(54);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(57);
		lblFinancialYear.setLayoutData(layout);
		lblFinancialYear.setVisible(false);
		
		dropdownFinancialYear = new CCombo(this, SWT.BORDER | SWT.READ_ONLY);
		dropdownFinancialYear.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(54);
		if(globals.session[8].toString().equals("gnome"))
		{
			layout.left = new FormAttachment(lblFinancialYear, 5);
			layout.right = new FormAttachment(82);
		}
		if(globals.session[8].toString().equals("ubuntu"))
		{
			layout.left = new FormAttachment(lblFinancialYear, 11);
			layout.right = new FormAttachment(79);	
		}
		layout.bottom = new FormAttachment(57);
		dropdownFinancialYear.setLayoutData(layout);
		dropdownFinancialYear.setVisible(false);
		
		btnProceed = new Button(this, SWT.NONE);
		btnProceed.setText("&Proceed");
		btnProceed.setToolTipText("Click to go to the login screen");
		btnProceed.setFont(new Font(display, "Times New Roman", 11, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblFinancialYear, 20);
		layout.left = new FormAttachment(68);
		layout.right = new FormAttachment(77);
		layout.bottom = new FormAttachment(64);
		btnProceed.setLayoutData(layout);
		btnProceed.setVisible(false);

		// Create New Organisation
		lblNewOrgName = new Label(this, SWT.None);
		lblNewOrgName.setText("Organisation N&ame :");
		lblNewOrgName.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(46);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(50);
		lblNewOrgName.setLayoutData(layout);
		lblNewOrgName.setVisible(false);
		
		
		
		/*lblNewOrgName = new Label(this, SWT.NONE);
		lblNewOrgName.setText("Organisation Na&me :");
		lblNewOrgName.setFont(new Font(display, "Times New Roman", 14,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(btnExistingOrg, 40);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(50);
		lblNewOrgName.setLayoutData(layout);
		lblNewOrgName.setVisible(false);
		*/
		txtNewOrgName = new Text(this, SWT.BORDER);
		layout = new FormData();
		layout.top = new FormAttachment(46);
		if(globals.session[8].toString().equals("gnome"))
		{
			layout.left = new FormAttachment(lblNewOrgName, 21);
			layout.right = new FormAttachment(97);
		}
		if(globals.session[8].toString().equals("ubuntu"))
		{
			layout.left = new FormAttachment(lblNewOrgName, 25);
			layout.right = new FormAttachment(94);	
		}
		layout.bottom = new FormAttachment(50);
		txtNewOrgName.setLayoutData(layout);
		txtNewOrgName.setToolTipText("Enter the name of your organization(upto 50 character)");
		txtNewOrgName.setTextLimit(54);
		txtNewOrgName.setVisible(false);
		
		lblOrgType = new Label(this, SWT.NONE);
		lblOrgType.setText("Organization T&ype  :");
		lblOrgType.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(52);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(56);
		lblOrgType.setLayoutData(layout);
		lblOrgType.setVisible(false);
		
		dropdownOrgType = new CCombo(this, SWT.READ_ONLY | SWT.BORDER);
		layout = new FormData();
		dropdownOrgType.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout.top = new FormAttachment(52);
		if(globals.session[8].toString().equals("gnome"))
		{
			layout.left = new FormAttachment(lblOrgType, 21);
			layout.right = new FormAttachment(80);
		}
		if(globals.session[8].toString().equals("ubuntu"))
		{
			layout.left = new FormAttachment(lblOrgType, 25);
			layout.right = new FormAttachment(81);	
		}
		layout.bottom = new FormAttachment(56);
		dropdownOrgType.setLayoutData(layout);
		dropdownOrgType.setToolTipText("Select profit making if your Organisation is commercial and NGO if it is NGO or under section 25");
		dropdownOrgType.add("--------Please Select--------");
		dropdownOrgType.add("Profit Making");
		dropdownOrgType.add("NGO");
		dropdownOrgType.select(0);
		dropdownOrgType.setVisible(false);

		lblNewFinancialYear = new Label(this, SWT.NONE);
		lblNewFinancialYear.setText("New F&inancial Year");
		lblNewFinancialYear.setFont(new Font(display, "Times New Roman", 11,SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(58);
		layout.left = new FormAttachment(48);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(62);
		lblNewFinancialYear.setLayoutData(layout);
		lblNewFinancialYear.setVisible(false);

		lblFromDt = new Label(this, SWT.NONE);
		lblFromDt.setText("&From Date    :");
		lblFromDt.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,6);
		layout.left = new FormAttachment(53);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(66);
		lblFromDt.setLayoutData(layout);
		lblFromDt.setVisible(false);

		txtFromdateDay = new Text(this, SWT.BORDER);
		txtFromdateDay.setMessage("dd");
		txtFromdateDay.setTextLimit(2);
		txtFromdateDay.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,8);
		layout.left = new FormAttachment(lblFromDt, 25);
		layout.right = new FormAttachment(69);
		layout.bottom = new FormAttachment(66);
		txtFromdateDay.setLayoutData(layout);
		txtFromdateDay.setToolTipText("Enter the date of starting financial year in dd-mm-yyyy format");
		txtFromdateDay.setVisible(false);

		lblFromDtDash1 = new Label(this, SWT.NONE);
		lblFromDtDash1.setText("-");
		lblFromDtDash1.setFont(new Font(display, "Time New Roman", 11, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,8);
		layout.left = new FormAttachment(69);
		layout.right = new FormAttachment(70);
		layout.bottom = new FormAttachment(65);
		lblFromDtDash1.setLayoutData(layout);
		lblFromDtDash1.setVisible(false);

		txtFromdateMonth = new Text(this, SWT.BORDER);
		txtFromdateMonth.setMessage("mm");
		txtFromdateMonth.setTextLimit(2);
		txtFromdateMonth.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,8);
		layout.left = new FormAttachment(70);
		layout.right = new FormAttachment(73);
		layout.bottom = new FormAttachment(66);
		txtFromdateMonth.setLayoutData(layout);
		txtFromdateMonth.setToolTipText("Enter the month of starting financial year in dd-mm-yyyy format");
		txtFromdateMonth.setVisible(false);

		lblFromDtDash2 = new Label(this, SWT.NONE);
		lblFromDtDash2.setText("-");
		lblFromDtDash2.setFont(new Font(display, "Time New Roman", 11, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,8);
		layout.left = new FormAttachment(73);
		layout.right = new FormAttachment(74);
		layout.bottom = new FormAttachment(65);
		lblFromDtDash2.setLayoutData(layout);
		lblFromDtDash2.setVisible(false);

		txtFromdateYear = new Text(this, SWT.BORDER);
		txtFromdateYear.setMessage("yyyy");
		txtFromdateYear.setTextLimit(4);
		txtFromdateYear.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblNewFinancialYear,8);
		layout.left = new FormAttachment(74);
		layout.right = new FormAttachment(78);
		layout.bottom = new FormAttachment(66);
		txtFromdateYear.setLayoutData(layout);
		txtFromdateYear.setToolTipText("Enter the year of starting financial year in dd-mm-yyyy format");
		txtFromdateYear.setVisible(false);

		lblToDt = new Label(this, SWT.NONE);
		lblToDt.setText("&To Date        :");
		lblToDt.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(lblFromDt, 10);
		layout.left = new FormAttachment(53);
		layout.right = new FormAttachment(63);
		layout.bottom = new FormAttachment(70);
		lblToDt.setLayoutData(layout);
		lblToDt.setVisible(false);
		
		txtTodateDay = new Text(this, SWT.BORDER);
		txtTodateDay.setMessage("dd");
		txtTodateDay.setTextLimit(2);
		txtTodateDay.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(txtFromdateDay, 7);
		layout.left = new FormAttachment(lblToDt , 25);
		layout.right = new FormAttachment(69);
		layout.bottom = new FormAttachment(70);
		txtTodateDay.setLayoutData(layout);
		txtTodateDay.setToolTipText("Enter the date of your financial year end in dd-mm-yyyy format");
		txtTodateDay.setVisible(false);

		lblToDtDash1 = new Label(this, SWT.NONE);
		lblToDtDash1.setText("-");
		lblToDtDash1.setFont(new Font(display, "Time New Roman", 11, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(txtFromdateDay, 7);
		layout.left = new FormAttachment(69);
		layout.right = new FormAttachment(70);
		layout.bottom = new FormAttachment(70);
		lblToDtDash1.setLayoutData(layout);
		lblToDtDash1.setVisible(false);

		txtTodateMonth = new Text(this, SWT.BORDER);
		txtTodateMonth.setMessage("mm");
		txtTodateMonth.setTextLimit(2);
		txtTodateMonth.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(txtFromdateDay, 7);
		layout.left = new FormAttachment(70);
		layout.right = new FormAttachment(73);
		layout.bottom = new FormAttachment(70);
		txtTodateMonth.setLayoutData(layout);
		txtTodateMonth.setToolTipText("Enter the month of starting financial year in dd-mm-yyyy format");
		txtTodateMonth.setVisible(false);

		lblToDtDash2 = new Label(this, SWT.NONE);
		lblToDtDash2.setText("-");
		lblToDtDash2.setFont(new Font(display, "Time New Roman", 11, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(txtFromdateDay, 7);
		layout.left = new FormAttachment(73);
		layout.right = new FormAttachment(74);
		layout.bottom = new FormAttachment(70);
		lblToDtDash2.setLayoutData(layout);
		lblToDtDash2.setVisible(false);

		txtTodateYear = new Text(this, SWT.BORDER);
		txtTodateYear.setMessage("yyyy");
		txtTodateYear.setTextLimit(4);
		txtTodateYear.setFont(new Font(display, "Times New Roman", 11, SWT.NORMAL));
		layout = new FormData();
		layout.top = new FormAttachment(txtFromdateDay, 7);
		layout.left = new FormAttachment(74);
		layout.right = new FormAttachment(78);
		layout.bottom = new FormAttachment(70);
		txtTodateYear.setLayoutData(layout);
		txtTodateYear.setToolTipText("Enter the year of starting financial year in dd-mm-yyyy format");
		txtTodateYear.setVisible(false);

		btnNext = new Button(this, SWT.NONE);
		btnNext.setText("&Next");
		btnNext.setToolTipText("Click to move to the next screen for Organization details");
		btnNext.setFont(new Font(display, "Times New Roman", 14, SWT.BOLD));
		layout = new FormData();
		layout.top = new FormAttachment(txtTodateDay, 21);
		layout.left = new FormAttachment(65);
		layout.right = new FormAttachment(72);
		layout.bottom = new FormAttachment(77);
		btnNext.setLayoutData(layout);
		btnNext.setVisible(false);

		btnPreferences=new Button(this, SWT.PUSH);
       btnPreferences.setText("&Connect to server");
		btnPreferences.setToolTipText("Click to change the server url location");
		btnPreferences.setFont(new Font(display,"Times New Roman",12,SWT.BOLD));
		layout=new FormData();
		layout.top=new FormAttachment(btnNext,45);
		layout.left=new FormAttachment(78);
		layout.right=new FormAttachment(91);
		btnPreferences.setLayoutData(layout);
		btnPreferences.setVisible(true);
		
		
		orgNameList1 = gnukhata.controllers.StartupController.getOrganisationNames();
		System.out.println(orgNameList1.length);
		if(orgNameList1.length==1)
		{
			btnExistingOrg.setVisible(false);
			
		}
		if(orgNameList1.length>1)
		{
			btnExistingOrg.setVisible(true);
		}
		this.setImage(globals.icon);
		
		sdf.setLenient(false);
		this.getAccessible();
		
		this.setEvents();
		this.pack();
		this.open();
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
        
		
		this.showView();
		
	}

	
	
	// the following method sets (registers) all the necesary event listenners
	// on the respective widgets.
	// this method will be the last call inside the constructor.
	private void setEvents() {
		


		
		// the selection listenner is click event.
		// We are going to use adapters instead of listenners.
		// adapters are abstract classes so Eclipse allows us to override the
		// methods.
			txtNewOrgName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode ==SWT.CR || arg0.keyCode == SWT.KEYPAD_CR)
				{
					
							
							
							dropdownOrgType.setFocus();
					
					
				}
				if(arg0.keyCode == SWT.ARROW_UP)
				{
					btnCreateOrg.setFocus();
					lblNewOrgName.setVisible(false);
					txtNewOrgName.setVisible(false);
					lblNewFinancialYear.setVisible(false);
					lblFromDt.setVisible(false);
					lblToDt.setVisible(false);
					txtFromdateDay.setVisible(false);
					txtTodateDay.setVisible(false);
					lblOrgType.setVisible(false);
					dropdownOrgType.setVisible(false);
					btnNext.setVisible(false);
					lblFromDtDash1.setVisible(false);
					lblFromDtDash1.setVisible(false);
					txtFromdateMonth.setVisible(false);
					lblFromDtDash2.setVisible(false);
					txtFromdateYear.setVisible(false);
					lblToDtDash1.setVisible(false);
					txtTodateMonth.setVisible(false);
					lblToDtDash2.setVisible(false);
					txtTodateYear.setVisible(false);
				}
			}
		});
		
		dropdownOrgName.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent arg0) {
				dropdownOrgName.setListVisible(true);
			};
		});
		
		dropdownFinancialYear.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				dropdownFinancialYear.setListVisible(true);
			};
		});
		
		dropdownOrgType.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				dropdownOrgType.setListVisible(true);
			};
		});
			
		txtNewOrgName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				if(!txtNewOrgName.getText().trim().equals(""))
				{
					String orgname1 = txtNewOrgName.getText();
					String titlecasename = toTitleCase(orgname1);
					txtNewOrgName.setText(titlecasename);
				}
				
				
			}
		});
		
		
		dropdownOrgType.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode ==SWT.CR || arg0.keyCode == SWT.KEYPAD_CR)
				{
					
						txtFromdateDay.setFocus();
					
				}
				
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					if(dropdownOrgType.getSelectionIndex()== 0 )
					{
						txtNewOrgName.setFocus();
					}
				}
			}
			});		
		txtFromdateDay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					
					//txtDtDOrg.traverse(SWT.TRAVERSE_TAB_NEXT);
					txtFromdateMonth.setFocus();
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					dropdownOrgType.setFocus();
				}
			

			}
		});
		
		txtFromdateDay.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				if(verifyFlag== false)
				{
					arg0.doit= true;
					return;
				}
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

		
/*		txtFromdateDay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
				{
				if(!txtFromdateDay.getText().equals("") && Integer.valueOf ( txtFromdateDay.getText())<10 && txtFromdateDay.getText().length()< txtFromdateDay.getTextLimit())
				{
					txtFromdateDay.setText("0"+ txtFromdateDay.getText());
					//txtFromDtMonth.setFocus();
					txtFromdateDay.setFocus();
					return;
					
					
					
				}
				
				}
				if(arg0.keyCode ==SWT.TAB)
				{
					if(txtFromdateDay.getText().equals(""))
					{
						txtFromdateDay.setText("");
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromdateDay.setFocus();
							}
						});
						return;
					}
				}
				

			}
		});
		*/
	txtFromdateMonth.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				if(verifyFlag== false)
				{
					arg0.doit= true;
					return;
				}
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
		
		
		txtFromdateMonth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
				{
				if(!	txtFromdateMonth.getText().equals("") && Integer.valueOf ( 	txtFromdateMonth.getText())<10 && 	txtFromdateMonth.getText().length()< 	txtFromdateMonth.getTextLimit())
				{
					txtFromdateMonth.setText("0"+ txtFromdateMonth.getText());
					//txtFromDtMonth.setFocus();
					
					txtFromdateYear.setFocus();
					return;
					
					
					
				}
				else
				{
					txtFromdateYear.setFocus();
				}
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtFromdateDay.setFocus();
				}
				

			}
		});

		txtFromdateYear.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			//super.keyPressed(arg0);
		
			if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
			{
				if(txtFromdateDay.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a Date.");
					msgDayErr.open();
					txtFromdateDay.setFocus();
					
					return;
				}
				if(txtFromdateMonth.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Month Validation Error!");
					msgDayErr.setMessage("Please enter a Month.");
					msgDayErr.open();
					txtFromdateMonth.setFocus();
					
					return;
				}
				
				if(txtFromdateYear.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Year Validation Error!");
					msgDayErr.setMessage("Please enter a Year.");
					msgDayErr.open();
					txtFromdateYear.setFocus();
					
					return;
				}
		
		
				if(!txtFromdateDay.getText().trim().equals("") && (Integer.valueOf(txtFromdateDay.getText())> 31 || Integer.valueOf(txtFromdateDay.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Date Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Date");
					txtFromdateDay.setText("");
					txtFromdateDay.setFocus();
					msgdateErr.open();
					return;
				}
		
				if(!txtFromdateMonth.getText().trim().equals("") && (Integer.valueOf(txtFromdateMonth.getText())> 12 || Integer.valueOf(txtFromdateMonth.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Month Validation Error!");
					msgdateErr.setMessage("You have entered an invalid month");
					txtFromdateMonth.setText("");
					txtFromdateMonth.setFocus();
					msgdateErr.open();
					return;
				}
		
				if(!txtFromdateYear.getText().trim().equals("") && (Integer.valueOf(txtFromdateYear.getText())> 2100 || Integer.valueOf(txtFromdateYear.getText()) < 1900) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Year Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Year");
					txtFromdateYear.setText("");
					txtFromdateYear.setFocus();
					msgdateErr.open();
					return;
				}
		
				
				
					txtTodateDay.setFocus();
				
			}
				
			if(arg0.keyCode==SWT.ARROW_UP)
			{
				txtFromdateMonth.setFocus();
			}
		

		}
	});
	

	txtFromdateYear.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				if(verifyFlag== false)
				{
					arg0.doit= true;
					return;
				}
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
	
	txtTodateDay.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			//super.keyPressed(arg0);
			
			if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
			{
			if(!	txtTodateDay.getText().equals("") && Integer.valueOf ( txtTodateDay.getText())<10 && 	txtTodateDay.getText().length()< 	txtTodateDay.getTextLimit())
			{
				txtTodateDay.setText("0"+ txtTodateDay.getText());
				//txtFromDtMonth.setFocus();
				txtTodateMonth.setFocus();
				return;
				
				
				
			}
			else
			{
				txtTodateMonth.setFocus();
				
			}
			}
			if(arg0.keyCode==SWT.ARROW_UP)
			{
				txtFromdateYear.setFocus();
			}
			
			
		}
	});
	
	txtTodateDay.addVerifyListener(new VerifyListener() {
		
		@Override
		public void verifyText(VerifyEvent arg0) {
			// TODO Auto-generated method stub
			if(verifyFlag== false)
			{
				arg0.doit= true;
				return;
			}
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
		
		txtTodateMonth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
				{
				if(!	txtTodateMonth.getText().equals("") && Integer.valueOf ( txtTodateMonth.getText())<10 && 	txtTodateMonth.getText().length()< 	txtTodateMonth.getTextLimit())
				{
					txtTodateMonth.setText("0"+ txtTodateMonth.getText());
					//txtFromDtMonth.setFocus();
					txtTodateYear.setFocus();
					return;
					
					
					
				}
				else
				{
					txtTodateYear.setFocus();
				}
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtTodateDay.setFocus();
				}

			}
		});
		
	txtTodateMonth.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				if(verifyFlag== false)
				{
					arg0.doit= true;
					return;
				}
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
		
		txtTodateYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
/*					if(txtTodateYear.getText().trim().equals(""))
					{
						MessageBox msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
			            msg.setText("Error!");
						
						msg.setMessage("Please enter valid date");
						msg.open();
					
						txtTodateYear.setFocus();
					}
*/					if(!txtTodateYear.getText().trim().equals(""))
					{
						//btnNext.setFocus();
						btnNext.notifyListeners(SWT.Selection, new Event());
					}
					/*if(txtTodateYear.getText().trim().equals(""))
					{
						
						
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtTodateYear.setFocus();							
							}
						});
						return;
						
					}
					
				*/	
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtTodateMonth.selectAll();
					txtTodateMonth.setFocus();
				}
			

			}
		});
		
	txtTodateYear.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				if(verifyFlag== false)
				{
					arg0.doit= true;
					return;
				}
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
		
		btnNext.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(txtTodateYear.getText().equals(""))
				{txtTodateYear.setText("");
				
				return;
				}
				if(arg0.keyCode==SWT.KEYPAD_CR || arg0.keyCode==SWT.CR)
				{
					txtTodateYear.setFocus();
					txtTodateYear.selectAll();
					return;
					
				}
			}
			public void keyReleased(KeyEvent arg0) {
				if(txtTodateYear.getText().equals(""))
				{txtTodateYear.setText("");
				
				return;
				}
				
				
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtTodateYear.setFocus();
					txtTodateYear.selectAll();
					return;
				}	
			}
		});
		this.btnExistingOrg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				// TODO Auto-generated method stub
				lblOrgName.setVisible(true);
				dropdownOrgName.setVisible(true);
				btnExistingOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
				btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.NORMAL));
				lblFinancialYear.setVisible(false);
				dropdownFinancialYear.setVisible(false);
				dropdownFinancialYear.removeAll();
				// params = new Vector<Object>();
				try {
					orgNameList1 = gnukhata.controllers.StartupController.getOrganisationNames();
					Arrays.sort(orgNameList1); 
					//Arrays.sort(orgNameList);
					dropdownOrgName.setItems(orgNameList1);
					dropdownOrgName.select(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error occured");
					startupForm sp=new startupForm();
				}
				// dropdownOrgName.setListVisible(true);
				btnProceed.setVisible(true);
				btnProceed.setEnabled(false);

				lblNewOrgName.setVisible(false);
				txtNewOrgName.setVisible(false);
				txtNewOrgName.setText("");
				lblNewFinancialYear.setVisible(false);
				lblFromDt.setVisible(false);
				lblToDt.setVisible(false);
				txtFromdateDay.setVisible(false);
				txtFromdateDay.setText("");
				txtTodateDay.setVisible(false);
				txtTodateDay.setText("");
				lblOrgType.setVisible(false);
				dropdownOrgType.setVisible(false);
				lblFromDtDash1.setVisible(false);
				txtFromdateMonth.setVisible(false);
				txtFromdateMonth.setText("");
				lblFromDtDash2.setVisible(false);
				txtFromdateYear.setVisible(false);
				txtFromdateYear.setText("");
				lblToDtDash1.setVisible(false);
				txtTodateMonth.setVisible(false);
				txtTodateMonth.setText("");
				lblToDtDash2.setVisible(false);
				txtTodateYear.setVisible(false);
				txtTodateYear.setText("");
				dropdownOrgType.select(0);
				btnNext.setVisible(false);
                dropdownOrgName.setFocus();

			}

		});

		dropdownFinancialYear.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.CR || arg0.keyCode == SWT.KEYPAD_CR)
				{
					btnProceed.notifyListeners(SWT.Selection ,new Event() );
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					if(dropdownFinancialYear.getSelectionIndex()== 0)
					{
						dropdownOrgName.setFocus();
					}
				}
			}
		});

		this.btnCreateOrg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
			// TODO Auto-generated method stub
				btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
				btnExistingOrg.setFont(new Font(display, "Times New Roman", 12,SWT.NORMAL));
				
				lblOrgName.setVisible(false);
				dropdownOrgName.setVisible(false);
				dropdownOrgName.select(0);
				lblFinancialYear.setVisible(false);
				dropdownFinancialYear.setVisible(false);
				dropdownFinancialYear.select(0);
				btnProceed.setVisible(false);
				lblNewOrgName.setVisible(true);
				txtNewOrgName.setVisible(true);
				txtNewOrgName.setText("");
				lblNewFinancialYear.setVisible(true);
				lblFromDt.setVisible(true);
				lblToDt.setVisible(true);
				txtFromdateDay.setVisible(true);
				txtFromdateDay.setText("");
				txtTodateDay.setVisible(true);
				txtTodateDay.setText("");
				lblOrgType.setVisible(true);
				dropdownOrgType.setVisible(true);
				dropdownOrgType.select(0);
				btnNext.setVisible(true);
				lblFromDtDash1.setVisible(true);
				lblFromDtDash1.setVisible(true);
				txtFromdateMonth.setVisible(true);
				txtFromdateMonth.setText("");
				lblFromDtDash2.setVisible(true);
				txtFromdateYear.setVisible(true);
				txtFromdateYear.setText("");
				lblToDtDash1.setVisible(true);
				txtTodateMonth.setVisible(true);
				txtTodateMonth.setText("");
				lblToDtDash2.setVisible(true);
				txtTodateYear.setVisible(true);
				txtTodateYear.setText("");
				txtNewOrgName.setFocus();
				if(!txtNewOrgName.getText().trim().equals("")&&dropdownOrgType.getSelectionIndex()>=0&&!txtFromdateDay.getText().trim().equals("")&&!txtFromdateMonth.getText().trim().equals("")&&!txtFromdateYear.getText().trim().equals("")&&!txtTodateDay.getText().trim().equals("")&&!txtTodateMonth.getText().trim().equals("")&&!txtTodateYear.getText().trim().equals(""))
				{
					txtNewOrgName.setText("");
					dropdownOrgType.select(0);
					txtFromdateDay.setText("");
					txtFromdateMonth.setText("");
					txtFromdateYear.setText("");
					txtTodateDay.setText("");
					txtTodateMonth.setText("");
					txtTodateYear.setText("");
					
				}
			}
		});
		dropdownOrgName.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				if(dropdownOrgName.getSelectionIndex() > 0 )
				{
				btnProceed.setEnabled(true);
				dropdownFinancialYear.setVisible(true);
				lblFinancialYear.setVisible(true);
				params = new Vector<Object>();
				params.add(dropdownOrgName.getItem(dropdownOrgName.getSelectionIndex()));
				Object[] result = gnukhata.controllers.StartupController.getFinancialYear(params);

				financialYears = new String[result.length];
				for (int i = 0; i < result.length; i++) {
					Object[] obj = (Object[]) result[i];
					financialYears[i] = obj[0] + " to " + obj[1];
				}
				params.clear();
				orgName = dropdownOrgName.getItem(dropdownOrgName.getSelectionIndex());
				dropdownFinancialYear.setItems(financialYears);
				if (dropdownFinancialYear.getItemCount() == 1) {
					dropdownFinancialYear.select(0);
				}
				if (dropdownFinancialYear.getItemCount() > 1) {
					dropdownFinancialYear.select(dropdownFinancialYear.getItemCount() - 1);
				}
				fromDate = dropdownFinancialYear.getItem(dropdownFinancialYear.getSelectionIndex()).substring(0,10);
				toDate = dropdownFinancialYear.getItem(dropdownFinancialYear.getSelectionIndex()).substring(14);
				}
				else
				{
					dropdownFinancialYear.setVisible(false);
					lblFinancialYear.setVisible(false);
					btnProceed.setEnabled(false);
				}

			}

		});
		
		btnPreferences.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dropdownOrgName.setVisible(false);
				lblOrgName.setVisible(false);
				dropdownFinancialYear.setVisible(false);
				btnProceed.setVisible(false);
				lblFinancialYear.setVisible(false);
				Shell shell = new Shell();
				Get_Preferences dialog = new Get_Preferences(shell);
				System.out.println(dialog.open()); 
			}			
		});
		
		btnProceed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if(dropdownOrgName.getSelectionIndex()>0)
				{
				fromDate = dropdownFinancialYear.getItem(dropdownFinancialYear.getSelectionIndex()).substring(0,10);
				toDate = dropdownFinancialYear.getItem(dropdownFinancialYear.getSelectionIndex()).substring(14);
				String[] connectParams = new String[] { orgName, fromDate,toDate };
				String id = gnukhata.controllers.StartupController.getConnection(connectParams);
				/*
				 * MessageBox success = new MessageBox(new Shell(), SWT.OK);
				 * success.setText("success"); success.setMessage("id is " +
				 * id); success.open();
				 */	
				dispose();
				gnukhata.controllers.StartupController.showLoginForm();
				}
			}
		});
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(txtNewOrgName.getText().trim().equals(""))
				{    
					MessageBox msg= new MessageBox(new Shell(),SWT.OK| SWT.ERROR |SWT.BOLD);
					msg.setText("Error!");
					
					msg.setMessage("Please enter name of the Organization");
					msg.open();
					//txtNewOrgName.setFocus();
					display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtNewOrgName.setFocus();					
						}
					});
					
					return;

				}
				if(txtNewOrgName.getText().trim().startsWith("#") || txtNewOrgName.getText().trim().startsWith("$") || txtNewOrgName.getText().trim().startsWith("&") || txtNewOrgName.getText().trim().startsWith("(") || txtNewOrgName.getText().trim().startsWith(")") || txtNewOrgName.getText().trim().startsWith("'") || txtNewOrgName.getText().trim().startsWith("|") || txtNewOrgName.getText().trim().startsWith(";") || txtNewOrgName.getText().trim().startsWith("<") || txtNewOrgName.getText().trim().startsWith(">") || txtNewOrgName.getText().trim().startsWith("?") || txtNewOrgName.getText().trim().startsWith("-") || txtNewOrgName.getText().trim().startsWith("\"") || txtNewOrgName.getText().trim().startsWith("\\"))
						{
					  
					MessageBox msg= new MessageBox(new Shell(),SWT.OK| SWT.ERROR |SWT.BOLD);
					msg.setText("Error!");
					
					msg.setMessage("Organisation name cannot starts with special character");
					txtNewOrgName.setFocus();
					msg.open();
					return;
						}
			
		
				if (dropdownOrgType.getSelectionIndex() == 0)
				{
					MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
					msg.setText("Alert!");
					msg.setMessage("Please select the type of your organization.");
					msg.open();
					//dropdownOrgType.setFocus();
					display.getCurrent().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							dropdownOrgType.setFocus();					
						}
					});
					
					return;

					
				}
				
				if(txtFromdateDay.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a Date.");
					msgDayErr.open();
					txtFromdateDay.setFocus();
					
					return;
				}
				if(txtFromdateMonth.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Month Validation Error!");
					msgDayErr.setMessage("Please enter a Month.");
					msgDayErr.open();
					txtFromdateMonth.setFocus();
					
					return;
				}
				
				if(txtFromdateYear.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Year Validation Error!");
					msgDayErr.setMessage("Please enter a Year.");
					msgDayErr.open();
					txtFromdateYear.setFocus();
					
					return;
				}
		
		
				if(!txtFromdateDay.getText().trim().equals("") && (Integer.valueOf(txtFromdateDay.getText())> 31 || Integer.valueOf(txtFromdateDay.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Date Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Date");
					txtFromdateDay.setText("");
					txtFromdateDay.setFocus();
					msgdateErr.open();
					return;
				}
		
				if(!txtFromdateMonth.getText().trim().equals("") && (Integer.valueOf(txtFromdateMonth.getText())> 12 || Integer.valueOf(txtFromdateMonth.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Month Validation Error!");
					msgdateErr.setMessage("You have entered an invalid month");
					txtFromdateMonth.setText("");
					txtFromdateMonth.setFocus();
					msgdateErr.open();
					return;
				}
		
				if(!txtFromdateYear.getText().trim().equals("") && (Integer.valueOf(txtFromdateYear.getText())> 2100 || Integer.valueOf(txtFromdateYear.getText()) < 1900) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Year Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Year");
					txtFromdateYear.setText("");
					txtFromdateYear.setFocus();
					msgdateErr.open();
					return;
				}
				try {
					Date startDate = sdf.parse(txtFromdateYear.getText()+ "-"+ txtFromdateMonth.getText()+"-"+ txtFromdateDay.getText());
					
					} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
					msg.setText("Error!");
					msg.setMessage("Invalid Date");
					txtFromdateDay.setText("");
					txtFromdateDay.setFocus();
					msg.open();
					return;
				}
		
		
				if(!txtFromdateDay.getText().equals("") && Integer.valueOf ( txtFromdateDay.getText())<10 && txtFromdateDay.getText().length()< txtFromdateMonth.getTextLimit())
				{	
					String dt = txtFromdateDay.getText();
					txtFromdateDay.setText("0"+dt);
					//txtFromDtMonth.setFocus();
					
					//txtDtYOrg.setFocus();
					return;
				}

				
				
			
				
				if(!txtFromdateMonth.getText().equals("") && Integer.valueOf ( txtFromdateMonth.getText())<10 && txtFromdateMonth.getText().length()< txtFromdateMonth.getTextLimit())
				{	
					String dt = txtFromdateMonth.getText();
					txtFromdateMonth.setText("0"+dt);
					//txtFromDtMonth.setFocus();
					
					//txtDtYOrg.setFocus();
					return;
				}	
			
						
				
						
				if(txtTodateDay.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Validation Date Error!");
					msgDayErr.setMessage("Please enter a Date.");
					msgDayErr.open();
					txtTodateDay.setFocus();
					
					return;
				}
				if(txtTodateMonth.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Month Validation Error!");
					msgDayErr.setMessage("Please enter a Month.");
					msgDayErr.open();
					txtTodateMonth.setFocus();
					
					return;
				}
				if(txtTodateYear.getText().trim().equals(""))
				{
					MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR);
					msgDayErr.setText("Year Validation Error!");
					msgDayErr.setMessage("Please enter a Year.");
					msgDayErr.open();
					txtTodateYear.setFocus();
					
					return;
				}
		
				if(!txtTodateDay.getText().trim().equals("") && (Integer.valueOf(txtTodateDay.getText())> 31 || Integer.valueOf(txtTodateDay.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Date Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Date");
					txtTodateDay.setText("");
					txtTodateDay.setFocus();
					msgdateErr.open();
					return;
				}
		
				if(!txtTodateMonth.getText().trim().equals("") && (Integer.valueOf(txtTodateMonth.getText())> 12 || Integer.valueOf(txtTodateMonth.getText()) <= 0) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Month Validation Error!");
					msgdateErr.setMessage("You have entered an invalid month");
					txtTodateMonth.setText("");
					txtTodateMonth.setFocus();
					msgdateErr.open();
					return;
				}

				if(!txtTodateYear.getText().trim().equals("") && (Integer.valueOf(txtTodateYear.getText())> 2100 || Integer.valueOf(txtTodateYear.getText()) < 1900) )
				{
					MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR);
					msgdateErr.setText("Year Validation Error!");
					msgdateErr.setMessage("You have entered an invalid Year");
					txtTodateYear.setText("");
					txtTodateYear.setFocus();
					msgdateErr.open();
					return;
				}
		
				try {
					Date startDate = sdf.parse(txtTodateYear.getText()+ "-"+ txtTodateMonth.getText()+"-"+ txtTodateDay.getText());
					
					} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
					msg.setText("Error!");
					txtTodateDay.setText("");
					msg.setMessage("Invalid Date");
					txtTodateDay.setFocus();
					msg.open();
					return;
				}
		
		
				if(!txtTodateDay.getText().equals("") && Integer.valueOf ( txtTodateDay.getText())<10 && txtTodateDay.getText().length()< txtTodateDay.getTextLimit())
				{	
					String dt = txtTodateDay.getText();
					txtTodateDay.setText("0"+dt);
					//txtFromDtMonth.setFocus();
					
					//txtDtYOrg.setFocus();
					return;
				}

				
				
			
				
				if(!txtTodateMonth.getText().equals("") && Integer.valueOf ( txtTodateMonth.getText())<10 && txtTodateMonth.getText().length()< txtTodateMonth.getTextLimit())
				{	
					String dt = txtTodateMonth.getText();
					txtTodateMonth.setText("0"+dt);
					//txtFromDtMonth.setFocus();
					
					//txtDtYOrg.setFocus();
					return;
				}	
			
				
				
				
				
				
		
				try {
					
					Date OrgStart= sdf.parse(txtFromdateYear.getText() + "-" + txtFromdateMonth.getText() + "-" + txtFromdateDay.getText());
					Date OrgEnd = sdf.parse(txtTodateYear.getText() + "-" + txtTodateMonth.getText() + "-" + txtTodateDay.getText());
					/*if(!txtFromdateYear.getText().trim().equals("") && (Integer.valueOf(txtFromdateYear.getText())< 1900 || Integer.valueOf(txtFromdateYear.getText()) <= 0))
					{
						MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgdateErr.setText("Error!");
						msgdateErr.setMessage(" Please Enter Valid year Date.");
						msgdateErr.open();
					
						
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromdateYear.setText("");
								txtFromdateYear.setFocus();
							}
						});
						return;
						
					}*/
					
					
					
					if(OrgStart.compareTo(OrgEnd)>0)
					{
						MessageBox msg = new MessageBox(new Shell(),SWT.ERROR|SWT.OK | SWT.ICON_ERROR );
						msg.setText("Validation Date Error!");
						msg.setMessage("Please Enter Valid Date");
						txtFromdateDay.setText("");
						txtFromdateDay.setFocus();
						msg.open();
						
						
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageBox msg = new MessageBox(new Shell(),SWT.ERROR|SWT.OK | SWT.ICON_ERROR );
					msg.setText("Date Error!");
					msg.setMessage("Improper Date Format");
					txtFromdateDay.setFocus();
					msg.open();
					return;

				}
					        	
				
				orgName = txtNewOrgName.getText();
				fromDate = txtFromdateDay.getText() + "-"+ txtFromdateMonth.getText() + "-"+ txtFromdateYear.getText();
				toDate = txtTodateDay.getText() + "-"+ txtTodateMonth.getText() + "-"+ txtTodateYear.getText();
				orgType = dropdownOrgType.getItem(dropdownOrgType.getSelectionIndex());
			
				boolean  success =gnukhata.controllers.StartupController.duplicateOrganisation(orgName,fromDate,toDate,orgType);
				
						
						if(success== true)
						{
							MessageBox msgduplicateOrg = new MessageBox(new Shell(), SWT.OK);
							msgduplicateOrg.setText("Warning");
							msgduplicateOrg.setMessage("Duplicate organisation" );
							msgduplicateOrg.open();
							txtNewOrgName.setFocus();
						}
						else if(success== false)
						{
						String[] initialParams = new String[4];
						initialParams[0] = orgName;
						initialParams[1] = fromDate;
						initialParams[2] = toDate;
						initialParams[3] = orgType;
						dispose();
						gnukhata.controllers.StartupController.showInitialSetup(initialParams);
				
						}
			}
		});
		
		txtTodateDay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);d
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		txtTodateMonth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtTodateYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtFromdateDay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR|| arg0.keyCode == SWT.ARROW_LEFT || arg0.keyCode== SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		
		txtFromdateMonth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		txtFromdateYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||arg0.keyCode == SWT.KEYPAD_0||
						arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
						arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.ARROW_LEFT||arg0.keyCode == SWT.ARROW_RIGHT)
				{
					arg0.doit = true;
				}
				else
				{
					
					arg0.doit = false;
				}
			}
		});
		

		txtFromdateMonth.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				super.focusGained(arg0);
				verifyFlag=true;
				if (dropdownOrgType.getSelectionIndex() == 0)
				{
					MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
					msg.setText("Alert!");
					msg.setMessage("Please select the type of your organization.");
					//msg.open();
					//dropdownOrgType.setFocus();
					
					
					return;

					
				}
			}
			public void focusLost(FocusEvent arg0) {
				
				verifyFlag=false;
								
				if(! txtFromdateMonth.getText().equals("") && Integer.valueOf ( txtFromdateMonth.getText())<10 && txtFromdateMonth.getText().length()< txtFromdateMonth.getTextLimit())
				{
					txtFromdateMonth.setText("0"+ txtFromdateMonth.getText());
					return;
				}
				
				
				
			}
			
		});
		
		txtTodateDay.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				super.focusGained(arg0);
												
				verifyFlag=true;
				if (dropdownOrgType.getSelectionIndex() == 0)
				{
					MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
					msg.setText("Alert!");
					msg.setMessage("Please select the type of your organization.");
					//msg.open();
					//dropdownOrgType.setFocus();
					
					
					return;

					
				}
			}
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				super.focusLost(arg0);
				verifyFlag=false;
				if(!txtTodateDay.getText().equals("") && Integer.valueOf ( txtTodateDay.getText())<10 && txtTodateDay.getText().length()< txtTodateDay.getTextLimit())
				{
					txtTodateDay.setText("0"+ txtTodateDay.getText());
				}
				
				
			}
			
		});
		
		txtTodateMonth.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				super.focusGained(arg0);
				verifyFlag=true;
				if (dropdownOrgType.getSelectionIndex() == 0)
				{
					MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
					msg.setText("Alert!");
					msg.setMessage("Please select the type of your organization.");
					//msg.open();
					//dropdownOrgType.setFocus();
					
					return;

					
				}
			}
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				verifyFlag=false;
				if(!txtTodateMonth.getText().equals("") && Integer.valueOf ( txtTodateMonth.getText())<10 && txtTodateMonth.getText().length()< txtTodateMonth.getTextLimit())
				{
					 txtTodateMonth.setText("0"+  txtTodateMonth.getText());
				}
					
			}
			
		});
		
		
		txtFromdateDay.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			//super.focusGained(arg0);
			verifyFlag=true;
		if (dropdownOrgType.getSelectionIndex() == 0)
			{
				MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
				msg.setText("Alert!");
				msg.setMessage("Please select the type of your organization.");
				//msg.open();
				//dropdownOrgType.setFocus();
				
				return;

				
			}
		}
			
			@Override
			
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				verifyFlag=false;
				
				if(!txtFromdateDay.getText().equals("") && Integer.valueOf ( txtFromdateDay.getText())<10 && txtFromdateDay.getText().length()< txtFromdateDay.getTextLimit())
				{
					txtFromdateDay.setText("0"+ txtFromdateDay.getText());
					return;
				}
				
			}
			
		});
		
		
		txtFromdateYear.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				super.focusGained(arg0);
				
				verifyFlag=true;
				if (dropdownOrgType.getSelectionIndex() == 0)
				{
					MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
					msg.setText("Alert!");
					msg.setMessage("Please select the type of your organization.");
					//msg.open();
					//dropdownOrgType.setFocus();
					
					
					return;

					
				}
			}

			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				verifyFlag=false;
			

				
				if(!txtFromdateYear.getText().equals("")&&Integer.valueOf(txtFromdateYear.getText()) > 1900)
				{
					Calendar cal = Calendar.getInstance();
					try {
						cal.set(Integer.valueOf(txtFromdateYear.getText()),( Integer.valueOf(txtFromdateMonth.getText())-1 )  , (Integer.valueOf(txtFromdateDay.getText())-1) );
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cal.add(Calendar.YEAR , 1);
					Date nextYear = cal.getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String FinalDate = sdf.format(nextYear);
										
					try {
						boolean isLeapYear = (((Integer.valueOf(txtFromdateYear.getText())+1) % 4 == 0) && ((Integer.valueOf(txtFromdateYear.getText())+1) % 100 != 0) || ((Integer.valueOf(txtFromdateYear.getText())+1) % 400 == 0));
						
						if (isLeapYear && Integer.valueOf(txtFromdateDay.getText())==1 && Integer.valueOf(txtFromdateMonth.getText())==3)
							{
							txtTodateDay.setText("29");
							}
						else
						    {
							txtTodateDay.setText(FinalDate.substring(0,2) );
							
						    }
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        txtTodateMonth.setText(FinalDate.substring(3,5));
					txtTodateYear.setText(FinalDate.substring(6));
				}
	
			}
					});
		txtTodateYear.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			super.focusGained(arg0);
			verifyFlag=true;
			if (dropdownOrgType.getSelectionIndex() == 0)
			{
				MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR);
				msg.setText("Alert!");
				msg.setMessage("Please select the type of your organization.");
				//msg.open();
				//dropdownOrgType.setFocus();
				
				
				return;

				
			}
		}
			
			
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				//super.focusLost(arg0);
				verifyFlag=false;
							
			
		}
			
		});

		dropdownOrgName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				//code here
				if(arg0.keyCode== SWT.CR || arg0.keyCode == SWT.KEYPAD_CR)
				{
					if(dropdownOrgName.getSelectionIndex()> 0  )
					{					
						dropdownFinancialYear.setFocus();
					return;
					}
				}
				if(arg0.keyCode == SWT.ARROW_UP)
				{
					if(dropdownOrgName.getSelectionIndex()== 0 )
					{
						btnExistingOrg.setFocus();
						lblOrgName.setVisible(false);
						dropdownOrgName.setVisible(false);

						lblFinancialYear.setVisible(false);
						dropdownFinancialYear.setVisible(false);
						btnProceed.setVisible(false);
					}
				}
				long now = System.currentTimeMillis();
				if (now > searchTexttimeout){
			         searchText = "";
			      }
				searchText += Character.toLowerCase(arg0.character);
				searchTexttimeout = now + 1000;					
				for(int i = 0; i < dropdownOrgName.getItemCount(); i++ )
				{
					if(dropdownOrgName.getItem(i).toLowerCase().startsWith(searchText ) ){
						//arg0.doit= false;
						dropdownOrgName.select(i);
						dropdownOrgName.notifyListeners(SWT.Selection ,new Event()  );
						break;
					}
				}
			}
		});
		
		btnExistingOrg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_RIGHT)
				{
					btnCreateOrg.setFocus();
					btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
					//btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.NORMAL));
					
				}
				if(arg0.keyCode==SWT.ARROW_DOWN)
				{
					btnPreferences.setFocus();
				}
			}
		});
		

		btnCreateOrg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				if(arg0.keyCode==SWT.ARROW_LEFT)
				{
					btnExistingOrg.setFocus();
					btnExistingOrg.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
					//btnCreateOrg.setFont(new Font(display, "Times New Roman", 12,SWT.NORMAL));
					
				}
				if(arg0.keyCode==SWT.ARROW_DOWN)
				{
					btnPreferences.setFocus();
				}
			}
		});
		

		btnPreferences.addKeyListener(new KeyAdapter(){
			
			public void keyPressed(KeyEvent args0)
			{
				if(args0.keyCode==SWT.ARROW_UP)
				{
					btnExistingOrg.setFocus();
				}
			}
		});
	}

	public static String toTitleCase(String input) 
	{
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;
	    char second_char=0;
	    String[] seperated_by_space =input.split(" "); 
	    try {
			for(int i= 0; i<seperated_by_space.length; i++)
			{
				if (seperated_by_space[i].length() > 1)
					{
						second_char = seperated_by_space[i].charAt(1);
					} 
					else if (seperated_by_space[i].length() == 1)
					{
						second_char = seperated_by_space[i].charAt(0);
					}
					if (Character.isDigit(second_char))
					{
						for (char c : seperated_by_space[i].toCharArray())
						{

							titleCase.append(c);
						}	
					}
					if (!Character.isAlphabetic(second_char) &&!Character.isDigit(second_char) )
					{
						for (char c : seperated_by_space[i].toCharArray())
						{

							titleCase.append(c);
						}
					}
					if (Character.isLowerCase(second_char))
					{
						for (char c : seperated_by_space[i].toCharArray())
						{
							if (nextTitleCase) {
								c = Character.toTitleCase(c);
								nextTitleCase = false;
							}

							titleCase.append(c);
						}
					} 
					else if (Character.isUpperCase(second_char)) 
					{
						for (char c : seperated_by_space[i].toCharArray())
						{

							titleCase.append(c);
						}
					}
					
				
				
				if (i != seperated_by_space.length - 1) 
				{
					titleCase.append(" ");
					nextTitleCase = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return titleCase.toString();
	}	

	public void makeaccessible(Control c) {
		/*
		 * getAccessible() method is the method of class Controlwhich is the
		 * parent class of all the UI components of SWT including Shell.so when
		 * the shell is made accessible all the controls which are contained by
		 * that shell are made accessible automatically.
		 */
		c.getAccessible();
	}

	protected void checkSubclass() {
		// this is blank method so will disable the check that prevents
		// subclassing of shells.
	}

	private void showView() {
		while (!this.isDisposed()) {
			if (!this.getDisplay().readAndDispatch()) {
				this.getDisplay().sleep();
				if (!this.getMaximized()) {
					this.setMaximized(true);
				}
			}

		}
		this.dispose();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// display = Display.getDefault();
		Display.setAppName("GNUKhata");
		startupForm sf = new startupForm();
	}
}
