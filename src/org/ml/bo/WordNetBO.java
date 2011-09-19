package org.ml.bo;

import org.ml.core.MlConversationPart;
import org.ml.core.MlWord;
import org.ml.core.WordNetWord;
import org.ml.dao.WordNetDAO;
import org.ml.util.LogHelper;

public class WordNetBO {

	public static String getSimpleVersionOfWord(String label){
		WordNetDAO wordnetDAO = new WordNetDAO();
		return wordnetDAO.getSimpleVersionOfWord(label);
	}
	
	public static void getWordNetWords(MlConversationPart convPart){
		LogHelper.debug("WordnetBO, find wordnet words in conversation part.");
		WordNetDAO wordnetDAO = new WordNetDAO();
		for(MlWord word : convPart.getWords())
			if(wordnetDAO.isInWordNet(word.getLabel()))
				convPart.getWordNetWords().add((wordnetDAO.buildWordNetWord(new WordNetWord(word))));
	}
	
	public static WordNetWord getWordNetWordWithId(MlConversationPart convPart, Integer id){
		for(WordNetWord word : convPart.getWordNetWords())
			if(word.getId() == id)
				return word;
		return null;
	}
				
}
