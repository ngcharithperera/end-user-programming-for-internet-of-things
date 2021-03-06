package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.RecommendationArrayAdapter;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.RecommendedProgram;
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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public class Tab_Recommendations extends SherlockFragment implements
RemindMeResultReceiver.Receiver{
	
	final String TAG = Tab_Recommendations.this.getClass().getSimpleName();

	private RemindMeResultReceiver mReceiver = null;
	
    private ProgressDialog progressDialog = null;
    
    private ListView listView = null;
    
    private ArrayList<RecommendedProgram> recomPrograms = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_recommendations_view, container, false);
        
		mReceiver = new RemindMeResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
		listView = (ListView) rootView.findViewById(R.id.recomm_listview);
		
		progressDialog = ProgressDialog.show(getActivity(),
			ApplicationConstants.EMPTY_STRING, getString(R.string.please_wait), true);
		
		final Intent serviceIntent = new Intent(getActivity(), GetDataService.class);
        serviceIntent.setAction(ApplicationConstants.SERVICE_GET_RECOMMENDATIONS);
        serviceIntent.putExtra("receiver", mReceiver);
        getActivity().startService(serviceIntent);
				
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				RemindMeApplicationContext.getRemindMeApplicationContext().setProgram(null);
				RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
				
				RemindMeApplicationContext.getRemindMeApplicationContext().setSelectedRecomProgram(recomPrograms.get(position));
				
				if(recomPrograms.get(position).getSmartObjectList() != null) {
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.getSelectedRecomProgram().setSmartObject(recomPrograms.get(position).getSmartObjectList().get(0));
				}
				
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
			try {
				recomPrograms = RemindMeApplicationContext.getRemindMeApplicationContext().getRecommendedProgramsList();
				displayRecommendations(recomPrograms);
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
	
    private void displayRecommendations(ArrayList<RecommendedProgram> recomPrograms) {
    	RecommendationArrayAdapter recommArrayAdapter = new RecommendationArrayAdapter(getActivity(), R.layout.recomm_item_row, recomPrograms);
        listView.setAdapter(recommArrayAdapter);
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
