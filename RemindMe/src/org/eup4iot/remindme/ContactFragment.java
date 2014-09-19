package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.ContactArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.User;
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

public class ContactFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = ContactFragment.this.getClass().getSimpleName();
	
	public static ContactFragment contactFragment = null;

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<User> resultList = null;
    
    private int contStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.contact_fragment_view, container, false);
		
		try {
			mReceiver = new RemindMeResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			
			contactFragment = this;
			
			listView = (ListView) rootView.findViewById(R.id.contact_listview);
			Button btnDone = (Button) rootView.findViewById(R.id.btn_done);
			
			progressDialog = ProgressDialog.show(getActivity(),
					ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);
				
			final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
		    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_USERS);
		    serviceIntent.putExtra("receiver", mReceiver);
		    getActivity().startService(serviceIntent);
		        
			if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
				if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getUserList() == null) {
					contStatus = 0;
				} else {
					contStatus = 1;
				}
			} else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
				contStatus = 2;
			}
	
		    btnDone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
	        		ArrayList<User> selectedList = new ArrayList<User>();
	        		for(User sel : resultList) {
	        			if(sel.isSelected()) {
		        			selectedList.add(sel);
		        		}
	        		}
	        		
					if(contStatus == 0 || contStatus == 1) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setUserList(selectedList);
					} if(contStatus == 2) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setUserList(selectedList);
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
				resultList = RemindMeApplicationContext.getRemindMeApplicationContext().getUsersList();
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
	
    private void displayResults(ArrayList<User> resultList) {
    	ContactArrayAdapter arrayAdapter = new ContactArrayAdapter(getActivity(), R.layout.contact_item_row, resultList);
        listView.setAdapter(arrayAdapter);
        listView.setDividerHeight(0);
    }
    
    public void changeSwitchStatus(int position, boolean status) {
    	resultList.get(position).setSelected(status);
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
