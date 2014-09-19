package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.TaskArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.Task;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class TaskFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = TaskFragment.this.getClass().getSimpleName();

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<Task> taskList = null;
    
    private int taskStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.task_fragment_view, container, false);

		try {
			mReceiver = new RemindMeResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			
			listView = (ListView) rootView.findViewById(R.id.task_listview);
			
	        if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
	        	taskStatus = 0;
	        } else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
	        	taskStatus = 2;
	        }	
	        
    		progressDialog = ProgressDialog.show(getActivity(),
    				ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);

    		final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
    	    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_TASKS);
    	    serviceIntent.putExtra("receiver", mReceiver);
    	    getActivity().startService(serviceIntent);
	
			
			listView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					
					if(taskStatus == 0 || taskStatus == 1) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setTask(taskList.get(position));
					} else if(taskStatus == 2) {
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setTask(taskList.get(position));
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
				taskList = RemindMeApplicationContext.getRemindMeApplicationContext().getTasksList();
				displayResults(taskList);
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
	
    private void displayResults(ArrayList<Task> tasks) {
    	TaskArrayAdapter arrayAdapter = new TaskArrayAdapter(getActivity(), R.layout.task_item_row, tasks);
        listView.setAdapter(arrayAdapter);
        listView.setDividerHeight(0);
    }
    
    public void goBack() {
    	getFragmentManager().popBackStack();
    }

	private void showErrorAlert(String message) {
		AlertDialog invalidAlert = new AlertDialog.Builder(getActivity()).create();
		invalidAlert.setIcon(android.R.drawable.ic_dialog_info);
		invalidAlert.setTitle(getString(R.string.error));
		invalidAlert.setMessage(message);
		invalidAlert.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});

		invalidAlert.show();
	}
}
