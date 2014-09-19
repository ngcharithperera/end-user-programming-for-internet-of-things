package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class ObjectPropertyManager extends RemindMeBaseServerResponse{
	
	ArrayList<ObjectProperty> ObjectPropertiesList;

	public ArrayList<ObjectProperty> getObjectPropertiesList() {
		return ObjectPropertiesList;
	}

	public void setObjectPropertiesList(
			ArrayList<ObjectProperty> objectPropertiesList) {
		ObjectPropertiesList = objectPropertiesList;
	}

}
