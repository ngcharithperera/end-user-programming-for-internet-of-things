package org.eup4iot.remindme;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class CalenderFragment extends SherlockFragment implements
		RemindMeResultReceiver.Receiver {

	final String TAG = CalenderFragment.this.getClass().getSimpleName();

	private RemindMeResultReceiver mReceiver = null;
	
    private int calStatus = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.calender_fragment_view, container, false);

		try {
			mReceiver = new RemindMeResultReceiver(new Handler());
			mReceiver.setReceiver(this);
			
			Button btnDone = (Button) rootView.findViewById(R.id.btn_done);
			Button btnInfo = (Button) rootView.findViewById(R.id.icn_info);
			
			final Spinner inDays = (Spinner) rootView.findViewById(R.id.sp_in_days);
			final Spinner inDuration = (Spinner) rootView.findViewById(R.id.sp_in_duration);
			
			final Spinner nextDays = (Spinner) rootView.findViewById(R.id.sp_next_days);
			final Spinner nextDuration = (Spinner) rootView.findViewById(R.id.sp_next_duration);
			
			final Switch inSwitch = (Switch) rootView.findViewById(R.id.in_switch);
			final Switch nextSwitch = (Switch) rootView.findViewById(R.id.next_switch);
			
			final CheckBox chkRepeat = (CheckBox) rootView.findViewById(R.id.chk_repeat);
			
			final CheckBox chkWinter = (CheckBox) rootView.findViewById(R.id.chk_winter);
			final CheckBox chkAutum = (CheckBox) rootView.findViewById(R.id.chk_autum);
			final CheckBox chkSpring = (CheckBox) rootView.findViewById(R.id.chk_spring);
			final CheckBox chkSummer = (CheckBox) rootView.findViewById(R.id.chk_summer);
			
			final CheckBox chkMon = (CheckBox) rootView.findViewById(R.id.chk_mon);
			final CheckBox chkTue = (CheckBox) rootView.findViewById(R.id.chk_tue);
			final CheckBox chkWed = (CheckBox) rootView.findViewById(R.id.chk_wed);
			final CheckBox chkThu = (CheckBox) rootView.findViewById(R.id.chk_thu);
			final CheckBox chkFri = (CheckBox) rootView.findViewById(R.id.chk_fri);		
			final CheckBox chkSat = (CheckBox) rootView.findViewById(R.id.chk_sat);
			final CheckBox chkSun = (CheckBox) rootView.findViewById(R.id.chk_sun);
			
			final CheckBox chkDay = (CheckBox) rootView.findViewById(R.id.chk_day);
			final CheckBox chkNight = (CheckBox) rootView.findViewById(R.id.chk_night);
			
			List<String> daysArray =  new ArrayList<String>();
			daysArray.add("");
			daysArray.add("1");
			daysArray.add("2");
			daysArray.add("3");
			daysArray.add("4");
		    
			List<String> durationArray =  new ArrayList<String>();
			durationArray.add("");
			durationArray.add("days");
			durationArray.add("weeks");
			durationArray.add("months");
			durationArray.add("years");
	
		    ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, daysArray);
		    daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
		    ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, durationArray);
		    durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    
			inDays.setAdapter(daysAdapter);
			inDuration.setAdapter(durationAdapter);
			
			nextDays.setAdapter(daysAdapter);
			nextDuration.setAdapter(durationAdapter);
			
			if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
				if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender() == null) {
					calStatus = 0;
				} else {
					calStatus = 1;
					
					chkRepeat.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isRepeat());
					
					chkWinter.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isWINTER());
					chkAutum.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isAUTUMN());
					chkSpring.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isSPRING());
					chkSummer.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isSUMMER());
					
					chkMon.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isMONDAY());
					chkTue.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isTUESDAY());
					chkWed.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isWEDNESDAY());
					chkThu.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isTHURSDAY());
					chkFri.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isFRIDAY());
					chkSat.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isSATURDAY());
					chkSun.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isSUNDAY());
					
					chkDay.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isDAYTIME());
					chkNight.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isNIGHTTIME());
					
					inSwitch.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isRange());
					nextSwitch.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().isNextWhen());
		
		        	String rangeValue = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().getRangeValue();
		        	if(rangeValue != null) {
			            int spinnerPosition = daysAdapter.getPosition(rangeValue);
			            inDays.setSelection(spinnerPosition);
		        	}
		        	
		        	String rangeUQuanti = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().getRangeTimeQuantifier();
		        	if(rangeUQuanti != null) {
			            int spinnerPosition = durationAdapter.getPosition(rangeUQuanti);
			            inDuration.setSelection(spinnerPosition);
		        	}
		        	
		        	String nextValue = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().getNextValue();
		        	if(nextValue != null) {
			            int spinnerPosition = daysAdapter.getPosition(nextValue);
			            nextDays.setSelection(spinnerPosition);
		        	}
		        	
		        	String nextUQuanti = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().getNextTimeQuantifier();
		        	if(nextUQuanti != null) {
			            int spinnerPosition = durationAdapter.getPosition(nextUQuanti);
			            nextDuration.setSelection(spinnerPosition);
		        	}
				}
			} else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
				calStatus = 2;
				
				chkRepeat.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isRepeat());
				
				chkWinter.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isWINTER());
				chkAutum.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isAUTUMN());
				chkSpring.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isSPRING());
				chkSummer.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isSUMMER());
				
				chkMon.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isMONDAY());
				chkTue.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isTUESDAY());
				chkWed.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isWEDNESDAY());
				chkThu.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isTHURSDAY());
				chkFri.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isFRIDAY());
				chkSat.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isSATURDAY());
				chkSun.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isSUNDAY());
				
				chkDay.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isDAYTIME());
				chkNight.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isNIGHTTIME());
				
				inSwitch.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isRange());
				nextSwitch.setChecked(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().isNextWhen());
	
	        	String rangeValue = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().getRangeValue();
	        	if(rangeValue != null) {
		            int spinnerPosition = daysAdapter.getPosition(rangeValue);
		            inDays.setSelection(spinnerPosition);
	        	}
	        	
	        	String rangeUQuanti = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().getRangeTimeQuantifier();
	        	if(rangeUQuanti != null) {
		            int spinnerPosition = durationAdapter.getPosition(rangeUQuanti);
		            inDuration.setSelection(spinnerPosition);
	        	}
	        	
	        	String nextValue = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().getNextValue();
	        	if(nextValue != null) {
		            int spinnerPosition = daysAdapter.getPosition(nextValue);
		            nextDays.setSelection(spinnerPosition);
	        	}
	        	
	        	String nextUQuanti = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().getNextTimeQuantifier();
	        	if(nextUQuanti != null) {
		            int spinnerPosition = durationAdapter.getPosition(nextUQuanti);
		            nextDuration.setSelection(spinnerPosition);
	        	}
			}
	
			
			inSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            	
	            	if(isChecked == false) {
	            		
	            		if(calStatus == 2) {
							RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRepeat(false);
							RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRange(false);
							
				        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRangeValue(null);
				        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRangeTimeQuantifier(null);
	            		} else {
							RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRepeat(false);
							RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRange(false);
							
				        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRangeValue(null);
				        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRangeTimeQuantifier(null);
	            		}
			        	
			        	chkRepeat.setChecked(false);
			        	inDays.setSelection(0);
			        	inDuration.setSelection(0);
	            	}
	            }
	        });
	        
			nextSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            	
	            	if(isChecked == false) {
	            		
	            		if(calStatus == 2) {
	            			RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setNextWhen(false);
	            		} else {
	    					RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setNextWhen(false);
	            		}
						
						nextDays.setSelection(0);
			        	nextDuration.setSelection(0);
	            	}
	            }
	        });
		    
		    btnDone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(calStatus == 2) {
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setWINTER(chkWinter.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setAUTUMN(chkAutum.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setSPRING(chkSpring.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setSUMMER(chkSummer.isChecked());
		
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setMONDAY(chkMon.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setTUESDAY(chkTue.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setWEDNESDAY(chkWed.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setTHURSDAY(chkThu.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setFRIDAY(chkFri.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setSATURDAY(chkSat.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setSUNDAY(chkSun.isChecked());
		
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setDAYTIME(chkDay.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setNIGHTTIME(chkNight.isChecked());
						
						if(inSwitch.isChecked() == true) {
							RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRepeat(chkRepeat.isChecked());
							RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRange(inSwitch.isChecked());
							
							if(inDays.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = inDays.getItemAtPosition(inDays.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRangeValue(spinnerSelValue);
					    	}
							
							if(inDuration.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = inDuration.getItemAtPosition(inDuration.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setRangeTimeQuantifier(spinnerSelValue);
					    	}
						}
						
						if(nextSwitch.isChecked() == true) {
							RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setNextWhen(nextSwitch.isChecked());
							
							if(nextDays.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = nextDays.getItemAtPosition(nextDays.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setNextValue(spinnerSelValue);
					    	}
							
							if(nextDuration.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = nextDuration.getItemAtPosition(nextDuration.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender().setNextTimeQuantifier(spinnerSelValue);
					    	}
						}
					} else {
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setWINTER(chkWinter.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setAUTUMN(chkAutum.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setSPRING(chkSpring.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setSUMMER(chkSummer.isChecked());
		
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setMONDAY(chkMon.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setTUESDAY(chkTue.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setWEDNESDAY(chkWed.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setTHURSDAY(chkThu.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setFRIDAY(chkFri.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setSATURDAY(chkSat.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setSUNDAY(chkSun.isChecked());
		
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setDAYTIME(chkDay.isChecked());
						RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setNIGHTTIME(chkNight.isChecked());
						
						if(inSwitch.isChecked() == true) {
							RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRepeat(chkRepeat.isChecked());
							RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRange(inSwitch.isChecked());
							
							if(inDays.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = inDays.getItemAtPosition(inDays.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRangeValue(spinnerSelValue);
					    	}
							
							if(inDuration.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = inDuration.getItemAtPosition(inDuration.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setRangeTimeQuantifier(spinnerSelValue);
					    	}
						}
						
						if(nextSwitch.isChecked() == true) {
							RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setNextWhen(nextSwitch.isChecked());
							
							if(nextDays.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = nextDays.getItemAtPosition(nextDays.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setNextValue(spinnerSelValue);
					    	}
							
							if(nextDuration.getSelectedItemPosition() != 0) {		    		
					        	String spinnerSelValue = nextDuration.getItemAtPosition(nextDuration.getSelectedItemPosition()).toString();
					        	RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender().setNextTimeQuantifier(spinnerSelValue);
					    	}
						}
						
						RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(true);
					}
					goBack();				
				}
			});
		    
		    btnInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.calender_daytime_info_msg));				
				}
			});
	    
		} catch(Exception ex) {
			Log.e(TAG, "onCreateView: " + ex.toString());
		}
		
		return rootView;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {        
		switch (resultCode) {
		case 0:
			Log.d(TAG, "Running");
		case 1:
			Log.d(TAG, "SUCESS");
			break;
		case 2:
			Log.d(TAG, "ERROR");
			showErrorAlert(resultData.getString("message"));
			break;
		default:
			break;
		}

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
	
	private void showInfoAlert(String message) {
		AlertDialog infoAlert = new AlertDialog.Builder(getActivity()).create();
		infoAlert.setIcon(android.R.drawable.ic_dialog_info);
		infoAlert.setTitle(getString(R.string.help));
		infoAlert.setMessage(message);
		infoAlert.setButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});

		infoAlert.show();
	}

}
