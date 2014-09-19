package org.eup4iot.remindme.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eup4iot.remindme.PropertyFragment;
import org.eup4iot.remindme.R;
import org.eup4iot.remindme.model.ObjectProperty;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PropertyArrayAdapter extends ArrayAdapter<ObjectProperty> {

    /** The tag. */
    private final String TAG = PropertyArrayAdapter.this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    private Context mcontext = null;
    
    int layoutResourceId;

    public PropertyArrayAdapter(Context context, int textViewResourceId, List<ObjectProperty> items) {
        super(context, textViewResourceId, items);
        this.mcontext = context;
        this.layoutResourceId = textViewResourceId;
        this.mInflater = ((Activity)context).getLayoutInflater();
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        try {
	        if (convertView == null) {
	            convertView = mInflater.inflate(layoutResourceId, null);
	            holder = new ViewHolder();
	            
	            holder.item_name = (TextView) convertView.findViewById(R.id.txt_prop);
	            holder.prop_switch = (Switch) convertView.findViewById(R.id.prop_switch);
	            holder.prop_condition = (Spinner) convertView.findViewById(R.id.sp_prop_condition);
	            holder.prop_value = (Spinner) convertView.findViewById(R.id.sp_prop_value);
	            
	            convertView.setTag(holder);            
	        } else {
	            // Get the ViewHolder back to get fast access to the TextView
	            // and the ImageView.
	            holder = (ViewHolder) convertView.getTag();
	        }
	        
			List<String> conditionArray =  new ArrayList<String>();
			conditionArray.add("");
			conditionArray.add("=");
			conditionArray.add(">");
			conditionArray.add("<");
			conditionArray.add(">=");
			conditionArray.add("<=");
		    
			List<String> valuesArray =  new ArrayList<String>();
			valuesArray.add("");
			valuesArray.add("10");
			valuesArray.add("20");

		    ArrayAdapter<String> condAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_spinner_item, conditionArray);
		    condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
		    ArrayAdapter<String> valueAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_spinner_item, valuesArray);
		    valueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
		    holder.prop_condition.setAdapter(condAdapter);
		    holder.prop_value.setAdapter(valueAdapter);
		    
		    final ObjectProperty dElement = getItem(position);
		    
		    if(dElement != null) {
		    	holder.item_name.setText(dElement.getObjectPropertyName());
		    	holder.prop_switch.setChecked(dElement.isSelected());
		    	
		    	if(dElement.getObjectPropertyComparisonOperator() != null) {
		    		int spinnerPosition = condAdapter.getPosition(dElement.getObjectPropertyComparisonOperator());	            	
		    		holder.prop_condition.setSelection(spinnerPosition);
		    	}
		    	
		    	if(dElement.getObjectPropertyValue() != null) {
		    		int spinnerPosition = valueAdapter.getPosition(dElement.getObjectPropertyValue());	            	
		    		holder.prop_value.setSelection(spinnerPosition);
		    	}		    	
		    	
		        holder.prop_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		        	
		            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		            	
		            	if(!isChecked) {
		        		    holder.prop_condition.setSelection(0);
		        		    holder.prop_value.setSelection(0);
		            		
		            		if(PropertyFragment.propertyFragment != null) {
		            			PropertyFragment.propertyFragment.changePropertyStatus(position, false, null, null);
		            		}
		            	}
		            }
		        });
		        
		        holder.prop_value.setOnItemSelectedListener(new OnItemSelectedListener() {
	            	
	                @Override
	                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int spinnerPosition, long id) {
	                    String spinnerSelectedItem = parentView.getItemAtPosition(spinnerPosition).toString();

	                    if(!spinnerSelectedItem.equals("")) {
	                    	
			            	if(PropertyFragment.propertyFragment != null) {

			            		if(holder.prop_switch.isChecked() == true) {
			            			
				                    String condSpinnerSelectedItem = holder.prop_condition.getItemAtPosition(holder.prop_condition.getSelectedItemPosition()).toString();
				                    
				                    if(!condSpinnerSelectedItem.equals("")) {
				            			PropertyFragment.propertyFragment.changePropertyStatus(position, true, condSpinnerSelectedItem, spinnerSelectedItem);
				                    }			                    

			            		}
			            	}
	                    }
	                }

	                @Override
	                public void onNothingSelected(AdapterView<?> parentView) {
	                    // your code here
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
        
        Switch prop_switch;
        
        Spinner prop_condition;
        
        Spinner prop_value;
    }
}
