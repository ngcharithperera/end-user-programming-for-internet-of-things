package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.ObjectFragment;
import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.SmartObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

public class ObjectArrayAdapter extends ArrayAdapter<SmartObject> {

    /** The tag. */
    private final String TAG = ObjectArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public ObjectArrayAdapter(Context context, int textViewResourceId, List<SmartObject> items) {
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
	  
	            holder.obj_name = (TextView) convertView.findViewById(R.id.object_name);
	            holder.obj_image = (ImageView) convertView.findViewById(R.id.itemimage);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final SmartObject dElements = getItem(position);
	        
	        if(dElements != null) {	 
	        	
	        	holder.obj_name.setText(dElements.getSmartObjectName());

		        if (dElements.getSmartObjectIconURL() != null) {					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElements.getSmartObjectIconURL();
					aq.id(R.id.itemimage).progress(R.id.progressBar1)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
				        	iv.setImageBitmap(bm);     
				        }			        
				    });				
				}
	        }
	        
        	holder.obj_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(ObjectFragment.objectFragment != null) {
						ObjectFragment.objectFragment.setSelectedSmartObject(position);
					}
				}
			});
	        
        } catch (Exception e) {
			Log.e(TAG, "getView: " + e.toString());
		}
        return convertView;
    }

    
    static class ViewHolder {
    	
        TextView obj_name;
        
        ImageView obj_image;
    }
}
