package org.ml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ml.core.SurveyInterest;
import org.ml.pool.SunyConnectionPool;
import org.ml.util.LogHelper;

public class SurveyInterestDAO {

	public void storeInterest(SurveyInterest interest){
		LogHelper.debug(this,"Store SurveyInterest");
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO SURVEY_INTEREST VALUES(?,?,?)");
			pstmt.setString(1, interest.getSurveyKey());
			pstmt.setString(2, interest.getCui());
			pstmt.setBoolean(3, interest.getIsPrimary());
			LogHelper.debug(this,pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);
		}		
	}
	
	public ArrayList<SurveyInterest> getInterestsOfSurveyWithKey(String key){
		LogHelper.debug(this,"Get SurveyInterests of key: " + key);
		ArrayList<SurveyInterest> interests = new ArrayList<SurveyInterest>();
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM SURVEY_INTEREST WHERE survey_key = ? ");
			pstmt.setString(1, key);
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				interests.add(make(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);				
		}	
		return interests;
	}
	
	private SurveyInterest make(ResultSet rs) throws SQLException{
		SurveyInterest interest = new SurveyInterest();
		interest.setSurveyKey(rs.getString("survey_key"));
		interest.setCui(rs.getString("cui"));
		interest.setIsPrimary(rs.getBoolean("is_primary"));
		return interest;
	}
	
}
