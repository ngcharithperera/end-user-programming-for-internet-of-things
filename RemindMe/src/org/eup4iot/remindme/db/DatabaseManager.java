package org.eup4iot.remindme.db;

import java.util.ArrayList;

import org.eup4iot.remindme.model.Activity_;
import org.eup4iot.remindme.model.Calender;
import org.eup4iot.remindme.model.Channel;
import org.eup4iot.remindme.model.Location;
import org.eup4iot.remindme.model.ObjectProperty;
import org.eup4iot.remindme.model.Program;
import org.eup4iot.remindme.model.RecommendedProgram;
import org.eup4iot.remindme.model.SmartObject;
import org.eup4iot.remindme.model.Task;
import org.eup4iot.remindme.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {
    /** The tag. */
    private final String TAG = DatabaseManager.this.getClass().getSimpleName();

    private SQLiteDatabase database;

    private static DatabaseManager INSTANCE;

    DataBaseHelper dbHelper = null;

    public DatabaseManager(Context context) {
        dbHelper = new DataBaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager(context);

        }
        return INSTANCE;
    }    
    
    
    public String createProgramID() {
    	String Id;
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_PROGRAM, null, null, null, null, null, null);
        cursor.moveToFirst();
        
        if(cursor.getCount() > 0) {
        	Id = cursor.getString(cursor.getColumnIndex("programID"));
        	
            int newId = Integer.parseInt(Id.substring(9));
            String strID = String.valueOf(++newId);
            
            if(strID.length() == 1) {
            	Id = "programID000" + strID;  
            } else if(strID.length() == 2) {
            	Id = "programID00" + strID;        	
            } else if(strID.length() == 3) {
            	Id = "programID0" + strID;        	
            } else if(strID.length() == 4) {
            	Id = "programID" + strID;        	
            }
        } else {
        	Id = "programID0001";
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		Log.i(TAG, "Id: " + Id);
        return Id;
    }
    
    public long deleteProgram(String programId) {
    	long rtnStatus = 0;
		Log.i(TAG, "delete-id: " + programId);
    	
    	try{
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_PROGRAM, "programID=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_ACTIVITY, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_CALENDER, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_CHANNEL, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_LOCATION, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_OBJECTPROPERTY, "id=?", new String[] {programId});	    	
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_SMARTOBJECT, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_TASK, "id=?", new String[] {programId});
	    	rtnStatus = database.delete(DataBaseHelper.TABLE_USER, "id=?", new String[] {programId});
    	} catch(Exception ex) {
    		Log.e(TAG, "deleteOrder: " + ex.getMessage().toString());
    	}
    	return rtnStatus;
    }
    
    public Boolean isProgramExist(String programId) {
    	Boolean isOrderAvail = false;
    	
		String[] orderId={programId};
		
        Cursor cursor = database.query(DataBaseHelper.TABLE_PROGRAM, null, "programID=?", orderId, null, null, null);
        if(cursor.getCount() > 0) {
        	isOrderAvail = true;
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
        return isOrderAvail;
    }
    
    public long insertProgram(Program program) {
    	long rtnStatus = 0;
    	String pID = null;

    	if(program.getProgramID() != null) {
	    	if(isProgramExist(program.getProgramID())) {
	    		deleteProgram(program.getProgramID());    		
	    		pID = program.getProgramID();
	    	}
    	} else {
    		pID = createProgramID();
    	}
    	
    	rtnStatus = insertProgramData(program, pID);
		
    	return rtnStatus;
    }    
    
    public long insertRecomProgram(RecommendedProgram recomProgram) {
    	long rtnStatus = 0;
    	String pID = null;

    	if(recomProgram.getProgramID() != null) {
    		
	    	if(isProgramExist(recomProgram.getProgramID())) {
	    		deleteProgram(recomProgram.getProgramID()); 
	    	}
	    	
	    	pID = recomProgram.getProgramID();
			Log.i(TAG, "Id: " + pID);
			
	    	rtnStatus = insertRecomProgramData(recomProgram, pID);
    	}    	
	
    	return rtnStatus;
    }
    
    public ArrayList<Program> getPrograms() {
    	ArrayList<Program> programs = new ArrayList<Program>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_PROGRAM, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Program program = cursorToPrograms(cursor);
        	programs.add(program);
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		Log.i(TAG, "order table size: " + programs.size());
        return programs;
    }
    
    private Program cursorToPrograms(Cursor cursor) {
    	Program program = new Program();
    	
    	String programID = cursor.getString(cursor.getColumnIndex("programID"));
    	program.setProgramID(programID);
    	
    	program.setProgramName(cursor.getString(cursor.getColumnIndex("programName")));
    	program.setProgramDesc(cursor.getString(cursor.getColumnIndex("programDesc")));
    	program.setProgramIconURL(cursor.getString(cursor.getColumnIndex("programIconURL")));
    	
    	program.setSmartObject(getSmartObjects(programID));
    	program.setTask(getTask(programID));
    	program.setCalender(getCalender(programID));
    	program.setLocationList(getLocationList(programID));
    	program.setActivityList(getActivityList(programID));    	
    	program.setUserList(getUserList(programID));
    	program.setChannelList(getChannelList(programID));
    	program.setObjectPropertyList(getPropertyList(programID));

        return program;
    }
    
    public SmartObject getSmartObjects(String programID) {
    	SmartObject smartOgj = new SmartObject();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_SMARTOBJECT, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	smartOgj.setSmartObjectID(cursor.getString(cursor.getColumnIndex("smartObjectID")));
        	smartOgj.setSmartObjectName(cursor.getString(cursor.getColumnIndex("smartObjectName")));
        	smartOgj.setSmartObjectDescription(cursor.getString(cursor.getColumnIndex("smartObjectDescription")));
        	smartOgj.setSmartObjectIconURL(cursor.getString(cursor.getColumnIndex("smartObjectIconURL")));
        	
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return smartOgj;
    }
    
    public Task getTask(String programID) {
    	Task obj = new Task();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_TASK, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	obj.setTaskID(cursor.getString(cursor.getColumnIndex("taskID")));
        	obj.setTaskName(cursor.getString(cursor.getColumnIndex("taskName")));
        	obj.setTaskDescription(cursor.getString(cursor.getColumnIndex("taskDescription")));
        	obj.setTaskIconURL(cursor.getString(cursor.getColumnIndex("taskIconURL")));
			
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return obj;
    }
    
    public Calender getCalender(String programID) {
    	Calender obj = new Calender();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_CALENDER, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	obj.setNextValue(cursor.getString(cursor.getColumnIndex("nextValue")));
        	obj.setNextTimeQuantifier(cursor.getString(cursor.getColumnIndex("nextTimeQuantifier")));
        	obj.setRangeValue(cursor.getString(cursor.getColumnIndex("rangeValue")));
        	obj.setRangeTimeQuantifier(cursor.getString(cursor.getColumnIndex("rangeTimeQuantifier")));
        	
        	obj.setNextWhen(cursor.getInt(cursor.getColumnIndex("isNextWhen")) > 0);
        	obj.setRepeat(cursor.getInt(cursor.getColumnIndex("isRepeat")) > 0);
        	obj.setRange(cursor.getInt(cursor.getColumnIndex("isRange")) > 0);
        	obj.setSUMMER(cursor.getInt(cursor.getColumnIndex("isSUMMER")) > 0);
        	obj.setWINTER(cursor.getInt(cursor.getColumnIndex("isWINTER")) > 0);
        	obj.setAUTUMN(cursor.getInt(cursor.getColumnIndex("isAUTUMN")) > 0);
        	obj.setSPRING(cursor.getInt(cursor.getColumnIndex("isSPRING")) > 0);
        	obj.setMONDAY(cursor.getInt(cursor.getColumnIndex("isMONDAY")) > 0);
        	obj.setTUESDAY(cursor.getInt(cursor.getColumnIndex("isTUESDAY")) > 0);
        	obj.setWEDNESDAY(cursor.getInt(cursor.getColumnIndex("isWEDNESDAY")) > 0);        	
        	obj.setTHURSDAY(cursor.getInt(cursor.getColumnIndex("isTHURSDAY")) > 0);
        	obj.setFRIDAY(cursor.getInt(cursor.getColumnIndex("isFRIDAY")) > 0);
        	obj.setSATURDAY(cursor.getInt(cursor.getColumnIndex("isSATURDAY")) > 0);
        	obj.setSUNDAY(cursor.getInt(cursor.getColumnIndex("isSUNDAY")) > 0);
        	obj.setDAYTIME(cursor.getInt(cursor.getColumnIndex("isDAYTIME")) > 0);
        	obj.setNIGHTTIME(cursor.getInt(cursor.getColumnIndex("isNIGHTTIME")) > 0);
			
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return obj;
    }
    
    public ArrayList<ObjectProperty> getPropertyList(String programID) {
    	ArrayList<ObjectProperty> objList = new ArrayList<ObjectProperty>();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_OBJECTPROPERTY, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	ObjectProperty obj = new ObjectProperty();
        	obj.setObjectPropertyID(cursor.getString(cursor.getColumnIndex("objectPropertyID")));
        	obj.setObjectPropertyName(cursor.getString(cursor.getColumnIndex("objectPropertyName")));
        	obj.setObjectPropertyDescription(cursor.getString(cursor.getColumnIndex("objectPropertyDescription")));
        	obj.setObjectPropertyComparisonOperator(cursor.getString(cursor.getColumnIndex("objectPropertyComparisonOperator")));
        	obj.setObjectPropertyValue(cursor.getString(cursor.getColumnIndex("objectPropertyValue")));
        	obj.setSelected(cursor.getInt(cursor.getColumnIndex("isSelected")) > 0);
			
        	objList.add(obj);			
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return objList;
    }
    
    public ArrayList<Location> getLocationList(String programID) {
    	ArrayList<Location> objList = new ArrayList<Location>();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_LOCATION, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	Location obj = new Location();
        	obj.setLocationID(cursor.getString(cursor.getColumnIndex("locationID")));
        	obj.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
        	obj.setLocationDescription(cursor.getString(cursor.getColumnIndex("locationDescription")));
        	obj.setLocationIconURL(cursor.getString(cursor.getColumnIndex("locationIconURL")));
        	obj.setLocationCategory(cursor.getString(cursor.getColumnIndex("locationCategory")));
        	obj.setSelected(cursor.getInt(cursor.getColumnIndex("isSelected")) > 0);
			
        	objList.add(obj);
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return objList;
    }
    
    public ArrayList<Activity_> getActivityList(String programID) {
    	ArrayList<Activity_> objList = new ArrayList<Activity_>();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_ACTIVITY, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	Activity_ obj = new Activity_();
        	obj.setActivityID(cursor.getString(cursor.getColumnIndex("activityID")));
        	obj.setActivityName(cursor.getString(cursor.getColumnIndex("activityName")));
        	obj.setActivityDescription(cursor.getString(cursor.getColumnIndex("activityDescription")));
        	obj.setActivityIconURL(cursor.getString(cursor.getColumnIndex("activityIconURL")));
        	obj.setSelected(cursor.getInt(cursor.getColumnIndex("isSelected")) > 0);
			
        	objList.add(obj);
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return objList;
    }
    
    public ArrayList<User> getUserList(String programID) {
    	ArrayList<User> objList = new ArrayList<User>();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_USER, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	User obj = new User();
        	obj.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
        	obj.setUserFullName(cursor.getString(cursor.getColumnIndex("userFullName")));
        	obj.setUsershortName(cursor.getString(cursor.getColumnIndex("usershortName")));
        	obj.setUsertelephoneNo(cursor.getString(cursor.getColumnIndex("usertelephoneNo")));        	
        	obj.setUserEmail(cursor.getString(cursor.getColumnIndex("userEmail")));
        	obj.setUserTweeterAccount(cursor.getString(cursor.getColumnIndex("userTweeterAccount")));
        	obj.setUserFacebookAccount(cursor.getString(cursor.getColumnIndex("userFacebookAccount")));
        	obj.setUserIconURL(cursor.getString(cursor.getColumnIndex("userIconURL")));
        	obj.setSelected(cursor.getInt(cursor.getColumnIndex("isSelected")) > 0);
			
        	objList.add(obj);
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return objList;
    }
    
    public ArrayList<Channel> getChannelList(String programID) {
    	ArrayList<Channel> objList = new ArrayList<Channel>();
    	
        Cursor cursor = database.query(DataBaseHelper.TABLE_CHANNEL, null, "id=?", new String[] {programID}, null, null, null);
        cursor.moveToFirst();
		Log.i(TAG, "cursor.getCount(): " + cursor.getCount());
        while (!cursor.isAfterLast()) {

        	Channel obj = new Channel();
        	obj.setChannelID(cursor.getString(cursor.getColumnIndex("channelID")));
        	obj.setChannelName(cursor.getString(cursor.getColumnIndex("channelName")));
        	obj.setChannelDescription(cursor.getString(cursor.getColumnIndex("channelDescription")));
        	obj.setChannelIconURL(cursor.getString(cursor.getColumnIndex("channelIconURL")));
        	obj.setSelected(cursor.getInt(cursor.getColumnIndex("isSelected")) > 0);
			
        	objList.add(obj);
            cursor.moveToNext();
        }

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return objList;
    }
    
    public long insertProgramData(Program program, String pID) {
    	long rtnStatus = 0;

		ContentValues cv = new ContentValues();
		cv.put("programID", pID);
		cv.put("programName", program.getProgramName());
		cv.put("programDesc", program.getProgramDesc());
		cv.put("programIconURL", program.getProgramIconURL());

/*		cv.put("objectStatus", program.getObjectStatus().toString());
		cv.put("taskStatus", program.getTaskStatus().toString());
		cv.put("propertyStatus", program.getPropertyStatus().toString());
		cv.put("calenderStatus", program.getCalenderStatus().toString());
		cv.put("locationStatus", program.getLocationStatus().toString());			
		cv.put("activityStatus", program.getActivityStatus().toString());
		cv.put("userStatus", program.getUserStatus().toString());
		cv.put("channelStatus", program.getChannelStatus().toString());*/

		rtnStatus = database.insert(DataBaseHelper.TABLE_PROGRAM, "programID", cv);

		if (rtnStatus != -1) {

			if(program.getSmartObject() != null) {
	    		ContentValues cv1 = new ContentValues();
				cv1.put("id", pID);
				cv1.put("smartObjectID", program.getSmartObject().getSmartObjectID());
				cv1.put("smartObjectName", program.getSmartObject().getSmartObjectName());
				cv1.put("smartObjectDescription", program.getSmartObject().getSmartObjectDescription());
				cv1.put("smartObjectIconURL", program.getSmartObject().getSmartObjectIconURL());

				rtnStatus = database.insert(DataBaseHelper.TABLE_SMARTOBJECT, "id", cv1);
			}

			if(program.getTask() != null) {
	    		ContentValues cv2 = new ContentValues();
				cv2.put("id", pID);
				cv2.put("taskID", program.getTask().getTaskID());
				cv2.put("taskName", program.getTask().getTaskName());
				cv2.put("taskDescription", program.getTask().getTaskDescription());
				cv2.put("taskIconURL", program.getTask().getTaskIconURL());

				rtnStatus = database.insert(DataBaseHelper.TABLE_TASK, "id", cv2);
			}

			if(program.getCalender() != null) {
	    		ContentValues cv3 = new ContentValues();
				cv3.put("id", pID);
				cv3.put("nextValue", program.getCalender().getNextValue());
				cv3.put("nextTimeQuantifier", program.getCalender().getNextTimeQuantifier());
				cv3.put("rangeValue", program.getCalender().getRangeValue());
				cv3.put("rangeTimeQuantifier", program.getCalender().getRangeTimeQuantifier());
				
				cv3.put("isNextWhen", program.getCalender().isNextWhen());
				cv3.put("isRepeat", program.getCalender().isRepeat());
				cv3.put("isRange", program.getCalender().isRange());
				cv3.put("isSUMMER", program.getCalender().isSUMMER());
				cv3.put("isWINTER", program.getCalender().isWINTER());
				cv3.put("isAUTUMN", program.getCalender().isAUTUMN());
				cv3.put("isSPRING", program.getCalender().isSPRING());					
				cv3.put("isMONDAY", program.getCalender().isMONDAY());
				cv3.put("isTUESDAY", program.getCalender().isTUESDAY());
				cv3.put("isWEDNESDAY", program.getCalender().isWEDNESDAY());
				cv3.put("isTHURSDAY", program.getCalender().isTHURSDAY());
				cv3.put("isFRIDAY", program.getCalender().isFRIDAY());					
				cv3.put("isSATURDAY", program.getCalender().isSATURDAY());
				cv3.put("isSUNDAY", program.getCalender().isSUNDAY());
				cv3.put("isDAYTIME", program.getCalender().isDAYTIME());
				cv3.put("isNIGHTTIME", program.getCalender().isNIGHTTIME());

				rtnStatus = database.insert(DataBaseHelper.TABLE_CALENDER, "id", cv3);
			}
			
			ArrayList<ObjectProperty> propertyList = program.getObjectPropertyList();
			if(propertyList != null) {
				for(ObjectProperty val : propertyList) {
					
		    		ContentValues cv8 = new ContentValues();
					cv8.put("id", pID);
					cv8.put("objectPropertyID", val.getObjectPropertyID());
					cv8.put("objectPropertyName", val.getObjectPropertyName());
					cv8.put("objectPropertyDescription", val.getObjectPropertyDescription());
					cv8.put("objectPropertyComparisonOperator", val.getObjectPropertyComparisonOperator());						
					cv8.put("objectPropertyValue", val.getObjectPropertyValue());
					cv8.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_OBJECTPROPERTY, "id", cv8);
				}
			}

			ArrayList<Location> locationList = program.getLocationList();
			if(locationList != null) {
				for(Location val : locationList) {
					
		    		ContentValues cv4 = new ContentValues();
					cv4.put("id", pID);
					cv4.put("locationID", val.getLocationID());
					cv4.put("locationName", val.getLocationName());
					cv4.put("locationDescription", val.getLocationDescription());
					cv4.put("locationIconURL", val.getLocationIconURL());						
					cv4.put("locationCategory", val.getLocationCategory());
					cv4.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_LOCATION, "id", cv4);
				}
			}

			ArrayList<Activity_> activityList = program.getActivityList();
			if(activityList != null) {
				for(Activity_ val : activityList) {
					
		    		ContentValues cv5 = new ContentValues();
					cv5.put("id", pID);
					cv5.put("activityID", val.getActivityID());
					cv5.put("activityName", val.getActivityName());
					cv5.put("activityDescription", val.getActivityDescription());
					cv5.put("activityIconURL", val.getActivityIconURL());
					cv5.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_ACTIVITY, "id", cv5);
				}
			}

			ArrayList<User> userList = program.getUserList();
			if(userList != null) {
				for(User val : userList) {
					
		    		ContentValues cv6 = new ContentValues();
					cv6.put("id", pID);
					cv6.put("userID", val.getUserID());
					cv6.put("userFullName", val.getUserFullName());
					cv6.put("usershortName", val.getUsershortName());
					cv6.put("usertelephoneNo", val.getUsertelephoneNo());
					cv6.put("userEmail", val.getUserEmail());						
					cv6.put("userTweeterAccount", val.getUserTweeterAccount());
					cv6.put("userFacebookAccount", val.getUserFacebookAccount());
					cv6.put("userIconURL", val.getUserIconURL());
					cv6.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_USER, "id", cv6);
				}
			}

			ArrayList<Channel> channelList = program.getChannelList();
			if(channelList != null) {
				for(Channel val : channelList) {
					
		    		ContentValues cv7 = new ContentValues();
					cv7.put("id", pID);
					cv7.put("channelID", val.getChannelID());
					cv7.put("channelName", val.getChannelName());
					cv7.put("channelDescription", val.getChannelDescription());
					cv7.put("channelIconURL", val.getChannelIconURL());
					cv7.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_CHANNEL, "id", cv7);
				}
			}

		}
		
		return rtnStatus;
    }
    
    public long insertRecomProgramData(RecommendedProgram recomProgram, String pID) {
    	long rtnStatus = 0;

		ContentValues cv = new ContentValues();
		cv.put("programID", pID);
		cv.put("programName", recomProgram.getProgramName());
		cv.put("programDesc", recomProgram.getProgramDesc());
		cv.put("programIconURL", recomProgram.getProgramIconURL());

/*		cv.put("objectStatus", program.getObjectStatus().toString());
		cv.put("taskStatus", program.getTaskStatus().toString());
		cv.put("propertyStatus", program.getPropertyStatus().toString());
		cv.put("calenderStatus", program.getCalenderStatus().toString());
		cv.put("locationStatus", program.getLocationStatus().toString());			
		cv.put("activityStatus", program.getActivityStatus().toString());
		cv.put("userStatus", program.getUserStatus().toString());
		cv.put("channelStatus", program.getChannelStatus().toString());*/

		rtnStatus = database.insert(DataBaseHelper.TABLE_PROGRAM, "programID", cv);

		if (rtnStatus != -1) {

			if(recomProgram.getSmartObject() != null) {
	    		ContentValues cv1 = new ContentValues();
				cv1.put("id", pID);
				cv1.put("smartObjectID", recomProgram.getSmartObject().getSmartObjectID());
				cv1.put("smartObjectName", recomProgram.getSmartObject().getSmartObjectName());
				cv1.put("smartObjectDescription", recomProgram.getSmartObject().getSmartObjectDescription());
				cv1.put("smartObjectIconURL", recomProgram.getSmartObject().getSmartObjectIconURL());

				rtnStatus = database.insert(DataBaseHelper.TABLE_SMARTOBJECT, "id", cv1);
			}

			if(recomProgram.getTask() != null) {
	    		ContentValues cv2 = new ContentValues();
				cv2.put("id", pID);
				cv2.put("taskID", recomProgram.getTask().getTaskID());
				cv2.put("taskName", recomProgram.getTask().getTaskName());
				cv2.put("taskDescription", recomProgram.getTask().getTaskDescription());
				cv2.put("taskIconURL", recomProgram.getTask().getTaskIconURL());

				rtnStatus = database.insert(DataBaseHelper.TABLE_TASK, "id", cv2);
			}

			if(recomProgram.getCalender() != null) {
	    		ContentValues cv3 = new ContentValues();
				cv3.put("id", pID);
				cv3.put("nextValue", recomProgram.getCalender().getNextValue());
				cv3.put("nextTimeQuantifier", recomProgram.getCalender().getNextTimeQuantifier());
				cv3.put("rangeValue", recomProgram.getCalender().getRangeValue());
				cv3.put("rangeTimeQuantifier", recomProgram.getCalender().getRangeTimeQuantifier());
				
				cv3.put("isNextWhen", recomProgram.getCalender().isNextWhen());
				cv3.put("isRepeat", recomProgram.getCalender().isRepeat());
				cv3.put("isRange", recomProgram.getCalender().isRange());
				cv3.put("isSUMMER", recomProgram.getCalender().isSUMMER());
				cv3.put("isWINTER", recomProgram.getCalender().isWINTER());
				cv3.put("isAUTUMN", recomProgram.getCalender().isAUTUMN());
				cv3.put("isSPRING", recomProgram.getCalender().isSPRING());					
				cv3.put("isMONDAY", recomProgram.getCalender().isMONDAY());
				cv3.put("isTUESDAY", recomProgram.getCalender().isTUESDAY());
				cv3.put("isWEDNESDAY", recomProgram.getCalender().isWEDNESDAY());
				cv3.put("isTHURSDAY", recomProgram.getCalender().isTHURSDAY());
				cv3.put("isFRIDAY", recomProgram.getCalender().isFRIDAY());					
				cv3.put("isSATURDAY", recomProgram.getCalender().isSATURDAY());
				cv3.put("isSUNDAY", recomProgram.getCalender().isSUNDAY());
				cv3.put("isDAYTIME", recomProgram.getCalender().isDAYTIME());
				cv3.put("isNIGHTTIME", recomProgram.getCalender().isNIGHTTIME());

				rtnStatus = database.insert(DataBaseHelper.TABLE_CALENDER, "id", cv3);
			}
			
			ArrayList<ObjectProperty> propertyList = recomProgram.getObjectPropertyList();
			if(propertyList != null) {
				for(ObjectProperty val : propertyList) {
					
		    		ContentValues cv8 = new ContentValues();
					cv8.put("id", pID);
					cv8.put("objectPropertyID", val.getObjectPropertyID());
					cv8.put("objectPropertyName", val.getObjectPropertyName());
					cv8.put("objectPropertyDescription", val.getObjectPropertyDescription());
					cv8.put("objectPropertyComparisonOperator", val.getObjectPropertyComparisonOperator());						
					cv8.put("objectPropertyValue", val.getObjectPropertyValue());
					cv8.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_OBJECTPROPERTY, "id", cv8);
				}
			}

			ArrayList<Location> locationList = recomProgram.getLocationList();
			if(locationList != null) {
				for(Location val : locationList) {
					
		    		ContentValues cv4 = new ContentValues();
					cv4.put("id", pID);
					cv4.put("locationID", val.getLocationID());
					cv4.put("locationName", val.getLocationName());
					cv4.put("locationDescription", val.getLocationDescription());
					cv4.put("locationIconURL", val.getLocationIconURL());						
					cv4.put("locationCategory", val.getLocationCategory());
					cv4.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_LOCATION, "id", cv4);
				}
			}

			ArrayList<Activity_> activityList = recomProgram.getActivityList();
			if(locationList != null) {
				for(Activity_ val : activityList) {
					
		    		ContentValues cv5 = new ContentValues();
					cv5.put("id", pID);
					cv5.put("activityID", val.getActivityID());
					cv5.put("activityName", val.getActivityName());
					cv5.put("activityDescription", val.getActivityDescription());
					cv5.put("activityIconURL", val.getActivityIconURL());
					cv5.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_ACTIVITY, "id", cv5);
				}
			}

			ArrayList<User> userList = recomProgram.getUserList();
			if(locationList != null) {
				for(User val : userList) {
					
		    		ContentValues cv6 = new ContentValues();
					cv6.put("id", pID);
					cv6.put("userID", val.getUserID());
					cv6.put("userFullName", val.getUserFullName());
					cv6.put("usershortName", val.getUsershortName());
					cv6.put("usertelephoneNo", val.getUsertelephoneNo());
					cv6.put("userEmail", val.getUserEmail());						
					cv6.put("userTweeterAccount", val.getUserTweeterAccount());
					cv6.put("userFacebookAccount", val.getUserFacebookAccount());
					cv6.put("userIconURL", val.getUserIconURL());
					cv6.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_USER, "id", cv6);
				}
			}

			ArrayList<Channel> channelList = recomProgram.getChannelList();
			if(locationList != null) {
				for(Channel val : channelList) {
					
		    		ContentValues cv7 = new ContentValues();
					cv7.put("id", pID);
					cv7.put("channelID", val.getChannelID());
					cv7.put("channelName", val.getChannelName());
					cv7.put("channelDescription", val.getChannelDescription());
					cv7.put("channelIconURL", val.getChannelIconURL());
					cv7.put("isSelected", val.isSelected());
					
					rtnStatus = database.insert(DataBaseHelper.TABLE_CHANNEL, "id", cv7);
				}
			}			
		}
		
		return rtnStatus;
    }
 }
