package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.ChannelArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.Channel;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ChannelFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = ChannelFragment.this.getClass().getSimpleName();
	
	public static ChannelFragment channelFragment = null;

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<Channel> resultList = null;
    
    private int chaStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.channel_fragment_view, container, false);

		try {
			mReceiver = new RemindMeResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			
			channelFragment = this;
			
			Button btnDone = (Button) rootView.findViewById(R.id.btn_done);		
			listView = (ListView) rootView.findViewById(R.id.channel_listview);
			
			progressDialog = ProgressDialog.show(getActivity(),
					ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);
				
			final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
		    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_CHANNELS);
		    serviceIntent.putExtra("receiver", mReceiver);
		    getActivity().startService(serviceIntent);
		    
			if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
				if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getChannelList() == null) {
					chaStatus = 0;
				} else {
					chaStatus = 1;
				}
			} else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
				chaStatus = 2;
			}
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					
				}
			});
			
		    btnDone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
	        		ArrayList<Channel> selectedList = new ArrayList<Channel>();
	        		for(Channel sel : resultList) {
	        			if(sel.isSelected()) {
		        			selectedList.add(sel);
		        		}
	        		}
	        		
					if(chaStatus == 0 || chaStatus == 1) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setChannelList(selectedList);
					} if(chaStatus == 2) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setChannelList(selectedList);
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
				resultList = RemindMeApplicationContext.getRemindMeApplicationContext().getChannelsList();
				displayResult(resultList);
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
	
    private void displayResult(ArrayList<Channel> resultList) {
    	ChannelArrayAdapter resultAdapter = new ChannelArrayAdapter(getActivity(), R.layout.channel_item_row, resultList);
        listView.setAdapter(resultAdapter);
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
