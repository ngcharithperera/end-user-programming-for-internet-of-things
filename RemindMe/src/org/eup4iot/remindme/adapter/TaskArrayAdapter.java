package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.Task;

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

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    /** The tag. */
    private final String TAG = TaskArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public TaskArrayAdapter(Context context, int textViewResourceId, List<Task> items) {
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
	            holder.item_image = (ImageView) convertView.findViewById(R.id.itemimage);
	            holder.description = (TextView) convertView.findViewById(R.id.description);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final Task dElements = getItem(position);
	        
	        if(dElements != null) {
	        	holder.item_name.setText(dElements.getTaskName());
	        	holder.description.setText(dElements.getTaskDescription());
	        	
		        if (dElements.getTaskIconURL() != null) {
					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElements.getTaskIconURL();
					aq.id(R.id.itemimage).progress(R.id.progressBar1)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
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
        
        ImageView item_image;
        
        TextView description;
    }
}
