package artem.finance.gui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import artem.finance.gui.autocomplete.Configurator;
import artem.finance.gui.db.BeansFactory;
import artem.finance.gui.renderer.BankCellRenderer;
import artem.finance.gui.renderer.ContractCellRenderer;
import artem.finance.gui.renderer.ContractSubjectCellRenderer;
import artem.finance.gui.renderer.CountCellRenderer;
import artem.finance.gui.renderer.GroupCellRenderer;
import artem.finance.gui.renderer.OrganizationCellRenderer;
import artem.finance.gui.renderer.PrilozhenieCellRenderer;
import artem.finance.gui.renderer.StyledComboBoxUI;
import artem.finance.gui.renderer.ValidityCellRenderer;
import artem.finance.gui.renderer.ZatrataCellRenderer;
import artem.finance.gui.verifier.SumFormattedTextFieldVerifier;
import artem.finance.server.persist.Bank;
import artem.finance.server.persist.Contract;
import artem.finance.server.persist.Organization;
import artem.finance.server.persist.Valuta;
import artem.finance.server.persist.beans.BankBean;
import artem.finance.server.persist.beans.ContractBean;
import artem.finance.server.persist.beans.OrganizationBean;
import artem.finance.server.persist.beans.ValutaBean;

public final class GuiUtils 
{
	//Variables
	public static final Logger LOG = Logger.getLogger(GuiUtils.class);
   private static List<OrganizationBean> organizations;
   private static List<BankBean> banks;
   private static List<ValutaBean> currencies;
   private static List<String> subj;
   private static List<String> groups;
   private static List<ContractBean> contracts;
   private static MaskFormatter mformatter;
   public static String DATE_SKELETON = "__.__.____";
   
   //GuiUtils will remember the comboboxes.
   private static JComboBox orgComboBox;
   
   private static DecimalFormat moneyFieldsFormat;
   private static DefaultFormatter defaultFormatter;
   
   
   //Methods
   
   public static JComboBox createOrganizationComboBox( BeansFactory factory, Rectangle rectangle )
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   
	   List<Organization> unpacked = new ArrayList<Organization>();
//	   if( orgComboBox == null )
//	   {
	      try 
		  {
		    if( organizations == null )
			{
			   organizations = factory.getOrganizationServiceSLSB().findAll();
			}
			
		    for(OrganizationBean bean: organizations)
		    {
		    	unpacked.add(bean.getOrganization());
		    }
		    unpacked.add( 0, new Organization());
		    
		    Organization[] organizationsWithEmptyValue = unpacked.toArray( new Organization[100] );
		    
		    orgComboBox = new JComboBox( organizationsWithEmptyValue );
			orgComboBox.setBounds(rectangle);
			
			//Need for auto search in combo box
			orgComboBox.setEditable(true);
		    Configurator.enableAutoCompletion(orgComboBox);
		   
		    orgComboBox.setUI(new StyledComboBoxUI() );
			
		    orgComboBox.setRenderer(GuiUtils.getOrganizationCellRenderer());
		    
		  } catch (RemoteException e) 
		  {
			 JOptionPane.showMessageDialog(null, e.getCause());
			 e.printStackTrace();
		  }
//	   }
	  return orgComboBox;
   }
   
   public static JComboBox createBankComboBox( BeansFactory factory, JComboBox bankComboBox, Rectangle rectangle )
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   
	   if (bankComboBox == null) 
		{
			List<Bank> unpacked = new ArrayList<Bank>();
			try {
				if( banks == null )
				{
				   banks = factory.getBankServiceSLSB().findAll();
				}
							    
			    for(BankBean bean: banks)
			    {
			    	unpacked.add( bean.getBank() );
			    }
			    
			    unpacked.add(0, new Bank());
			
			    Bank[] banksWithEmptyValue = unpacked.toArray( new Bank[10]);
			    
			    bankComboBox = new JComboBox(banksWithEmptyValue);
				bankComboBox.setBounds(rectangle);
				
				makeComboBoxAutoSearchible( bankComboBox, getBankCellRenderer() );
				
			} catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(null, e.getCause());
				e.printStackTrace();
			}
		}
	   return bankComboBox;
   }
   
   public static JComboBox createValutaComboBox( BeansFactory factory, JComboBox valComboBox, Rectangle rectangle )
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   
	   if (valComboBox == null) 
		{
			try 
			{
				if( currencies == null )
				{
					currencies = factory.getValutaServiceSLSB().findAll();
				}
				
				Valuta[] currenciesWithFirstEmptyValue  = new Valuta[currencies.size()+1];
				
				currenciesWithFirstEmptyValue[0] = new Valuta();
				
				for(int i = 0; i < currencies.size(); i++)
				{
					currenciesWithFirstEmptyValue[i+1] = currencies.get(i).getValuta();
				}
			    
			    valComboBox = new JComboBox( currenciesWithFirstEmptyValue );
				valComboBox.setBounds( rectangle );
			} catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(null, e.getCause());
				e.printStackTrace();
			}
		}
	   return valComboBox;
   }
   
   public static JComboBox createContractSubjectComboBox( BeansFactory factory, JComboBox dogovorSubjectComboBox, Rectangle rectangle)
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   
	   if (dogovorSubjectComboBox == null) 
	   {
			dogovorSubjectComboBox = new JComboBox();
			dogovorSubjectComboBox.setBounds(rectangle);
		
			try {
				if( subj== null )
				{
					subj = factory.getContractServiceSLSB().getPossibleContractSubjects();
				}
			
			    subj.add(0, StringUtils.EMPTY );
			    
			    Object[] subjects = subj.toArray();
			    
			    dogovorSubjectComboBox = new JComboBox( subjects );
				dogovorSubjectComboBox.setBounds(rectangle);
				
				//Need for auto search in combo box
				dogovorSubjectComboBox.setEditable(true);
    		    Configurator.enableAutoCompletion(dogovorSubjectComboBox);
    		   
    		    dogovorSubjectComboBox.setUI(new StyledComboBoxUI() );
    			
    		    dogovorSubjectComboBox.setRenderer(GuiUtils.getContractSubjectCellRenderer());
				
			} catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(null, e.getCause());
				e.printStackTrace();
			}
		}
		return dogovorSubjectComboBox;
   }
   
   public static JComboBox createGroupComboBox( BeansFactory factory, JComboBox groupComboBox, Rectangle rectangle )
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   if (groupComboBox == null) 
		{
			try {
				if( groups == null)
				{
				   groups = factory.getVipiskaServiceSLSB().getGroups();
				}
			
			    groups.add( 0, StringUtils.EMPTY );
			
			    Object[] groupsWithEmptyValue = groups.toArray();
			    
			    groupComboBox = new JComboBox( groupsWithEmptyValue );
				groupComboBox.setBounds(rectangle);
				
				//Need for auto search in combo box
				groupComboBox.setEditable(true);
    		    Configurator.enableAutoCompletion(groupComboBox);
    		   
    		    groupComboBox.setUI(new StyledComboBoxUI() );
    			
    		    groupComboBox.setRenderer(GuiUtils.getGroupCellRenderer());
    		    
			} catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(null, e.getCause());
				e.printStackTrace();
			}
			
		}
		return groupComboBox;
   }
   
   public static JComboBox createContractComboBox( BeansFactory factory, JComboBox contractComboBox, Rectangle rectangle)
   {
	   if(factory == null || rectangle == null){
		   throw new IllegalArgumentException("Beans factory or rectangle should be not null.");
	   }
	   if (contractComboBox == null) 
	   {
			List<Contract> unpacked = new ArrayList<Contract>();
			try 
			{  
				if( contracts == null )
				{
				   contracts = factory.getContractServiceSLSB().findAll();
				}
				
                for(ContractBean bean: contracts)
                {
               	   unpacked.add(bean.getContract());
                }
                unpacked.add(0, new Contract());
                
                Contract[] contractsWithEmptyValue = unpacked.toArray( new Contract[20]);
               
                contractComboBox = new JComboBox( contractsWithEmptyValue );
    		    contractComboBox.setBounds(rectangle);
    		    
    		    //Need for auto search in combo box
    		    contractComboBox.setEditable(true);
    		    Configurator.enableAutoCompletion(contractComboBox);
    		   
    		    contractComboBox.setUI(new StyledComboBoxUI() );
    			
    		    contractComboBox.setRenderer(GuiUtils.getContractCellRenderer());
			} 
			catch (RemoteException e) 
			{
				JOptionPane.showMessageDialog(null, e.getCause());
				e.printStackTrace();
			}
		}
		return contractComboBox;
	   
   }
   
   /**
	 * This method initializes mask formatter	
	 * 	
	 * @return javax.swing.text.MaskFormatter	
	 */
	public static MaskFormatter getMformatter() 
	{
		if (mformatter == null) 
		{
			mformatter = new MaskFormatter();
			try 
			{
				mformatter.setMask("##.##.####");
			} catch (ParseException e) 
			{
				JOptionPane.showMessageDialog(null, "Date field has wrong value." + " " +e.getMessage());
				e.printStackTrace();
			}
			mformatter.setPlaceholderCharacter('_');
		}
		return mformatter;
	}
	
	/**
	 * Products string representation of the current date in format "dd.MM.yyyy".
	 * @return String representation of current date.
	 */
	public static String productCurrentDate()
	{
	    SimpleDateFormat sdformat = new SimpleDateFormat( "dd.MM.yyyy" );
        String currentDate = sdformat.format( new java.util.Date() );
        return currentDate;
	}
	
	public static void clear( Container contentPane )
	{
		//clear frame fields
		if( contentPane != null )
		{
	       Component[] components = contentPane.getComponents();
			
		   for (Component c:components)
		   {
			   if (c.getClass().getCanonicalName().equals("javax.swing.JTextField")){
				   ((JTextField)c).setText("");
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JFormattedTextField")){
				   //((JFormattedTextField)c).setText("");
				   ((JFormattedTextField)c).setValue(null);
				   ((JFormattedTextField)c).setEditable(true);
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JComboBox"))
			   {
			       if( ((JComboBox)c).getItemCount() >= 1 )
                   {
			           ((JComboBox)c).setSelectedIndex(0);
                   }
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JCheckBox"))
			   {
				   ((JCheckBox)c).setSelected(false);
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JTextPane"))
			   {
			       ((JTextPane)c).setText("");
			   }
		   }
	    }
	}
	
	public static void clear( JPanel panel )
	{
		//clear frame fields
		if( panel != null )
		{
	       Component[] components = panel.getComponents();
			
		   for (Component c:components)
		   {
			   if (c.getClass().getCanonicalName().equals("javax.swing.JTextField")){
				   ((JTextField)c).setText("");
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JFormattedTextField")){
				   ((JFormattedTextField)c).setText("");
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JComboBox"))
			   {
			       if( ((JComboBox)c).getItemCount() >= 1 )
                   {
			           ((JComboBox)c).setSelectedIndex(0);
                   }
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JCheckBox"))
			   {
				   ((JCheckBox)c).setSelected(false);
			   }
			   if (c.getClass().getCanonicalName().equals("javax.swing.JTextPane"))
			   {
			       ((JTextPane)c).setText("");
			   }
		   }
	    }
	}
	
	public static Date getDateFromFTF( JFormattedTextField field )
	{
		String date = field.getText();
		if( "".equals(date) || DATE_SKELETON.equals(date) )
		{
			return null;
		}
	    GregorianCalendar calendar = null;
        StringTokenizer tokenizer = new StringTokenizer( field.getText(), ".", false );
        int day = 0;
        int month = 0;
        int year = 0;
        while(tokenizer.hasMoreTokens()){
            day = Integer.parseInt(tokenizer.nextToken());
            month = Integer.parseInt(tokenizer.nextToken());
            year = Integer.parseInt(tokenizer.nextToken());
            calendar = new GregorianCalendar();
            calendar.set(year, month-1, day);
        }
        
        return new Date( calendar.getTimeInMillis() );
	}
	
	public static JFormattedTextField createMoneyUnitsFormattedTextField( JFormattedTextField formattedTextFeld, Properties properties )
	{
		if( formattedTextFeld == null )
		{
//		   BigDecimal bdecimal = new BigDecimal("0.00");
//	       bdecimal.setScale(2);
           formattedTextFeld = new JFormattedTextField();//bdecimal);
           
           DefaultFormatter df = getDefaultFormatterForMoneyUnitsFields();
			
		   DefaultFormatterFactory dff = new DefaultFormatterFactory(df, df, df);
			
		   formattedTextFeld.setFormatterFactory(dff);
		   //setting sum verifier
		   formattedTextFeld.setInputVerifier( new SumFormattedTextFieldVerifier( properties ) );
		}
		return formattedTextFeld;
	
	}
	
	public static DefaultFormatter getDefaultFormatterForMoneyUnitsFields()
	{
		if( moneyFieldsFormat == null || defaultFormatter == null )
		{
			moneyFieldsFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols() );
		
			moneyFieldsFormat.setParseBigDecimal(true);
			moneyFieldsFormat.setMaximumFractionDigits(2);
			moneyFieldsFormat.setMinimumFractionDigits(2);
	    
	        DecimalFormatSymbols dfs = moneyFieldsFormat.getDecimalFormatSymbols();
	        dfs.setGroupingSeparator(' ');
            dfs.setDecimalSeparator('.');
            
            defaultFormatter = new NumberFormatter(moneyFieldsFormat);
			
    		defaultFormatter.setValueClass( BigDecimal.class );
		}
		
		return defaultFormatter;
	}
	
	public static BigDecimal getBigDecimalFromFormattedTextField( JFormattedTextField formattedTextField )
	{
		if( formattedTextField == null )
		{
			throw new IllegalArgumentException(" JFormattedTextField object is null.");
		}
		BigDecimal value = null;
		try 
		{
			value = (BigDecimal) getDefaultFormatterForMoneyUnitsFields().stringToValue( formattedTextField.getText() );
		} catch (ParseException e) 
		{
			JOptionPane.showMessageDialog( null, "Can not parse the formetted text fields value.");
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static void makeComboBoxAutoSearchible( JComboBox combo, ListCellRenderer listCellRenderer )
	{
		if( combo != null )
		{
			//Need for auto search in combo box
			combo.setEditable(true);
			Configurator.enableAutoCompletion(combo);
	   
			combo.setUI(new StyledComboBoxUI() );
		
			combo.setRenderer(listCellRenderer);
		}
	}
	
	public static ContractCellRenderer getContractCellRenderer()
	{
	     return new ContractCellRenderer();
	}
	
	public static CountCellRenderer getCountCellRenderer()
	{
	     return new CountCellRenderer();
	}
	
	public static GroupCellRenderer getGroupCellRenderer()
	{
	     return new GroupCellRenderer();
	}
	
	public static ContractSubjectCellRenderer getContractSubjectCellRenderer()
	{
	     return new ContractSubjectCellRenderer();
	}
	
	public static ValidityCellRenderer getValidityCellRenderer()
	{
	     return new ValidityCellRenderer();
	}
	
	public static OrganizationCellRenderer getOrganizationCellRenderer()
	{
	     return new OrganizationCellRenderer();
	}
	
	public static BankCellRenderer getBankCellRenderer()
	{
	     return new BankCellRenderer();
	}
	
	public static ZatrataCellRenderer getZatrataCellRenderer()
	{
	     return new ZatrataCellRenderer();
	}
	
	public static PrilozhenieCellRenderer getPrilozhenieCellRenderer()
	{
	     return new PrilozhenieCellRenderer();
	}
	
}
