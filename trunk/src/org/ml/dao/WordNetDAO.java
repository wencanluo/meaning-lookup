package org.ml.dao;

import org.ml.core.MlWord;
import org.ml.core.WordNetWord;
import org.ml.util.Global;
import org.ml.util.LogHelper;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordNetDAO {
	
	static WordNetDatabase database;
	static{
		System.setProperty("wordnet.database.dir", Global.WORDNET_LOCATION);
	}

	public String getSimpleVersionOfWord(String label){
		Synset[] synsets = database.getSynsets(label);
		if(synsets != null && synsets.length > 0 && synsets[0].getWordForms().length > 0)
			return synsets[0].getWordForms()[0];
		return null;

	}
		
	
	public WordNetWord buildWordNetWord(MlWord w){
		LogHelper.debug(this, "Build new WordNetWord out of MlWord");
		WordNetWord word = new WordNetWord(w);
		database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(word.getLabel());
		LogHelper.debug(this, synsets.length+" synsets found");
		for (int i = 0; i < synsets.length; i++)
			word.getSynsets().add( synsets[i]);
		return word;
	}
	
	public boolean isInWordNet(String content){
		LogHelper.debug(this, "Check if "+content+" is in WordNet database.");
		database = WordNetDatabase.getFileInstance();
		if(database.getSynsets(content).length>0)
			return true;
		return false;
	}
	         
}
