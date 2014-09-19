/**********************************************
This class store the data that is required to 
built an 'item' in the 'Channel_Activity'
***********************************************/

package org.eup4iot.remindme.model;


public class Channel {
	
	String channelID;
	
	String channelName;
	
	String channelDescription;
	
	String channelIconURL;
	
	boolean isSelected;
	
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelDescription() {
		return channelDescription;
	}
	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}
	public String getChannelIconURL() {
		return channelIconURL;
	}
	public void setChannelIconURL(String channelIconURL) {
		this.channelIconURL = channelIconURL;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
