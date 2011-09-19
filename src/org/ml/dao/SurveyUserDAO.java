package org.ml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.ml.core.SurveyUser;
import org.ml.pool.SunyConnectionPool;
import org.ml.util.LogHelper;

public class SurveyUserDAO {

	public void storeSurveyUser(SurveyUser user){
		LogHelper.debug(this,"Store SurveyUser");
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO SURVEY_USER(first_name,last_name,email,date_created,survey_key) VALUES(?,?,?,?,?)");
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getEmail());
			pstmt.setTimestamp(4,  (Timestamp) user.getDateCreated());
			pstmt.setString(5, user.getKey());
			LogHelper.debug(this,pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
	}
	
	public void storeSurveySubmissionDate(String key, Timestamp date){
		LogHelper.debug(this,"Store SurveyUser");
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("UPDATE SURVEY_USER SET date_submited = ? WHERE survey_key = ? ");
			pstmt.setTimestamp(1,  date);
			pstmt.setString(2, key);
			LogHelper.debug(this,pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
	}
	
	public SurveyUser getSurveyUsers(String key){
		LogHelper.debug(this,"Get SurveyUser with key: "+key);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM SURVEY_USER where survey_key = ?");
			pstmt.setString(1, key);
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return make(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return null;
	}
	
	
	public ArrayList<SurveyUser> getAllSurveyUsers(){
		LogHelper.debug(this,"Get all SurveyUser");
		ArrayList<SurveyUser> users = new ArrayList<SurveyUser>();
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM SURVEY_USER");
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				users.add(make(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return users;
	}
	
	public void removeSurveyUsers(String key){
		LogHelper.debug(this,"Remove SurveyUser with key: "+key);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM SURVEY_USER where survey_key = ?");
			pstmt.setString(1, key);
			LogHelper.debug(this,pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
	}
	
	
	private SurveyUser make(ResultSet rs) throws SQLException{
		LogHelper.debug(this,"Making surveyUser");
		SurveyUser user = new SurveyUser();
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setEmail(rs.getString("email"));
		user.setDateCreated(rs.getTimestamp("date_created"));
		if(rs.getTimestamp("date_submited")!=null)
			user.setDateSubmited(rs.getTimestamp("date_submited"));
		user.setKey(rs.getString("survey_key"));
		return user;
	}
	
}
