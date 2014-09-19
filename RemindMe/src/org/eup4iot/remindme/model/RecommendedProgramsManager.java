package org.eup4iot.remindme.model;

import java.util.ArrayList;

import org.eup4iot.remindme.server.response.RemindMeBaseServerResponse;

public class RecommendedProgramsManager extends RemindMeBaseServerResponse{
	
	ArrayList<RecommendedProgram> RecommendedProgramsList;

	public ArrayList<RecommendedProgram> getRecommendedProgramsList() {
		return RecommendedProgramsList;
	}

	public void setRecommendedProgramsList(
			ArrayList<RecommendedProgram> recommendedProgramsList) {
		RecommendedProgramsList = recommendedProgramsList;
	}
}
