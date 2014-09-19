package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.PropertyArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.ObjectProperty;
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
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class PropertyFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = PropertyFragment.this.getClass().getSimpleName();
	
	public static PropertyFragment propertyFragment = null;

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<ObjectProperty> propertyList = null;
    
    private int propStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.property_fragment_view, container, false);

		mReceiver = new RemindMeResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
		propertyFragment = this;
		
		Button btnDone = (Button) rootView.findViewById(R.id.btn_done);
		listView = (ListView) rootView.findViewById(R.id.property_listview);
		
		progressDialog = ProgressDialog.show(getActivity(),
				ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);
		
		final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
        serviceIntent.setAction(ApplicationConstants.SERVICE_GET_PROPERTIES);
        serviceIntent.putExtra("receiver", mReceiver);
        getActivity().startService(serviceIntent);
        
		if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
			if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getObjectPropertyList() == null) {
				propStatus = 0;
			} else {
				propStatus = 1;
			}
		} else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
			propStatus = 2;
		}

	    
	    btnDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
        		ArrayList<ObjectProperty> selectedList = new ArrayList<ObjectProperty>();
        		for(ObjectProperty sel : propertyList) {
        			if(sel.isSelected()) {
	        			selectedList.add(sel);
	        		}
        		}
        		
				if(propStatus == 0 || propStatus == 1) {
					RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
					RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setObjectPropertyList(selectedList);
				} if(propStatus == 2) {
					RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
					RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setObjectPropertyList(selectedList);
				}
				
				goBack();				
			}
		});
		
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
				propertyList = RemindMeApplicationContext.getRemindMeApplicationContext().getPropertyList();
				displayResults(propertyList);
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
	
    private void displayResults(ArrayList<ObjectProperty> propertyList) {
    	PropertyArrayAdapter arrayAdapter = new PropertyArrayAdapter(getActivity(), R.layout.property_item_row, propertyList);
        listView.setAdapter(arrayAdapter);
        listView.setDividerHeight(0);
    }
    
    public void changePropertyStatus(int position, boolean status, String condition, String value) {
    	propertyList.get(position).setSelected(status);
		propertyList.get(position).setObjectPropertyComparisonOperator(condition);
		propertyList.get(position).setObjectPropertyValue(value);
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
