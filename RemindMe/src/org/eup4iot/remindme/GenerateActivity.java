package org.eup4iot.remindme;

import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.model.Program;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ViewFlipper;

public class GenerateActivity extends Activity {
	
	final String TAG = GenerateActivity.this.getClass().getSimpleName();
	
	private Program newProgram = null;
	
	private EditText reminderNote = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
        	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.generate_view);
	        Log.d(TAG, "onCreate");

	        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.vf_reminder);
	        reminderNote = (EditText) findViewById(R.id.reminder_note);    
	        
	        newProgram = new Program();

	        for(int i=0; i<5; i++) {
		        View view = getLayoutInflater().inflate(R.layout.remind_view, null);
	        	viewFlipper.addView(view);
	        }
	        
	        viewFlipper.setOnTouchListener(new OnSwipeTouchListener(GenerateActivity.this) {
	            
	            @Override
	            public void onSwipeLeft() {
	                Log.i(TAG, "Move Left");
	                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in));
	                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out));
	                viewFlipper.showNext();
	            }
	            
	            @Override
	            public void onSwipeRight() {
	                Log.i(TAG, "Move Right");
	                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in));
	                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_out));
	                viewFlipper.showPrevious();
	            }
	        });
        } catch (Exception e) {
			Log.e(TAG, "onCreate: " + e.toString());
		}
    }
    
    public void onGeneratePrgClickHandler(View view) {
    	
		RemindMeApplicationContext.getRemindMeApplicationContext().setProgram(null);
		RemindMeApplicationContext.getRemindMeApplicationContext().setSetPrgData(false);
		RemindMeApplicationContext.getRemindMeApplicationContext().setSelectedRecomProgram(null);		
		RemindMeApplicationContext.getRemindMeApplicationContext().setActivityList(null);
		RemindMeApplicationContext.getRemindMeApplicationContext().setChannelsList(null);
		RemindMeApplicationContext.getRemindMeApplicationContext().setLocationsList(null);		
		RemindMeApplicationContext.getRemindMeApplicationContext().setPropertyList(null);
		RemindMeApplicationContext.getRemindMeApplicationContext().setRecommendedProgramsList(null);
		RemindMeApplicationContext.getRemindMeApplicationContext().setSmartObjectsList(null);		
		RemindMeApplicationContext.getRemindMeApplicationContext().setTasksList(null);
		
    	newProgram.setUserNote(reminderNote.getText().toString());
    	RemindMeApplicationContext.getRemindMeApplicationContext().setProgram(newProgram);
    	
    	Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
    	startActivity(homeIntent);
    	
    	reminderNote.setText(ApplicationConstants.EMPTY_STRING);
    }
}
