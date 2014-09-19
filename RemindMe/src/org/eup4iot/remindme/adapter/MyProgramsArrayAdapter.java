package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.Program;
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

public class MyProgramsArrayAdapter extends ArrayAdapter<Program> {

    /** The tag. */
    private final String TAG = MyProgramsArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public MyProgramsArrayAdapter(Context context, int textViewResourceId, List<Program> items) {
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
	  
	            holder.program_name = (TextView) convertView.findViewById(R.id.name);
	            holder.program_desc = (TextView) convertView.findViewById(R.id.description);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final Program dElements = getItem(position);
	        
	        if(dElements != null) {
	        	holder.program_name.setText(dElements.getProgramName());
	        	holder.program_desc.setText(dElements.getProgramDesc());
	        	
		        if (dElements.getProgramIconURL() != null) {
					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElements.getProgramIconURL();
					aq.id(R.id.itemimage)
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
    	
        TextView program_name;
        
        TextView program_desc;
    }
}
