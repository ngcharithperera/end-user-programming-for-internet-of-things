package org.eup4iot.remindme.adapter;

import java.util.List;

import org.eup4iot.remindme.ChannelFragment;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ChannelArrayAdapter extends ArrayAdapter<Channel> {

    /** The tag. */
    private final String TAG = ChannelArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;
    
    private boolean[] selected;

    public ChannelArrayAdapter(Context context, int textViewResourceId, List<Channel> items) {
        super(context, textViewResourceId, items);
        this.mcontext = context;
        this.layoutResourceId = textViewResourceId;
        this.mInflater = ((Activity)context).getLayoutInflater();
        selected = new boolean[items.size()];
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
	        convertView = mInflater.inflate(layoutResourceId, null);
	            
	        TextView item_name = (TextView) convertView.findViewById(R.id.chan_txt);
	        Switch item_switch = (Switch) convertView.findViewById(R.id.chan_switch);
	        
	        final Channel dElement = getItem(position);
	        
	        if(dElement != null) {
	        	item_name.setText(dElement.getChannelName());
		    	item_switch.setChecked(dElement.isSelected());
	        	
		        if (dElement.getChannelIconURL() != null) {
					
		            AQuery aq = null;
		            
		            //this image is huge, avoid memory caching
		            boolean memCache = false;
		            boolean fileCache = true;
		            
					aq = new AQuery(convertView);
					String imageUrl = dElement.getChannelIconURL();
					aq.id(R.id.chan_img).progress(R.id.progressBar1)
						.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
	
				        @Override
				        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
				                iv.setImageBitmap(bm);     
				        }			        
				    });				
				}
		        
		        item_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		        	
		            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            	
		            	if(isChecked == true) {
		            		selected[position] = true;
		            		
		            		if(ChannelFragment.channelFragment != null) {
		            			ChannelFragment.channelFragment.changeSwitchStatus(position, true);
		            		}
		            	} else {
		            		if(ChannelFragment.channelFragment != null) {
		            			ChannelFragment.channelFragment.changeSwitchStatus(position, false);
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

}
