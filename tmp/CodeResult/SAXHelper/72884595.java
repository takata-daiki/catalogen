package com.ianl.prayertimes;

import java.net.MalformedURLException;
import android.content.Context;
import android.os.AsyncTask;

/**
 * AsyncTask to perform the URL connection and XML parsing
 * in a background thread, off the main UI thread.
 * 
 * @author Tehmur
 *
 */
public class PrayerTimeLoadingTask extends AsyncTask<String, Void, PrayerTimings> {	
	private Context mContext;
	
	public PrayerTimeLoadingTask(Context context) {
		mContext = context;
	}

	@Override
	protected PrayerTimings doInBackground(String... urls) {
		SAXHelper sh = null;
		try {
			sh = new SAXHelper(urls[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sh.parseContent();
	}
	
}