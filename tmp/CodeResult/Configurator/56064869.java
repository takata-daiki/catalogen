import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

public class Configurator {
	  private static final Configurator instance = new Configurator();
	  // **************************
	  // Connection data
	  private  Integer port = new Integer(4555);
	  private  String  server = "";
	  private  Integer databasePort = new Integer(5432);
	  private  String  databaseHost = "";
	  //****************************
	  private String failStorePath = "";
	  //****************************
	  private String pathFile = null;
	  private BufferedReader BR = null;
	  private String encoding = Constants.encoding;
	  private int ERROR = 0; // For future use

	/*=============================================================================*/
	private Configurator() {
        try {
        	pathFile = Constants.confFile;
            BR = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), encoding));
          }
        catch (UnsupportedEncodingException e) {
          ERROR = 1;
          CitadelClient.log("Configurator Unknown encoding of input file "+pathFile);
        }
        catch (FileNotFoundException e) {
          ERROR = 2;
          CitadelClient.log("Configurator Can't open file "+ pathFile);
        }
        load();
	}

	// Public Interface
	/*=============================================================================*/
	public static Configurator getInstance() {
		return instance;
	}

	/*=============================================================================*/
	public String getServerAddress() {
		CitadelClient.log("Configurator getServerAddress() Host = "+server);
		return server;
	}

	/*=============================================================================*/
	public int getServerPort() {
		CitadelClient.log("Configurator getServerPort() Client Port = "+port.intValue());
		return port.intValue();
	}

	/*=============================================================================*/
	public String getDBHost() {
		CitadelClient.log("Configurator getDBHost() DBHost = "+databaseHost);
		return databaseHost;
	}

	/*=============================================================================*/
	public int getDBPort() {
		CitadelClient.log("Configurator getDBPort() Client DBPort = "+databasePort.intValue());
		return databasePort.intValue();
	}

	/*=============================================================================*/
	public String getMailStorePath() {
		return failStorePath;
	}

    // Private Part
    /*=========================================================================================*/
    private void load() {
        if(ERROR > 0) {
        	CitadelClient.log("Configurator load() ERROR in process Loading configuration file = "+ERROR);
        }
        String str;
        String gstr = new String("");

        try {
          while (!(str = BR.readLine()).equals(null)) {
              if(str.equals("")) continue; // Skip empty strings
              if(str.startsWith("#")) continue;  // Scip comments
              if(str.indexOf('#') > 0) str = str.substring(0, str.indexOf('#')); //Delete following comments
              gstr += str.trim();
              if(str.indexOf(';') < 0) continue;
              if(!parseObject(gstr)) {
                  ERROR = 6;
                  CitadelClient.log("Configurator load() Error in configuration file"+pathFile);
                  break;
              }
              gstr = ""; // Prepare for new content
          }
        }
        catch (IOException e) {
        	CitadelClient.log("Configurator load() Unexpected IO Error "+e);
          ERROR = 3;
        }
        catch (NullPointerException e) {
          ERROR = 0;
        }

        try {
            BR.close();
          }
          catch (IOException e) {
              ERROR = 5;
              CitadelClient.log("Configurator load() Unexpected IO error "+e);
          }

    if(ERROR != 0) CitadelClient.log("Configurator load() ERROR in process Loading configuration file = "+ERROR);
    }

    /*=========================================================================================*/
    private boolean parseObject(String str) {
        int i;
        String INNER_BUFFER = new String(str);

        INNER_BUFFER = INNER_BUFFER.replace('\t', ' ');
        INNER_BUFFER = INNER_BUFFER.substring(0, INNER_BUFFER.indexOf(";")).trim();
        i = INNER_BUFFER.indexOf(' ');
        String kst = INNER_BUFFER.substring(0, i).trim();
        INNER_BUFFER = INNER_BUFFER.substring(i, INNER_BUFFER.length()).trim();
        i = INNER_BUFFER.indexOf(' ');
        String MODE = INNER_BUFFER.substring(0, i).trim();
        MODE = MODE.toUpperCase();
        if(!MODE.equals("L") && !MODE.equals("I") && !MODE.equals("S") && !MODE.equals("T")) {
            ERROR = 4;
            CitadelClient.log("Configurator parseObject() Error - Undefinit data type in string "+kst +" in file "+pathFile);
            return false;
        }
        INNER_BUFFER = INNER_BUFFER.substring(i, INNER_BUFFER.length()).trim();
        if(MODE.equals("I")) {
            Integer value = new Integer(INNER_BUFFER);
//	    	Port, MaxNumMailersAtTime, ForwardMailPort, MaxMsgSize
	    	if("Port".equalsIgnoreCase(kst)) port = value;
	    	else if("DBPort".equalsIgnoreCase(kst)) databasePort = value;
	    	else CitadelClient.log("Configurator parseObject() Unknown KEY = "+kst+" in configuration file.");
        }
        if(MODE.equalsIgnoreCase("S")) {
//	    	Host, ForwardMailToHost, OurHostName, SoftVersion, MailStorePath
	    	if("Server".equalsIgnoreCase(kst)) server = INNER_BUFFER;
	    	else if("FailStorePath".equalsIgnoreCase(kst)) failStorePath = INNER_BUFFER;
	    	else if("DBHost".equalsIgnoreCase(kst)) databaseHost = INNER_BUFFER;
	    	else CitadelClient.log("Configurator parseObject() Unknown KEY = "+kst+" in configuration file.");
        }
    if(ERROR == 0) return true;
    else return false;
    }

}
