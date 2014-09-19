package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.ObjectArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.SmartObject;
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
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ObjectFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = ObjectFragment.this.getClass().getSimpleName();

	private RemindMeResultReceiver mReceiver = null;
	
	public static ObjectFragment objectFragment = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<SmartObject> smartObjectsList = null;
    
    int objStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.object_fragment_view, container, false);

		mReceiver = new RemindMeResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
		objectFragment = this;
		
		listView = (ListView) rootView.findViewById(R.id.obj_listview);
		        
        if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
        	objStatus = 0;
        } else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
        	objStatus = 2;
        }
        
		progressDialog = ProgressDialog.show(getActivity(),
				ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);
			
		final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
	    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_OBJECTS);
	    serviceIntent.putExtra("receiver", mReceiver);
	    getActivity().startService(serviceIntent);

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
				smartObjectsList = RemindMeApplicationContext.getRemindMeApplicationContext().getSmartObjectsList();
				displayResults(smartObjectsList);
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
	
    private void displayResults(ArrayList<SmartObject> smartObjectsList) {
    	ObjectArrayAdapter arrayAdapter = new ObjectArrayAdapter(getActivity(), R.layout.object_item_row, smartObjectsList);
        listView.setAdapter(arrayAdapter);
        listView.setDividerHeight(0);
    }
    
    public void setSelectedSmartObject(int position) {

		if(objStatus == 0) {
			RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
			RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setSmartObject(smartObjectsList.get(position));
		} else if(objStatus == 2) {
			RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setSmartObject(smartObjectsList.get(position));
		}
		goBack();
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
