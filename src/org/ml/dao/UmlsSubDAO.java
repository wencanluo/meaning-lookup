package org.ml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ml.core.UMLSWord;
import org.ml.pool.SunyConnectionPool;
import org.ml.util.LogHelper;

public class UmlsSubDAO implements UmlsDAOInterface{

        public ArrayList<String> SABList;

        public UmlsSubDAO() {
            ArrayList<String> SABList = new ArrayList<String>();
            this.SABList = SABList;
        }

        public UmlsSubDAO(ArrayList<String> SABList) {
            this.SABList = SABList;
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

	public ArrayList<UMLSWord> makeUMLSWordWithExactLabel(String label, HashMap<String,UMLSWord> exisiting){
		LogHelper.debug(this,"Find word with label: "+label);
		ArrayList<UMLSWord> words = new ArrayList<UMLSWord>();
                
		for(String cui : getNewCuis(label,exisiting)){
			UMLSWord word = new UMLSWord(cui, label);
			word.setType(getType(word.getCUI()));
			word.setSynonyms(getSynonymsOfCUI(word.getCUI()));
			word.setDefinition(getDef(word.getCUI()));
			words.add(word);	
		}
		return words;	
	}
	
	public ArrayList<UMLSWord> makeUMLSWordsWithSimilarLabel(String label, HashMap<String,UMLSWord> exisiting){
		ArrayList<UMLSWord> words = new ArrayList<UMLSWord>();
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select  distinct cui, str from umls_2010aa_sub  "+
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
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return words;	
	}
	
	public ArrayList<UMLSWord> getSynonymsOfWord(String word){
		LogHelper.debug(this,"Getting synonyms for : "+word);
		ArrayList<UMLSWord> list = new ArrayList<UMLSWord>();
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select distinct m2.cui, m2.str from umls_2010aa_sub m1 left join umls_2010aa_sub m2 on (m1.cui = m2.cui) "+
															"where m1.str = UPPER(?) ");
			pstmt.setString(1, word);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new UMLSWord(rs.getString("cui"), rs.getString("str")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return list;
	}

        public ArrayList<UMLSWord> getSynonymsOfCUI(String cui) {
            return getSynonymsOfCUI(cui, this.SABList);
        }

	public ArrayList<UMLSWord> getSynonymsOfCUI(String cui,  ArrayList<String> SABList){
		LogHelper.debug(this,"Getting synonyms for cui : "+cui);
		ArrayList<UMLSWord> list = new ArrayList<UMLSWord>();
                String SABListString = "";
                for(String SAB : SABList) {
                    SABListString = SABListString + "'" + SAB + "'"  +  ",";
                }
                SABListString = SABListString.substring(0, SABListString.length() - 1);
                LogHelper.debug(SABListString);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select distinct m2.str from umls_2010aa_sub m1 left join umls_2010aa_sub m2 on (m1.cui = m2.cui) "+
				"where m1.cui = ?  and m2.sab in (" + SABListString + ")" );
			pstmt.setString(1, cui);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new UMLSWord(cui, rs.getString("str")));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return list;
	}
	
	
	public ArrayList<String> getNewCuis(String word , HashMap<String,UMLSWord> exisiting){
		LogHelper.debug(this,"Getting cui for : "+word);
		ArrayList<String> cuis = new ArrayList<String>();
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select  distinct cui from umls_2010aa_sub  "+
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
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return cuis;
	}
	
	public String getLabel(String cui){
		LogHelper.debug(this,"Getting label for : "+cui);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select str from umls_2010aa_sub where cui = ? ");
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("str");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return null;
	}		
	
	public String getDef(String cui){
		LogHelper.debug(this,"Getting def for : "+cui);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select def from umls_2010aa_sub where cui = ? ");
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("def");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return null;
	}		
	
	public String getType(String cui){
		LogHelper.debug(this,"Getting type for : "+cui);
		Connection con = null;
		try {
			con = SunyConnectionPool.getInstance().borrowConnection();
			PreparedStatement pstmt = con.prepareStatement("select sty from umls_2010aa_sub where cui = ? ");
			pstmt.setString(1, cui);
			LogHelper.debug(this,pstmt.toString());	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("sty");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SunyConnectionPool.getInstance().returnConnection(con);					
		}
		return null;
	}
}

/*
 * 
 * 

drop table if exists suny_reach.umls_2010aa_sub;
create table suny_reach.umls_2010aa_sub(
cui char(8),
str varchar(1023),
sab varchar(20),
sty varchar(50),
def text
);

insert into suny_reach.umls_2010aa_sub
select distinct MC.cui , UPPER(cast(left(MC.str, 1023) as char(1023))) as str , MC.sab , STY.sty, DEF.def  
from umls_2010aa.MRCONSO MC LEFT JOIN umls_2010aa.MRSTY STY ON (MC.CUI = STY.cui) 
							LEFT JOIN umls_2010aa.MRDEF DEF ON (MC.CUI = DEF.cui) 
WHERE MC.sab = 'DSM4' OR MC.sab = 'MSH' OR MC.sab = 'NCI' OR MC.sab = 'NCBI' OR MC.sab = 'GO'
OR MC.sab = 'MDR' OR MC.sab = 'MTH' OR MC.sab = 'RXNORM' OR MC.sab = 'SNOMEDCT';

CREATE INDEX id_cui ON suny_reach.umls_2010aa_sub (cui);
CREATE INDEX id_str ON suny_reach.umls_2010aa_sub (str);
CREATE INDEX id_sab ON suny_reach.umls_2010aa_sub (sab);
CREATE INDEX id_sty ON suny_reach.umls_2010aa_sub (sty);


 * 
 * 
 * 
 */
