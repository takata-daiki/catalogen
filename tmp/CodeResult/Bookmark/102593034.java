package com.piab.catalogue.android;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import com.piab.catalogue.android.R;
import com.piab.catalogue.android.ListAdapter.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import ec.hana.Controller.AddonSettingsHotspotController;
import ec.hana.Core.JsonToDB;
import ec.hana.Core.Settings;
import ec.hana.Core.Util;
import ec.hana.Model.BaseModel;

import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class Bookmark extends ListActivity {

	/** Called when the activity is first created. */

	ArrayList<String> notes;
	ArrayList<Integer> cell_ids;
	ArrayList<Integer> bkmId, page_no_list;
	ArrayList<Integer> hotspot_id, cat_ids;
	ArrayList img_path;
	ArrayList hotspot_title;
	boolean editStatus = true;
	public static boolean show_all;
	// ListView mList;
	public static int cell_id = 0;
	int pos, catalogue_id, flag = 0;
	Context cntx;
	TextView title;
	Adapter adapter;
	public static boolean home_flag = false;
	Button btnEdit = null;
	Button btnDone = null;
	public static int position_value = 0;
	public static Dialog dialog = null;
	public static boolean bookmark_value = false;
	public int delete_count = 0;

	public static int bookmark_locale_flag = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bookmark_list);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// mList = (ListView) findViewById(R.id.bookmark_list);
		title = (TextView) findViewById(R.id.title);
		title.setText(getString(R.string.myBookmarks));
		// try {

		Bundle bundle = getIntent().getExtras();
		notes = bundle.getStringArrayList("messages");
		cell_ids = bundle.getIntegerArrayList("cell_ids");
		bkmId = bundle.getIntegerArrayList("bkmId");
		show_all = bundle.getBoolean("show_all");
		hotspot_id = bundle.getIntegerArrayList("hotspot_id");
		img_path = bundle.getStringArrayList("img_path");

		hotspot_title = bundle.getStringArrayList("hotspot_title");
		cat_ids = bundle.getIntegerArrayList("cat_ids");
		home_flag = bundle.getBoolean("home_flag");
		if (show_all)
			cntx = getApplicationContext();
		else
			cntx = getApplicationContext();
		/*
		 * } catch (Exception e) {
		 * Util.log("Error while reading bundle of Bookmark %s", e.toString());
		 * }
		 */

		Button btnBack = (Button) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (HomeView.home_back_flag == true) {
					HomeView.home_back_flag = false;
					Intent home_intent = new Intent(Bookmark.this,
							HomeView.class);
					home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(home_intent);

				}

				finish();

			}
		});

		try {
			btnEdit = (Button) findViewById(R.id.edit);
			btnDone = (Button) findViewById(R.id.done);

			btnDone.setVisibility(View.INVISIBLE);
			// btnEdit.setText(getString(R.string.edit));
			btnDone.setText(getString(R.string.done));
		} catch (Exception e) {
			// TODO: handle exception
		}
		btnEdit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// editStatus = false;
				// btnDone.setVisibility(View.VISIBLE);
				// btnEdit.setVisibility(View.INVISIBLE);
				// title.setText("Edit");
				// JsonToDB showBookmark = new JsonToDB("Bookmark", "bkmId");
				// showBookmark.update_list(show_all, cntx, getListView(),
				// editStatus, null);
			}
		});
		try {
			btnDone.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// editStatus = true;
					// title.setText("Bookmarks");
					// btnEdit.setVisibility(View.VISIBLE);
					// btnDone.setVisibility(View.INVISIBLE);
					// JsonToDB showBookmark = new JsonToDB("Bookmark",
					// "bkmId");
					// showBookmark.update_list(show_all, cntx, getListView(),
					// editStatus, null);

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		adapter = new Adapter(show_all, cntx, true, getListView());
		adapter.setData(notes, bkmId, cell_ids, hotspot_id, img_path,
				hotspot_title,cat_ids);
		setListAdapter(adapter);

		registerForContextMenu(getListView());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Util.error("Book mark clicked");
		if (!editStatus) {
			try {

				if (Settings.flag == 1) { // This for temp if condition. i will
											// change this condition soon.
											// why this condition means while
											// edit the bookmark it will not
											// respont in proper lost posison
											// after any one delete bookmark
					if (flag != 0)
						position = position + 1;

					flag = 1;
				}
				JSONObject data_toc = BaseModel.ObjectForId(
						"eccatalogues.Bookmark", bkmId.get(position), "bkmId");
				String notes = data_toc.get("notes").toString();

				Bookmark_Edit(notes, cell_ids.get(position),
						bkmId.get(position));

			} catch (Exception e) {
				Util.log(e.toString());
			}
		} else {
			try {

				position_value = position;
				
//				CellPointer.toc_landscape_clear_flag=true;
				CatalogueGridView.temp_falg =0;
				new LoadAsyncTask().execute();

				finish();

			} catch (Exception e) {
				e.printStackTrace();

				Util.log("Error while opening catalogue from bookmark %s",
						e.toString());
			}
		}
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);

	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		int index = info.position;

		switch (item.getItemId()) {

		case R.id.edit:

			editStatus = true;
			// btnDone.setVisibility(View.VISIBLE);
			// btnEdit.setVisibility(View.INVISIBLE);
			// title.setText("Edit");

			try {

				// if (Settings.flag == 1) { // This for temp if condition. i
				// will
				// // change this condition soon.
				// // why this condition means while
				// // edit the bookmark it will not
				// // respont in proper lost posison
				// // after any one delete bookmark
				//
				//
				// index = index + 1;
				//
				//
				// }

				JSONObject data_toc = BaseModel.ObjectForId(
						"eccatalogues.Bookmark", bkmId.get(index), "bkmId");
				String notes = data_toc.get("notes").toString();

				Bookmark_Edit(notes, cell_ids.get(index), bkmId.get(index));

			} catch (Exception e) {
				Util.log(e.toString());

			}

			return true;

		case R.id.delete:

			JsonToDB deleteBookmark = new JsonToDB("Bookmark", "cell_id");
			// Settings.flag = 1;
			try {

				// if(delete_count == 0){
				deleteBookmark.delete(show_all, this.getApplicationContext(),
						bkmId.get(index), getListView(), true);

				bkmId.remove(index);

				// }
				// else {

				// deleteBookmark.delete(show_all, this.getApplicationContext(),
				// bkmId.get(index+delete_count), getListView(), true);
				// Toast.makeText(getApplicationContext(),
				// "else sank", Toast.LENGTH_LONG).show();
				adapter.notifyDataSetChanged();

				// }
				// delete_count+=1;

			} catch (Exception e) {
				Util.trace(e.toString());
			}

			return true;

		case R.id.view:

			try {

				position_value = index;

				new LoadAsyncTask().execute();
				finish();

			} catch (Exception e) {
				e.printStackTrace();

				Util.log("Error while opening catalogue from bookmark %s",
						e.toString());
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if (HomeView.home_back_flag == true) {
				HomeView.home_back_flag = false;

				Intent home_intent = new Intent(Bookmark.this, HomeView.class);

				startActivity(home_intent);

			}

			finish();

			return true;
		}
		return false;
	}

	protected void Bookmark_Edit(final String notes, final int cell_id,
			final int bkmId) {

		dialog = new Dialog(this);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.customalert);

		dialog.setCanceledOnTouchOutside(true);

		dialog.setCancelable(true);

		Util.show_keypad(getApplicationContext());

		final EditText go_text = (EditText) dialog.findViewById(R.id.goto_text);

		go_text.setHint(getString(R.string.addBookmarkPlaceholder));
		go_text.setMaxLines(5);
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(100);
		go_text.setFilters(FilterArray);
		go_text.setText(notes);
		go_text.setImeOptions(0x00000006);

		go_text.requestFocus();

		go_text.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				if (actionId == EditorInfo.IME_ACTION_DONE
						|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					try {
						Util.hide_keypad(go_text, cntx);

						dialog.dismiss();
						JSONObject Bookmark_Object = new JSONObject();
						JsonToDB SaveBookmark = new JsonToDB("Bookmark",
								"bkmId");
						Bookmark_Object.put("bkmId", bkmId);

						Bookmark_Object.put("modified", new Date().toString());
						Bookmark_Object.put("notes", go_text.getText()
								.toString());

						SaveBookmark.save(Bookmark_Object, false);
						SaveBookmark.update_list(show_all,
								getApplicationContext(), getListView(),
								editStatus, null);
					} catch (Exception e) {

					}

				}

				return false;
			}
		});

		Button cancel = (Button) dialog.findViewById(R.id.cancel_goto);

		Button ok = (Button) dialog.findViewById(R.id.ok_goto);

		ok.setText(getString(R.string.ok));

		dialog.setCancelable(false);

		dialog.show();

		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Util.hide_keypad(go_text, cntx);
					dialog.dismiss();
					JSONObject Bookmark_Object = new JSONObject();
					JsonToDB SaveBookmark = new JsonToDB("Bookmark", "bkmId");
					Bookmark_Object.put("bkmId", bkmId);

					Bookmark_Object.put("modified", new Date().toString());
					Bookmark_Object.put("notes", go_text.getText().toString());

					SaveBookmark.save(Bookmark_Object, false);
					SaveBookmark.update_list(show_all, getApplicationContext(),
							getListView(), editStatus, null);
					// adapter.notifyDataSetChanged();
				} catch (Exception e) {
					// Toast.makeText(getApplicationContext(), e.toString(),
					// 1000).show();
				}
				dialog.dismiss();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Util.hide_keypad(go_text, getApplicationContext());
				dialog.dismiss();

			}
		});

	}

	private class LoadAsyncTask extends AsyncTask<Void, Void, Void> {
		private final ProgressDialog ECdialog = new ProgressDialog(
				Bookmark.this);

		// can use UI thread here
		protected void onPreExecute() {
			this.ECdialog.setMessage(getApplicationContext().getString(
					R.string.loading));
			this.ECdialog.setCancelable(false);
			this.ECdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			return null;
		}

		protected void onPostExecute(Void result) {
			try {

				cell_id = cell_ids.get(position_value);
				catalogue_id = cat_ids.get(position_value);
				Util.error("position value" + position_value);
				Util.error("Cell id " + cell_id);
				
//				CellPointer.current_cell = cell_id;

				if (show_all) {
					Bookmark.bookmark_locale_flag = 1;

					Intent catalogue = new Intent(getApplicationContext(),
							Catalogue.class);
					Util.error("Cell id show_all" + cell_id);
					Util.error("Cat id" +catalogue_id);
					Bundle bundle = new Bundle();
					bundle.putInt("id", catalogue_id);
					bundle.putInt("current_cell_id", cell_id);
					catalogue.putExtras(bundle);
					startActivity(catalogue);

				}

				if (hotspot_id.get(position_value) == 0) {

					int x = Catalogue.content_holder.getWidth();
					cell_id = cell_ids.get(position_value);
					ECScrollView.pagevalue_set = true;
					ECScrollView.thumb_load_flag = false;
					Catalogue.content_holder.remove_all_normalviews();
					Catalogue.controller.navigate_to_page(cell_id, 0);

				} else {
					String product_id = null;
					try {
						JSONObject data_product = BaseModel.ObjectForId(
								"eccatalogues.HotspotProductinformation",
								hotspot_id.get(position_value), "id");
						product_id = data_product.optString("product_id");
					} catch (Exception e) {
						e.toString();
					}
					Bookmark.bookmark_value = true;
					Intent hot_spot = new Intent(getApplicationContext(),
							HotpotView.class);
					hot_spot.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					Bundle bundle = new Bundle();
					bundle.putInt("cell_id", cell_id);
					bundle.putInt("catalogue_id", catalogue_id);
					bundle.putString("product_id", product_id);
					// bundle.putString("url",
					// "http://46.137.7.79/eccatalogues/hotspotproduct/?product="+produce_id+"&cell_id="+cell_id);
					hot_spot.putExtras(bundle);
					startActivity(hot_spot);
					show_all = false;
					// AddonSettingsHotspotController.getProductViewControl(cell_ids.get(position),product_id);
				}

				if (this.ECdialog.isShowing()) {
					this.ECdialog.dismiss();

				}

			} catch (Exception e) {

			}
		}
	}

}