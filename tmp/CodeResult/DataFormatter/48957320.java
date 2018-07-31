package logue.robert.dayinformation.sqlite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import logue.robert.dayinformation.R;
import logue.robert.dayinformation.sqlite.DayInformationDBHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateFormatAdapter extends CursorAdapter {
	
	private final LayoutInflater mInflater;

	public DateFormatAdapter(Context context, Cursor c) {
		super(context, c,false);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		long time = cursor.getLong(cursor.getColumnIndex(DayInformationDBHelper.COLUMN_DATE)) * 1000L;
		String information = cursor.getString(cursor.getColumnIndex(DayInformationDBHelper.COLUMN_INFORMATION));

		
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        String format;
        
        if(isToday(cal))
        {
        	format = "h:mm";
        }
        else
        {
        	format = "dd/MM/yyyy";
        }
        
        SimpleDateFormat dataFormatter = new SimpleDateFormat(format,Locale.UK);
        String formattedDate = dataFormatter.format(cal.getTime());

        ((TextView) view.findViewById(R.id.date_entry)).setText(formattedDate);
        
        ((TextView) view.findViewById(R.id.information_entry)).setText(information);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.list_data, parent, false);
	}

	private static boolean isToday(Calendar dateToCheck) {
        if (dateToCheck == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        
        Calendar today = Calendar.getInstance();
        
        return (dateToCheck.get(Calendar.ERA) == today.get(Calendar.ERA) &&
        		dateToCheck.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
        		dateToCheck.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR));
    }
}
