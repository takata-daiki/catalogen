package ca.imgd.jlee.gpsmap.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.imgd.jlee.gpsmap.R;
import ca.imgd.jlee.gpsmap.activity.MainActivity;
import ca.imgd.jlee.gpsmap.activity.UpdateGpsLocationActivity;
import ca.imgd.jlee.gpsmap.data.PicturesManager;
import ca.imgd.jlee.gpsmap.model.GpsLocation;

import com.androidquery.AQuery;


public class GpsLocationAdapter extends ArrayAdapter<GpsLocation>
{

  private final List<GpsLocation> list;
  private final Activity context;

  public GpsLocationAdapter(Activity context, List<GpsLocation> list)
  {
    super(context, R.layout.gps_location_single_list_item, list);
    this.context = context;
    this.list = list;
  }

  @Override
  public int getCount()
  {
    return list.size();
  }

  @Override
  public void clear()
  {
    list.clear();
  }

  @Override
  public void add(GpsLocation gpsLocation)
  {
    list.add(gpsLocation);
  }

  @Override
  public GpsLocation getItem(int position)
  {
    return list.get(position);
  }

  @Override
  public long getItemId(int position)
  {
    return position;
  }


  static class ViewHolder
  {
    public ImageView ivThumb;
    public TextView tvId;
    public TextView tvDesc;
    public Button btnDeselect;
    public Button btnUpdate;
    public Button btnDelete;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent)
  {
    final ViewHolder holder;

    if (convertView == null)
    {
      LayoutInflater inflator = context.getLayoutInflater();
      convertView = inflator.inflate(R.layout.gps_location_single_list_item,
                                     null);


      holder = new ViewHolder();
      holder.ivThumb = (ImageView) convertView
          .findViewById(R.id.ivListItemThumb);
      holder.tvId = (TextView) convertView.findViewById(R.id.ListItemId);
      holder.tvDesc = (TextView) convertView.findViewById(R.id.ListItemDesc);
      holder.btnDeselect = (Button) convertView
          .findViewById(R.id.btnListItemDeselect);
      holder.btnUpdate = (Button) convertView
          .findViewById(R.id.btnListItemUpdate);
      holder.btnDelete = (Button) convertView
          .findViewById(R.id.btnListItemDelete);

      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }

    final GpsLocation gpsLocation = list.get(position);
    holder.tvId.setText("" + list.get(position).getId());
    holder.tvId.setTag(gpsLocation);
    holder.tvDesc.setText(list.get(position).description);

    // show thumbnail for the gps location item:
    AQuery aQuery = new AQuery(convertView);
    if (gpsLocation.picName == null)
    {
      aQuery.id(holder.ivThumb).image(R.drawable.ic_launcher);
    }
    else
    {
      String thumbSource = PicturesManager.fullPath(gpsLocation.picName);
      aQuery.id(holder.ivThumb).image(thumbSource);
    }

    holder.btnDeselect.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        gpsLocation.selected = GpsLocation.NO;
        gpsLocation.save();
        holder.btnDeselect.setText("DESELECTED");
      }
    });

    holder.btnUpdate.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // Launching new Activity on selecting single List Item
        Intent updateIntent = new Intent(context,
            UpdateGpsLocationActivity.class);
        // sending data to new activity
        Bundle bundle = new Bundle();
        bundle.putInt("gpsLocationId", gpsLocation.getId().intValue());
        updateIntent.putExtras(bundle);
        context.startActivity(updateIntent);
      }
    });

    holder.btnDelete.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        gpsLocation.delete();
        new PicturesManager().deletePicture(gpsLocation.picName);
        list.remove(position);
        notifyDataSetChanged();
      }
    });

    return convertView;
  }
}
