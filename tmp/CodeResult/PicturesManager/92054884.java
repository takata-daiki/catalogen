package ca.imgd.jlee.gpsmap.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import ca.imgd.jlee.gpsmap.R;
import ca.imgd.jlee.gpsmap.data.PicturesManager;
import ca.imgd.jlee.gpsmap.generator.GpsLocationGenerator;
import ca.imgd.jlee.gpsmap.model.GpsLocation;
import ca.imgd.jlee.gpsmap.view.BaseFilterDialog;
import ca.imgd.jlee.gpsmap.view.FilterDialogFactory;
import ca.imgd.jlee.gpsmap.view.ThemeManager;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;


public class MainActivity extends Activity
{
  public static Context appContext;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    appContext = this;

    setTheme(ThemeManager.getTheme());

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  // handles all the clicks on the menu items.
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int menuItemId = item.getItemId();
    switch (menuItemId)
    {
    case R.id.liveMap:
      startActivity(new Intent(this, LiveMapActivity.class));
      return true;
    case R.id.generateData:
      Toast.makeText(this, "Generated Random Locations ", Toast.LENGTH_SHORT)
          .show();
      final int TO_GENERATE = 6;
      new GpsLocationGenerator().generate(TO_GENERATE);
      return true;
    case R.id.deleteData:
      Toast.makeText(this, "Deleted All Locations ", Toast.LENGTH_SHORT).show();
      new Delete().from(GpsLocation.class).execute();
      new PicturesManager().deleteAllPictures();
      return true;
    case R.id.allLocations:
      showGpsLocations(GpsLocation.getAll());
      return true;
    case R.id.newLocation:
      startActivity(new Intent(this, NewGpsLocationActivity.class));
      return true;
      
      // gps location filter criteria:
    case R.id.bySelectionStatus:
    case R.id.byTime:
    case R.id.byDescription:
    case R.id.byContent:
      popupFilterDialog(menuItemId);
      return true;

      // theme configuration:
    case R.id.themeWater:
      ThemeManager.changeTheme(this, R.style.Theme_Water);
      return true;
    case R.id.themeFire:
      ThemeManager.changeTheme(this, R.style.Theme_Fire);
      return true;
    }
    return false;
  }

  public void clickButtonGoToMap(View view)
  {
    startActivity(new Intent(this, ShowMapActivity.class));
  }

  public void clickButtonCreateNewLocation(View view)
  {
    startActivity(new Intent(this, NewGpsLocationActivity.class));
  }

  // passes all the gps locations onto the next activity to show them
  // to the user.
  public void clickButtonAllSelectedLocations(View view)
  {
    List<GpsLocation> list = new Select().from(GpsLocation.class)
        .where("Selected =  ?", true).execute();

    showGpsLocations(list);
  }

  // turns on and off the selection status of all the locations in the datbase.
  public void handleSelectButtonToggle(View view)
  {
    boolean selected = view.getId() == R.id.buttonSelectAll;
    List<GpsLocation> list = GpsLocation.getAll();

    for (int pos = 0; pos < list.size(); pos++)
    {
      GpsLocation gpsLocation = list.get(pos);
      gpsLocation.selected = GpsLocation.selected(selected);
      gpsLocation.save();
    }
  }

  //======================================================================
  //  METHODS RELATED TO SHOWING GPS LOCATIONS
  //======================================================================
  public void clickButtonAllLocations(View view)
  {
    showGpsLocations(GpsLocation.getAll());
  }

  public void handleButtonFilterBy(View v)
  {
    popupFilterDialog(v.getId());
  }

  // prompts a filter dialog that filters gps locations using the 
  // given filter id as the criteria.
  private void popupFilterDialog(int filterId)
  {
    BaseFilterDialog dialog = FilterDialogFactory.getFilterDialog(filterId);
    dialog.show(getFragmentManager(), null);
  }

  // extracts ids from "list" and passes it onto the next activity which
  // is ShowGpsLocationActivity.
  public void showGpsLocations(List<GpsLocation> list)
  {
    long[] ids = new long[list.size()];
    for (int i = 0; i < list.size(); i++)
      ids[i] = list.get(i).getId();

    Bundle bundle = new Bundle();
    Intent intent = new Intent(this, ShowGpsLocationsActivity.class);
    bundle.putLongArray("gpsLocationIds", ids);
    intent.putExtras(bundle);
    startActivity(intent);
  }

}
