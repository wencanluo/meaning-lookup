package org.ml.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.ml.core.UMLSWord;

public interface UmlsDAOInterface {
	
	public ArrayList<UMLSWord> makeUMLSWordWithExactLabel(String label, HashMap<String,UMLSWord> exisiting);
	
	public ArrayList<UMLSWord> makeUMLSWordsWithSimilarLabel(String label, HashMap<String,UMLSWord> exisiting);
	
	public ArrayList<UMLSWord> getSynonymsOfWord(String word);
	
	public ArrayList<UMLSWord> getSynonymsOfCUI(String cui, ArrayList<String> SABList);
	
	public ArrayList<String> getNewCuis(String word , HashMap<String,UMLSWord> exisiting);
	
	public String getLabel(String cui);	
	
	public String getDef(String cui);
	
	public String getType(String cui);

}
