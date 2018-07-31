package com.nelson.jarvisclientics;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * When the application opens It first checks to see if network is available. 
 * If there is a connection it reads notes from the computer
 * It then reads from internal storage
 * @author austinn
 *
 */
public class Notes extends Activity implements OnInitListener {

	ArrayAdapter<String> arrayAdapter;
	ArrayList<String> notes;
	ListView notesListView;
	Socket socket = null;
	DataOutputStream dataOutputStream = null;
	DataInputStream dataInputStream = null;
	private TextToSpeech tts;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private int MY_DATA_CHECK_CODE = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Intent intent = new Intent(this, MainPort.class);
			finish();
			startActivity(intent);
		}
		setContentView(R.layout.notes); //all of this determines what the orientation is and what Activity/Layout

		Intent intent = getIntent();
		notes = new ArrayList<String>();
		Bundle extras = intent.getExtras();
		notes = extras.getStringArrayList("Notes"); //Gets the ArrayList of all notes
		notesListView = (ListView)findViewById(R.id.listView1); //list view for displaying notes
		arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, notes);
		readNotes(); //reads notes from internal storage. Adds them to notes ArrayList
		if(isNetworkAvailable()) { new DownloadFilesTask().execute("readnotes" , null, null); } //if there is Internet, send a message via Sockets. readnotes will read from the textfile on computer and add them to notes ArrayList 

		ArrayList<String> notesCopy = new ArrayList<String>();
		for(int i = 0; i < notes.size(); i++) {
			notesCopy.add(notes.get(i));
		}
		for(int i = 0; i < notes.size(); i++) {
			for(int j = 0; j < notesCopy.size(); j++) {
				if(notes.get(i).equals(notesCopy.get(j))) {
					notes.remove(i);
				}
			}
		}

		notesListView.setAdapter(arrayAdapter); ///////////Sets the listview with the adapter

		/*
		 * ListView listener
		 * If a note is pressed, remove it from the arraylist
		 * Rewrite notes to internal storage
		 * Send command writenotes via Socket that writes notes to the textfile on the computer
		 */
		notesListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, final int pos,
					long arg3) {

				final Dialog confirmationDialog = new Dialog(Notes.this); 
				confirmationDialog.setContentView(R.layout.areyousure); //sets the layout
				confirmationDialog.setTitle("Are you sure you wish to delete this note?"); //sets the title
				confirmationDialog.setCancelable(true); //allows the user to exit the dialog
				Button yes = (Button)confirmationDialog.findViewById(R.id.yes);
				Button no = (Button)confirmationDialog.findViewById(R.id.no);
				confirmationDialog.show();
				
				yes.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						notes.remove(pos);
						new DownloadFilesTask().execute("WriteInternal" , null, null);
						String writenotes = "";
						for(int i = 0; i < notes.size(); i++) { writenotes+=notes.get(i) + "\n"; }
						if(isNetworkAvailable()) { new DownloadFilesTask().execute("writenotes" + writenotes, null, null); }
						confirmationDialog.cancel();
					}
				});

				no.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						confirmationDialog.cancel();
					}
				});
				
			}
		});

		/*
		 * newNote Button listener
		 * Will prompt user to enter a new note
		 * OnClick - add the note to ArrayList
		 *         - write the notes to internal storage
		 *         - send command via Socket
		 */
		Button newNote = (Button)findViewById(R.id.button1);
		newNote.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				final Dialog commandDialog = new Dialog(Notes.this); 
				commandDialog.setContentView(R.layout.commanddialog); //sets the layout
				commandDialog.setTitle("Enter a command"); //sets the title
				commandDialog.setCancelable(true); //allows the user to exit the dialog
				final EditText entercommand = (EditText)commandDialog.findViewById(R.id.entercommand);
				final ImageButton go = (ImageButton)commandDialog.findViewById(R.id.go);
				go.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						notes.add(entercommand.getText().toString());
						new DownloadFilesTask().execute("WriteInternal" , null, null);
						String writenotes = "";
						for(int i = 0; i < notes.size(); i++) { writenotes+=notes.get(i) + "\n"; }
						if(isNetworkAvailable()) { new DownloadFilesTask().execute("writenotes" + writenotes , null, null); }
						commandDialog.hide();
					}
				});
				commandDialog.show();
				return false;
			}
		});

		newNote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * Sets up navigation buttons on the side of screen
		 */
		ImageButton home = (ImageButton)findViewById(R.id.home);

		home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MainLand.class);
				intent.putExtra("Notes", notes);
				finish();
				startActivity(intent);
			}
		});

		ImageButton smsButton = (ImageButton)findViewById(R.id.sms);
		smsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SMS.class);
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton passwordButton = (ImageButton)findViewById(R.id.password);
		passwordButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), PasswordManager.class);
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton musicButton = (ImageButton)findViewById(R.id.music);
		musicButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Music.class);
				finish();
				startActivity(intent);
			}
		});

	} /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// END OF onCreate();


	/**
	 * Checks to see if an Internet connection is available.
	 * @return true if there is a connection
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	/**
	 * Helper method that reads notes from internal storage
	 */
	private void readNotes() {
		String res = null;
		try {
			InputStream in = openFileInput("notes.txt");
			if (in != null) {
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while (( res = buffreader.readLine()) != null) {
					notes.add(res);
				}
				in.close();
			}
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Recognizes key words in responseTexts and runs the appropriate function
	 * @param responseText
	 */
	private void responseHandler(String responseText) {
		if(responseText.startsWith("Notes", 0)) {
			responseText = responseText.substring(5, responseText.length());
			String lines[] = responseText.split("\\n");
			for(String line: lines) {
				notes.add(line);
			}
			new DownloadFilesTask().execute("WriteInternal" , null, null);
		}
	}



	/**
	 * SENDS COMMAND TO SERVER
	 */
	protected void sendCommand(String command) {
		try {
			String responseText = "";
			socket = new Socket("192.168.0.105", 8756);
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream.writeUTF(command);
			responseText = dataInputStream.readUTF();
			responseHandler(responseText);
		} catch (UnknownHostException e) {
			Toast.makeText(this, "Unknown Host Exception", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		finally{
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
			if (dataOutputStream != null){
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
			if (dataInputStream != null){
				try {
					dataInputStream.close();
				} catch (IOException e) {
					Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Class for handling NetworkOnMainThread
	 * Sends the command Asynchronously 
	 * @author austinn
	 *
	 */
	private class DownloadFilesTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... command) {
			if(command[0].equals("WriteInternal")) {
				//write to internal storage
				FileOutputStream fOut = null;
				OutputStreamWriter osw = null;
				try{
					fOut = openFileOutput("notes.txt", Context.MODE_PRIVATE);
					osw = new OutputStreamWriter(fOut);
					for (int i = 0; i < notes.size(); i++){
						osw.write(notes.get(i) + "\n");
					}
					osw.close();
					fOut.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
				runOnUiThread(new Runnable() {
					public void run() {
						notesListView.setAdapter(arrayAdapter);  //refreshes the listView
					}
				});
			}
			else {
				sendCommand(command[0]);
			}
			return null;
		}

		protected void onProgressUpdate(Void... progress) {}
		protected void onPostExecute(String result) {}
	}

	/**
	 * Fire an intent to start the speech recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	/**
	 * Handle the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it could have heard
			ArrayList<String> matches = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);
			notes.add(matches.get(0)); //What is recognized by the speech input
			new DownloadFilesTask().execute("WriteInternal" , null, null);
			String writenotes = "";
			for(int i = 0; i < notes.size(); i++) { writenotes+=notes.get(i) + "\n"; }
			if(isNetworkAvailable()) { new DownloadFilesTask().execute("writenotes" + writenotes , null, null); }

		}
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				tts = new TextToSpeech(getApplicationContext(), this);
			}
			else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void onInit(int status) {       
		if (status == TextToSpeech.SUCCESS) {

		}
		else if (status == TextToSpeech.ERROR) {
			Toast.makeText(Notes.this,
					"Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
		}
	}
}