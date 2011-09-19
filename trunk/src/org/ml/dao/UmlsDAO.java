package org.ml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ml.core.UMLSWord;
import org.ml.core.Synonym;
import org.ml.pool.UmlsConnectionPool;
import org.ml.util.LogHelper;

public class UmlsDAO implements UmlsDAOInterface{
        public ArrayList<String> SABList;
        
        public UmlsDAO() {
            ArrayList<String> SABList = new ArrayList<String>();
            this.SABList = SABList;
        }

        public UmlsDAO(ArrayList<String> SABList) {
            this.SABList = SABList;  
        }

	public ArrayList<UMLSWord> makeUMLSWordWithExactLabel(String label, HashMap<String,UMLSWord> exisiting){
		LogHelper.debug(this,"Find word with label: " + label);
		ArrayList<UMLSWord> words = new ArrayList<UMLSWord>();
                ArrayList<String> sabList = new ArrayList<String>();
                boolean arePublicSourcesOnly = true;
                if (arePublicSourcesOnly) {
                    sabList = getPublicSABs();
                    this.SABList = sabList;
                }
		for(String cui : getNewCuis(label,exisiting)){
			UMLSWord word = new UMLSWord(cui, label);
                        String cuiKey = word.getCUI();
			word.setType(getType(cuiKey));
                        word.setSTN(getSTN(cuiKey));
			word.setSynonyms(getSynonymsOfCUI(cuiKey));
			word.setDefinition(getDef(cuiKey));
                        word.setSuiList(getSynonymListIndexedBySui(cuiKey));
                        word.setSABList(getSAB(cuiKey));
			words.add(word);	
		}
		return words;	
	}
	
	public ArrayList<UMLSWord> makeUMLSWordsWithSimilarLabel(String label, HashMap<String,UMLSWord> exisiting){
		ArrayList<UMLSWord> words = new ArrayList<UMLSWord>();
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select  distinct cui, str from MRCONSO  "+
															"where str like UPPER(?) ");
			pstmt.setString(1, "%"+label+"%");
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				if(exisiting.get(rs.getString("cui"))==null)
					words.add(new UMLSWord(rs.getString("cui"),rs.getString("str")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return words;	
	}
	
	public ArrayList<UMLSWord> getSynonymsOfWord(String word) {
		LogHelper.debug(this,"Getting synonyms for : " + word);
		ArrayList<UMLSWord> list = new ArrayList<UMLSWord>();
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select distinct m2.cui, m2.str from MRCONSO m1 left join MRCONSO m2 on (m1.cui = m2.cui) "+
															"where m1.str = UPPER(?) ");
			pstmt.setString(1, word);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new UMLSWord(rs.getString("cui"), rs.getString("str")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return list;
	}

        private String makeSABListString() {
                String SABListString = "";
                if (this.SABList.size() > 0) {
                    for(String SAB : this.SABList) {
                        SABListString = SABListString + "'" + SAB + "'"  +  ",";
                    }
                   SABListString = SABListString.substring(0, SABListString.length() - 1);
                }
                return SABListString;
        }

        private String makeSABListSQL(String sql_prefix) {
            String SABListSQL = "";
            if (this.SABList.size() > 0) {
                  SABListSQL = sql_prefix + " (" + makeSABListString() + ")";
             }
            return SABListSQL;
        }

        public ArrayList<UMLSWord> getSynonymsOfCUI(String cui) {
            return getSynonymsOfCUI(cui,this.SABList);
        }
        
	public ArrayList<UMLSWord> getSynonymsOfCUI(String cui, ArrayList<String> SABList) {
                String SABListString = "";
                String SABListSQL = "";
                this.SABList = SABList;

		ArrayList<UMLSWord> list = new ArrayList<UMLSWord>();
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();

                        SABListSQL = makeSABListSQL("and m2.SAB in ");

			PreparedStatement pstmt = con.prepareStatement("select distinct m2.str from MRCONSO m1 join MRCONSO m2 on (m1.cui = m2.cui) "
                                + "where m1.cui = ? " + SABListSQL);
			pstmt.setString(1, cui);
                        LogHelper.debug(SABListString);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new UMLSWord(cui, rs.getString("str")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return list;
	}
	
	
	public ArrayList<String> getNewCuis(String word , HashMap<String,UMLSWord> exisiting){
		LogHelper.debug(this,"Getting cui for : "+word);
		ArrayList<String> cuis = new ArrayList<String>();
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select  distinct cui from MRCONSO  "+
															"where str = UPPER(?) ");
			pstmt.setString(1, word);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				if(exisiting.get(rs.getString("cui"))==null)
					cuis.add(rs.getString("cui"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return cuis;
	}
	
	public String getLabel(String cui){
		LogHelper.debug(this,"Getting label for : " + cui);
		Connection con = null;
                String SABListSQL = "";
		try {
		       con = UmlsConnectionPool.getInstance().borrowConnection();
                        SABListSQL = makeSABListSQL("and SAB in");
			PreparedStatement pstmt = con.prepareStatement("select str from MRCONSO where cui = ?" + SABListSQL);
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("str");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
	}		
	
	public String getDef(String cui){
		LogHelper.debug(this,"Getting def for : "+cui);
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select def from MRDEF where cui = ? " + makeSABListSQL("and SAB in "));
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("def");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
	}

        public ArrayList<String> getPublicSABs() {
		LogHelper.debug(this,"Getting Public SAB");
                ArrayList<String> publicSABs = new ArrayList<String>();

		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select distinct RSAB from MRSAB ms where ms.SRL = 0 order by RSAB");
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();

                        while(rs.next()) {
				publicSABs.add(rs.getString("RSAB"));
                        }
                        return publicSABs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
	}

	public String getType(String cui){
		LogHelper.debug(this,"Getting type for : "+cui);
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select sty from MRSTY where cui = ? ");
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
                            String rsl = rs.getString("sty");
				return rs.getString("sty");
                    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
	}

        public String getSTN(String cui){
		LogHelper.debug(this,"Getting STN for : "+cui);
		Connection con = null;
		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select stn from MRSTY where cui = ? ");
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
                        {   String rsm = rs.getString("stn");
				return rs.getString("stn");
                    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
	}

        public HashMap<String, ArrayList<Synonym>> getSynonymListIndexedBySui(String cui){
                LogHelper.debug(this,"Getting SuiList for : "+cui);
		Connection con = null;
                HashMap<String, ArrayList<Synonym>> suiList = new HashMap<String, ArrayList<Synonym>>();
                Synonym s;

		try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select sui, aui, sab from MRCONSO where cui = ? " + makeSABListSQL("and SAB in "));
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
                            s = new Synonym(rs.getString("sui"), rs.getString("aui"), rs.getString("sab"));
                            String suiKey = s.getSui();
                            if(!suiList.containsKey(suiKey)){
                                ArrayList<Synonym> synonymList = new ArrayList<Synonym>();
                                synonymList.add(s);
                                suiList.put(suiKey, synonymList);
                            }
                            else
                                suiList.get(suiKey).add(s);
                       }
		return suiList;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
		}
		return null;
        }

        public ArrayList<String> getSAB(String cui){
            LogHelper.debug(this,"Getting SuiList for : "+cui);
		Connection con = null;
                ArrayList<String> resultList = new ArrayList<String>();
                try {
			con = UmlsConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select distinct mc.SAB,ms.SSN from MRCONSO mc "
                                + " join MRSAB ms on ms.RSAB =  mc.SAB where mc.CUI = ? " + makeSABListSQL("and mc.SAB in ")
                                + "order by ms.RSAB"
                                );
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
                            resultList.add(rs.getString("sab"));
                        }
                }
                catch (SQLException e) {
			e.printStackTrace();
		}finally{
			UmlsConnectionPool.getInstance().returnConnection(con);
                        return resultList;
		}
        }
                
}