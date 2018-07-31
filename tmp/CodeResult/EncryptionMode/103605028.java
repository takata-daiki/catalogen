import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.TimeZone;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Joshua Wilkins
 * @version 1.05 - last updated 5/2/2008
 * Client class for the Clock in and out program, connects to the server and allows the sending of commands to the server.
 * built for Networks First Semester Final Project, a graduate level course (502).
 * Environment variables for system or IDE must have the mysql-connector-java-5.1.5-bin.jar set up for the system to work,
 * as well as a working mysql server. The server class contains the code for interaction with the mysql server.
 */
public class Client {

	private Scanner in;
	private Scanner SocketIn;
	private PrintWriter SocketOut;
	private PrintWriter out;
	private FileWriter fileOut;
	private File file;
	private Socket socket;
	private int errorCount;
	private State presentState;
	private int eventState = 0;
	private Event eventType;
	private boolean isUsrEvent;
	private boolean isTimerEvent;
	private boolean isServerEvent;
	private int[][] eventStateTable;
	private GregorianCalendar calendar;
	private String defaultServerAddress = "localhost";
	private int defaultServerPort = 19000;
	private String frame;
	private boolean programStarted, encryptionMode;
	private String serverInput, userInput;
	private ArrayList<String> serverInputList;
	private boolean timeToListenToServer = false;
	private byte[] keyBytes = new byte[] {(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x00};
	private SecretKey key = new SecretKeySpec(keyBytes,"DES");;
	private DesEncrypter encrypter = new DesEncrypter(key);
	
	/**
	* State machine event values
	*/
	private enum Event {
		IOREQ, CNCTREQ, RJCTRCVD, TEXP, CLOSEREQ, USRDATAREQ, CLOSEACKRCVD, ACCPTRECVD, IORESPNSERCVD, DATARESPNSERCVD
	}

	/**
	*	State machine state values
	*/
	private enum State {
		UNCONNECTED, PENDING_CONNECTION, CONNECTED, WAIT_IO_CONFIRMATION, WAIT_DATA_RESPNSE, PENDING_CLOSE
	}

	/**
	*	Default constructor
	*/
	public Client() {
		initialize();
		loop();
	}

	/**
	 *	Constructor with added user parameters
	 */
	public Client(String serverAddress, int serverPort) {
		defaultServerAddress = serverAddress;
		defaultServerPort = serverPort;
		Client client = new Client();
	}

	/**
	 *	Constructor with single user parameter
	 */
	public Client(String serverAddress) {
		defaultServerAddress = serverAddress;
		Client client = new Client();
	}

	public static void main(String[] args) {
		Client client;
		if (args != null && args.length > 0) {
			if (args.length == 2) {
				client = new Client(args[0], Integer.parseInt(args[1]));
			}
			client = new Client(args[0]);
		}
		else {
			client = new Client();
		}
	}

	/**
	 * initializes the client and statemachine
	 */
	private void initialize() {
		System.out.println("Program Starting!");
		System.out.println("Type COMMANDS for a list of commands.");
		presentState = State.UNCONNECTED;
		errorCount = 0;
		eventType = Event.CNCTREQ;
		programStarted = true;
		isUsrEvent = false;
		isTimerEvent = false;
		isServerEvent = false;
		encryptionMode = false;
		serverInputList = new ArrayList<String>();
		calendar = new GregorianCalendar(TimeZone.getDefault());
		buildEventStateTable();
		in = new Scanner(System.in);
	}

	/**
	 * Basic state machine event loop
	 */
	private void loop() {
		while (true) {
			int eventState = 0;
			if (programStarted) {
				System.out
						.println("Program Initialized. Attempting to connect to server...");
				eventState = findEventState(eventType, presentState);
				caseSwitch(eventState);
				programStarted = false;
			}
			if (programStarted == false) {
				if (timeToListenToServer) {
					try {
						if (SocketIn.hasNextLine()) {
							
							serverInput = SocketIn.nextLine();
							catchServerEvent(serverInput);
							if (isServerEvent) {
								eventState = findEventState(eventType,
										presentState);
								caseSwitch(eventState);
							}
						}
						else {
							System.out.println("we skipped the socket in :(");
						}
					}
					catch (Exception e) {
						// do nothing if socket hasn't been connected;
					}
				}

				if (in.hasNextLine()) {
					userInput = in.nextLine();
					if (userInput.equals("COMMANDS")) {
						System.out
								.println("USER COMMANDS (CASE-SENSATIVE): IOREQ, CNCTREQ, CLOSEREQ, USRDATAREQ, QUIT");
						timeToListenToServer = false;
					}
					if (userInput.equals("QUIT")) {
						timeToListenToServer = false;
						if (presentState.equals(State.UNCONNECTED)) {
							System.out.println("Quitting...");
							break;
						}
						else {
							timeToListenToServer = false;
							System.out
									.println("You forgot to close the connection with the server!");
							System.out
									.println("Please use the command: CLOSEREQ before quitting.");
						}
					}
					catchUsrEvent(userInput);
					if (isUsrEvent) {
						eventState = findEventState(eventType, presentState);
						caseSwitch(eventState);
					}
				}
			}
		}
	}

	/**
	 * State machine for the client
	 */
	@SuppressWarnings("static-access")
	private void caseSwitch(int eventState) {

		switch (eventState) {
			case 0: { // Nothing happens case
				System.out.println("and nothing happens...");
				break;
			}
			case 1: { // IOREQ && UNCONNECTED
				encryptionMode = false;
				timeToListenToServer = false;
				System.out
						.println("Client not currently connected to server...");
				System.out.println("...Writing to log file...");
				System.out.println("Please enter your user ID...");
				String userID = in.nextLine();
				try {
					fileOut = new FileWriter("log.txt", true);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = new PrintWriter(fileOut, true);
				out.print(userID);
				System.out.println("Please enter your password...");
				String userPass = in.nextLine();
				out.print(" " + userPass);
				out.print(" IN/OUT");
				calendar = new GregorianCalendar(TimeZone.getDefault());
				int temp = calendar.get(calendar.MONTH);
				out.print(" " + (temp + 1) + "/"
						+ calendar.get(calendar.DAY_OF_MONTH) + "/"
						+ calendar.get(calendar.YEAR) + " "
						+ calendar.get(calendar.HOUR_OF_DAY) + ":"
						+ calendar.get(calendar.MINUTE) + ":"
						+ calendar.get(calendar.SECOND));
				out.println();
				System.out.println("writing to file...");
				try {
					Thread.sleep(800);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("...done");
				out.close();
				presentState = State.UNCONNECTED;
				eventType = null;
				break;
			}
			case 2: { // IOREQ && CONNECTED
				encryptionMode = true;
				System.out.println("Please Enter Your UserID...");
				String userID = in.nextLine();
				String encUserID = encData(userID);
				System.out.println("Please enter your password...");
				String password = in.nextLine();
				String encPassword = encData(password);
				System.out.println("Transmitting...");
				frame = encData("IOREQ");
				txFrame(frame);
				frame = encUserID;
				txFrame(frame);
				frame = encPassword;
				txFrame(frame);
				presentState = State.WAIT_IO_CONFIRMATION;
				timeToListenToServer = true;
				eventType = null;
				break;
			}
			case 3: { // CONNECT REQ & UNCONNECTED
				encryptionMode = false;
				try {
					socket = new Socket(defaultServerAddress, defaultServerPort);
					SocketIn = new Scanner(socket.getInputStream());
					SocketOut = new PrintWriter(socket.getOutputStream(), true);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out
							.println("Socket Connection Failed! Perhaps the server is not online or incorrect hostname & port entered?");
					System.out.print("Aborting");
					try {
						Thread.sleep(800);
						System.out.print(".");
						Thread.sleep(800);
						System.out.print(".");
						Thread.sleep(800);
						System.out.print(".\n");
					}
					catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					presentState = State.UNCONNECTED;
					eventType = null;
					break;
				}
				String str = "CONNECT";
				frame = encData(str);
				txFrame(frame);
				// start_timer;
				timeToListenToServer = true;
				presentState = State.PENDING_CONNECTION;
				eventType = null;
				break;
			}
			case 4: { // RJCTRCVD && PENDING CONNECTION
				encryptionMode = false;
					System.out
							.println("Failed to connect Securely to server, Reject Message Received.");
					System.out
							.println("Perhaps the Private key for encryption needs updating on this machine?");
					presentState = State.UNCONNECTED;
					eventType = null;
					break;
			}

				// I decided against implementing time-outs given the time
				// allotment for the project

			case 5: { // TEXP & PENDING CONNECTION
				break;
			}
			case 6: { // TEXP & WaitIOConf
				break;
			}
			case 7: { // TEXP & WaitDataRspns
				break;
			}
			case 8: { // TEXP & Pending CLose
				break;
			}
			case 9: { // CloseREQ & Connected
				encryptionMode = true;
				String str = "CLOSEREQ";
				frame = encData(str);
				txFrame(frame);
				timeToListenToServer = true;
				presentState = State.PENDING_CLOSE;
				eventType = null;
				break;
			}
			case 10: { // USRData REQ and Unconnected
				encryptionMode = false;
				timeToListenToServer = false;
				System.out
						.println("Not connected to server, cant retreive database data...aborting...");
				presentState = State.UNCONNECTED;
				eventType = null;
				break;
			}
			case 11: { // UsrDataReq and Connected
				boolean temp = true;
				boolean temp2 = true;
				boolean all = false;
				boolean single = false;
				encryptionMode = true;
				System.out
						.println("Is this a Single Employee hours request or All currently clocked in employees? Specify either SINGLE or ALLCLOCKEDIN");
				while (temp2) {
					String userInput = in.nextLine();
					if (userInput.equals("SINGLE")
							|| userInput.equals("ALLCLOCKEDIN")) {
						if(userInput.equals("ALLCLOCKEDIN")) {
								all = true;
						}
						if(userInput.equals("SINGLE")) {
							single = true;
						}
						frame = encData(userInput);
						txFrame(frame);
						temp2 = false;
					}
					else {
						System.out
								.println("Incorrect Command entered, please try again.");
					}
				}
				if(single) {
				System.out
						.println("Enter employee's username");
				String username = in.nextLine();
				System.out.println("Enter start date in the format mm-dd-yyyy");
				String startDate = in.nextLine();
				System.out.println("Enter end date in the format mm-dd-yyyy");
				String endDate = in.nextLine();
				frame = encData(username);
				txFrame(frame);
				frame = encData(startDate);
				txFrame(frame);
				frame = encData(endDate);
				txFrame(frame);
				}
				else {
					//nothin
				}
				presentState = State.WAIT_DATA_RESPNSE;
				eventType = null;
				timeToListenToServer = true;
				break;
			}
			case 12: { // CloseAck Recieved && Pending Close
				encryptionMode = false;
				System.out.println("Connection Closed Successfully...");
				presentState = State.UNCONNECTED;
				timeToListenToServer = false;
				eventType = null;
				break;
			}
			case 13: { // Accpt Recieved && Pending Connection
				System.out.println("Connection sucessful...");
				encryptionMode = true;
				presentState = State.CONNECTED;
				timeToListenToServer = false;
				eventType = null;
				break;
			}
			case 14: { // IO Response Recieved && WaitIOConf
				encryptionMode = false;
				String ioResponse = SocketIn.nextLine();
				String decIOresponse = decData(ioResponse);
				//System.out.println(decIOresponse);
				if (decIOresponse.startsWith("IN")) {
					System.out.println("Clock-IN OKAY: " + decIOresponse);
				}
				else if (decIOresponse.startsWith("OUT")) {
					System.out.println("Clock-OUT OKAY: " + decIOresponse);
				}
				else {
					System.out.println(decIOresponse);
				}
				presentState = State.CONNECTED;
				timeToListenToServer = false;
				eventType = null;
				break;
			}
			case 15: { // DataRSPNS Received && WaitDataResponse
				encryptionMode = false;
				boolean temp = true;
				while (temp) {
					String dataResponse = SocketIn.nextLine();
					String decDataResponse = decData(dataResponse);
					if (decDataResponse.equals("END")) {
						temp = false;
					}
					else {
						System.out.println(decDataResponse);
					}
				}
				presentState = State.CONNECTED;
				timeToListenToServer = false;
				eventType = null;
				break;
			}
		}
	}

	/**
	*	Builds the event State table for use by the State Machine
	*/
	private void buildEventStateTable() {
		eventStateTable = new int[10][6];
		for (int r = 0; r < eventStateTable.length; r++) {
			for (int c = 0; c < eventStateTable[r].length; c++) {
				if (r == 0 && c == 0) {
					eventStateTable[r][c] = 1;
				}
				else if (r == 0 && c == 2) {
					eventStateTable[r][c] = 2;
				}
				else if (r == 1 && c == 0) {
					eventStateTable[r][c] = 3;
				}
				else if (r == 2 && c == 1) {
					eventStateTable[r][c] = 4;
				}
				else if (r == 3 && c == 1) {
					eventStateTable[r][c] = 5;
				}
				else if (r == 3 && c == 3) {
					eventStateTable[r][c] = 6;
				}
				else if (r == 3 && c == 4) {
					eventStateTable[r][c] = 7;
				}
				else if (r == 3 && c == 5) {
					eventStateTable[r][c] = 8;
				}
				else if (r == 4 && c == 2) {
					eventStateTable[r][c] = 9;
				}
				else if (r == 5 && c == 0) {
					eventStateTable[r][c] = 10;
				}
				else if (r == 5 && c == 2) {
					eventStateTable[r][c] = 11;
				}
				else if (r == 6 && c == 5) {
					eventStateTable[r][c] = 12;
				}
				else if (r == 7 && c == 1) {
					eventStateTable[r][c] = 13;
				}
				else if (r == 8 && c == 3) {
					eventStateTable[r][c] = 14;
				}
				else if (r == 9 && c == 4) {
					eventStateTable[r][c] = 15;
				}
				else {
					eventStateTable[r][c] = 0;
				}
			}
		}
	}

	/**
	 * Catches events passed from the server and assigns the correct statemachine values for that event
	 */
	private void catchServerEvent(String input) {
		String decodedInput;
		if (encryptionMode) {
			decodedInput = decData(input);
			if (checkServerEvent(decodedInput)) {
				if (decodedInput.equals("REJECTED")) {
					eventType = Event.RJCTRCVD;
					isServerEvent = true;
				}
				if (decodedInput.equals("CLOSEACK")) {
					eventType = Event.CLOSEACKRCVD;
					isServerEvent = true;
				}
				if (decodedInput.equals("ACCEPT")) {
					eventType = Event.ACCPTRECVD;
					isServerEvent = true;
				}
				if (decodedInput.equals("IORESPONSE")) {
					eventType = Event.IORESPNSERCVD;
					isServerEvent = true;
				}
				if (decodedInput.equals("DATARESPONSE")) {
					eventType = Event.DATARESPNSERCVD;
					isServerEvent = true;
				}
			}
		}
		else {
			if (checkServerEvent(input)) {
				if (input.equals("REJECTED")) {
					eventType = Event.RJCTRCVD;
					isServerEvent = true;
				}
				if (input.equals("CLOSEACK")) {
					eventType = Event.CLOSEACKRCVD;
					isServerEvent = true;
				}
				if (input.equals("ACCEPT")) {
					eventType = Event.ACCPTRECVD;
					isServerEvent = true;
				}
				if (input.equals("IORESPONSE")) {
					eventType = Event.IORESPNSERCVD;
					isServerEvent = true;
				}
				if (input.equals("DATARESPONSE")) {
					eventType = Event.DATARESPNSERCVD;
					isServerEvent = true;
				}
			}
		}
	}

	/**
	 * Decodes the encrypted string
	 */
	private String decData(String input) {
		String output = encrypter.decrypt(input);
		return output;
	}

	/**
	 * checks to makesure the server event is a valid event
	 */
	private boolean checkServerEvent(String input) {
		boolean temp = false;
		String str[] = { "REJECTED", "CLOSEACK", "ACCEPT", "IORESPONSE",
				"DATARESPONSE" };
		for (String str1 : str) {
			if (input.equals(str1)) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * Checks to make sure the user event is valid
	 */
	private void catchUsrEvent(String input) {
		if (checkInput(input)) {
			for (Event event : Event.values()) {
				if (input.equals(event.toString())) {
					eventType = event;
					isUsrEvent = true;
				}
			}
		}
		else {
			isUsrEvent = false;
		}
	}
	
	/**
	 * finds the eventStateTable integer value for the given event and state
	 */
	private int findEventState(Event event, State currentState) {
		int eventState = 0;
		int eventInt = findEventInt(event);
		int stateInt = findStateInt(currentState);
		for (int r = 0; r < eventStateTable.length; r++) {
			for (int c = 0; c < eventStateTable[r].length; c++) {
				if (eventInt != 42 || stateInt != 42) {
					eventState = eventStateTable[eventInt][stateInt];
				}
			}
		}
		return eventState;
	}

	/**
	 * Helper method for the findEventState method
	 */
	private int findStateInt(State state) {
		int stateInt = 42;

		if (state.equals(State.UNCONNECTED)) {
			stateInt = 0;
		}
		else if (state.equals(State.PENDING_CONNECTION)) {
			stateInt = 1;
		}
		else if (state.equals(State.CONNECTED)) {
			stateInt = 2;
		}
		else if (state.equals(State.WAIT_IO_CONFIRMATION)) {
			stateInt = 3;
		}
		else if (state.equals(State.WAIT_DATA_RESPNSE)) {
			stateInt = 4;
		}
		else if (state.equals(State.PENDING_CLOSE)) {
			stateInt = 5;
		}
		else {
			System.out.println("Invalid State Error");
		}
		return stateInt;
	}

	/**
	 * Helper method for the findEventState method
	 */
	private int findEventInt(Event event) {
		int eventInt = 42;
		if (event.equals(Event.IOREQ)) {
			eventInt = 0;
		}
		else if (event.equals(Event.CNCTREQ)) {
			eventInt = 1;
		}
		else if (event.equals(Event.RJCTRCVD)) {
			eventInt = 2;
		}
		else if (event.equals(Event.TEXP)) {
			eventInt = 3;
		}
		else if (event.equals(Event.CLOSEREQ)) {
			eventInt = 4;
		}
		else if (event.equals(Event.USRDATAREQ)) {
			eventInt = 5;
		}
		else if (event.equals(Event.CLOSEACKRCVD)) {
			eventInt = 6;
		}
		else if (event.equals(Event.ACCPTRECVD)) {
			eventInt = 7;
		}
		else if (event.equals(Event.IORESPNSERCVD)) {
			eventInt = 8;
		}
		else if (event.equals(Event.DATARESPNSERCVD)) {
			eventInt = 9;
		}
		else {
			System.out.println("invalid event error");
		}
		return eventInt;
	}

	/**
	 * Checks the user input for correct syntax
	 */
	private boolean checkInput(String input) {
		boolean temp = false;
		String[] str = { "IOREQ", "CNCTREQ", "CLOSEREQ", "USRDATAREQ" };
		for (String str1 : str) {
			if (input.compareTo(str1) == 0) {
				temp = true;
			}
		}
		if (temp == false) {
			if (!input.equals("COMMANDS"))
				System.out.println("Invalid Command or Syntax");
		}
		return temp;
	}

	/**
	 * encodes the data to be sent
	 */
	private String encData(String str) {
		String encStr = encrypter.encrypt(str);
		return encStr;
	}

	/**
	 * transmits the frame
	 */
	private void txFrame(String str) {
		try {
			SocketOut.println(str);
		}
		catch (Exception e) {
			System.err
					.println("NOT CONNECTED! Cannot Transmit Frame!");
		}
	}

}
