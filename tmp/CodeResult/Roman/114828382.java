package gnukhata.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import gnukhata.globals;
import gnukhata.controllers.accountController;
import gnukhata.controllers.transactionController;
import gnukhata.controllers.reportController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Color;
public class ViewLedger extends Composite{
	
	Color Background;
	Color Foreground;
	Color FocusBackground;
	Color FocusForeground;
	Color BtnFocusForeground;

	static Display display;
	String strOrgName;
	String strFromYear;
	String strToYear;
	String strype;
	String accountName;
	String fromDate;
	String toDate;
	String searchText = "";
	long searchTexttimeout = 0;
	Color bgbtnColor;
	Color fgbtnColor;
	Color bgcomboColor;
	Color fgcomboColor;
	Color bgtxtColor;
	Color fgtxtColor;
	String[] financialYear;
	
	Label lblorgName;
	Label lblAccountName;
	Combo dropdownAccountName;
	Combo dropdownFinancialYear;
	
	Label lblPeriod;
	Label lblFromDt;
	Label lblToDt;
	Label lblFinancialYear;
	
	Text txtFromDtDay;
	Text txtToDtDay;
	Text txtFromDtMonth;
	Text txtToDtMonth;
	Text txtFromDtYear;
	Text txtToDtYear;
	
	Label lblFromDash1;
	Label lblFromDash2;
	Label lblToDash1;
	Label lblToDash2;
	Label lblFeatures;
	Label lblLogo;
	Label lblLink ;
	Label lblLine;
	Label lblledgerTitle;
	Label lblSelectProject;
	String oldaccName;
	String oldfromdate;
	String oldenddate;
	String oldprojectName;
	String oldselectproject;
	boolean narration;
	boolean tbflag;
	boolean projectflag;
	boolean ledgerflag;
	
	Combo dropdownProjectName;
	Button btnView;
	Button btnCheck;
	boolean dualflag=false;
	String ProjectName;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	long wait=0;
	String tb;
		
		public ViewLedger(Composite parent, int style,String accname,String startDate,String endDate,String ledgerProject,boolean narrationflag1,boolean tbDrilldown,boolean psDrilldown,String tbtype,String projectName,boolean dualledgerflag) {
			super(parent,style);
		// TODO Auto-generated constructor stub
			sdf.setLenient(false);
			strOrgName = globals.session[1].toString();
			strFromYear =  globals.session[2].toString();
			strToYear =  globals.session[3].toString();
			
			oldaccName=accname;
			oldfromdate=startDate;
			oldenddate=endDate;
			oldprojectName=ledgerProject;
			oldselectproject=projectName;
			narration=narrationflag1;
			tbflag=tbDrilldown;
			projectflag=psDrilldown;
			tb=tbtype;
			
			dualflag=dualledgerflag;
			
			
			FormLayout formLayout= new FormLayout();
			this.setLayout(formLayout);
		    FormData layout=new FormData();
		    MainShell.lblLogo.setVisible(false);
			MainShell.lblLine.setVisible(false);
			MainShell.lblOrgDetails.setVisible(false);
			    
		  
			Label lblLogo = new Label(this, SWT.None);
			layout = new FormData();
			layout.top = new FormAttachment(1);
			layout.left = new FormAttachment(63);
			layout.right = new FormAttachment(87);
			layout.bottom = new FormAttachment(9);
			//layout.right = new FormAttachment(95);
			//layout.bottom = new FormAttachment(18);
			//lblLogo.setSize(getClientArea().width, getClientArea().height);
			lblLogo.setLocation(getClientArea().width, getClientArea().height);
			lblLogo.setLayoutData(layout);
			//Image img = new Image(display,"finallogo1.png");
			lblLogo.setImage(globals.logo);
			
			Label lblOrgDetails = new Label(this,SWT.NONE);
			lblOrgDetails.setFont( new Font(display,"Times New Roman", 11, SWT.BOLD ) );
			lblOrgDetails.setText(globals.session[1]+"\n"+"For Financial Year "+"From "+globals.session[2]+" To "+globals.session[3] );
			layout = new FormData();
			layout.top = new FormAttachment(2);
			layout.left = new FormAttachment(2);
			//layout.right = new FormAttachment(53);
			//layout.bottom = new FormAttachment(18);
			lblOrgDetails.setLayoutData(layout);

			/*Label lblLink = new Label(this,SWT.None);
			lblLink.setText("www.gnukhata.org");
			lblLink.setFont(new Font(display, "Times New Roman", 11, SWT.ITALIC));
			layout = new FormData();
			layout.top = new FormAttachment(lblLogo,0);
			layout.left = new FormAttachment(65);
			//layout.right = new FormAttachment(33);
			//layout.bottom = new FormAttachment(19);
			lblLink.setLayoutData(layout);*/
			 
			Label lblLine = new Label(this,SWT.NONE);
			lblLine.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			lblLine.setFont(new Font(display, "Times New Roman",18, SWT.ITALIC));
			layout = new FormData();
			layout.top = new FormAttachment( lblLogo , 2);
			layout.left = new FormAttachment(2);
			layout.right = new FormAttachment(99);
			layout.bottom = new FormAttachment(22);
			lblLine.setLayoutData(layout);
			

			lblledgerTitle=new Label(this,SWT.NONE);
			if(dualledgerflag)
			{
			lblledgerTitle.setText("View Dual Ledger");
			}
			else
			{
				lblledgerTitle.setText("View Ledger");
				
			}
			lblledgerTitle.setFont(new Font(display, "Times New Roman", 16, SWT.BOLD));
			layout = new FormData();
			layout.top = new FormAttachment(23);
			layout.left = new FormAttachment(45);
			lblledgerTitle.setLayoutData(layout);
		
			lblAccountName= new Label(this,SWT.NONE);
			lblAccountName.setText("A&ccount Name : ");
			lblAccountName.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
			layout=new FormData();
			layout.top=new FormAttachment(lblledgerTitle,40);
			layout.left=new FormAttachment(36);
			lblAccountName.setLayoutData(layout);
			
			dropdownAccountName = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
			dropdownAccountName.setFont(new Font(display,"Times New Roman",11,SWT.NORMAL));
			layout = new FormData();
			layout.top = new FormAttachment(lblledgerTitle,40);
			layout.left = new FormAttachment(lblAccountName,7);
			layout.right = new FormAttachment(69);
			layout.bottom = new FormAttachment(15);
			dropdownAccountName.add("  -------------------Please select-------------------  ");
			dropdownAccountName.setLayoutData(layout);
			dropdownAccountName.setFocus();
			dropdownAccountName.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			dropdownAccountName.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
			lblPeriod=new Label(this, SWT.NONE);
			lblPeriod.setText("Period :");
			lblPeriod.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
			layout=new FormData();
			layout.top=new FormAttachment(lblAccountName,31);
			layout.left=new FormAttachment(36);
			lblPeriod.setLayoutData(layout);
		
			lblFromDt=new Label(this, SWT.NONE);
			lblFromDt.setText("&From :");
			lblFromDt.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
			layout=new FormData();
			layout.top =new FormAttachment(lblPeriod, 15);
			layout.left=new FormAttachment(41);		
			lblFromDt.setLayoutData(layout);
		    
			txtFromDtDay=new Text(this, SWT.BORDER);
			txtFromDtDay.setMessage("dd");
			txtFromDtDay.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtFromDtDay.setText(strFromYear.substring(0,2));	
			txtFromDtDay.setTextLimit(2);
			layout = new FormData();
			layout.top=new FormAttachment(lblPeriod, 5);
			layout.left = new FormAttachment(lblFromDt, 3);
			txtFromDtDay.setLayoutData(layout);
			txtFromDtDay.setVisible(true);
			txtFromDtDay.setEnabled(false);
					
			lblFromDash1 = new Label(this, SWT.NONE);
			lblFromDash1.setText("-");
			lblFromDash1.setFont(new Font(display,"Times New Roman",14,SWT.NORMAL));
			layout = new FormData();
			layout.left = new FormAttachment(txtFromDtDay,2);
			layout.top = new FormAttachment(lblPeriod, 5);
			lblFromDash1.setLayoutData(layout);
			lblFromDash1.setVisible(true);
			
			txtFromDtMonth=new Text(this, SWT.BORDER);
			txtFromDtMonth.setMessage("mm");
			txtFromDtMonth.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtFromDtMonth.setText(strFromYear.substring(3,5));
			txtFromDtMonth.setTextLimit(2);
			layout = new FormData();
			layout.top=new FormAttachment(lblPeriod, 5);
			layout.left = new FormAttachment(lblFromDash1, 3);
			txtFromDtDay.setTabs(1);
			txtFromDtMonth.setLayoutData(layout);
			txtFromDtMonth.setVisible(true);
			txtFromDtMonth.setEnabled(false);
			
			
			lblFromDash2 = new Label(this, SWT.NONE);
			lblFromDash2.setText("-");
			lblFromDash2.setFont(new Font(display,"Times New Roman",14,SWT.NORMAL));
			layout = new FormData();
			layout.left = new FormAttachment(txtFromDtMonth,3);
			layout.top = new FormAttachment(lblPeriod, 5);
			lblFromDash2.setLayoutData(layout);
			lblFromDash2.setVisible(true);
			
			
			txtFromDtYear=new Text(this, SWT.BORDER);
			txtFromDtYear.setMessage("yyyy");
			txtFromDtYear.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtFromDtYear.setTextLimit(4);
			txtFromDtYear.setText(strFromYear.substring(6));
			layout = new FormData();
			layout.top=new FormAttachment(lblPeriod, 5);
			layout.left = new FormAttachment(lblFromDash2, 3);
			txtFromDtYear.setLayoutData(layout);
			txtFromDtYear.setEnabled(false);
			
			lblToDt=new Label(this, SWT.NONE);
			lblToDt.setText("T&o      :");
			lblToDt.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
			layout=new FormData();
			layout.top =new FormAttachment(lblFromDt, 14);
			layout.left=new FormAttachment(41);
			lblToDt.setLayoutData(layout);
			
			txtToDtDay=new Text(this, SWT.BORDER);
			txtToDtDay.setMessage("dd");
			txtToDtDay.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtToDtDay.setText(strToYear.substring(0,2));
			txtToDtDay.setTextLimit(2);
			layout = new FormData();
			layout.top=new FormAttachment(lblFromDt, 14);
			layout.left = new FormAttachment(lblToDt, 4);
			txtToDtDay.setLayoutData(layout);
			txtToDtDay.setVisible(true);
			txtToDtDay.setEnabled(false);
			
			lblToDash1 = new Label(this, SWT.NONE);
			lblToDash1.setText("-");
			lblToDash1.setFont(new Font(display, "Time New Roman", 14, SWT.NORMAL));
			layout = new FormData();
			layout.left = new FormAttachment(txtToDtDay,2);
			layout.top = new FormAttachment(lblFromDt, 14);
			lblToDash1.setLayoutData(layout);
			lblToDash1.setVisible(true);
			
			txtToDtMonth=new Text(this, SWT.BORDER);
			txtToDtMonth.setMessage("mm");
			txtToDtMonth.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtToDtMonth.setText(strToYear.substring(3,5));
			txtToDtMonth.setTextLimit(2);
			layout = new FormData();
			layout.top=new FormAttachment(lblFromDt, 14);
			layout.left = new FormAttachment(lblFromDash1, 3);
			txtToDtMonth.setLayoutData(layout);
			txtToDtMonth.setVisible(true);
			txtToDtMonth.setEnabled(false);
			
			
			lblToDash2 = new Label(this, SWT.NONE);
			lblToDash2.setText("-");
			lblToDash2.setFont(new Font(display, "Time New Roman", 14, SWT.NORMAL));
			layout = new FormData();
			layout.left = new FormAttachment(txtToDtMonth,3);
			layout.top = new FormAttachment(lblFromDt, 14);
			lblToDash2.setLayoutData(layout);
			lblToDash2.setVisible(true);
			
			
			txtToDtYear=new Text(this, SWT.BORDER);
			txtToDtYear.setMessage("yyyy");
			txtToDtYear.setFont(new Font(display,"Times New Roman",10,SWT.NORMAL));
			txtToDtYear.setText(strToYear.substring(6));	
			txtToDtYear.setTextLimit(4);
			layout = new FormData();
			layout.top=new FormAttachment(lblFromDt, 14);
			layout.left = new FormAttachment(lblToDash2, 3);
			txtToDtYear.setLayoutData(layout);
			txtToDtYear.setVisible(true);
			txtToDtYear.setEnabled(false);
			
			btnCheck=new Button(this, SWT.CHECK);
			btnCheck.setText("&With Narration");
			btnCheck.setFont(new Font(display,"Times New Roman",12,SWT.BOLD));
			layout = new FormData();
			layout.top=new FormAttachment(lblToDt, 19);
			layout.left = new FormAttachment(46);
			btnCheck.setLayoutData(layout);
			btnCheck.setVisible(true);
			
			lblSelectProject= new Label(this,SWT.NONE);
			lblSelectProject.setText("&Select Projects  : ");
			lblSelectProject.setFont(new Font(display, "Times New Roman", 12, SWT.BOLD));
			layout=new FormData();
			layout.top=new FormAttachment(btnCheck, 34);
			layout.left=new FormAttachment(36);
			lblSelectProject.setLayoutData(layout);
			
			dropdownProjectName= new Combo(this, SWT.DROP_DOWN| SWT.READ_ONLY);
			dropdownProjectName.setFont(new Font(display,"Times New Roman",11,SWT.NORMAL));
			layout=new FormData();
			layout.top=new FormAttachment(btnCheck, 34);
			layout.left=new FormAttachment(lblSelectProject,11);
			layout.right=new FormAttachment(66);
			//layout.bottom=new FormAttachment(45);
			dropdownProjectName.setLayoutData(layout);
		    dropdownProjectName.setVisible(true);
			dropdownProjectName.add("No Project");
			
			if(dropdownProjectName.getSelectionIndex()==-1)
			{
				dropdownProjectName.setVisible(false);
				lblSelectProject.setVisible(false);
			}
			
			String[] allProjects = gnukhata.controllers.transactionController.getAllProjects();
			for (int i = 0; i < allProjects.length; i++ )
			{
				lblSelectProject.setVisible(true);
				dropdownProjectName.setVisible(true);
				dropdownProjectName.add(allProjects[i]);
			}
			dropdownProjectName.select(0);
		
			
			btnView= new Button(this, SWT.PUSH);
			btnView.setText(" &View ");
			btnView.setFont(new Font(display, "Times New Roman", 12,SWT.BOLD));
			btnView.setEnabled(false);
			layout = new FormData();
			layout.top=new FormAttachment(dropdownProjectName, 34);
			layout.left = new FormAttachment(49);
			layout.right = new FormAttachment(56);
			
			
			
			
			btnView.setLayoutData(layout);
			String[] allAccounts = gnukhata.controllers.accountController.getAllAccounts();
			
			

			for (int i = 0; i < allAccounts.length; i++ )
			{
				dropdownAccountName.add(allAccounts[i]);
	
			}
			dropdownAccountName.select(0);
	
			this.getAccessible();
			this.pack();
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
	        
			this.setEvents();
			 dropdownAccountName.setBackground(FocusBackground);
			 dropdownAccountName.setForeground(FocusForeground);

	}
		
		private void setEvents()
		{
			
			
			dropdownAccountName.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (dropdownAccountName.getSelectionIndex() ==0)
					{
						txtFromDtYear.setEnabled(false);
						txtFromDtMonth.setEnabled(false);
						txtFromDtDay.setEnabled(false);
						txtToDtDay.setEnabled(false);
						txtToDtMonth.setEnabled(false);
						txtToDtYear.setEnabled(false);
						btnView.setEnabled(false);
						return;
					}
					else
					{
						txtFromDtYear.setEnabled(true);
						txtFromDtMonth.setEnabled(true);
						txtFromDtDay.setEnabled(true);
						txtToDtDay.setEnabled(true);
						txtToDtMonth.setEnabled(true);
						txtToDtYear.setEnabled(true);
						
						btnView.setEnabled(true);
					}
					
					
					}
					

				
			});
			
			dropdownAccountName.addKeyListener(new KeyAdapter() {
			
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{
						
						if (dropdownAccountName.getSelectionIndex() == 0)
						{
							//dropdownAccountName.setFocus();
							MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
							msg.setText("Error!");
							msg.setMessage("Please select the Account Name.");
							msg.open();
							dropdownAccountName.setFocus();
						
							btnView.setEnabled(false);
							return;
						}
						txtFromDtDay.setFocus();
						txtFromDtDay.setSelection(0,2);
						btnView.setEnabled(true);
					}
					
					
				}
				});
			
			
			
			
			dropdownAccountName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					//code here
					if(arg0.keyCode== SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{
						dropdownAccountName.notifyListeners(SWT.Selection ,new Event()  );
						//dropdownFinancialYear.setFocus();
						return;
					}
					/*if(!Character.isLetterOrDigit(arg0.character) )
					{
						return;
					}
					
					*/
					long now = System.currentTimeMillis();
					if (now > searchTexttimeout)
					{
				         searchText = "";
				      }
					searchText += Character.toLowerCase(arg0.character);
					searchTexttimeout = now + 500;
					
					for(int i = 0; i < dropdownAccountName.getItemCount(); i++ )
					{
						if(dropdownAccountName.getItem(i).toLowerCase().startsWith(searchText ) ){
							//arg0.doit= false;
							dropdownAccountName.select(i);
							break;
						}
					}
				}
			});
			
				txtFromDtDay.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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
			
			txtFromDtMonth.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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
			
			
			txtFromDtYear.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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

			txtToDtDay.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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
			
			
			txtToDtMonth.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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
			
			
			txtToDtYear.addVerifyListener(new VerifyListener() {
				
				@Override
				public void verifyText(VerifyEvent arg0) {
					// TODO Auto-generated method stub
//					/*if(verifyFlag== false)
//					{
//						arg0.doit= true;
//						return;
//					}*/
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
			
			
			txtFromDtDay.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					
					if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
					{
						
						//txtDtDOrg.traverse(SWT.TRAVERSE_TAB_NEXT);
						txtFromDtMonth.setFocus();
					}
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						dropdownAccountName.setFocus();
					}
				

				}
			});
			
			txtFromDtDay.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{
					if(!txtFromDtDay.getText().equals("") && Integer.valueOf ( txtFromDtDay.getText())<10 && txtFromDtDay.getText().length()< txtFromDtDay.getTextLimit())
					{
						txtFromDtDay.setText("0"+ txtFromDtDay.getText());
						//txtFromDtMonth.setFocus();
						txtFromDtDay.setFocus();
						return;
						
						
						
					}
					
					}
					if(arg0.keyCode ==SWT.TAB)
					{
						if(txtFromDtDay.getText().equals(""))
						{
							txtFromDtDay.setText("");
							Display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									txtFromDtDay.setFocus();
								}
							});
							return;
						}
					}
					

				}
			});
			
			
			txtFromDtMonth.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{
					if(!	txtFromDtMonth.getText().equals("") && Integer.valueOf ( 	txtFromDtMonth.getText())<10 && 	txtFromDtMonth.getText().length()< 	txtFromDtMonth.getTextLimit())
					{
						txtFromDtMonth.setText("0"+ txtFromDtMonth.getText());
						//txtFromDtMonth.setFocus();
						
						txtFromDtYear.setFocus();
						return;
						
						
						
					}
					else
					{
						txtFromDtYear.setFocus();
					}
					}
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						txtFromDtDay.setFocus();
					}
					

				}
			});

			txtFromDtYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
			
				if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
				{
					
						txtToDtDay.setFocus();
					
				}
					
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtFromDtMonth.setFocus();
				}
			

			}
		});
		

		
		
		txtToDtDay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//super.keyPressed(arg0);
				
				if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
				{
				if(!	txtToDtDay.getText().equals("") && Integer.valueOf ( txtToDtDay.getText())<10 && 	txtToDtDay.getText().length()< 	txtToDtDay.getTextLimit())
				{
					txtToDtDay.setText("0"+ txtToDtDay.getText());
					//txtFromDtMonth.setFocus();
					txtToDtMonth.setFocus();
					return;
					
					
					
				}
				else
				{
					txtToDtMonth.setFocus();
					
				}
				}
				if(arg0.keyCode==SWT.ARROW_UP)
				{
					txtFromDtYear.setFocus();
				}
				
				
			}
		});
			
			txtToDtMonth.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					
					if(arg0.keyCode ==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{
					if(!	txtToDtMonth.getText().equals("") && Integer.valueOf ( txtToDtMonth.getText())<10 && 	txtToDtMonth.getText().length()< 	txtToDtMonth.getTextLimit())
					{
						txtToDtMonth.setText("0"+ txtToDtMonth.getText());
						//txtFromDtMonth.setFocus();
						txtToDtYear.setFocus();
						return;
						
						
						
					}
					else
					{
						txtToDtYear.setFocus();
					}
					}
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						txtToDtDay.setFocus();
					}

				}
			});
			
			txtToDtYear.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					
					if(arg0.keyCode==SWT.CR | arg0.keyCode==SWT.KEYPAD_CR)
					{
						if(txtToDtYear.getText().trim().equals(""))
						{
							
							txtToDtYear.setFocus();
							return;
						}
						
						if(! txtToDtYear.getText().trim().equals(""))
						{
                         
									// TODO Auto-generated method stub
										
									display.getCurrent().asyncExec(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											btnCheck.setFocus();	
									
										}
									});
									
						
                                    							
							
						}
						/*if(txtToDtYear.getText().trim().equals(""))
						{
							
							
							Display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									txtToDtYear.setFocus();							
								}
							});
							return;
							
						}
						
					
				*/	}
				
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						txtToDtMonth.selectAll();
						txtToDtMonth.setFocus();
					}
				

				}
			});
			

			
			btnCheck.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode==SWT.CR||arg0.keyCode == SWT.KEYPAD_CR)
					{	
						if(dropdownProjectName.isVisible())
						{
							dropdownProjectName.setFocus();
						}
						else
						{
							//btnView.setFocus();
						
							btnView.notifyListeners(SWT.Selection, new Event());
							
						}
					}
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						txtToDtYear.setFocus();
					}
				}
			});
			
			dropdownProjectName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					//code here
					if(arg0.keyCode== SWT.CR || arg0.keyCode == SWT.KEYPAD_CR)
					{
						if(dropdownProjectName.getSelectionIndex()> 0  )
						{
						
						btnView.setFocus();
						return;
						}
					}
					long now = System.currentTimeMillis();
					if (now > searchTexttimeout){
				         searchText = "";
				      }
					searchText += Character.toLowerCase(arg0.character);
					searchTexttimeout = now + 1000;					
					for(int i = 0; i < dropdownProjectName.getItemCount(); i++ )
					{
						if(dropdownProjectName.getItem(i).toLowerCase().startsWith(searchText ) ){
							//arg0.doit= false;
							dropdownProjectName.select(i);
							dropdownProjectName.notifyListeners(SWT.Selection ,new Event()  );
							break;
						}
					}
				}
			});
			
			
			dropdownProjectName.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode ==SWT.CR)
					{
						
						if (dropdownProjectName.getSelectionIndex() == 0)
						{
							MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
							msg.setText("Error!");
							msg.setMessage("Please select the Project name.");
							//msg.open();
							btnView.notifyListeners(SWT.Selection ,new Event() );
							//dropdownProjectName.setFocus();
							return;
						}
						//btnView.setFocus();
						btnView.notifyListeners(SWT.Selection ,new Event() );
					}
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						if(dropdownProjectName.getSelectionIndex()<=1)
						{
						btnCheck.setFocus();
						}
					}
					
				}
				});
			
			btnView.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if(arg0.keyCode==SWT.ARROW_UP)
					{
						if(dropdownProjectName.isVisible())
						{
							dropdownProjectName.setFocus();
						}
						else
						{
							btnCheck.setFocus();
						}
					}
				}
			});
			btnView.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					//super.widgetSelected(arg0);
					//String dualledgerflag=btnView.getData("dualflag").toString();
					try {
						Date startDate = sdf.parse(txtFromDtYear.getText()+ "-"+ txtFromDtMonth.getText()+"-"+ txtFromDtDay.getText());
						
						} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Invalid Date");
						txtFromDtDay.setFocus();
						msg.open();
						return;
					}
					
					try {
						Date startDate = sdf.parse(txtToDtYear.getText()+ "-"+ txtToDtMonth.getText()+"-"+ txtToDtDay.getText());
						
						} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MessageBox msg = new MessageBox(new Shell(),SWT.OK | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Invalid Date");
						txtToDtDay.setFocus();
						msg.open();
						return;
					}
					
					if(txtFromDtDay.getText().trim().equals("")&&txtFromDtMonth.getText().trim().equals("")&&txtFromDtYear.getText().trim().equals("")&&txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals("")||txtFromDtDay.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtFromDtDay.setFocus();
						
						return;
					}
					
					if(txtFromDtMonth.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtFromDtMonth.setFocus();
						
						return;
					}
					
					
					if(txtFromDtYear.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtFromDtYear.setFocus();
						
						return;
					}
					
					
					if(txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals("")||txtToDtDay.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtToDtDay.setFocus();
						
						return;
					}
					if(txtToDtDay.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtToDtDay.setFocus();
						
						return;
					}
					if(txtToDtMonth.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtToDtMonth.setFocus();
						
						return;
					}
					if(txtToDtYear.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						txtToDtYear.setFocus();
						
						return;
					}
					
					if(dropdownAccountName.getSelectionIndex()<=0 )
						
					{
						MessageBox err = new MessageBox(new Shell(),SWT.ERROR|SWT.OK |SWT.ICON_ERROR);
						err.setText("Error!");
						err.setMessage("Please select an account name for seeing it's ledger report");
						err.open();
						dropdownAccountName.setFocus();
						return;
					}
					
					
					if(dualflag==false)
						//make a call to the reportController.getLedger()
					
						{
							
							Composite grandParent = (Composite) btnView.getParent().getParent();
							//MessageBox msg = new MessageBox(new Shell(), SWT.OK);
							//msg.setMessage(grandParent.getText());
							//msg.open();
							String fromDate = txtFromDtYear.getText() + "-" + txtFromDtMonth.getText() + "-" + txtFromDtDay.getText();
							String toDate = txtToDtYear.getText() + "-" + txtToDtMonth.getText() + "-" + txtToDtDay.getText();
							try {
								Date LedgerStart = sdf.parse(txtFromDtYear.getText()+ "-"+ txtFromDtMonth.getText()+"-"+ txtFromDtDay.getText() );
								Date LedgerEnd = sdf.parse(txtToDtYear.getText()+ "-"+ txtToDtMonth.getText()+"-"+ txtToDtDay.getText() );
								
								Date financialStart = sdf.parse(globals.session[2].toString().substring(6) +"-"+globals.session[2].toString().substring(3,5)+"-"+ globals.session[2].toString().substring(0,2));
								Date financialEnd = sdf.parse(globals.session[3].toString().substring(6) +"-"+globals.session[3].toString().substring(3,5)+"-"+ globals.session[3].toString().substring(0,2));
								if(LedgerStart.compareTo(LedgerEnd)>0 || LedgerStart.compareTo(financialStart)<0 || LedgerEnd.compareTo(financialEnd)>0 )
								{
									MessageBox msg = new MessageBox(new Shell(),SWT.ERROR|SWT.OK |SWT.ICON_ERROR );
									msg.setText("Validation Date Error!");
									msg.setMessage("Invalid Date");
									msg.open();
									txtToDtYear.setFocus();
									txtFromDtDay.selectAll();
									//txtFromDtDay.setFocus();

									return;
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String ProjectName=dropdownProjectName.getItem(dropdownProjectName.getSelectionIndex());
							if(dropdownProjectName.isEnabled()==true)
							{
								ProjectName=dropdownProjectName.getItem(dropdownProjectName.getSelectionIndex());
							}
							if(dropdownProjectName.isEnabled()==false)
							{
								ProjectName="";
							}
					
							String accountname =  dropdownAccountName.getItem(dropdownAccountName.getSelectionIndex());
							boolean narrationFlag=btnCheck.getSelection();
					
					/*MessageBox msg=new MessageBox(new Shell(),SWT.OK);
					msg.setMessage("Project name"+projectName);
					msg.open();*/
					
				//ViewLedger(Composite parent,String accnamesent,String fromDate,String enddate,String projectName,boolean narrationflag1,boolean tbDrilldown,boolean psDrilldown,String tbtype,String selectProject,boolean dualledgerflag, int style) {

					//dispose();
					gnukhata.controllers.reportController.showLedger(grandParent, accountname, fromDate, toDate, ProjectName, narrationFlag, false, false, "", "");
						btnView.getParent().dispose();
				//	dispose();
					
						}
					if(dualflag==true)
					{
						
						
						Composite grandParent = (Composite) btnView.getParent().getParent();
						//MessageBox msg = new MessageBox(new Shell(), SWT.OK);
						//msg.setMessage(grandParent.getText());
						//msg.open();
						String fromDate1= txtFromDtYear.getText() + "-" + txtFromDtMonth.getText() + "-" + txtFromDtDay.getText();
						String toDate1 = txtToDtYear.getText() + "-" + txtToDtMonth.getText() + "-" + txtToDtDay.getText();
						String ProjectName1=dropdownProjectName.getItem(dropdownProjectName.getSelectionIndex());
						if(dropdownProjectName.isEnabled()==true)
						{
							ProjectName1=dropdownProjectName.getItem(dropdownProjectName.getSelectionIndex());
						}
						if(dropdownProjectName.isEnabled()==false)
						{
							ProjectName1="";
						}
				
						String accountname1 =  dropdownAccountName.getItem(dropdownAccountName.getSelectionIndex());
						boolean narrationFlag=btnCheck.getSelection();
					
						
							
						gnukhata.controllers.reportController.showDualLedger(grandParent, accountname1,oldaccName, fromDate1,oldfromdate, toDate1,oldenddate, ProjectName1,oldprojectName, narrationFlag,narration, false,false, false,false, "","","", "",true,true);
						
						//gnukhata.controllers.reportController.showLedger(grandParent, accountname, fromDate, toDate, ProjectName, narrationFlag, false, false, "", "",true);
						btnView.getParent().dispose();

				
					}
				}
			});
			
			
			
			//date validations
			
			txtFromDtDay.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(
						KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			txtToDtDay.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
						
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			txtToDtMonth.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			
			txtToDtYear.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			txtFromDtMonth.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			txtFromDtYear.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					//super.keyPressed(arg0);
					if((arg0.keyCode>= 48 && arg0.keyCode <= 57) ||  arg0.keyCode== 8 || arg0.keyCode == 13||
							arg0.keyCode == SWT.KEYPAD_0||arg0.keyCode == SWT.KEYPAD_1||arg0.keyCode == SWT.KEYPAD_2||arg0.keyCode == SWT.KEYPAD_3||arg0.keyCode == SWT.KEYPAD_4||
							arg0.keyCode == SWT.KEYPAD_5||arg0.keyCode == SWT.KEYPAD_6||arg0.keyCode == SWT.KEYPAD_7||arg0.keyCode == SWT.KEYPAD_8||arg0.keyCode == SWT.KEYPAD_9||arg0.keyCode == SWT.KEYPAD_CR
							||arg0.keyCode == SWT.ARROW_RIGHT||arg0.keyCode == SWT.ARROW_LEFT)
					{
						arg0.doit = true;
					}
					else
					{
						
						arg0.doit = false;
					}
				}
			});
			
			txtFromDtMonth.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
				
					txtFromDtMonth.clearSelection();
					if(!txtFromDtMonth.getText().equals("") && (Integer.valueOf(txtFromDtMonth.getText())> 12 || Integer.valueOf(txtFromDtMonth.getText()) <= 0))
					{
						MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
						msgdateErr.setText("Error!");
						msgdateErr.setMessage("You have entered an Invalid Month, please enter it in mm format.");
						msgdateErr.open();
						
						
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromDtMonth.setText("");
								txtFromDtMonth.setFocus();
								
							}
						});
						return;
						
					}
					if(! txtFromDtMonth.getText().equals("") && Integer.valueOf ( txtFromDtMonth.getText())<10 && txtFromDtMonth.getText().length()< txtFromDtMonth.getTextLimit())
					{
						txtFromDtMonth.setText("0"+ txtFromDtMonth.getText());
						return;
					}
					
					
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
					txtFromDtMonth.clearSelection();
					if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select an Account Name ");
						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});
			
			txtToDtDay.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					if(!txtToDtDay.getText().equals("") && Integer.valueOf ( txtToDtDay.getText())<10 && txtToDtDay.getText().length()< txtToDtDay.getTextLimit())
					{
						txtToDtDay.setText("0"+ txtToDtDay.getText());
					}
					
					if(!txtToDtDay.getText().equals("") && (Integer.valueOf(txtToDtDay.getText())> 31 || Integer.valueOf(txtToDtDay.getText()) <= 0) )
					{
						MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
						msgdateErr.setText("Validation Date Error!");
						msgdateErr.setMessage("You have entered an Invalid Date");
						msgdateErr.open();
						
						txtToDtDay.setText("");
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtToDtDay.setFocus();
								
							}
						});	
						return;
					}
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
					if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR |SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select Account Name ");
						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});
			
			txtToDtMonth.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					if(!txtToDtMonth.getText().equals("") && Integer.valueOf ( txtToDtMonth.getText())<10 && txtToDtMonth.getText().length()< txtToDtMonth.getTextLimit())
					{
						 txtToDtMonth.setText("0"+  txtToDtMonth.getText());
					}
					if(!txtToDtMonth.getText().equals("") && (Integer.valueOf(txtToDtMonth.getText())> 12 || Integer.valueOf(txtToDtMonth.getText()) <= 0) )
					{
						MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgdateErr.setText("Validation Month Error!");
						msgdateErr.setMessage("You have entered an Invalid Month");
						msgdateErr.open();
						
						txtToDtMonth.setText("");
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtToDtMonth.setFocus();
								
							}
						});
						return;
					}
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
					if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select Account Name ");
						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});
			
			
			txtFromDtDay.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					//txtFromDtDay.clearSelection();
				if(!txtFromDtDay.getText().equals("") && (Integer.valueOf(txtFromDtDay.getText())> 31 || Integer.valueOf(txtFromDtDay.getText()) <= 0) )
					{
						MessageBox msgdateErr = new MessageBox(new Shell(), SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgdateErr.setText("Validation Date Error!");
						msgdateErr.setMessage("You have entered an Invalid Date");
						msgdateErr.open();
						
						txtFromDtDay.setText("");
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromDtDay.setFocus();							
							}
						});
						return;
					}
					if(!txtFromDtDay.getText().equals("") && Integer.valueOf ( txtFromDtDay.getText())<10 && txtFromDtDay.getText().length()< txtFromDtDay.getTextLimit())
					{
						txtFromDtDay.setText("0"+ txtFromDtDay.getText());
						return;
					}
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
				if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select Account Name ");
						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});
			
			
			txtFromDtYear.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					
				if(!txtFromDtYear.getText().trim().equals("") && Integer.valueOf(txtFromDtYear.getText()) < 1900)
							{
						MessageBox msgbox = new MessageBox(new Shell(), SWT.OK |SWT.ERROR |SWT.ICON_ERROR);
						msgbox.setText("Validation Year Error!");
						msgbox.setMessage("You have entered an Invalid Year");
						msgbox.open();
					
						txtFromDtYear.setText("");
						Display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromDtYear.setFocus();
								txtFromDtYear.selectAll();
								
							}
						});
						return;
					}
					if(txtFromDtDay.getText().trim().equals("")&&txtFromDtMonth.getText().trim().equals("")&&txtFromDtYear.getText().trim().equals("")&&txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals("")||txtFromDtDay.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
						txtFromDtDay.setFocus();
							}
						});
						return;
					}
					if(txtFromDtMonth.getText().trim().equals(""))
					{
						MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
						msgDayErr.setText("Validation Date Error!");
						msgDayErr.setMessage("Please enter a valid Date.");
						msgDayErr.open();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
						txtFromDtMonth.setFocus();
							}
						});
						return;
					}
					

					if(!txtFromDtYear.getText().equals("")&&Integer.valueOf(txtFromDtYear.getText()) > 1900)
					{
						Calendar cal = Calendar.getInstance();
						try {
							cal.set(Integer.valueOf(txtFromDtYear.getText()),( Integer.valueOf(txtFromDtMonth.getText())-1 )  , (Integer.valueOf(txtFromDtDay.getText())-1) );
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cal.add(Calendar.YEAR , 1);
						Date nextYear = cal.getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String FinalDate = sdf.format(nextYear);
						
						txtToDtDay.setText(FinalDate.substring(0,2) );
						txtToDtMonth.setText(FinalDate.substring(3,5));
						txtToDtYear.setText(FinalDate.substring(6));
						
					}
					
					
					
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
				if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR | SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select Account Name ");						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});
			
			
				

			txtToDtYear.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
				try {
						if (!txtToDtYear.getText().equals("")&&Integer.valueOf(txtToDtYear.getText()) < Integer.valueOf(txtFromDtYear.getText()))
						{
							MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
							msgDayErr.setText("Error!");
							msgDayErr.setMessage("To Year"+" should be equal to or greater than"+" From Year.");
							msgDayErr.open();
							txtToDtYear.setText("");
							display.getCurrent().asyncExec(new Runnable() {
								
								@Override
						
								
								public void run() {
									// TODO Auto-generated method stub
									txtToDtYear.setFocus();
									txtToDtYear.setText("");
									
								}
							});
							return;
						}
						if(txtFromDtDay.getText().trim().equals("")&&txtFromDtMonth.getText().trim().equals("")&&txtFromDtYear.getText().trim().equals("")&&txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals("")||txtFromDtDay.getText().trim().equals(""))
						{
							MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
							msgDayErr.setText("Validation Date Error!");
							msgDayErr.setMessage("Please enter a valid Date.");
							msgDayErr.open();
							display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
							txtFromDtDay.setFocus();
								}
							});
							return;
							
							
						}
						
						
						if(txtFromDtYear.getText().trim().equals("")&&txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals(""))
						{
							MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
							msgDayErr.setText("Validation Date Error!");
							msgDayErr.setMessage("Please enter a valid Date.");
							msgDayErr.open();
							display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
							txtFromDtYear.setFocus();
								}
							});
							return;
						}
						
						if(!txtFromDtYear.getText().trim().equals("")&&txtToDtDay.getText().trim().equals("")&&txtToDtMonth.getText().trim().equals("")&&txtToDtYear.getText().trim().equals("")||txtToDtDay.getText().trim().equals(""))
						{
							MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR |SWT.ICON_ERROR);
							msgDayErr.setText("Validation Date Error!");
							msgDayErr.setMessage("Please enter a valid Date.");
							msgDayErr.open();
							display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
							txtToDtDay.setFocus();
								}
							});
							return;
						}
						if(txtToDtMonth.getText().trim().equals(""))
						{
							MessageBox msgDayErr = new MessageBox(new Shell(),SWT.OK | SWT.ERROR | SWT.ICON_ERROR);
							msgDayErr.setText("Validation Date Error!");
							msgDayErr.setMessage("Please enter a valid Date.");
							msgDayErr.open();
							display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
							txtToDtMonth.setFocus();
								}
							});
							return;
						}
						
					
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						MessageBox msg = new MessageBox(new Shell(),SWT.ERROR|SWT.OK | SWT.ICON_ERROR );
						msg.setText("Validation Date Error!");
						msg.setMessage("Please enter valid Date");
						msg.open();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								txtFromDtYear.setFocus();
								
								
							}
						});
						return;
					}
					
				
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {

						Date voucherDate = sdf.parse(txtToDtYear.getText() + "-" + txtToDtMonth.getText() + "-" + txtToDtDay.getText());
						Date fromDate = sdf.parse(globals.session[2].toString().substring(6)+ "-" + globals.session[2].toString().substring(3,5) + "-"+ globals.session[2].toString().substring(0,2));
						Date toDate = sdf.parse(globals.session[3].toString().substring(6)+ "-" + globals.session[3].toString().substring(3,5) + "-"+ globals.session[3].toString().substring(0,2));
						
						if(voucherDate.compareTo(fromDate)<0 || voucherDate.compareTo(toDate) > 0 )
						{
						
							MessageBox msg = new MessageBox(new Shell(),SWT.ERROR|SWT.OK | SWT.ICON_ERROR);
							msg.setText("Validation Year Error!");
							msg.setMessage("Please enter the date range within the financial year");
							msg.open();
							txtToDtYear.setText("");
							Display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									txtToDtYear.setFocus();
								}
							});
							
							return;
						}
										
					} catch(java.text.ParseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			}
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					super.focusGained(arg0);
					txtToDtYear.clearSelection();
					txtToDtYear.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					txtToDtYear.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
					if (dropdownAccountName.getSelectionIndex() == 0)
					{
						MessageBox	 msg = new MessageBox(new Shell(),SWT.OK| SWT.ERROR |SWT.ICON_ERROR);
						msg.setText("Error!");
						msg.setMessage("Please select Account Name ");
						msg.open();
						//dropdownAccountName.setFocus();
						display.getCurrent().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								dropdownAccountName.setFocus();					
							}
						});
						
						return;

						
					}
				}
			});

			txtFromDtYear.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date voucherDate = sdf.parse(txtFromDtYear.getText() + "-" + txtFromDtMonth.getText() + "-" + txtFromDtDay.getText());
						Date fromDate = sdf.parse(globals.session[2].toString().substring(6)+ "-" + globals.session[2].toString().substring(3,5) + "-"+ globals.session[2].toString().substring(0,2));
						Date toDate = sdf.parse(globals.session[3].toString().substring(6)+ "-" + globals.session[3].toString().substring(3,5) + "-"+ globals.session[3].toString().substring(0,2));
						
						if(voucherDate.compareTo(fromDate)< 0 || voucherDate.compareTo(toDate) > 0 )
						{
							MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK | SWT.ICON_ERROR);
							errMsg.setText("Validation Date Error!");
							errMsg.setMessage("Please enter the date within the financial year");
							errMsg.open();
							txtFromDtYear.setText("");
							Display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									
									txtFromDtYear.setFocus();
									
								}
							});
							
							return;
						}
					} 
					catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			});
			
				
			/*txtToDtYear.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					//super.focusLost(arg0);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date voucherDate = sdf.parse(txtToDtYear.getText() + "-" + txtToDtMonth.getText() + "-" + txtToDtDay.getText());
						Date fromDate = sdf.parse(globals.session[2].toString().substring(6)+ "-" + globals.session[2].toString().substring(3,5) + "-"+ globals.session[2].toString().substring(0,2));
						Date toDate = sdf.parse(globals.session[3].toString().substring(6)+ "-" + globals.session[3].toString().substring(3,5) + "-"+ globals.session[3].toString().substring(0,2));
						
						if(voucherDate.compareTo(fromDate)< 0 || voucherDate.compareTo(toDate) > 0 )
						{
							MessageBox errMsg = new MessageBox(new Shell(),SWT.ERROR |SWT.OK );
							errMsg.setMessage("please enter the date within the financial year");
							errMsg.open();
							txtToDtYear.setText("");
							Display.getCurrent().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									
									txtToDtYear.setFocus();
									
								}
							});
							
							return;
						}
					} 
					catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			});	
			
*/
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

		

	}