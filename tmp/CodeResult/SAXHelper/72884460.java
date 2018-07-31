package com.ianl.podcasts;

import java.net.MalformedURLException;
import java.util.ArrayList;

import com.ianl.podcasts.PodcastCategoryList.PodcastsCategories;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class PodcastLoadingTask extends AsyncTask<String, Void, ArrayList<Podcast>> {	
	private Context mContext;
	private PodcastsCategories mPodcastCategory;
	private ProgressDialog mDialog;
	
	public PodcastLoadingTask(Context context, PodcastsCategories podcastType) {
		mContext = context;
		mPodcastCategory = podcastType;
	}
	
	@Override
	protected void onPreExecute() {
		mDialog = new ProgressDialog(mContext);
	    mDialog.setMessage("Loading. Please wait...");
	    mDialog.setIndeterminate(true);
	    mDialog.show();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Podcast> doInBackground(String... urls) {
		SAXHelper sh = null;
		try {
			sh = new SAXHelper(urls[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sh.parseContent(mPodcastCategory);
	}

	@Override
	protected void onPostExecute(ArrayList<Podcast> s) {
		if (mDialog.isShowing()) {
	        mDialog.dismiss();
	    }
		super.onPostExecute(s);
	}
	
}