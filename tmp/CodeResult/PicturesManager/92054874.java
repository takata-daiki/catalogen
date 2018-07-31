package ca.imgd.jlee.gpsmap.activity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.imgd.jlee.gpsmap.R;
import ca.imgd.jlee.gpsmap.data.PicturesManager;
import ca.imgd.jlee.gpsmap.model.GpsLocation;
import ca.imgd.jlee.gpsmap.view.ThemeManager;

import com.androidquery.AQuery;


public class BaseGpsLocationFormActivity extends Activity
{
  protected static final int REQUEST_IMAGE_CAPTURE = 0;

  protected long timestamp;
  protected int selected;
  protected String desc;
  protected String content;
  protected Bitmap bmpToSave;
  protected GpsLocation gpsLocation;
  protected AQuery aQuery;

  protected TextView tvMessage;
  protected Button btnTakePicture;
  
  protected TextView tvLatitude;
  protected TextView tvLongitude;
  protected TextView tvTimestamp;
  protected EditText etDesc;
  protected EditText etContent;
  protected Button btnSave;

  protected void onCreate(Bundle savedInstanceState, int dynamicPart)
  {
    setTheme(ThemeManager.getTheme());
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_gps_location_form);

    bmpToSave = null;
    aQuery = new AQuery(this);
    tvMessage = (TextView) findViewById(R.id.tvMessage);
    btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
    tvLatitude = (TextView) findViewById(R.id.tvLatitude);
    tvLongitude = (TextView) findViewById(R.id.tvLongitude);
    tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
    etDesc = (EditText) findViewById(R.id.etDesc);
    etContent = (EditText) findViewById(R.id.etContent);
    btnSave = (Button) findViewById(R.id.btnSaveLocUpdate);

    // inflate any additional part of the form coming from 
    // the children activities:
    ViewStub vsDynamicPart = (ViewStub) findViewById(R.id.vsDynamicPart);
    vsDynamicPart.setLayoutResource(dynamicPart);
    vsDynamicPart.inflate();

    // get current date & time in Millis since Epoch
    timestamp = Calendar.getInstance().getTimeInMillis();

    // display current time stamp
    String strTimestamp = DateFormat.getDateTimeInstance().format(timestamp);
    tvTimestamp.setText(strTimestamp);


    btnTakePicture.setOnClickListener(new OnClickListener()
    {

      @Override
      public void onClick(View v)
      {
        btnTakePicture.setEnabled(false);
        // start camera to take picture of the current location:
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                                   ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
      }
    });
  }


  // saves location information in the database
  protected void saveLocation()
  {
    gpsLocation.description = desc;
    gpsLocation.content = content;
    gpsLocation.selected = selected;
    gpsLocation.save();

    finish();
  }

  // validates description and content fields
  // both fields must not be empty.
  protected boolean validate()
  {
    desc = etDesc.getText().toString().trim();
    content = etContent.getText().toString().trim();

    if (desc.isEmpty())
    {
      tvMessage.setText("Please provide description of this location.");
      return false;
    }
    if (content.isEmpty())
    {
      tvMessage.setText("Please provide content of this location.");
      return false;
    }

    return true;
  }

  // shows picture of the given filename on the image view
  protected void showPicture(String fileName)
  {
    aQuery.id(R.id.ivPicture).image(PicturesManager.fullPath(fileName));
  }

  // shows picture of the given bitmap data on the image view
  protected void showPicture(Bitmap bmp)
  {
    aQuery.id(R.id.ivPicture).image(bmp);
  }

  // saves the gps location picture as file in the cache dir
  protected File saveLocationPicture(String filename)
  {
    try
    {
      return new PicturesManager().saveBmpAsFile(bmpToSave, filename);
    }
    catch(IOException e )
    {
      Toast.makeText(this, "IO Exception occurred while saving the picture.",
                     Toast.LENGTH_LONG).show();
      return null;
    }
  }

  // executed after having taken a picture. sets the pic as a bmp to save
  // and then shows the picture on the image view
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    btnTakePicture.setEnabled(true);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
    {
      bmpToSave = (Bitmap) data.getExtras().get("data");
      showPicture(bmpToSave);
    }
  }

}
