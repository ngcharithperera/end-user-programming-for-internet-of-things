package org.eup4iot.remindme;

import java.util.ArrayList;

import org.eup4iot.remindme.adapter.NwActivityArrayAdapter;
import org.eup4iot.remindme.adapter.NwChannelArrayAdapter;
import org.eup4iot.remindme.adapter.NwLocationArrayAdapter;
import org.eup4iot.remindme.adapter.NwUserArrayAdapter;
import org.eup4iot.remindme.db.DatabaseManager;
import org.eup4iot.remindme.model.Activity_;
import org.eup4iot.remindme.model.Calender;
import org.eup4iot.remindme.model.Channel;
import org.eup4iot.remindme.model.Location;
import org.eup4iot.remindme.model.ObjectProperty;
import org.eup4iot.remindme.model.SmartObject;
import org.eup4iot.remindme.model.Task;
import org.eup4iot.remindme.model.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.meetme.android.horizontallistview.HorizontalListView;

public class Tab_NewProgram extends SherlockFragment {
	
	final String TAG = Tab_NewProgram.this.getClass().getSimpleName();
	
    private int prgStatus = 0;
    
	DatabaseManager dbMgr = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_newprogram_view, container, false);
        
    	try {
    		
    		dbMgr = new DatabaseManager(getActivity()); 
    		
	        Button btnObjectEdit = (Button) rootView.findViewById(R.id.object_img_edit);
	        Button btnPropertyEdit = (Button) rootView.findViewById(R.id.property_img_edit);
	        Button btnTaskEdit = (Button) rootView.findViewById(R.id.task_img_edit);
	
	        Button btnCalenderEdit = (Button) rootView.findViewById(R.id.when_img_edit);
	        Button btnLocationEdit = (Button) rootView.findViewById(R.id.where_img_edit);
	        Button btnActivityEdit = (Button) rootView.findViewById(R.id.while_img_edit);        
	        Button btnWhoEdit = (Button) rootView.findViewById(R.id.who_img_edit);
	        Button btnChannelEdit = (Button) rootView.findViewById(R.id.how_img_edit);
	        
	        Button btnObjectInfo = (Button) rootView.findViewById(R.id.icn_object_info);
	        Button btnTaskInfo = (Button) rootView.findViewById(R.id.icn_task_info);
	        Button btnPropertyInfo = (Button) rootView.findViewById(R.id.icn_property_info);
	        Button btnCalenderInfo = (Button) rootView.findViewById(R.id.icn_when_info);
	        Button btnLocationInfo = (Button) rootView.findViewById(R.id.icn_where_info);        
	        Button btnActivityInfo = (Button) rootView.findViewById(R.id.icn_while_info);
	        Button btnWhoInfo = (Button) rootView.findViewById(R.id.icn_who_info);
	        Button btnChannelInfo = (Button) rootView.findViewById(R.id.icn_how_info);
	
	        Button btnTaskNote = (Button) rootView.findViewById(R.id.task_note);
	        
	        Button btnTaskCheck = (Button) rootView.findViewById(R.id.task_img_check);
	        
	        Button btnSave = (Button) rootView.findViewById(R.id.btn_save);
	        
	        TextView objectName = (TextView) rootView.findViewById(R.id.object_name);
	        TextView txtWhen = (TextView) rootView.findViewById(R.id.when);
	        TextView txtWhen2 = (TextView) rootView.findViewById(R.id.when2);
	        TextView txtWhen3 = (TextView) rootView.findViewById(R.id.when3);
	        TextView txtWhen4 = (TextView) rootView.findViewById(R.id.when4);
	        TextView txtWhen5 = (TextView) rootView.findViewById(R.id.when5);
	        TextView txtTaskName = (TextView) rootView.findViewById(R.id.task);
	        
	        LinearLayout propertyContainer = (LinearLayout) rootView.findViewById(R.id.txtproperty_container);
	        
	        HorizontalListView nwWhereListView = (HorizontalListView) rootView.findViewById(R.id.nw_where_listview);
	        HorizontalListView nwWhileListView = (HorizontalListView) rootView.findViewById(R.id.nw_while_listview);
	        HorizontalListView nwWhoListView = (HorizontalListView) rootView.findViewById(R.id.nw_who_listview);
	        HorizontalListView nwHowListView = (HorizontalListView) rootView.findViewById(R.id.nw_how_listview);
	        
	        btnSave.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showProgSaveDialog();
				}
			});
	
	        btnObjectEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new ObjectFragment());
				}
			});
	        
	        btnPropertyEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new PropertyFragment());
				}
			});
	        
	        btnCalenderEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new CalenderFragment());
				}
			});
	        
	        btnLocationEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new LocationFragment());
				}
			});
	        
	        btnActivityEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new ActivityFragment());
				}
			});
	        
	        btnWhoEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new ContactFragment());
				}
			});
	        
	        btnChannelEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new ChannelFragment());
				}
			});
	        
	        btnTaskEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callFragment(new TaskFragment());
				}
			});
	        
	        
	        btnObjectInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.object_info_msg));
				}
			});
	        
	        btnTaskInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.task_info_msg));
				}
			});
	        
	        btnPropertyInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.property_info_msg));
				}
			});
	        
	        btnCalenderInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.calender_info_msg));
				}
			});
	        
	        btnLocationInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.location_info_msg));
				}
			});
	        
	        btnActivityInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.activity_info_msg));
				}
			});
	        
	        btnWhoInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.who_info_msg));
				}
			});
	        
	        btnChannelInfo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInfoAlert(getString(R.string.channel_info_msg));
				}
			});
	        
	        btnTaskNote.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showPropertyNoteDialog();
				}
			});
	        
	        btnTaskCheck.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					view.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tick));
				}
			});
	        
	        
	        if(RemindMeApplicationContext.getRemindMeApplicationContext().isSetPrgData()) {
	        	prgStatus = 1;
	        	Log.i(TAG, "================> prgStatus: " + prgStatus);
	        	/* set selected data */
	        	SmartObject smartObject = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getSmartObject();
	        	if(smartObject != null) {
	        		objectName.setText(smartObject.getSmartObjectName());
	        		
			        if (smartObject.getSmartObjectIconURL() != null) {
						
			            AQuery aq = null;
			            
			            //this image is huge, avoid memory caching
			            boolean memCache = false;
			            boolean fileCache = true;
			            
						aq = new AQuery(rootView);
						String imageUrl = smartObject.getSmartObjectIconURL();
						aq.id(R.id.object_img)
							.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
		
					        @Override
					        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
					                iv.setImageBitmap(bm);     
					        }			        
					    });				
					}
	        	}
	        	
	        	Task task = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getTask();
	        	if(task != null) {
	        		if(task.getTaskName() != null) {
	        			txtTaskName.setText("[" + task.getTaskName() + "]");
	        		}
	        	}
	        	
	        	Calender calender = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getCalender();
	        	if(calender != null) {
	    	        
	        		if(calender.getRangeValue() != null && calender.getRangeTimeQuantifier() != null) {
		        		String stringWhen = "[Every] [" + calender.getRangeValue() + "] [" + calender.getRangeTimeQuantifier() + "]";
		        		txtWhen.setText(stringWhen);
	        		}
	        		
	        		if(calender.getNextValue()!= null && calender.getNextTimeQuantifier() != null) {
		        		String stringWhen = "[next] [" + calender.getNextValue() + "] [" + calender.getNextTimeQuantifier() + "]";
		        		txtWhen2.setText(stringWhen);
	        		}
	        		
	        		String seasons = "";
	        		if(calender.isWINTER()) {
	        			seasons += "[" + getString(R.string.winter) + "] ";
	        		}
	        		if(calender.isAUTUMN()) {
	        			seasons += "[" + getString(R.string.autum) + "] ";
	        		}
	        		if(calender.isSPRING()) {
	        			seasons += "[" + getString(R.string.spring) + "] ";
	        		}
	        		if(calender.isSUMMER()) {
	        			seasons += "[" + getString(R.string.summer) + "] ";
	        		}
	        		
	        		if(!seasons.equals("")) {
	        			txtWhen3.setText(seasons);
	        		}
	        		
	        		String days = "";
	        		if(calender.isMONDAY()) {
	        			days += "[" + getString(R.string.mon) + "] ";
	        		}
	        		if(calender.isTUESDAY()) {
	        			days += "[" + getString(R.string.tue) + "] ";
	        		}
	        		if(calender.isWEDNESDAY()) {
	        			days += "[" + getString(R.string.wed) + "] ";
	        		}
	        		if(calender.isTHURSDAY()) {
	        			days += "[" + getString(R.string.thu) + "] ";
	        		}
	        		if(calender.isFRIDAY()) {
	        			days += "[" + getString(R.string.fri) + "] ";
	        		}
	        		if(calender.isSATURDAY()) {
	        			days += "[" + getString(R.string.sat) + "] ";
	        		}
	        		if(calender.isSUNDAY()) {
	        			days += "[" + getString(R.string.sun) + "] ";
	        		}
	        		
	        		if(!days.equals("")) {
	        			txtWhen4.setText(days);
	        		}
	        		
	        		String dayOfTime = "";
	        		if(calender.isDAYTIME()) {
	        			dayOfTime += "[" + getString(R.string.day) + "] ";
	        		}
	        		if(calender.isNIGHTTIME()) {
	        			dayOfTime += "[" + getString(R.string.night) + "] ";
	        		}
	        		
	        		if(!dayOfTime.equals("")) {
	        			txtWhen5.setText(dayOfTime);
	        		}
	        		
	        	}
	        	
	        	ArrayList<ObjectProperty> propertyList = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getObjectPropertyList();
	        	if(propertyList != null) {
	        		
	        		for(ObjectProperty obj : propertyList) {
	        			
		        		TextView view = (TextView) getActivity().getLayoutInflater().inflate(R.layout.property_selected_textview, null);
		        		String valProperty = "";
		        		
	        			if(obj.getObjectPropertyName() != null) {
	        				valProperty = "[" + obj.getObjectPropertyName() + "]";
	        			}
	        			
	        			if(obj.getObjectPropertyComparisonOperator() != null) {
	        				valProperty += " [" + obj.getObjectPropertyComparisonOperator() + "]";
	        			}
	        			
	        			if(obj.getObjectPropertyValue() != null) {
	        				valProperty += " [" + obj.getObjectPropertyValue() + "]";
	        			}
	        			
	        			view.setText(valProperty);
	        			propertyContainer.addView(view);
	        		}
	        	}
	        	
	        	ArrayList<Location> locationList = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getLocationList();
	        	if(locationList != null) {
	        		NwLocationArrayAdapter arrayAdapter = new NwLocationArrayAdapter(getActivity(), R.layout.hori_listview_item_row, locationList);
	                nwWhereListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<Activity_> activityList = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getActivityList();
	        	if(activityList != null) {
	        		NwActivityArrayAdapter arrayAdapter = new NwActivityArrayAdapter(getActivity(), R.layout.hori_listview_item_row, activityList);
	        		nwWhileListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<User> userList = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getUserList();
	        	if(userList != null) {
	        		NwUserArrayAdapter arrayAdapter = new NwUserArrayAdapter(getActivity(), R.layout.hori_listview_item_row, userList);
	        		nwWhoListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<Channel> channelList = RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().getChannelList();
	        	if(channelList != null) {
	        		NwChannelArrayAdapter arrayAdapter = new NwChannelArrayAdapter(getActivity(), R.layout.hori_listview_item_row, channelList);
	        		nwHowListView.setAdapter(arrayAdapter);
	        	}
	        	
	        } else if(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram() != null) {
	        	prgStatus = 0;
	        	Log.i(TAG, "================> prgStatus: " + prgStatus);
	        } else if(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram() != null) {
	        	prgStatus = 2;
	        	Log.i(TAG, "================> prgStatus: " + prgStatus);

	        	/* set selected data */
	        	SmartObject smartObject = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getSmartObject();
	        	if(smartObject != null) {
	        		objectName.setText(smartObject.getSmartObjectName());
	        		
			        if (smartObject.getSmartObjectIconURL() != null) {
						
			            AQuery aq = null;
			            
			            //this image is huge, avoid memory caching
			            boolean memCache = false;
			            boolean fileCache = true;
			            
						aq = new AQuery(rootView);
						String imageUrl = smartObject.getSmartObjectIconURL();
						aq.id(R.id.object_img)
							.image(imageUrl, memCache, fileCache, 0, 0, new BitmapAjaxCallback(){
		
					        @Override
					        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){			                                
					                iv.setImageBitmap(bm);     
					        }			        
					    });		
					}
	        	}
	        	
	        	Task task = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getTask();
	        	if(task != null) {
	        		
	        		if(task.getTaskName() != null) {
	        			txtTaskName.setText("[" + task.getTaskName() + "]");
	        		}
	        	}
	        	
	        	Calender calender = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getCalender();
	        	if(calender != null) {
	        		
	        		if(calender.getRangeValue() != null && calender.getRangeTimeQuantifier() != null) {
		        		String stringWhen = "[Every] [" + calender.getRangeValue() + "] [" + calender.getRangeTimeQuantifier() + "]";
		        		txtWhen.setText(stringWhen);
	        		}
	        		
	        		if(calender.getNextValue()!= null && calender.getNextTimeQuantifier() != null) {
		        		String stringWhen = "[next] [" + calender.getNextValue() + "] [" + calender.getNextTimeQuantifier() + "]";
		        		txtWhen2.setText(stringWhen);
	        		}
	        		
	        		String seasons = "";
	        		if(calender.isWINTER()) {
	        			seasons += "[" + getString(R.string.winter) + "] ";
	        		}
	        		if(calender.isAUTUMN()) {
	        			seasons += "[" + getString(R.string.autum) + "] ";
	        		}
	        		if(calender.isSPRING()) {
	        			seasons += "[" + getString(R.string.spring) + "] ";
	        		}
	        		if(calender.isSUMMER()) {
	        			seasons += "[" + getString(R.string.summer) + "] ";
	        		}
	        		
	        		if(!seasons.equals("")) {
	        			txtWhen3.setText(seasons);
	        		}
	        		
	        		String days = "";
	        		if(calender.isMONDAY()) {
	        			days += "[" + getString(R.string.mon) + "] ";
	        		}
	        		if(calender.isTUESDAY()) {
	        			days += "[" + getString(R.string.tue) + "] ";
	        		}
	        		if(calender.isWEDNESDAY()) {
	        			days += "[" + getString(R.string.wed) + "] ";
	        		}
	        		if(calender.isTHURSDAY()) {
	        			days += "[" + getString(R.string.thu) + "] ";
	        		}
	        		if(calender.isFRIDAY()) {
	        			days += "[" + getString(R.string.fri) + "] ";
	        		}
	        		if(calender.isSATURDAY()) {
	        			days += "[" + getString(R.string.sat) + "] ";
	        		}
	        		if(calender.isSUNDAY()) {
	        			days += "[" + getString(R.string.sun) + "] ";
	        		}
	        		
	        		if(!days.equals("")) {
	        			txtWhen4.setText(days);
	        		}
	        		
	        		String dayOfTime = "";
	        		if(calender.isDAYTIME()) {
	        			dayOfTime += "[" + getString(R.string.day) + "] ";
	        		}
	        		if(calender.isNIGHTTIME()) {
	        			dayOfTime += "[" + getString(R.string.night) + "] ";
	        		}
	        		
	        		if(!dayOfTime.equals("")) {
	        			txtWhen5.setText(dayOfTime);
	        		}
	        	}
	        	
	        	ArrayList<ObjectProperty> propertyList = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getObjectPropertyList();
	        	if(propertyList != null) {
	        		
	        		for(ObjectProperty obj : propertyList) {
	        			
		        		TextView view = (TextView) getActivity().getLayoutInflater().inflate(R.layout.property_selected_textview, null);
		        		String valProperty = "";
		        		
	        			if(obj.getObjectPropertyName() != null) {
	        				valProperty = "[" + obj.getObjectPropertyName() + "]";
	        			}
	        			
	        			if(obj.getObjectPropertyComparisonOperator() != null) {
	        				valProperty += " [" + obj.getObjectPropertyComparisonOperator() + "]";
	        			}
	        			
	        			if(obj.getObjectPropertyValue() != null) {
	        				valProperty += " [" + obj.getObjectPropertyValue() + "]";
	        			}
	        			
	        			view.setText(valProperty);
	        			propertyContainer.addView(view);
	        		}
	        	}
	        	
	        	ArrayList<Location> locationList = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getLocationList();
	        	if(locationList != null) {
	        		NwLocationArrayAdapter arrayAdapter = new NwLocationArrayAdapter(getActivity(), R.layout.hori_listview_item_row, locationList);
	                nwWhereListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<Activity_> activityList = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getActivityList();
	        	if(activityList != null) {
	        		NwActivityArrayAdapter arrayAdapter = new NwActivityArrayAdapter(getActivity(), R.layout.hori_listview_item_row, activityList);
	        		nwWhileListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<User> userList = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getUserList();
	        	if(userList != null) {
	        		NwUserArrayAdapter arrayAdapter = new NwUserArrayAdapter(getActivity(), R.layout.hori_listview_item_row, userList);
	        		nwWhoListView.setAdapter(arrayAdapter);
	        	}
	        	
	        	ArrayList<Channel> channelList = RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().getChannelList();
	        	if(channelList != null) {
	        		NwChannelArrayAdapter arrayAdapter = new NwChannelArrayAdapter(getActivity(), R.layout.hori_listview_item_row, channelList);
	        		nwHowListView.setAdapter(arrayAdapter);
	        	}
	        }
	        
    	} catch(Exception ex) {
    		Log.e(TAG, "onCreateView: " + ex.toString());
    	}
        
        return rootView;
    }    
    
    public void callFragment(Fragment fragmentObj) {
		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
		fragTransaction.replace(R.id.fragment_container, fragmentObj);
		fragTransaction.addToBackStack(null);
		fragTransaction.commit();
    }
    
    public void showProgSaveDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_prog_edit_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button dialogEditBtn = (Button) dialog.findViewById(R.id.btn_edit);
        final EditText name = (EditText) dialog.findViewById(R.id.name);
        final EditText desc = (EditText) dialog.findViewById(R.id.desc);
        
        dialogEditBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	
            	if(name.getText().toString().length() > 0 && desc.getText().toString().length() > 0) {
            		
            		long rtnValue = 0;
            		
            		if(prgStatus == 0 || prgStatus ==1) {
	            		RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setProgramName(name.getText().toString());
	            		RemindMeApplicationContext.getRemindMeApplicationContext().getProgram().setProgramDesc(desc.getText().toString());
	            		
	        	        rtnValue = dbMgr.insertProgram(RemindMeApplicationContext.getRemindMeApplicationContext().getProgram());
            		} else if(prgStatus == 2) {
	            		RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setProgramName(name.getText().toString());
	            		RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram().setProgramDesc(desc.getText().toString());
	            		
	        	        rtnValue = dbMgr.insertRecomProgram(RemindMeApplicationContext.getRemindMeApplicationContext().getSelectedRecomProgram());
            		}
        	        
        	        if(rtnValue != -1) {
                    	Intent intent = new Intent(getActivity(), GenerateActivity.class);
                    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    	startActivity(intent);
        	        }
            	}
                dialog.dismiss();
            }
        });
        
        dialog.show();        
    }
    
    public void showPropertyNoteDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_property_note_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); 

        Button dialogEditBtn = (Button) dialog.findViewById(R.id.btn_edit);
        
        dialogEditBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), GenerateActivity.class);
            	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(intent);
            	
                dialog.dismiss();
            }
        });
        
        dialog.show();        
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

