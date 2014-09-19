package org.eup4iot.remindme;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends SherlockFragmentActivity {
	
	final String TAG = HomeActivity.this.getClass().getSimpleName();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
	        setContentView(R.layout.home_view);
	        Log.d(TAG, "onCreate");
	        
			ActionBar actionBar = getSupportActionBar();
	
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);	
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	
			ActionBar.Tab Tab1 = actionBar.newTab().setText("New Program Editor");
			ActionBar.Tab Tab2 = actionBar.newTab().setText("My Programs");
			ActionBar.Tab Tab3 = actionBar.newTab().setText("Recommendations");
	
			Tab1.setTabListener(new TabListener(new Tab_NewProgram()));
			Tab2.setTabListener(new TabListener(new Tab_MyPrograms()));
			Tab3.setTabListener(new TabListener(new Tab_Recommendations()));
	
			actionBar.addTab(Tab1);
			actionBar.addTab(Tab2);
			actionBar.addTab(Tab3);
        } catch (Exception e) {
			Log.e(TAG, "onCreate: " + e.toString());
		}
    }
}
