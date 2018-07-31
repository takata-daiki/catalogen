package ca.imgd.jlee.gpsmap.activity;

import java.io.File;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import ca.imgd.jlee.gpsmap.R;
import ca.imgd.jlee.gpsmap.data.PicturesManager;
import ca.imgd.jlee.gpsmap.model.GpsLocation;

import com.activeandroid.query.Select;


public class UpdateGpsLocationActivity extends BaseGpsLocationFormActivity
{
  private CheckBox cbLocSelected;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState, R.layout.activity_update_gps_location);

    Bundle bundle = getIntent().getExtras();
    int locationId = bundle.getInt("gpsLocationId");
    gpsLocation = new Select().from(GpsLocation.class)
        .where("Id = ?", locationId).executeSingle();

    showPicture(gpsLocation.picName);

    // display latitude and longitude
    tvLatitude.setText(Double.valueOf(gpsLocation.latitude).toString());
    tvLongitude.setText(Double.valueOf(gpsLocation.longitude).toString());

    etDesc.setText(gpsLocation.description);
    etContent.setText(gpsLocation.content);

    cbLocSelected = (CheckBox) findViewById(R.id.cbLocSelected);
    cbLocSelected.setChecked(gpsLocation.selected==GpsLocation.YES);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.update_gps_location, menu);
    return true;
  }

  public void handleSaveAction(View v)
  {
    if (!validate())
      return;

    if (bmpToSave != null)
    {
      // new picture has been taken. 

      // save the new picture:
      String filename = Long.toString(timestamp);
      File pic = saveLocationPicture(filename);

      if (pic == null)
        return;

      // delete old pic
      new PicturesManager().deletePicture(gpsLocation.picName);

      // update database
      gpsLocation.picName = pic.getName();
    }

    selected = GpsLocation.selected(cbLocSelected.isChecked());
    saveLocation();
  }

}
