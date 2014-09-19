package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.MyProgramsArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.Program;
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

public class Tab_MyPrograms extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = Tab_MyPrograms.this.getClass().getSimpleName();

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<Program> programList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_myprogram_view, container, false);

		mReceiver = new RemindMeResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
		listView = (ListView) rootView.findViewById(R.id.my_prg_listview);
		
		progressDialog = ProgressDialog.show(getActivity(),
			ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);

		final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
	    serviceIntent.setAction(ApplicationConstants.SERVICE_GET_PROGRAMS);
	    serviceIntent.putExtra("receiver", mReceiver);
	    getActivity().startService(serviceIntent);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				RemindMeApplicationContext.getRemindMeApplicationContext().setProgram(null);
				RemindMeApplicationContext.getRemindMeApplicationContext().setSelectedRecomProgram(null);
				
				RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
				RemindMeApplicationContext.getRemindMeApplicationContext().setProgram(programList.get(position));
				
				getSherlockActivity().getSupportActionBar().setSelectedNavigationItem(0);
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
			programList = RemindMeApplicationContext.getRemindMeApplicationContext().getMyProgramsList();
			displayMyPrograms(programList);
			break;
		case 2:
			Log.d(TAG, "ERROR");
			showErrorAlert(resultData.getString("message"));
			break;
		default:
			break;
		}

	}
	
    private void displayMyPrograms(ArrayList<Program> programs) {
    	MyProgramsArrayAdapter myProgramArrayAdapter = new MyProgramsArrayAdapter(getActivity(), R.layout.myprogram_item_row, programs);
        listView.setAdapter(myProgramArrayAdapter);
        listView.setDividerHeight(0);
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
