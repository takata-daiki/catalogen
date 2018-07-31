package androidlab.exercise4_1.fragments;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidlab.exercise4_1.R;
import androidlab.exercise4_1.datatypes.Reminder;
import androidlab.exercise4_1.db.DatabaseManager;
import androidlab.exercise4_1.elements.ReminderAdapterToday;
import androidlab.exercise4_1.main.MainWindow;
import androidlab.exercise4_1.service.GPSTracker;

/* 
 * Copyright (c) 2012 Leander Sabel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Today extends Fragment {

  /**
   * The fragment argument representing the section number for this fragment.
   */
  public static final String ARG_SECTION_NUMBER = "section_number";

  // This is the Adapter being used to display the list's data
  private ListView listView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.today, container, false);
	

	ArrayList<Reminder> reminders = DatabaseManager.getInstance().getTable_reminders().getTodaysReminders();

	ReminderAdapterToday adapter = new ReminderAdapterToday(view.getContext(), R.layout.list_row, reminders);
	listView = (ListView) view.findViewById(R.id.today_list);
	listView.setAdapter(adapter);

	// Register the update receiver
	IntentFilter updateFilter = new IntentFilter(MainWindow.UPDATE_INTENT);
	getActivity().registerReceiver(updateReceiver, updateFilter);

	// Handle the click on the create new reminder button
	view.findViewById(R.id.today_button_new_reminder).setOnClickListener(new OnClickListener() {

	  @Override
	  public void onClick(View v) {
		Intent openEditWindow = new Intent(MainWindow.WINDOW_EDIT_INTENT);
		getActivity().sendBroadcast(openEditWindow);
	  }
	});

//	GPSTracker gps = new GPSTracker(view.getContext());
//
//	Toast.makeText(view.getContext(), "Attempting to get GPS location", Toast.LENGTH_LONG).show();
//	if (gps.canGetLocation()) {
//
//	  double latitude = gps.getLatitude();
//	  double longitude = gps.getLongitude();
//
//	  // \n is for new line
//	  Toast.makeText(view.getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//	}
//	else {
//	  // can't get location
//	  // GPS or Network is not enabled
//	  // Ask user to enable GPS/network in settings
//	  gps.showSettingsAlert();
//	}

	return view;
  }

  private BroadcastReceiver updateReceiver = new BroadcastReceiver() {

	@Override
	public void onReceive(Context context, Intent intent) {

	  ArrayList<Reminder> reminders = DatabaseManager.getInstance().getTable_reminders().getTodaysReminders();
	  ReminderAdapterToday adapter = new ReminderAdapterToday(context, R.layout.list_row, reminders);
	  listView.setAdapter(adapter);

	}
  };

}
