/**********************************************
This class store the data that is required to 
built an 'item' in the 'ObjectProperty_Activity'
***********************************************/

package org.eup4iot.remindme.model;

public class ObjectProperty {
	
	String objectPropertyID;
	
	String objectPropertyName;
	
	String objectPropertyDescription;
	
	String objectPropertyComparisonOperator;
	
	String objectPropertyValue;
	
	boolean isSelected;
	
	public String getObjectPropertyID() {
		return objectPropertyID;
	}
	public void setObjectPropertyID(String objectPropertyID) {
		this.objectPropertyID = objectPropertyID;
	}
	public String getObjectPropertyName() {
		return objectPropertyName;
	}
	public void setObjectPropertyName(String objectPropertyName) {
		this.objectPropertyName = objectPropertyName;
	}
	public String getObjectPropertyDescription() {
		return objectPropertyDescription;
	}
	public void setObjectPropertyDescription(String objectPropertyDescription) {
		this.objectPropertyDescription = objectPropertyDescription;
	}
	public String getObjectPropertyComparisonOperator() {
		return objectPropertyComparisonOperator;
	}
	public void setObjectPropertyComparisonOperator(
			String objectPropertyComparisonOperator) {
		this.objectPropertyComparisonOperator = objectPropertyComparisonOperator;
	}
	public String getObjectPropertyValue() {
		return objectPropertyValue;
	}
	public void setObjectPropertyValue(String objectPropertyValue) {
		this.objectPropertyValue = objectPropertyValue;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
	
}
