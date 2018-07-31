package androidlab.exercise3_1.fragments;

import java.util.ArrayList;
import java.util.Collections;

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
import androidlab.exercise3_1.R;
import androidlab.exercise3_1.datatypes.Reminder;
import androidlab.exercise3_1.db.DatabaseManager;
import androidlab.exercise3_1.elements.ReminderAdapter;
import androidlab.exercise3_1.main.MainWindow;

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

  public Today() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.today, container, false);

	ArrayList<Reminder> reminders = DatabaseManager.getInstance().getTable_reminders().getTodaysReminders();

	ReminderAdapter adapter = new ReminderAdapter(view.getContext(), R.layout.list_row, reminders);
	listView = (ListView) view.findViewById(R.id.today_list);
	listView.setAdapter(adapter);

	IntentFilter updateFilter = new IntentFilter(MainWindow.UPDATE_INTENT);
	getActivity().registerReceiver(updateReceiver, updateFilter);

	view.findViewById(R.id.today_button_new_reminder).setOnClickListener(new OnClickListener() {

	  @Override
	  public void onClick(View v) {
		Intent openEditWindow = new Intent("Androidlab.main.window.edit");
		getActivity().sendBroadcast(openEditWindow);

	  }
	});

	return view;

  }

  private BroadcastReceiver updateReceiver = new BroadcastReceiver() {

	@Override
	public void onReceive(Context context, Intent intent) {

	  ArrayList<Reminder> reminders = DatabaseManager.getInstance().getTable_reminders().getTodaysReminders();
	  ReminderAdapter adapter = new ReminderAdapter(context, R.layout.list_row, reminders);
	  listView.setAdapter(adapter);

	}
  };

}
