package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.RecommendedProgram;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;

public class RecommendationArrayAdapter extends ArrayAdapter<RecommendedProgram> {

    /** The tag. */
    private final String TAG = RecommendationArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public RecommendationArrayAdapter(Context context, int textViewResourceId, List<RecommendedProgram> items) {
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
	            holder.program_date = (TextView) convertView.findViewById(R.id.date);
	            holder.program_image = (ImageView) convertView.findViewById(R.id.itemimage);
	            holder.program_rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
	        final RecommendedProgram dElements = getItem(position);
	        
	        if(dElements != null) {
	        	holder.program_name.setText(dElements.getCreatorFullName());
	        	holder.program_date.setText(dElements.getCreatorDate());
	        	holder.program_rating.setRating(dElements.getProgramRating());
	        	
	            AQuery aq = null;
	            
	            //this image is huge, avoid memory caching
	            boolean memCache = false;
	            boolean fileCache = true;

/*				if (dElements.getImagePath() != null) {
					
					aq = new AQuery(convertView);
					String imageUrl = dElements.getImagePath();
					aq.id(R.id.category_image).progress(R.id.progressBar)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
				                iv.setImageBitmap(bm);     
				        }			        
				    });				
				} else {
					aq.id(R.id.category_image).image(R.drawable.pizza_bg);
				}*/
	        }
	        
        } catch (Exception e) {
			Log.e(TAG, "getView: " + e.toString());
		}
        return convertView;
    }

    
    static class ViewHolder {
    	
        TextView program_name;
        
        TextView program_desc;
        
        TextView program_date;
        
        ImageView program_image;
        
        RatingBar program_rating;
    }
}
