package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class SmartObjectManager extends RemindMeBaseServerResponse{
	
	ArrayList<SmartObject> SmartObjectsList;

	public ArrayList<SmartObject> getSmartObjectsList() {
		return SmartObjectsList;
	}

	public void setSmartObjectsList(ArrayList<SmartObject> smartObjectsList) {
		SmartObjectsList = smartObjectsList;
	}
}
