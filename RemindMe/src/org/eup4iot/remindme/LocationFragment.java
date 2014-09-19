package org.eup4iot.remindme;

import java.util.ArrayList;
import java.util.List;

import org.eup4iot.remindme.adapter.LocationArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.Location;
import org.eup4iot.remindme.service.GetDataService;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class LocationFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = LocationFragment.this.getClass().getSimpleName();
	
	public static LocationFragment locationFragment = null;

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private Spinner locationsSpinner = null;
    
    private LocationArrayAdapter arrayAdapter = null;
    
    private ArrayList<Location> locationResult = null;
    
    private int locStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.location_fragment_view, container, false);

		try {
			mReceiver = new RemindMeResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			
			locationFragment = this;
			
			Button btnDone = (Button) rootView.findViewById(R.id.btn_done);		
			listView = (ListView) rootView.findViewById(R.id.where_listview);
			locationsSpinner = (Spinner) rootView.findViewById(R.id.sp_locations);
	
			progressDialog = ProgressDialog.show(getActivity(),
					ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);	
				
			final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
		    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_LOCATIONS);
		    serviceIntent.putExtra("receiver", mReceiver);
		    getActivity().startService(serviceIntent);
		        
			if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
				if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getLocationList() == null) {
					locStatus = 0;
				} else {
					locStatus = 1;
				}
			} else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
				locStatus = 2;
			}
	
					
			locationsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	
			    	ArrayList<Location> searchResults = new ArrayList<Location>();
		            String spinnerSelectedItem = parentView.getItemAtPosition(position).toString();
		            
		            if(spinnerSelectedItem.equals("All")) {
			            displayResults(locationResult);
		            } else {
			            for(Location locations : locationResult) {
			            	if(locations.getLocationCategory().contains(spinnerSelectedItem)) {
			            		searchResults.add(locations);
			            	}
			            }
			            
			            displayResults(searchResults);
		            }
			    }
	
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
	
			    }
			});
		    
		    btnDone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
	        		ArrayList<Location> selectedList = new ArrayList<Location>();
	        		for(Location loc : locationResult) {
	        			if(loc.isSelected()) {
		        			selectedList.add(loc);
		        		}
	        		}
	        		
					if(locStatus == 0 || locStatus == 1) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setLocationList(selectedList);
					} if(locStatus == 2) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setLocationList(selectedList);
					}
					
					goBack();				
				}
			});
	    
		} catch(Exception ex) {
			Log.e(TAG, "onCreateView: " + ex.toString());
		}
		
		return rootView;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        
		switch (resultCode) {
		case 0:
			Log.d(TAG, "Running");
		case 1:
			Log.d(TAG, "SUCESS");
			try {
				ArrayList<Location> resultList = RemindMeApplicationContext.getRemindMeApplicationContext().getLocationsList();
				
				locationResult = resultList;
				
				populateLocationSpinner(resultList);
				displayResults(resultList);
			} catch(Exception ex) {
				Log.e(TAG, "onReceiveResult: " + ex.toString());
			}
			break;
		case 2:
			Log.d(TAG, "ERROR");
			showErrorAlert(resultData.getString("message"));
			break;
		default:
			break;
		}
	}
	
	public void populateLocationSpinner(ArrayList<Location> locations) {
		List<String> locationsArray =  new ArrayList<String>();
		
		locationsArray.add("All");
		for(Location loc : locations) {
			locationsArray.add(loc.getLocationCategory());
		}
	    
	    ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locationsArray);
	    locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    locationsSpinner.setAdapter(locationAdapter);
	}
    
    private void displayResults(ArrayList<Location> locations) {
    	this.arrayAdapter = new LocationArrayAdapter(getActivity(), R.layout.location_item_row, locations);
        listView.setAdapter(this.arrayAdapter);
        listView.setDividerHeight(0);
    }
    
    public void changeSwitchStatus(int position, boolean status) {    	
		locationResult.get(position).setSelected(status);
    }
    
    public void goBack() {
    	getFragmentManager().popBackStack();
    }

	private void showErrorAlert(String message) {
		AlertDialog invalidAlert = new AlertDialog.Builder(getActivity()).create();
		invalidAlert.setIcon(android.R.drawable.ic_dialog_info);
		invalidAlert.setTitle(getString(R.string.error));
		invalidAlert.setMessage(message);
		invalidAlert.setButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});

		invalidAlert.show();
	}
}
