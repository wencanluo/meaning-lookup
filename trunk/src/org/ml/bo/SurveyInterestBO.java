package org.ml.bo;

import java.util.ArrayList;

import org.ml.core.SurveyInterest;
import org.ml.dao.SurveyInterestDAO;

public class SurveyInterestBO {

	public static void storeInterest(SurveyInterest interest){
		SurveyInterestDAO dao = new SurveyInterestDAO();
		dao.storeInterest(interest);
	}
	
	public static ArrayList<SurveyInterest> getInterestsOfSurveyWithKey(String key){
		SurveyInterestDAO dao = new SurveyInterestDAO();
		return dao.getInterestsOfSurveyWithKey(key);
	}
	
	
	
}
