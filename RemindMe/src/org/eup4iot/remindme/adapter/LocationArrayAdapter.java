package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.ActivityFragment;
import org.eup4iot.remindme.LocationFragment;
import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.Location;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LocationArrayAdapter extends ArrayAdapter<Location> {

    /** The tag. */
    private final String TAG = LocationArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public LocationArrayAdapter(Context context, int textViewResourceId, List<Location> items) {
        super(context, textViewResourceId, items);
        this.mcontext = context;
        this.layoutResourceId = textViewResourceId;
        this.mInflater = ((Activity)context).getLayoutInflater();
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {
	        if (convertView == null) {
	            convertView = mInflater.inflate(layoutResourceId, null);
	            holder = new ViewHolder();
	  
	            holder.item_name = (TextView) convertView.findViewById(R.id.name);
	            holder.where_switch = (Switch) convertView.findViewById(R.id.where_switch);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final Location dElement = getItem(position);
	        
	        if(dElement != null) {
	        	holder.item_name.setText(dElement.getLocationName());
		    	holder.where_switch.setChecked(dElement.isSelected());
	        	
		        if (dElement.getLocationIconURL() != null) {
					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElement.getLocationIconURL();
					aq.id(R.id.itemimage).progress(R.id.progressBar1)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
				                iv.setImageBitmap(bm);     
				        }			        
				    });				
				}
		        
		        holder.where_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		        	
		            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            	
		            	if(isChecked == true) {

		            		if(LocationFragment.locationFragment != null) {
		            			LocationFragment.locationFragment.changeSwitchStatus(position, true);
		            		}
		            	} else {
		            		if(LocationFragment.locationFragment != null) {
		            			LocationFragment.locationFragment.changeSwitchStatus(position, false);
		            		}
		            	}
		            }
		        });
	        }
	        
        } catch (Exception e) {
			Log.e(TAG, "getView: " + e.toString());
		}
        return convertView;
    }

    
    static class ViewHolder {
    	
        TextView item_name;
        
        Switch where_switch;
    }
}
