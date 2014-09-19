/**********************************************
This class store the data that is required to 
built an 'item' in the 'Recommendations' Tab.
***********************************************/

package org.eup4iot.remindme.model;

public class RecommendedProgram extends Program {

	float ProgramRating;
	
	String creatorID;
	
	String creatorFullName;
	
	String creatorDate;

	public float getProgramRating() {
		return ProgramRating;
	}

	public void setProgramRating(float programRating) {
		ProgramRating = programRating;
	}

	public String getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}

	public String getCreatorFullName() {
		return creatorFullName;
	}

	public void setCreatorFullName(String creatorFullName) {
		this.creatorFullName = creatorFullName;
	}

	public String getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}

}
