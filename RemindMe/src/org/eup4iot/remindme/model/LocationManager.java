package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class LocationManager extends RemindMeBaseServerResponse{
	
	ArrayList<Location> LocationsList;

	public ArrayList<Location> getLocationsList() {
		return LocationsList;
	}

	public void setLocationsList(ArrayList<Location> locationsList) {
		LocationsList = locationsList;
	}
}
