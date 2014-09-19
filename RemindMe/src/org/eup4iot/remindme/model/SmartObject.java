/**********************************************
This class store the data that is required to 
built an 'item' in the 'SmartObject_Activity'
 ***********************************************/

package org.eup4iot.remindme.model;

import java.util.HashMap;

public class SmartObject {
	
	String smartObjectID;
	
	String smartObjectName;
	
	String smartObjectDescription;
	
	String smartObjectIconURL;
	
	HashMap<String, String> smartObjectExtras;
	
	public String getSmartObjectID() {
		return smartObjectID;
	}
	public void setSmartObjectID(String smartObjectID) {
		this.smartObjectID = smartObjectID;
	}
	public String getSmartObjectName() {
		return smartObjectName;
	}
	public void setSmartObjectName(String smartObjectName) {
		this.smartObjectName = smartObjectName;
	}
	public String getSmartObjectDescription() {
		return smartObjectDescription;
	}
	public void setSmartObjectDescription(String smartObjectDescription) {
		this.smartObjectDescription = smartObjectDescription;
	}
	public String getSmartObjectIconURL() {
		return smartObjectIconURL;
	}
	public void setSmartObjectIconURL(String smartObjectIconURL) {
		this.smartObjectIconURL = smartObjectIconURL;
	}
	public HashMap<String, String> getSmartObjectExtras() {
		return smartObjectExtras;
	}
	public void setSmartObjectExtras(HashMap<String, String> smartObjectExtras) {
		this.smartObjectExtras = smartObjectExtras;
	}

}
