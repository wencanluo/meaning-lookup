package org.ml.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ml.core.MlConversationPart;
import org.ml.core.MlWord;
import org.ml.core.UMLSWord;
import org.ml.core.WordNetWord;
import org.ml.dao.UmlsDAO;
import org.ml.dao.UmlsDAOInterface;
import org.ml.dao.UmlsSubDAO;
import org.ml.engine.MlDisambiguator;
import org.ml.util.LogHelper;

public class UmlsBO {

	private static final Boolean USE_SUBSET = false;
	
	public static void getExactUmlsWords(MlConversationPart convPart){
		LogHelper.debug("Get exact UMLS words");
		UmlsDAOInterface dao = (USE_SUBSET)? new UmlsSubDAO() : new UmlsDAO();
		for(MlWord w : convPart.getWords())
			for(UMLSWord word : dao.makeUMLSWordWithExactLabel(w.getLabel(),convPart.getUMLSWords())){
				word.setMlId(w.getId());
				convPart.getUMLSWords().put(word.getCUI(),word);
			}
	}
	
	public static void getMuliplePartsUmlsWords(MlConversationPart convPart){
		LogHelper.debug("Get multiple parts UMLS words");
		UmlsDAOInterface dao = (USE_SUBSET)? new UmlsSubDAO() : new UmlsDAO();
		String label = "";
		for(int i = 2 ; i < convPart.getWords().size() ; i++)
			for(int j = 0 ; j < convPart.getWords().size() - i + 1 ; j++){
				for(int k = j ; k < j+i ; k++)
					label += convPart.getWords().get(k).getLabel()+ " ";		
				for(UMLSWord word : dao.makeUMLSWordWithExactLabel(label.trim(),convPart.getUMLSWords()))
					convPart.getUMLSWords().put(word.getCUI(), word);
				label = "";
				}
	}
	
	public static void getWholePhraseUmlsWord(MlConversationPart convPart){
		LogHelper.debug("Get the whole phrase UMLS word");
		UmlsDAOInterface dao = (USE_SUBSET)? new UmlsSubDAO() : new UmlsDAO();
		for(UMLSWord word : dao.makeUMLSWordWithExactLabel(convPart.getProcessedText(),convPart.getUMLSWords()))
			convPart.getUMLSWords().put(word.getCUI(), word);
	}
	
	public static void getDisambiguatedUmlsWords(MlConversationPart convPart){
		LogHelper.debug("Get the whole phrase UMLS word");
		UmlsDAOInterface dao = (USE_SUBSET)? new UmlsSubDAO() : new UmlsDAO();
		for(WordNetWord wnword : convPart.getWordNetWords()){
			for(UMLSWord umlsword : dao.makeUMLSWordsWithSimilarLabel(wnword.getSimpleForm(),convPart.getUMLSWords()))
				if(MlDisambiguator.doesConversationContainSentence(convPart,umlsword.getLabel()))
					convPart.getUMLSWords().put(umlsword.getCUI(), umlsword);
		}
	}
	
	public static UMLSWord makeUmlsWord(String cui){
		UmlsDAOInterface dao = (USE_SUBSET)? new UmlsSubDAO() : new UmlsDAO();
		UMLSWord word = new UMLSWord(cui);
		word.setLabel(dao.getLabel(word.getCUI()));
		word.setType(dao.getType(word.getCUI()));
		word.setDefinition(dao.getDef(word.getCUI()));
		return word;
	}
	
	public static ArrayList<String> getUMLSWordsKeysSortedByNumberOfWords(MlConversationPart convPart){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Iterator<String> it = convPart.getUMLSWords().keySet().iterator();
		String key;
		while(it.hasNext()){
			key = it.next();
			map.put(key, countOccurrences(convPart.getUMLSWords().get(key).getLabel()," "));
		}
		ArrayList<String> ret = new ArrayList<String>();
		ArrayList<Map.Entry> ess = new ArrayList( map.entrySet() );  
		Collections.sort( ess , new Comparator() {  
			public int compare( Object o1 , Object o2 ) {  
		                 Map.Entry e1 = (Map.Entry)o1 ;  
		                 Map.Entry e2 = (Map.Entry)o2 ;  
		                 Integer first = (Integer)e1.getValue();  
		                 Integer second = (Integer)e2.getValue();  
		                 return - first.compareTo( second );  
		             }  
		         });  
		for(Map.Entry es : ess)
			ret.add((String) es.getKey());
		return ret;
	}
	
    public static Integer countOccurrences(String arg1, String arg2) {
    	Integer count = 0;
    	Integer index = 0;
        while ((index = arg1.indexOf(arg2, index)) != -1) {
             ++index;
             ++count;
        }
        return count;
   }
    
	
	
}
