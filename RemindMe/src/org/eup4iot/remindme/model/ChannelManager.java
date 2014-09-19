package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class ChannelManager extends RemindMeBaseServerResponse {
	ArrayList<Channel> ChannelsList;

	public ArrayList<Channel> getChannelsList() {
		return ChannelsList;
	}

	public void setChannelsList(ArrayList<Channel> channelsList) {
		ChannelsList = channelsList;
	}
}
