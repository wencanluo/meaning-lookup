package org.ml.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ml.core.SurveyUser;
import org.ml.dao.SurveyUserDAO;

public class SurveyUserBO {

	public static void storeSurveyUser(SurveyUser user){
		SurveyUserDAO dao = new SurveyUserDAO();
		dao.storeSurveyUser(user);
	}
	
	public static void storeSurveySubmissionDate(String key, Timestamp date){
		SurveyUserDAO dao = new SurveyUserDAO();
		dao.storeSurveySubmissionDate(key, date);
	}
	
	public static SurveyUser getSurveyUsers(String key){
		SurveyUserDAO dao = new SurveyUserDAO();
		return dao.getSurveyUsers(key);
	}
	
	public static ArrayList<SurveyUser> getAllSurveyUsers(){
		SurveyUserDAO dao = new SurveyUserDAO();
		return dao.getAllSurveyUsers();
	}
	
	public static void removeSurveyUsers(String key){
		SurveyUserDAO dao = new SurveyUserDAO();
		dao.removeSurveyUsers(key);
	}
	
	public static SurveyUser makeSurveyUserFromCsvRecord(String record, char csvSeparator) {
		SurveyUser user = new SurveyUser();
		boolean quoted = false;
	    StringBuilder fieldBuilder = new StringBuilder();
	    List<String> tmp = new ArrayList<String>();
	    for (int i = 0; i < record.length(); i++) {
	    	char c = record.charAt(i);
	    	fieldBuilder.append(c);
	    	if (c == '"')
	    		quoted = !quoted; // Detect nested quotes.
	    	if ((!quoted && c == csvSeparator)|| i + 1 == record.length()){
	    		String field = fieldBuilder.toString() // Obtain the field, ..
	    		.replaceAll(csvSeparator + "$", "") // .. trim ending separator, ..
	    		.replaceAll("^\"|\"$", "") // .. trim surrounding quotes, ..
	    		.replace("\"\"", "\""); // .. and un-escape quotes.	   
	    		tmp.add(field.trim());
	    		fieldBuilder = new StringBuilder(); // Reset.
	    	}
	    }
	    user.setFirstName(tmp.get(0));
		user.setLastName(tmp.get(1));
		user.setEmail(tmp.get(2));
	    user.setDateCreated(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    if((user.getEmail() + new Timestamp(Calendar.getInstance().getTimeInMillis()).toString()).hashCode() < 0 )
	    	user.setKey(Integer.toString((user.getEmail() + new Timestamp(Calendar.getInstance().getTimeInMillis()).toString()).hashCode() * -1));
	    else
	    	user.setKey(Integer.toString((user.getEmail() + new Timestamp(Calendar.getInstance().getTimeInMillis()).toString()).hashCode()));
	    return user;
	   
	}
}
