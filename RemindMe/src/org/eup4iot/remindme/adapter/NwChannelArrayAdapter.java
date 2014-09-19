package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.Channel;

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
import android.widget.ImageView;
import android.widget.TextView;

public class NwChannelArrayAdapter extends ArrayAdapter<Channel> {

    /** The tag. */
    private final String TAG = NwChannelArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public NwChannelArrayAdapter(Context context, int textViewResourceId, List<Channel> items) {
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
	  
	            holder.item_name = (TextView) convertView.findViewById(R.id.item_name);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final Channel dElement = getItem(position);
	        
	        if(dElement != null) {
	        	holder.item_name.setText(dElement.getChannelName());
	        	
		        if (dElement.getChannelIconURL() != null) {
					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElement.getChannelIconURL();
					aq.id(R.id.item_img)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {			                                
				                iv.setImageBitmap(bm);     
				        }			        
				    });				
				}
	        }
	        
        } catch (Exception e) {
			Log.e(TAG, "getView: " + e.toString());
		}
        return convertView;
    }

    
    static class ViewHolder {
    	
        TextView item_name;
    }
}
